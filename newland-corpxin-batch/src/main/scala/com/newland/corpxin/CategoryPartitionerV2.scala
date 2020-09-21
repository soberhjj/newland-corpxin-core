package com.newland.corpxin

import org.apache.spark.Partitioner

/**
 * @Author: sober  2020-09-17 17:00        
 */
class CategoryPartitionerV2 extends Partitioner {
  override def numPartitions: Int = 38

  override def getPartition(key: Any): Int = {
    if (key == "baiduxin_focalPoint_opennotice")
      0
    else if (key == "baiduxin_focalPoint_filinginfo")
      1
    else if (key == "baiduxin_basicData_basicData")
      2
    else if (key == "baiduxin_basicData_holdsData")
      3
    else if (key == "baiduxin_focalPoint_taxviolation")
      4
    else if (key == "baiduxin_focalPoint_simplecancellation")
      5
    else if (key == "baiduxin_focalPoint_terminationcase")
      6
    else if (key == "baiduxin_focalPoint_stockFreeze")
      7
    else if (key == "baiduxin_focalPoint_restrictedConsumer")
      8
    else if (key == "baiduxin_focalPoint_penalties")
      9
    else if (key == "baiduxin_basicData_branchsData")
      10
    else if (key == "baiduxin_basicData_changeRecordData")
      11
    else if (key == "baiduxin_basicData_directorsData")
      12
    else if (key == "baiduxin_basicData_headCompany")
      13
    else if (key == "baiduxin_basicData_investRecordData")
      14
    else if (key == "baiduxin_basicData_shareholdersData")
      15
    else if (key == "baiduxin_basicData_annualReportData")
      16
    else if (key == "baiduxin_compManage_doublecheckup")
      17
    else if (key == "baiduxin_compManage_foodquality")
      18
    else if (key == "baiduxin_compManage_license")
      19
    else if (key == "baiduxin_compManage_quality")
      20
    else if (key == "baiduxin_compManage_randominspection")
      21
    else if (key == "baiduxin_intellectualProperty_copyright")
      22
    else if (key == "baiduxin_intellectualProperty_icpinfo")
      23
    else if (key == "baiduxin_intellectualProperty_mark")
      24
    else if (key == "baiduxin_intellectualProperty_patent")
      25
    else if (key == "baiduxin_focalPoint_equitypledge")
      26
    else if (key == "baiduxin_focalPoint_discredit")
      27
    else if (key == "baiduxin_focalPoint_clearaccount")
      28
    else if (key == "baiduxin_focalPoint_lawWenshu")
      29
    else if (key == "baiduxin_focalPoint_chattelmortgage")
      30
    else if (key == "baiduxin_intellectualProperty_workright")
      31
    else if (key == "baiduxin_focalPoint_executedPerson")
      32
    else if (key == "baiduxin_focalPoint_abnormal")
      33
    else if (key == "baiduxin_focalPoint_getCourtNoticeData")
      34
    else if (key == "baiduxin_focalPoint_illegal")
      35
    else if (key == "baiduxin_focalPoint_judicialauction")
      36
    else
      37
  }
}
