package com.newland.corpxin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.corpxin.model.BasicData;
import com.newland.corpxin.model.BasicInfo;
import com.newland.corpxin.service.MysqlService;
import com.newland.corpxin.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.TaskContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.HasOffsetRanges;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.apache.spark.streaming.kafka010.OffsetRange;

import com.newland.corpxin.conf.Configuration;
import com.newland.corpxin.conf.SqlConstant;
import com.newland.corpxin.model.Offset;
import com.newland.corpxin.service.OffsetQueryService;

/**
 * 实时流处理入口
 *
 * @author Administrator
 *
 */
@Slf4j
public class Main implements Tool,Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception{
		ToolRunner.run(new Main(), args);
	}

	/**
	 * 执行
	 */
	@Override
	public int run(Configuration conf) throws Exception {
		SparkConf sparkConf = new SparkConf();
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		JavaStreamingContext jssc = new JavaStreamingContext(jsc, Durations.seconds(conf.getDuration()));
		JavaInputDStream<ConsumerRecord<String, String>> stream = null;

		OffsetQueryService offsetQueryService = new OffsetQueryService();
		// 从mysql获取上一次的消费记录
		List<Offset> offsets = offsetQueryService.query(conf.getTopic(), conf.getGroupId());
		// 构建kafka参数
		Map<String, Object> kafkaParams = buildKafkaParameters(conf);
		if (offsets == null || offsets.isEmpty()) {
			log.info(String.format("topic[%s] consumer[%s] not found consumer record", conf.getTopic(), conf.getGroupId()));
			stream = KafkaUtils.createDirectStream(jssc, LocationStrategies.PreferConsistent(), ConsumerStrategies.Subscribe(Arrays.asList(conf.getTopic()), kafkaParams));
		} else {
			Map<TopicPartition, Long> fromOffsets = new HashMap<TopicPartition, Long>();
			for (Offset offset : offsets) {
				log.info(String.format("topic[%s] consumer[%s] partition[%s] offset[%s]", offset.getTopic(), conf.getGroupId(), offset.getPartition(), offset.getOffset()));
				fromOffsets.put(new TopicPartition(offset.getTopic(), offset.getPartition()), offset.getOffset());
			}
			stream = KafkaUtils.createDirectStream(jssc, LocationStrategies.PreferConsistent(), ConsumerStrategies.Assign(fromOffsets.keySet(), kafkaParams, fromOffsets));
		}
		// 处理
		process(conf,stream);

		jssc.start();

		try {
			jssc.awaitTermination();
		} catch (InterruptedException e) {
			log.error("awaitTermination", e);
		} finally {
			jssc.stop();
		}

		return 0;
	}

	private void process(Configuration conf,JavaInputDStream<ConsumerRecord<String, String>> stream) {

		final String consumerId = conf.getGroupId();

		stream.foreachRDD(rdd -> {
			OffsetRange[] offsets = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
			for(OffsetRange offset : offsets){
				System.out.println(String.format("prepare process topic[%s] partition[%s] offset[%s]", offset.topic(),offset.partition(),offset.untilOffset()));
			}

			rdd.foreachPartition(tuples -> {
				final OffsetRange offset = offsets[TaskContext.get().partitionId()];
				System.out.println(String.format("current process topic[%s] partition[%s] offset[%s]", offset.topic(),offset.partition(),offset.untilOffset()));

				/* 业务逻辑 */
				MysqlService mysqlService = new MysqlService();
				while(tuples.hasNext()){
					//获取kafka(spider_bxin)的数据
					String message = tuples.next().value();
					JSONObject json = JSONObject.parseObject(message);

					if(!"baiduxin_basicData_basicData".equals(json.getString("xwhat"))){
						continue;
					}

					//获取xcontent数据
					String content = json.getJSONArray("xcontent").getString(0);
					BasicData basicData = JSON.toJavaObject(JSON.parseObject(content), BasicData.class);
					BasicInfo basicInfo = new BasicInfo();
					//basicData拷贝到basicInfo
					BeanUtil.copyProperties(basicData, basicInfo);

					//将营业期限分开为营业开始时间和营业结束时间
					String openTime = basicData.getOpenTime();
					String openTimeSep = "至";
					if(!StringUtil.judgeEmptyOrNull(openTime)){
						basicInfo.setOpenStart(openTime.split(openTimeSep)[0]);
						basicInfo.setOpenEnd(openTime.split(openTimeSep)[1]);
					}

					basicInfo.setLastUpdateTimestamp(json.getLong("xtime"));

					if(StringUtil.judgeEmptyOrNull(basicData.getUnifiedCode())){
						mysqlService.saveBasicInfoError(basicInfo);
					}else {
						mysqlService.saveBasicInfo(basicInfo);
					}


				}
				saveKafkaOffset(offset, consumerId);
			});
		});
	}


	private void saveKafkaOffset(OffsetRange offset, String consumerId) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBConnectionManager.getInstance().getConnection();
			System.out.println("init dbconnection"+DBConnectionManager.getInstance());
			stmt = conn.prepareStatement(SqlConstant.SQL_UPDATE_OFFSET);
			stmt.setString(1, offset.topic());
			stmt.setInt(2, offset.partition());
			stmt.setString(3, consumerId);
			stmt.setLong(4, offset.untilOffset());
			stmt.execute();
		} catch (Exception e) {
			throw e;
		} finally{
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	}



	// 构建kafka参数
	private Map<String, Object> buildKafkaParameters(Configuration conf) {
		Map<String, Object> kafkaParams = new HashMap<String, Object>();
		kafkaParams.put("bootstrap.servers", conf.getBrokers());
		kafkaParams.put("key.deserializer", StringDeserializer.class);
		kafkaParams.put("value.deserializer", StringDeserializer.class);
		kafkaParams.put("auto.offset.reset", "earliest");
		kafkaParams.put("enable.auto.commit", false);
		kafkaParams.put("request.timeout.ms", 100000);
		kafkaParams.put("session.timeout.ms", 10000);
		kafkaParams.put("heartbeat.interval.ms", 6000);
		kafkaParams.put("group.id", conf.getGroupId());
		return kafkaParams;
	}






}
