package com.newland.corpxin

import com.alibaba.fastjson.JSON
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD


/**
 * @Author: sober  2020-07-28 16:39        
 */
object BaiduxinDailyDataMerge {
  def main(args: Array[String]): Unit = {

    val sc: SparkContext = new SparkContext(new SparkConf().setAppName("BaiduxinDailyDataMerge").setMaster("local[3]"))

    //数据输入根路径 -->暂时固定
    //数据输出根路径 -->暂时固定

    //以旧的完整版数据创建RDD
    val rdd1: RDD[String] = sc.textFile("D:\\outputdata.new\\20200724002\\part-00000.txt")
    //以新的数据创建RDD
    val rdd2: RDD[String] = sc.textFile("D:\\inputdata\\20200728\\flume.1595918926666.txt")

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
      val key = values._1
      val lastTimeRecord: List[(String, String, String)] = values._2.toList.sortBy(_._2)(Ordering.String.reverse).take(1)
      val list: List[String] = lastTimeRecord.map(_._3)
      var str: String = ""
      for (s <- list)
        str = str + s
      str
    })

    rdd6.repartition(1).saveAsTextFile("D:\\outputdata\\resaa")
  }

}
