package com.newland.corpxin

import com.alibaba.fastjson.{JSON, JSONArray}
import org.apache.spark.{SparkConf, SparkContext}
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
    val errorXcontentDataPath="obs://data-warehouse/ods/baiduxin/error_format/"+incrementDate

    //以旧的完整版数据创建RDD
    val rdd1: RDD[String] = sc.textFile(fullDataPath)

    //以新的数据创建RDD
    val rdd2: RDD[String] = sc.textFile(incrementDataPath)
    //处理"xcontent"字段值可能出现格式错误的问题
    val dealXcontent: RDD[String] = rdd2.map(line => {
      try {
        val jsonObj = JSON.parseObject(line)
        val arr: JSONArray = jsonObj.getJSONArray("xcontent")
        "success"
      } catch {
        case error: Error => line
        case exception: Exception => line
      }
    })
    val errorXcontent: RDD[String] = dealXcontent.filter(line => line != "success")
    val noErrorXcontent: RDD[String] = rdd2.subtract(errorXcontent)

    val rdd3: RDD[String] = rdd1.union(noErrorXcontent)

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
      val key = values._1
      val lastTimeRecord: List[(String, String, String)] = values._2.toList.sortBy(_._2)(Ordering.String.reverse).take(1)
      val list: List[String] = lastTimeRecord.map(_._3)
      var str: String = ""
      for (s <- list)
        str = str + s
      str
    })

    rdd6.coalesce(10).saveAsTextFile(resultDataPath)
    errorXcontent.coalesce(1).saveAsTextFile(errorXcontentDataPath)
  }
}
