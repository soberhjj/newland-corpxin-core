package com.newland.corpxin.app

import com.newland.corpxin.constant.Constant
import com.newland.corpxin.handler.{DataAssortHandler, DataAssortHandler, DataToHiveHandler, DataToHiveHandler}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}


object BaiduxinETLApp {
  def main(args: Array[String]): Unit = {
    // TODO 初始化Spark相关环境
    val sparkConf: SparkConf = new SparkConf()
      .setAppName("BaiduxinETLToHive")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "com.newland.corpxin.model.MyRegistrator")
//      .set("spark.kryoserializer.buffer.max", "128m")
//      .set("spark.kryoserializer.buffer.mb","10")
      .set("spark.kryo.registrationRequired","false")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    // TODO shell脚本入参日期，如20200731
    val dateTime: String = args(0)

    // TODO 读取obs上的对应日期的文件，并做缓存，为之后复用此rdd做优化
    val originRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime+ "/part-*").persist(StorageLevel.MEMORY_ONLY_SER)

    // TODO 对数据进行分表并入库
    DataAssortHandler.DataAssort(originRDD,dateTime,spark)

    // TODO 关闭连接资源
    sc.stop()

  }

}
