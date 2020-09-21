package com.newland.corpxin

import java.util

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.newland.corpxin.constant.Constant
import com.newland.corpxin.model._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * @Author: sober  2020-09-18 08:48
 */
object BaiduxinETLAppV2 {
  def main(args: Array[String]): Unit = {
    // TODO 初始化Spark相关环境
    val sparkConf: SparkConf = new SparkConf()
      .setAppName("BaiduxinETLToHive")
      //设置序列化方式。spark默认的是org.apache.spark.serializer.JavaSerializer。而我们要修改成org.apache.spark.serializer.KryoSerializer。后者序列化后的数据更小。修改序列化方式可视为一种优化手段。
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "com.newland.corpxin.model.MyRegistrator")
      .set("spark.kryoserializer.buffer.max", "1024m")
      .set("spark.kryo.registrationRequired", "false")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    // TODO shell脚本入参日期，如20200730
    val dateTime: String = args(0)

    import spark.implicits._

    //待处理：判断RDD是否包含元素。如果RDD是空的，那么对该RDD执行相关操作时，程序会不会报错退出？

    //处理baiduxin_focalPoint_opennotice类别的数据
    val opennoticeRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00000")
    opennoticeRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Opennotice = JSON.parseObject(arr.getString(i), classOf[Opennotice])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line)
      .map(opennoticeOBJ => {
      val arrPlaintiff: ArrayBuffer[String] = new ArrayBuffer[String]()
      val arrDefendant: ArrayBuffer[String] = new ArrayBuffer[String]()
      if ((opennoticeOBJ.`plaintifflist` != null && opennoticeOBJ.`plaintifflist`.length() != 0) && (opennoticeOBJ.`defendantlist` != null && opennoticeOBJ.`defendantlist`.length() != 0)) {
        val array1: util.List[String] = JSON.parseArray(opennoticeOBJ.`plaintifflist`, classOf[String])
        val array2: util.List[String] = JSON.parseArray(opennoticeOBJ.`defendantlist`, classOf[String])
        for (i <- 0 until array1.size()) {
          arrPlaintiff.append(array1.get(i))
        }
        for (i <- 0 until array2.size()) {
          arrDefendant.append(array2.get(i))
        }
      }
      Opennotice2(opennoticeOBJ.`corpId`, opennoticeOBJ.`hearingDate`, opennoticeOBJ.`caseNo`, opennoticeOBJ.`caseReason`, opennoticeOBJ.`judge`, opennoticeOBJ.`court`, opennoticeOBJ.`tribunal`, arrPlaintiff.toArray, arrDefendant.toArray, opennoticeOBJ.`ename`, opennoticeOBJ.`pureRole`, opennoticeOBJ.`dataId`, opennoticeOBJ.`content`, opennoticeOBJ.`region`, opennoticeOBJ.`department`, opennoticeOBJ.`author`, opennoticeOBJ.`judgeType`, opennoticeOBJ.`detailUrl`, opennoticeOBJ.`ds`)
    }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_opennotice")

    //处理baiduxin_focalPoint_filinginfo类别的数据
    val filinginfoRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00001")
    filinginfoRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Filinginfo = JSON.parseObject(arr.getString(i), classOf[Filinginfo])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line)
      .map(filinginfoOBJ=>{
        val arrMapPlaintiff: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
        val arrMapDefendant: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
        val map1: mutable.Map[String, String] = mutable.Map[String, String]()
        val map2: mutable.Map[String, String] = mutable.Map[String, String]()
        if ((filinginfoOBJ.`plaintiff` != null && filinginfoOBJ.`plaintiff`.length() != 0) && (filinginfoOBJ.`defendant` != null && filinginfoOBJ.`defendant`.length() != 0)) {
          val array1: JSONArray = JSON.parseArray(filinginfoOBJ.`plaintiff`)
          val array2: JSONArray = JSON.parseArray(filinginfoOBJ.`defendant`)
          for (i <- 0 until array1.size()) {
            map1.clear()
            val obj: JSONObject = array1.getJSONObject(i)
            val keys: util.Iterator[String] = obj.keySet().iterator()
            while (keys.hasNext) {
              val key: String = keys.next()
              map1.put(key, obj.getString(key))
            }
            arrMapPlaintiff.append(map1)
          }
          for (i <- 0 until array2.size()) {
            map2.clear()
            val obj: JSONObject = array2.getJSONObject(i)
            val keys: util.Iterator[String] = obj.keySet().iterator()
            while (keys.hasNext) {
              val key: String = keys.next()
              map2.put(key, obj.getString(key))
            }
            arrMapDefendant.append(map2)
          }
        }
        Filinginfo2(filinginfoOBJ.`corpId`, filinginfoOBJ.`date`, filinginfoOBJ.`caseNumber`, filinginfoOBJ.`court`, arrMapPlaintiff.toArray, arrMapDefendant.toArray, filinginfoOBJ.`filingInfoId`, filinginfoOBJ.`detailUrl`, filinginfoOBJ.`ds`)
      }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_filinginfo")

    //处理baiduxin_basicData_basicData类别的数据
    val basicDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00002")
    basicDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Basicdata = JSON.parseObject(arr.getString(i), classOf[Basicdata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line)
      .map(basicDataOBJ=>{
        val labelsMap: mutable.Map[String, String] = mutable.Map[String, String]()
        if (basicDataOBJ.`labels` != null && basicDataOBJ.`labels`.length() != 0){
          val obj: JSONObject = JSON.parseObject(basicDataOBJ.`labels`)
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            labelsMap.put(key, obj.getString(key))
          }
        }
        Basicdata2(basicDataOBJ.`corpId`, basicDataOBJ.`prevEntName`, basicDataOBJ.`startDate`, basicDataOBJ.`authority`, basicDataOBJ.`legalPerson`, basicDataOBJ.`licenseNumber`, basicDataOBJ.`district`, basicDataOBJ.`scope`, basicDataOBJ.`openStatus`, basicDataOBJ.`taxNo`, basicDataOBJ.`entType`, basicDataOBJ.`annualDate`, basicDataOBJ.`realCapital`, basicDataOBJ.`industry`, basicDataOBJ.`unifiedCode`, basicDataOBJ.`openTime`, basicDataOBJ.`regAddr`, basicDataOBJ.`regCapital`, basicDataOBJ.`orgNo`, basicDataOBJ.`addr`, basicDataOBJ.`aifanfanJumpUrl`, basicDataOBJ.`benchMark`, basicDataOBJ.`claimUrl`, basicDataOBJ.`compNum`, basicDataOBJ.`compNumLink`, basicDataOBJ.`describe`, basicDataOBJ.`districtCode`, basicDataOBJ.`email`, basicDataOBJ.`entLogo`, basicDataOBJ.`entLogoWord`, basicDataOBJ.`entName`, basicDataOBJ.`isClaim`, basicDataOBJ.`isCollected`, labelsMap, basicDataOBJ.`noRzvip`, basicDataOBJ.`oldEntName`, basicDataOBJ.`orgType`, basicDataOBJ.`paidinCapital`, basicDataOBJ.`personId`, basicDataOBJ.`personLink`, basicDataOBJ.`personLogo`, basicDataOBJ.`personLogoWord`, basicDataOBJ.`prinType`, basicDataOBJ.`scale`, basicDataOBJ.`shareLogo`, basicDataOBJ.`telephone`, basicDataOBJ.`regNo`, basicDataOBJ.`website`, basicDataOBJ.`analyse_website`, basicDataOBJ.`ds`)
      }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_basicData")


    //处理baiduxin_basicData_holdsData类别的数据
    val holdsDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00003")
    holdsDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Holdsdata = JSON.parseObject(arr.getString(i), classOf[Holdsdata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_holdsData")

    //处理baiduxin_focalPoint_taxviolation类别的数据
    val taxviolationRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00004")
    taxviolationRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Taxviolation = JSON.parseObject(arr.getString(i), classOf[Taxviolation])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_taxviolation")

    //处理baiduxin_focalPoint_simplecancellation类别的数据
    val simplecancellationRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00005")
    simplecancellationRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Simplecancellation = JSON.parseObject(arr.getString(i), classOf[Simplecancellation])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).map(simplecancellationOBJ=>{
      val arrMapObjections: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val arrMapResult: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val map1: mutable.Map[String, String] = mutable.Map[String, String]()
      val map2: mutable.Map[String, String] = mutable.Map[String, String]()
      if ((simplecancellationOBJ.`gsScaObjections` != null && simplecancellationOBJ.`gsScaObjections`.length() != 0) && (simplecancellationOBJ.`gsScaResult` != null && simplecancellationOBJ.`gsScaResult`.length() != 0)) {
        val array1: JSONArray = JSON.parseArray(simplecancellationOBJ.`gsScaObjections`)
        val array2: JSONArray = JSON.parseArray(simplecancellationOBJ.`gsScaResult`)
        for (i <- 0 until array1.size()) {
          map1.clear()
          val obj: JSONObject = array1.getJSONObject(i)
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            map1.put(key, obj.getString(key))
          }
          arrMapObjections.append(map1)
        }
        for (i <- 0 until array2.size()) {
          map2.clear()
          val obj: JSONObject = array2.getJSONObject(i)
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            map2.put(key, obj.getString(key))
          }
          arrMapResult.append(map2)
        }
      }
      Simplecancellation2(simplecancellationOBJ.`corpId`, simplecancellationOBJ.`entName`, simplecancellationOBJ.`creditNo`, simplecancellationOBJ.`noticePeriodDate`, simplecancellationOBJ.`departMent`, arrMapObjections.toArray, simplecancellationOBJ.`cancelId`, simplecancellationOBJ.`cancelImageUrl`, simplecancellationOBJ.`detailUrl`, arrMapResult.toArray, simplecancellationOBJ.`ds`)
    }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_simplecancellation")

    //处理baiduxin_focalPoint_terminationcase类别的数据
    val terminationcaseRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00006")
    terminationcaseRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Terminationcase = JSON.parseObject(arr.getString(i), classOf[Terminationcase])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_terminationcase")

    //处理baiduxin_focalPoint_stockFreeze类别的数据
    val stockFreezeRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00007")
    stockFreezeRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Stockfreeze = JSON.parseObject(arr.getString(i), classOf[Stockfreeze])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_stockFreeze")

    //处理baiduxin_focalPoint_restrictedConsumer类别的数据
    val restrictedConsumerRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00008")
    restrictedConsumerRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Restrictedconsumer = JSON.parseObject(arr.getString(i), classOf[Restrictedconsumer])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_restrictedConsumer")

    //处理baiduxin_focalPoint_penalties类别的数据
    val penaltiesRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00009")
    penaltiesRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Penalties = JSON.parseObject(arr.getString(i), classOf[Penalties])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_penalties")


    //处理baiduxin_basicData_branchsData类别的数据
    val branchsDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00010")
    branchsDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Branchsdata = JSON.parseObject(arr.getString(i), classOf[Branchsdata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_branchsData")

    //处理baiduxin_basicData_changeRecordData类别的数据
    val changeRecordDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00011")
    changeRecordDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Changerecorddata = JSON.parseObject(arr.getString(i), classOf[Changerecorddata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_changeRecordData")

    //处理baiduxin_basicData_directorsData类别的数据
    val directorsDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00012")
    directorsDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Directorsdata = JSON.parseObject(arr.getString(i), classOf[Directorsdata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_directorsData")

    //处理baiduxin_basicData_headCompany类别的数据
    val headCompanyRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00013")
    headCompanyRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Headcompany = JSON.parseObject(arr.getString(i), classOf[Headcompany])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_headCompany")

    //处理baiduxin_basicData_investRecordData类别的数据
    val investRecordDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00014")
    investRecordDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Investrecorddata = JSON.parseObject(arr.getString(i), classOf[Investrecorddata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_investRecordData")

    //处理baiduxin_basicData_shareholdersData类别的数据
    val shareholdersDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00015")
    shareholdersDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Shareholdersdata = JSON.parseObject(arr.getString(i), classOf[Shareholdersdata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_shareholdersData")

    //处理baiduxin_basicData_annualReportData类别的数据
    val annualReportDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00016")
    annualReportDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Annualreportdata = JSON.parseObject(arr.getString(i), classOf[Annualreportdata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_basicData_annualReportData")

    //处理baiduxin_compManage_doublecheckup类别的数据
    val doublecheckupRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00017")
    doublecheckupRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Doublecheckup = JSON.parseObject(arr.getString(i), classOf[Doublecheckup])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_compManage_doublecheckup")

    //处理baiduxin_compManage_foodquality类别的数据
    val foodqualityRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00018")
    foodqualityRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Foodquality = JSON.parseObject(arr.getString(i), classOf[Foodquality])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line)
      .map(foodqualityOBJ=>{
        val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()
        if (foodqualityOBJ.`detail` != null && foodqualityOBJ.`detail`.length() != 0) {
          val obj: JSONObject = JSON.parseObject(foodqualityOBJ.`detail`)
          detailMap.clear()
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            detailMap.put(key, obj.getString(key))
          }
        }
        Foodquality2(foodqualityOBJ.`corpId`, foodqualityOBJ.`detailUrl`, foodqualityOBJ.`insId`, foodqualityOBJ.`notificationDate`, foodqualityOBJ.`notificationNum`, foodqualityOBJ.`productName`,
          foodqualityOBJ.`qualityId`, foodqualityOBJ.`result`, foodqualityOBJ.`type`, detailMap, foodqualityOBJ.`ds`)
      }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_compManage_foodquality")

    //处理baiduxin_compManage_license类别的数据
    val licenseRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00019")
    licenseRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: License = JSON.parseObject(arr.getString(i), classOf[License])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_compManage_license")

    //处理baiduxin_compManage_quality类别的数据
    val qualityRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00020")
    qualityRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Quality = JSON.parseObject(arr.getString(i), classOf[Quality])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line)
      .map(qualityOBJ=>{
        val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()
        if (qualityOBJ.`detail` != null && qualityOBJ.`detail`.length() != 0) {
          val obj: JSONObject = JSON.parseObject(qualityOBJ.`detail`)
          detailMap.clear()
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            detailMap.put(key, obj.getString(key))
          }
        }
        Quality2(qualityOBJ.`corpId`, qualityOBJ.`samlingYears`, qualityOBJ.`samlingBatch`, qualityOBJ.`productName`, qualityOBJ.`samplingResult`, qualityOBJ.`detailUrl`, qualityOBJ.`insId`, qualityOBJ.`qualityId`, qualityOBJ.`type`, detailMap, qualityOBJ.`ds`)
      }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_compManage_quality")

    //处理baiduxin_compManage_randominspection类别的数据
    val randominspectionRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00021")
    randominspectionRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Randominspection = JSON.parseObject(arr.getString(i), classOf[Randominspection])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_compManage_randominspection")

    //处理baiduxin_intellectualProperty_copyright类别的数据
    val copyrightRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00022")
    copyrightRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Copyright = JSON.parseObject(arr.getString(i), classOf[Copyright])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line)
      .map(copyrightOBJ=>{
        val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()
        if (copyrightOBJ.`detail` != null && copyrightOBJ.`detail`.length() != 0) {
          val obj: JSONObject = JSON.parseObject(copyrightOBJ.`detail`)
          detailMap.clear()
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            detailMap.put(key, obj.getString(key))
          }
        }
        Copyright2(copyrightOBJ.`corpId`, copyrightOBJ.`softwareName`, copyrightOBJ.`shortName`, copyrightOBJ.`batchNum`, copyrightOBJ.`softwareType`, copyrightOBJ.`typeCode`, copyrightOBJ.`regDate`, copyrightOBJ.`softId`, copyrightOBJ.`detailUrl`, detailMap, copyrightOBJ.`ds`)
      }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_intellectualProperty_copyright")

    //处理baiduxin_intellectualProperty_icpinfo类别的数据
    val icpinfoRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00023")
    icpinfoRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Icpinfo = JSON.parseObject(arr.getString(i), classOf[Icpinfo])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line)
      .map(icpinfoOBJ=>{
        val arrHomesite: ArrayBuffer[String] = new ArrayBuffer[String]()
        val arrdomain: ArrayBuffer[String] = new ArrayBuffer[String]()
        if ((icpinfoOBJ.`homeSite` != null && icpinfoOBJ.`homeSite`.length() != 0) && (icpinfoOBJ.`domain` != null && icpinfoOBJ.`domain`.length() != 0)) {
          val array1: util.List[String] = JSON.parseArray(icpinfoOBJ.`homeSite`, classOf[String])
          val array2: util.List[String] = JSON.parseArray(icpinfoOBJ.`domain`, classOf[String])
          for (i <- 0 until array1.size()) {
            arrHomesite.append(array1.get(i))
          }
          for (i <- 0 until array2.size()) {
            arrdomain.append(array2.get(i))
          }
        }
        Icpinfo2(icpinfoOBJ.`corpId`, icpinfoOBJ.`siteName`, arrHomesite.toArray, arrdomain.toArray, icpinfoOBJ.`icpNo`, icpinfoOBJ.`ds`)
      }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_intellectualProperty_icpinfo")

    //处理baiduxin_intellectualProperty_mark类别的数据
    val markRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00024")
    markRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Mark = JSON.parseObject(arr.getString(i), classOf[Mark])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_intellectualProperty_mark")

    //处理baiduxin_intellectualProperty_patent类别的数据
    val patentRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00025")
    patentRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Patent = JSON.parseObject(arr.getString(i), classOf[Patent])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_intellectualProperty_patent")

    //处理baiduxin_focalPoint_equitypledge类别的数据
    val equitypledgeRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00026")
    equitypledgeRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Equitypledge = JSON.parseObject(arr.getString(i), classOf[Equitypledge])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_equitypledge")

    //处理baiduxin_focalPoint_discredit类别的数据
    val discreditRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00027")
    discreditRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Discredit = JSON.parseObject(arr.getString(i), classOf[Discredit])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_discredit")

    //处理baiduxin_focalPoint_clearaccount类别的数据
    val clearaccountRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00028")
    clearaccountRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Clearaccount = JSON.parseObject(arr.getString(i), classOf[Clearaccount])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_clearaccount")

    //处理baiduxin_focalPoint_lawWenshu类别的数据
    val lawWenshuRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00029")
    lawWenshuRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Lawwenshu = JSON.parseObject(arr.getString(i), classOf[Lawwenshu])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_lawWenshu")

    //处理baiduxin_focalPoint_chattelmortgage类别的数据
    val chattelmortgageRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00030")
    chattelmortgageRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Chattelmortgage = JSON.parseObject(arr.getString(i), classOf[Chattelmortgage])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_chattelmortgage")

    //处理baiduxin_intellectualProperty_workright类别的数据
    val workrightRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00031")
    workrightRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Workright = JSON.parseObject(arr.getString(i), classOf[Workright])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_intellectualProperty_workright")

    //处理baiduxin_focalPoint_executedPerson类别的数据
    val executedPersonRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00032")
    executedPersonRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Executedperson = JSON.parseObject(arr.getString(i), classOf[Executedperson])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_executedPerson")

    //处理baiduxin_focalPoint_abnormal类别的数据
    val abnormalRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00033")
    abnormalRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Abnormal = JSON.parseObject(arr.getString(i), classOf[Abnormal])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_abnormal")

    //处理baiduxin_focalPoint_getCourtNoticeData类别的数据
    val getCourtNoticeDataRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00034")
    getCourtNoticeDataRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Getcourtnoticedata = JSON.parseObject(arr.getString(i), classOf[Getcourtnoticedata])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line)
      .map(getCourtNoticeDataOBJ=>{
        val arrMapPeople: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
        val map1: mutable.Map[String, String] = mutable.Map[String, String]()
        if (getCourtNoticeDataOBJ.`people` != null && getCourtNoticeDataOBJ.`people`.length() != 0) {
          val array1: JSONArray = JSON.parseArray(getCourtNoticeDataOBJ.`people`)
          for (i <- 0 until array1.size()) {
            map1.clear()
            val obj: JSONObject = array1.getJSONObject(i)
            val keys: util.Iterator[String] = obj.keySet().iterator()
            while (keys.hasNext) {
              val key: String = keys.next()
              map1.put(key, obj.getString(key))
            }
            arrMapPeople.append(map1)
          }
        }
        Getcourtnoticedata2(getCourtNoticeDataOBJ.`corpId`, getCourtNoticeDataOBJ.date, getCourtNoticeDataOBJ.`type`, getCourtNoticeDataOBJ.`cause`, getCourtNoticeDataOBJ.`courtnoticeId`, getCourtNoticeDataOBJ.`court`, arrMapPeople.toArray, getCourtNoticeDataOBJ.`detailUrl`, getCourtNoticeDataOBJ.`ds`)
      }).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_getCourtNoticeData")

    //处理baiduxin_focalPoint_illegal类别的数据
    val illegalRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00035")
    illegalRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Illegal = JSON.parseObject(arr.getString(i), classOf[Illegal])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_illegal")

    //处理baiduxin_focalPoint_judicialauction类别的数据
    val judicialauctionRDD: RDD[String] = sc.textFile(Constant.SPARK_INPUT_PATH + dateTime + "/part-00036")
    judicialauctionRDD.map(line => {
      val jsonStr: JSONObject = JSON.parseObject(line)
      val xwho: String = jsonStr.getString("xwho")
      val arr: JSONArray = jsonStr.getJSONArray("xcontent")

      for (i <- 0 until arr.size()) yield {
        val data: Judicialauction = JSON.parseObject(arr.getString(i), classOf[Judicialauction])
        data.`corpId` = xwho
        data.`ds` = dateTime
        data
      }
    }).flatMap(line=>line).toDF().write.mode("overwrite").insertInto("ods_baiduxin_db.ods_baiduxin_focalPoint_judicialauction")

  }
}
