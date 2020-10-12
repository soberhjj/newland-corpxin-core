package com.newland.corpxin.constant


/**
 * 项目中用到的常量
 */
object Constant {

  // ETL读取路径
  val SPARK_INPUT_PATH = "obs://data-warehouse/ods/baiduxin/origin_events/"

  //生产环境
//  val SPARK_INPUT_PATH = "hdfs://master:9000/ods/baiduxin/origin_events/"


  // Hive数据库
  val DB = "ods_baiduxin_db"

  //测试环境
//  val DB = "default"

}
