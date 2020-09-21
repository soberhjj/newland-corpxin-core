package com.newland.corpxin

import org.apache.spark.Partitioner

import scala.collection.mutable


/**
 * @Author: sober  2020-09-17 13:45
 *
 *          自定义一个分区器：该分区器按照类别（共37个类别）将数据分别写进对应的分区，一个类别对应一个分区，所以分区个数是37个
 *          类别如下：baiduxin_focalPoint_opennotice
 *          baiduxin_focalPoint_filinginfo
 *          baiduxin_basicData_basicData
 *          baiduxin_basicData_holdsData
 *          baiduxin_focalPoint_taxviolation
 *          baiduxin_focalPoint_simplecancellation
 *          baiduxin_focalPoint_terminationcase
 *          baiduxin_focalPoint_stockFreeze
 *          baiduxin_focalPoint_restrictedConsumer
 *          baiduxin_focalPoint_penalties
 *          baiduxin_basicData_branchsData
 *          baiduxin_basicData_changeRecordData
 *          baiduxin_basicData_directorsData
 *          baiduxin_basicData_headCompany
 *          baiduxin_basicData_investRecordData
 *          baiduxin_basicData_shareholdersData
 *          baiduxin_basicData_annualReportData
 *          baiduxin_compManage_doublecheckup
 *          baiduxin_compManage_foodquality
 *          baiduxin_compManage_license
 *          baiduxin_compManage_quality
 *          baiduxin_compManage_randominspection
 *          baiduxin_intellectualProperty_copyright
 *          baiduxin_intellectualProperty_icpinfo
 *          baiduxin_intellectualProperty_mark
 *          baiduxin_intellectualProperty_patent
 *          baiduxin_focalPoint_equitypledge
 *          baiduxin_focalPoint_discredit
 *          baiduxin_focalPoint_clearaccount
 *          baiduxin_focalPoint_lawWenshu
 *          baiduxin_focalPoint_chattelmortgage
 *          baiduxin_intellectualProperty_workright
 *          baiduxin_focalPoint_executedPerson
 *          baiduxin_focalPoint_abnormal
 *          baiduxin_focalPoint_getCourtNoticeData
 *          baiduxin_focalPoint_illegal
 *          baiduxin_focalPoint_judicialauction
 */
class CategoryPartitioner extends Partitioner {

  //所有类别，共37个
  val categorys: Array[String] = Array("baiduxin_focalPoint_opennotice", "baiduxin_focalPoint_filinginfo", "baiduxin_basicData_basicData",
    "baiduxin_basicData_holdsData", "baiduxin_focalPoint_taxviolation", "baiduxin_focalPoint_simplecancellation",
    "baiduxin_focalPoint_terminationcase", "baiduxin_focalPoint_stockFreeze", "baiduxin_focalPoint_restrictedConsumer",
    "baiduxin_focalPoint_penalties", "baiduxin_basicData_branchsData", "baiduxin_basicData_changeRecordData",
    "baiduxin_basicData_directorsData", "baiduxin_basicData_headCompany", "baiduxin_basicData_investRecordData",
    "baiduxin_basicData_shareholdersData", "baiduxin_basicData_annualReportData", "baiduxin_compManage_doublecheckup",
    "baiduxin_compManage_foodquality", "baiduxin_compManage_license", "baiduxin_compManage_quality",
    "baiduxin_compManage_randominspection", "baiduxin_intellectualProperty_copyright", "baiduxin_intellectualProperty_icpinfo",
    "baiduxin_intellectualProperty_mark", "baiduxin_intellectualProperty_patent", "baiduxin_focalPoint_equitypledge",
    "baiduxin_focalPoint_discredit", "baiduxin_focalPoint_clearaccount", "baiduxin_focalPoint_lawWenshu",
    "baiduxin_focalPoint_chattelmortgage", "baiduxin_intellectualProperty_workright", "baiduxin_focalPoint_executedPerson",
    "baiduxin_focalPoint_abnormal", "baiduxin_focalPoint_getCourtNoticeData", "baiduxin_focalPoint_illegal",
    "baiduxin_focalPoint_judicialauction")

  //声明一个map用于存放 类别与分区标号的对应关系
  val keyToCategory = new mutable.HashMap[String, Int]()

  //i是分区标号，以0作为初始值
  var i = 0

  //设置类别与分区标号的对应关系
  for (category <- categorys) {
    keyToCategory(category) = i
    i += 1
  }

  //分区数量
  override def numPartitions: Int = 37

  //根据键值对RDD的传入的key计算分区编号
  override def getPartition(key: Any): Int = {
    keyToCategory(key.toString)
  }
}
