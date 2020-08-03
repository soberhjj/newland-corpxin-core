package com.newland.corpxin.app

import com.newland.corpxin.model._
import com.newland.corpxin.constant.Constant
import com.newland.corpxin.handler.{DataAssortHandler, DataToHiveHandler}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object BaiduxinETLApp {
  def main(args: Array[String]): Unit = {

    // 初始化Spark相关环境
    val sparkConf: SparkConf = new SparkConf().setAppName("BaiduxinETLToHive")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    // shell脚本入参日期，如20200731
    val dateTime:String = args(0)

    // 读取obs上的对应日期的文件，并做缓存，为之后复用此rdd做优化
    val originRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime+ "/part-*").cache()

    /*
      非复杂结构 数据分类、入库
    */
    val basicRDD: RDD[List[(String, Basicdata)]] = DataAssortHandler.basicHandler(originRDD,dateTime)
    DataToHiveHandler.basic(basicRDD, spark)

    val holdRDD: RDD[List[(String, Holdsdata)]] = DataAssortHandler.holdHandler(originRDD,dateTime)
    DataToHiveHandler.hold(holdRDD, spark)

    val branchRDD: RDD[List[(String, Branchsdata)]] = DataAssortHandler.branchHandler(originRDD,dateTime)
    DataToHiveHandler.branch(branchRDD, spark)

    val changerecordRDD: RDD[List[(String, Changerecorddata)]] = DataAssortHandler.changerecordHandler(originRDD,dateTime)
    DataToHiveHandler.changerecord(changerecordRDD, spark)

    val directorsRDD: RDD[List[(String, Directorsdata)]] = DataAssortHandler.directorsHandler(originRDD,dateTime)
    DataToHiveHandler.directorsdata(directorsRDD, spark)

    val headcompanyRDD: RDD[List[(String, Headcompany)]] = DataAssortHandler.headcompanyHandler(originRDD,dateTime)
    DataToHiveHandler.headcompany(headcompanyRDD, spark)

    val investrecordRDD: RDD[List[(String, Investrecorddata)]] = DataAssortHandler.investrecordHandler(originRDD,dateTime)
    DataToHiveHandler.investrecord(investrecordRDD, spark)

    val shareholdersRDD: RDD[List[(String, Shareholdersdata)]] = DataAssortHandler.shareholdersHandler(originRDD,dateTime)
    DataToHiveHandler.shareholders(shareholdersRDD, spark)

    val annualReportRDD: RDD[List[(String, Annualreportdata)]] = DataAssortHandler.annualreportHandler(originRDD,dateTime)
    DataToHiveHandler.annualreport(annualReportRDD, spark)

    val doublecheckupRDD: RDD[List[(String, Doublecheckup)]] = DataAssortHandler.doublecheckupHandler(originRDD,dateTime)
    DataToHiveHandler.doublecheckup(doublecheckupRDD, spark)

    val licenseRDD: RDD[List[(String, License)]] = DataAssortHandler.licenseHandler(originRDD,dateTime)
    DataToHiveHandler.license(licenseRDD, spark)

    val randominspectionRDD: RDD[List[(String, Randominspection)]] = DataAssortHandler.randominspectionHandler(originRDD,dateTime)
    DataToHiveHandler.randominspection(randominspectionRDD, spark)

    val markRDD: RDD[List[(String, Mark)]] = DataAssortHandler.markHandler(originRDD,dateTime)
    DataToHiveHandler.mark(markRDD, spark)

    val patentRDD: RDD[List[(String, Patent)]] = DataAssortHandler.patentHandler(originRDD,dateTime)
    DataToHiveHandler.patent(patentRDD, spark)

    val workrightRDD: RDD[List[(String, Workright)]] = DataAssortHandler.workrightHandler(originRDD,dateTime)
    DataToHiveHandler.workright(workrightRDD, spark)

    val abnormalRDD: RDD[List[(String, Abnormal)]] = DataAssortHandler.abnormalHandler(originRDD,dateTime)
    DataToHiveHandler.abnormal(abnormalRDD, spark)

    val chattelmortgageRDD: RDD[List[(String, Chattelmortgage)]] = DataAssortHandler.chattelmortgageHandler(originRDD,dateTime)
    DataToHiveHandler.chattelmortgage(chattelmortgageRDD, spark)

    val clearaccountRDD: RDD[List[(String, Clearaccount)]] = DataAssortHandler.clearaccountHandler(originRDD,dateTime)
    DataToHiveHandler.clearaccount(clearaccountRDD, spark)

    val discreditRDD: RDD[List[(String, Discredit)]] = DataAssortHandler.discreditHandler(originRDD,dateTime)
    DataToHiveHandler.discredit(discreditRDD, spark)

    val equitypledgeRDD: RDD[List[(String, Equitypledge)]] = DataAssortHandler.equitypledgeHandler(originRDD,dateTime)
    DataToHiveHandler.equitypledge(equitypledgeRDD, spark)

    val executedpersonRDD: RDD[List[(String, Executedperson)]] = DataAssortHandler.executedpersonHandler(originRDD,dateTime)
    DataToHiveHandler.executedperson(executedpersonRDD, spark)

    val illegalRDD: RDD[List[(String, Illegal)]] = DataAssortHandler.illegalHandler(originRDD,dateTime)
    DataToHiveHandler.illegal(illegalRDD, spark)

    val judicialauctionRDD: RDD[List[(String, Judicialauction)]] = DataAssortHandler.judicialauctionHandler(originRDD,dateTime)
    DataToHiveHandler.judicialauction(judicialauctionRDD, spark)

    val lawWenshuRDD: RDD[List[(String, Lawwenshu)]] = DataAssortHandler.lawWenshuHandler(originRDD,dateTime)
    DataToHiveHandler.lawwenshu(lawWenshuRDD, spark)

    val penaltiesRDD: RDD[List[(String, Penalties)]] = DataAssortHandler.penaltiesHandler(originRDD,dateTime)
    DataToHiveHandler.penalties(penaltiesRDD, spark)

    val restrictedconsumerRDD: RDD[List[(String, Restrictedconsumer)]] = DataAssortHandler.restrictedConsumerHandler(originRDD,dateTime)
    DataToHiveHandler.restrictedconsumer(restrictedconsumerRDD, spark)

    val stockFreezeRDD: RDD[List[(String, Stockfreeze)]] = DataAssortHandler.stockFreezeHandler(originRDD,dateTime)
    DataToHiveHandler.stockfreeze(stockFreezeRDD, spark)

    val terminationcaseRDD: RDD[List[(String, Terminationcase)]] = DataAssortHandler.terminationcaseHandler(originRDD,dateTime)
    DataToHiveHandler.terminationcase(terminationcaseRDD, spark)

    val taxviolationRDD: RDD[List[(String, Taxviolation)]] = DataAssortHandler.taxviolationHandler(originRDD,dateTime)
    DataToHiveHandler.taxviolation(taxviolationRDD, spark)

    /*
     复杂结构 数据分类、入库
    */
    val filingRDD: RDD[List[(String, Filinginfo)]] = DataAssortHandler.filinginfoHandler(originRDD,dateTime)
    DataToHiveHandler.filing(filingRDD, spark)

    val foodqualityRDD: RDD[List[(String, Foodquality)]] = DataAssortHandler.foodqualityHandler(originRDD,dateTime)
    DataToHiveHandler.foodquality(foodqualityRDD, spark)

    val qualityRDD: RDD[List[(String, Quality)]] = DataAssortHandler.qualityHandler(originRDD,dateTime)
    DataToHiveHandler.quality(qualityRDD, spark)

    val copyrightRDD: RDD[List[(String, Copyright)]] = DataAssortHandler.copyrightHandler(originRDD,dateTime)
    DataToHiveHandler.copyright(copyrightRDD, spark)

    val icpinfoRDD: RDD[List[(String, Icpinfo)]] = DataAssortHandler.icpinfoHandler(originRDD,dateTime)
    DataToHiveHandler.icpinfo(icpinfoRDD, spark)

    val getcourtnoticedataRDD: RDD[List[(String, Getcourtnoticedata)]] = DataAssortHandler.getcourtnoticedataHandler(originRDD,dateTime)
    DataToHiveHandler.getcourtnoticedata(getcourtnoticedataRDD, spark)

    val opennoticeRDD: RDD[List[(String, Opennotice)]] = DataAssortHandler.opennoticeHandler(originRDD,dateTime)
    DataToHiveHandler.opennotice(opennoticeRDD, spark)

    val simplecancellationRDD: RDD[List[(String, Simplecancellation)]] = DataAssortHandler.simplecancellationHandler(originRDD,dateTime)
    DataToHiveHandler.simplecancellation(simplecancellationRDD, spark)

    // 关闭连接资源
    sc.stop()

  }

}
