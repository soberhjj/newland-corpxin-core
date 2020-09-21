package com.newland.corpxin

import com.alibaba.fastjson.JSON
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @Author: sober  2020-07-28 16:39
 */
object BaiduxinDailyDataMerge {
  def main(args: Array[String]): Unit = {

    val sc: SparkContext = SparkSession.builder().appName("BaiduxinDailyDataMerge").getOrCreate().sparkContext

    val incrementDate=args(0)
    val fullDate=args(1)

    val incrementDataPath="obs://data-warehouse/staging/baiduxin/origin_events/"+incrementDate+"/flume.*"
    val fullDataPath="obs://data-warehouse/ods/baiduxin/origin_events/"+fullDate+"/part-*"
    val resultDataPath="obs://data-warehouse/ods/baiduxin/origin_events/"+incrementDate

    //以旧的完整版数据创建RDD
    val rdd1: RDD[String] = sc.textFile(fullDataPath)

    //以新的数据创建RDD
    val rdd2: RDD[String] = sc.textFile(incrementDataPath)

    val rdd3: RDD[String] = rdd1.union(rdd2)

    val rdd4: RDD[(String, String, String)] = rdd3.map(line => {
      val jsonObj = JSON.parseObject(line)
      val xwhat = jsonObj.get("xwhat")
      val xwho = jsonObj.get("xwho")
      val xtime = "" + jsonObj.get("xtime")
      val key: String = "" + xwhat + xwho
      (key, xtime, line)
    })

    val rdd5: RDD[(String, Iterable[(String, String, String)])] = rdd4.groupBy(_._1)

    val rdd6: RDD[String] = rdd5.map(values => {
      val lastTimeRecord: List[(String, String, String)] = values._2.toList.sortBy(_._2)(Ordering.String.reverse).take(1)
      val list: List[String] = lastTimeRecord.map(_._3)
      var str: String = ""
      for (s <- list)
        str = str + s
      str
    })

    val rdd7: RDD[(String, String)] = rdd6.map(line => {
      val jsonObj = JSON.parseObject(line)
      val xwhat = jsonObj.get("xwhat")
      val key: String = "" + xwhat
      (key, line)
    })

    //根据类别进行重分区，重分区后的rdd是键值对rdd，其中key是类别，value是完整数据，把value取出即可
    val rdd8: RDD[(String, String)] = rdd7.partitionBy(new CategoryPartitionerV2)

    val rdd9: RDD[String] = rdd8.map(line => line._2)

    rdd9.saveAsTextFile(resultDataPath)

  }
}
