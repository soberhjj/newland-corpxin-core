package com.newland.corpxin.handler

import com.newland.corpxin.model._
import java.util

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.newland.corpxin.constant.Constant
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * 将分类好的数据按日期分区写入Hive表,同时将部分字段类型转为Hive表定义好的结构,如map，array...
 */
object DataToHiveHandler {

  /*
  立案信息
   */
  def filing(list: List[(String, Filinginfo)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val arrMapPlaintiff: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val arrMapDefendant: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val map1: mutable.Map[String, String] = mutable.Map[String, String]()
      val map2: mutable.Map[String, String] = mutable.Map[String, String]()

      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Filinginfo] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if ((bean.`plaintiff` != null && bean.`plaintiff`.length() != 0) && (bean.`defendant` != null && bean.`defendant`.length() != 0)) {
          arrMapPlaintiff.clear()
          arrMapDefendant.clear()
          val array1: JSONArray = JSON.parseArray(bean.`plaintiff`)
          val array2: JSONArray = JSON.parseArray(bean.`defendant`)
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
        Filinginfo2(bean.`corpId`, bean.`date`, bean.`caseNumber`, bean.`court`, arrMapPlaintiff.toArray, arrMapDefendant.toArray, bean.`filingInfoId`, bean.`detailUrl`, bean.`ds`)
      }.toDF()

      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
  基本信息
   */
  def basic(list: List[(String, Basicdata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val labelsMap: mutable.Map[String, String] = mutable.Map[String, String]()
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Basicdata] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if (bean.`labels` != null && bean.`labels`.length() != 0) {
          val obj: JSONObject = JSON.parseObject(bean.`labels`)
          labelsMap.clear()
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            labelsMap.put(key, obj.getString(key))
          }
        }
        Basicdata2(bean.`corpId`, bean.`prevEntName`, bean.`startDate`, bean.`authority`, bean.`legalPerson`, bean.`licenseNumber`, bean.`district`, bean.`scope`, bean.`openStatus`, bean.`taxNo`, bean.`entType`, bean.`annualDate`, bean.`realCapital`, bean.`industry`, bean.`unifiedCode`, bean.`openTime`, bean.`regAddr`, bean.`regCapital`, bean.`orgNo`, bean.`addr`, bean.`aifanfanJumpUrl`, bean.`benchMark`, bean.`claimUrl`, bean.`compNum`, bean.`compNumLink`, bean.`describe`, bean.`districtCode`, bean.`email`, bean.`entLogo`, bean.`entLogoWord`, bean.`entName`, bean.`isClaim`, bean.`isCollected`, labelsMap, bean.`noRzvip`, bean.`oldEntName`, bean.`orgType`, bean.`paidinCapital`, bean.`personId`, bean.`personLink`, bean.`personLogo`, bean.`personLogoWord`, bean.`prinType`, bean.`scale`, bean.`shareLogo`, bean.`telephone`, bean.`regNo`, bean.`website`, bean.`analyse_website`, bean.`ds`)
      }.toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
  控股企业
   */
  def hold(list: List[(String, Holdsdata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
分支机构
 */
  def branch(list: List[(String, Branchsdata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
变更记录
*/
  def changerecord(list: List[(String, Changerecorddata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
主要人员
*/
  def directorsdata(list: List[(String, Directorsdata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
总公司
*/
  def headcompany(list: List[(String, Headcompany)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
对外投资
*/
  def investrecord(list: List[(String, Investrecorddata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
股东信息
*/
  def shareholders(list: List[(String, Shareholdersdata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
企业年报
*/
  def annualreport(list: List[(String, Annualreportdata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
双随机抽检
*/
  def doublecheckup(list: List[(String, Doublecheckup)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
食品抽检
*/
  def foodquality(list: List[(String, Foodquality)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Foodquality] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if (bean.`detail` != null && bean.`detail`.length() != 0) {
          val obj: JSONObject = JSON.parseObject(bean.`detail`)
          detailMap.clear()
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            detailMap.put(key, obj.getString(key))
          }
        }
        Foodquality2(bean.`corpId`, bean.`detailUrl`, bean.`insId`, bean.`notificationDate`, bean.`notificationNum`, bean.`productName`,
          bean.`qualityId`, bean.`result`, bean.`type`, detailMap, bean.`ds`)
      }.toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
行政许可
*/
  def license(list: List[(String, License)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
质量监督检查
*/
  def quality(list: List[(String, Quality)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Quality] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if (bean.`detail` != null && bean.`detail`.length() != 0) {
          val obj: JSONObject = JSON.parseObject(bean.`detail`)
          detailMap.clear()
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            detailMap.put(key, obj.getString(key))
          }
        }
        Quality2(bean.`corpId`, bean.`samlingYears`, bean.`samlingBatch`, bean.`productName`, bean.`samplingResult`, bean.`detailUrl`, bean.`insId`, bean.`qualityId`, bean.`type`, detailMap, bean.`ds`)
      }.toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
抽查检查
*/
  def randominspection(list: List[(String, Randominspection)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
软件著作权信息
*/
  def copyright(list: List[(String, Copyright)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Copyright] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if (bean.`detail` != null && bean.`detail`.length() != 0) {
          val obj: JSONObject = JSON.parseObject(bean.`detail`)
          detailMap.clear()
          val keys: util.Iterator[String] = obj.keySet().iterator()
          while (keys.hasNext) {
            val key: String = keys.next()
            detailMap.put(key, obj.getString(key))
          }
        }
        Copyright2(bean.`corpId`, bean.`softwareName`, bean.`shortName`, bean.`batchNum`, bean.`softwareType`, bean.`typeCode`, bean.`regDate`, bean.`softId`, bean.`detailUrl`, detailMap, bean.`ds`)
      }.toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
网站备案
*/
  def icpinfo(list: List[(String, Icpinfo)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val arrHomesite: ArrayBuffer[String] = new ArrayBuffer[String]()
      val arrdomain: ArrayBuffer[String] = new ArrayBuffer[String]()
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Icpinfo] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if ((bean.`homeSite` != null && bean.`homeSite`.length() != 0) && (bean.`domain` != null && bean.`domain`.length() != 0)) {
          arrHomesite.clear()
          arrdomain.clear()
          val array1: util.List[String] = JSON.parseArray(bean.`homeSite`, classOf[String])
          val array2: util.List[String] = JSON.parseArray(bean.`domain`, classOf[String])
          for (i <- 0 until array1.size()) {
            arrHomesite.append(array1.get(i))
          }
          for (i <- 0 until array2.size()) {
            arrdomain.append(array2.get(i))
          }
        }
        Icpinfo2(bean.`corpId`, bean.`siteName`, arrHomesite.toArray, arrdomain.toArray, bean.`icpNo`, bean.`ds`)
      }.toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
商标信息
*/
  def mark(list: List[(String, Mark)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
专利信息
*/
  def patent(list: List[(String, Patent)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
作品著作权
*/
  def workright(list: List[(String, Workright)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
经营异常
*/
  def abnormal(list: List[(String, Abnormal)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
动产抵押
*/
  def chattelmortgage(list: List[(String, Chattelmortgage)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
清算组信息
*/
  def clearaccount(list: List[(String, Clearaccount)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
失信被执行人
*/
  def discredit(list: List[(String, Discredit)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
股权出质
*/
  def equitypledge(list: List[(String, Equitypledge)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
被执行人
*/
  def executedperson(list: List[(String, Executedperson)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
法院公告
*/
  def getcourtnoticedata(list: List[(String, Getcourtnoticedata)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val arrMapPeople: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val map1: mutable.Map[String, String] = mutable.Map[String, String]()
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Getcourtnoticedata] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if (bean.`people` != null && bean.`people`.length() != 0) {
          arrMapPeople.clear()
          val array1: JSONArray = JSON.parseArray(bean.`people`)
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
        Getcourtnoticedata2(bean.`corpId`, bean.date, bean.`type`, bean.`cause`, bean.`courtnoticeId`, bean.`court`, arrMapPeople.toArray, bean.`detailUrl`, bean.`ds`)
      }.toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
严重违法
*/
  def illegal(list: List[(String, Illegal)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
司法拍卖
*/
  def judicialauction(list: List[(String, Judicialauction)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
裁判文书
*/
  def lawwenshu(list: List[(String, Lawwenshu)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
开庭公告
*/
  def opennotice(list: List[(String, Opennotice)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val arrPlaintiff: ArrayBuffer[String] = new ArrayBuffer[String]()
      val arrDefendant: ArrayBuffer[String] = new ArrayBuffer[String]()
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Opennotice] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if ((bean.`plaintifflist` != null && bean.`plaintifflist`.length() != 0) && (bean.`defendantlist` != null && bean.`defendantlist`.length() != 0)) {
          arrPlaintiff.clear()
          arrDefendant.clear()
          val array1: util.List[String] = JSON.parseArray(bean.`plaintifflist`, classOf[String])
          val array2: util.List[String] = JSON.parseArray(bean.`defendantlist`, classOf[String])
          for (i <- 0 until array1.size()) {
            arrPlaintiff.append(array1.get(i))
          }
          for (i <- 0 until array2.size()) {
            arrDefendant.append(array2.get(i))
          }
        }
        Opennotice2(bean.`corpId`, bean.`hearingDate`, bean.`caseNo`, bean.`caseReason`, bean.`judge`, bean.`court`, bean.`tribunal`, arrPlaintiff.toArray, arrDefendant.toArray, bean.`ename`, bean.`pureRole`, bean.`dataId`, bean.`content`, bean.`region`, bean.`department`, bean.`author`, bean.`judgeType`, bean.`detailUrl`, bean.`ds`)
      }.toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
行政处罚
*/
  def penalties(list: List[(String, Penalties)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
限制高消费
*/
  def restrictedconsumer(list: List[(String, Restrictedconsumer)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
简易注销公告
*/
  def simplecancellation(list: List[(String, Simplecancellation)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val arrMapObjections: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val arrMapResult: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val map1: mutable.Map[String, String] = mutable.Map[String, String]()
      val map2: mutable.Map[String, String] = mutable.Map[String, String]()
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val data: RDD[Simplecancellation] = spark.sparkContext.parallelize(list).map(_._2)
      val df = data.map { bean =>
        if ((bean.`gsScaObjections` != null && bean.`gsScaObjections`.length() != 0) && (bean.`gsScaResult` != null && bean.`gsScaResult`.length() != 0)) {
          arrMapObjections.clear()
          arrMapResult.clear()
          val array1: JSONArray = JSON.parseArray(bean.`gsScaObjections`)
          val array2: JSONArray = JSON.parseArray(bean.`gsScaResult`)
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
        Simplecancellation2(bean.`corpId`, bean.`entName`, bean.`creditNo`, bean.`noticePeriodDate`, bean.`departMent`, arrMapObjections.toArray, bean.`cancelId`, bean.`cancelImageUrl`, bean.`detailUrl`, arrMapResult.toArray, bean.`ds`)
      }.toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
税务违法
*/
  def taxviolation(list: List[(String, Taxviolation)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
股权冻结
*/
  def stockfreeze(list: List[(String, Stockfreeze)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
终本案件
*/
  def terminationcase(list: List[(String, Terminationcase)], spark: SparkSession) = {
    if (list.size > 0) {
      import spark.implicits._
      val tableName: String = Constant.DB + ".ods_" + list.head._1
      val df: DataFrame = spark.sparkContext.parallelize(list).map(_._2).toDF()
      df.write.mode("overwrite").insertInto(tableName)
    }
  }
}
