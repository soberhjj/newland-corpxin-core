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
 * 将分类好的数据按日期分区存入Hive表
 */
object DataToHiveHandler {

  /*
  立案信息
   */
  def filing(dataRDD: RDD[List[(String, Filinginfo)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val arrMapPlaintiff: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val arrMapDefendant: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val map1: mutable.Map[String, String] = mutable.Map[String, String]()
      val map2: mutable.Map[String, String] = mutable.Map[String, String]()

      val data: RDD[(String, Filinginfo)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        arrMapPlaintiff.clear()
        arrMapDefendant.clear()
        val array1: JSONArray = JSON.parseArray(x._2.`plaintiff`)
        val array2: JSONArray = JSON.parseArray(x._2.`defendant`)
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
        Filinginfo2(x._2.`corpId`, x._2.`date`, x._2.`caseNumber`, x._2.`court`, arrMapPlaintiff.toArray, arrMapDefendant.toArray, x._2.`filingInfoId`, x._2.`detailUrl`, x._2.`ds`)
      }.toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
  基本信息
   */
  def basic(dataRDD: RDD[List[(String, Basicdata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val labelsMap: mutable.Map[String, String] = mutable.Map[String, String]()

      val data: RDD[(String, Basicdata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        val bean: Basicdata = x._2
        val obj: JSONObject = JSON.parseObject(bean.`labels`)
        labelsMap.clear()
        val keys: util.Iterator[String] = obj.keySet().iterator()
        while (keys.hasNext) {
          val key: String = keys.next()
          labelsMap.put(key, obj.getString(key))
        }
        Basicdata2(bean.`corpId`, bean.`prevEntName`, bean.`startDate`, bean.`authority`, bean.`legalPerson`, bean.`licenseNumber`, bean.`district`, bean.`scope`, bean.`openStatus`, bean.`taxNo`, bean.`entType`, bean.`annualDate`, bean.`realCapital`, bean.`industry`, bean.`unifiedCode`, bean.`openTime`, bean.`regAddr`, bean.`regCapital`, bean.`orgNo`, bean.`addr`, bean.`aifanfanJumpUrl`, bean.`benchMark`, bean.`claimUrl`, bean.`compNum`, bean.`compNumLink`, bean.`describe`, bean.`districtCode`, bean.`email`, bean.`entLogo`, bean.`entLogoWord`, bean.`entName`, bean.`isClaim`, bean.`isCollected`, labelsMap, bean.`noRzvip`, bean.`oldEntName`, bean.`orgType`, bean.`paidinCapital`, bean.`personId`, bean.`personLink`, bean.`personLogo`, bean.`personLogoWord`, bean.`prinType`, bean.`scale`, bean.`shareLogo`, bean.`telephone`, bean.`regNo`, bean.`website`, bean.`analyse_website`, bean.`ds`)
      }.toDF()

//       df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
  控股企业
   */
  def hold(dataRDD: RDD[List[(String, Holdsdata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Holdsdata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
分支机构
 */
  def branch(dataRDD: RDD[List[(String, Branchsdata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Branchsdata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
变更记录
*/
  def changerecord(dataRDD: RDD[List[(String, Changerecorddata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Changerecorddata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
主要人员
*/
  def directorsdata(dataRDD: RDD[List[(String, Directorsdata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Directorsdata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
总公司
*/
  def headcompany(dataRDD: RDD[List[(String, Headcompany)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Headcompany)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
对外投资
*/
  def investrecord(dataRDD: RDD[List[(String, Investrecorddata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Investrecorddata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
股东信息
*/
  def shareholders(dataRDD: RDD[List[(String, Shareholdersdata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Shareholdersdata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
企业年报
*/
  def annualreport(dataRDD: RDD[List[(String, Annualreportdata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Annualreportdata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
双随机抽检
*/
  def doublecheckup(dataRDD: RDD[List[(String, Doublecheckup)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Doublecheckup)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
食品抽检
*/
  def foodquality(dataRDD: RDD[List[(String, Foodquality)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()

      val data: RDD[(String, Foodquality)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        val bean: Foodquality = x._2
        val obj: JSONObject = JSON.parseObject(bean.`detail`)
        detailMap.clear()
        val keys: util.Iterator[String] = obj.keySet().iterator()
        while (keys.hasNext) {
          val key: String = keys.next()
          detailMap.put(key, obj.getString(key))
        }
        Foodquality2(bean.`corpId`, bean.`detailUrl`, bean.`insId`, bean.`notificationDate`, bean.`notificationNum`, bean.`productName`,
          bean.`qualityId`, bean.`result`, bean.`type`, detailMap, bean.`ds`)
      }.toDF()

      // df.show(false)
      //df.printSchema()

      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
行政许可
*/
  def license(dataRDD: RDD[List[(String, License)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, License)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
质量监督检查
*/
  def quality(dataRDD: RDD[List[(String, Quality)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()

      val data: RDD[(String, Quality)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        val bean: Quality = x._2
        val obj: JSONObject = JSON.parseObject(bean.`detail`)
        detailMap.clear()
        val keys: util.Iterator[String] = obj.keySet().iterator()
        while (keys.hasNext) {
          val key: String = keys.next()
          detailMap.put(key, obj.getString(key))
        }
        Quality2(bean.`corpId`, bean.`samlingYears`, bean.`samlingBatch`, bean.`productName`, bean.`samplingResult`, bean.`detailUrl`, bean.`insId`, bean.`qualityId`, bean.`type`, detailMap, bean.`ds`)
      }.toDF()

      // df.show(false)

      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
抽查检查
*/
  def randominspection(dataRDD: RDD[List[(String, Randominspection)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Randominspection)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
软件著作权信息
*/
  def copyright(dataRDD: RDD[List[(String, Copyright)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val detailMap: mutable.Map[String, String] = mutable.Map[String, String]()

      val data: RDD[(String, Copyright)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        val bean: Copyright = x._2
        val obj: JSONObject = JSON.parseObject(bean.`detail`)
        detailMap.clear()
        val keys: util.Iterator[String] = obj.keySet().iterator()
        while (keys.hasNext) {
          val key: String = keys.next()
          detailMap.put(key, obj.getString(key))
        }
        Copyright2(bean.`corpId`, bean.`softwareName`, bean.`shortName`, bean.`batchNum`, bean.`softwareType`, bean.`typeCode`, bean.`regDate`, bean.`softId`, bean.`detailUrl`, detailMap, bean.`ds`)
      }.toDF()

      // df.show(false)

      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
网站备案
*/
  def icpinfo(dataRDD: RDD[List[(String, Icpinfo)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val arrHomesite: ArrayBuffer[String] = new ArrayBuffer[String]()
      val arrdomain: ArrayBuffer[String] = new ArrayBuffer[String]()

      val data: RDD[(String, Icpinfo)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        arrHomesite.clear()
        arrdomain.clear()
        val bean: Icpinfo = x._2
        val array1: util.List[String] = JSON.parseArray(bean.`homeSite`, classOf[String])
        val array2: util.List[String] = JSON.parseArray(bean.`domain`, classOf[String])
        for (i <- 0 until array1.size()) {
          arrHomesite.append(array1.get(i))
        }
        for (i <- 0 until array2.size()) {
          arrdomain.append(array2.get(i))
        }
        Icpinfo2(bean.`corpId`, bean.`siteName`, arrHomesite.toArray, arrdomain.toArray, bean.`icpNo`, bean.`ds`)
      }.toDF()

      // df.show(false)

      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
商标信息
*/
  def mark(dataRDD: RDD[List[(String, Mark)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Mark)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
专利信息
*/
  def patent(dataRDD: RDD[List[(String, Patent)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Patent)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
作品著作权
*/
  def workright(dataRDD: RDD[List[(String, Workright)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Workright)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
经营异常
*/
  def abnormal(dataRDD: RDD[List[(String, Abnormal)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Abnormal)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
动产抵押
*/
  def chattelmortgage(dataRDD: RDD[List[(String, Chattelmortgage)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Chattelmortgage)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
清算组信息
*/
  def clearaccount(dataRDD: RDD[List[(String, Clearaccount)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Clearaccount)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
失信被执行人

*/
  def discredit(dataRDD: RDD[List[(String, Discredit)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Discredit)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
股权出质
*/
  def equitypledge(dataRDD: RDD[List[(String, Equitypledge)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Equitypledge)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
被执行人
*/
  def executedperson(dataRDD: RDD[List[(String, Executedperson)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Executedperson)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
法院公告
*/
  def getcourtnoticedata(dataRDD: RDD[List[(String, Getcourtnoticedata)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val arrMapPeople: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val map1: mutable.Map[String, String] = mutable.Map[String, String]()

      val data: RDD[(String, Getcourtnoticedata)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        arrMapPeople.clear()
        val bean: Getcourtnoticedata = x._2
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
        Getcourtnoticedata2(bean.`corpId`, bean.date, bean.`type`, bean.`cause`, bean.`courtnoticeId`, bean.`court`, arrMapPeople.toArray, bean.`detailUrl`, bean.`ds`)
      }.toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
严重违法
*/
  def illegal(dataRDD: RDD[List[(String, Illegal)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Illegal)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
司法拍卖
*/
  def judicialauction(dataRDD: RDD[List[(String, Judicialauction)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Judicialauction)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
裁判文书
*/
  def lawwenshu(dataRDD: RDD[List[(String, Lawwenshu)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Lawwenshu)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
开庭公告
*/
  def opennotice(dataRDD: RDD[List[(String, Opennotice)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val arrPlaintiff: ArrayBuffer[String] = new ArrayBuffer[String]()
      val arrDefendant: ArrayBuffer[String] = new ArrayBuffer[String]()

      val data: RDD[(String, Opennotice)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        arrPlaintiff.clear()
        arrDefendant.clear()
        val bean: Opennotice = x._2
        val array1: util.List[String] = JSON.parseArray(bean.`plaintifflist`, classOf[String])
        val array2: util.List[String] = JSON.parseArray(bean.`defendantlist`, classOf[String])
        for (i <- 0 until array1.size()) {
          arrPlaintiff.append(array1.get(i))
        }
        for (i <- 0 until array2.size()) {
          arrDefendant.append(array2.get(i))
        }
        Opennotice2(bean.`corpId`, bean.`hearingDate`, bean.`caseNo`, bean.`caseReason`, bean.`judge`, bean.`court`, bean.`tribunal`, arrPlaintiff.toArray, arrDefendant.toArray, bean.`ename`, bean.`pureRole`, bean.`dataId`, bean.`content`, bean.`region`, bean.`department`, bean.`author`, bean.`judgeType`, bean.`detailUrl`, bean.`ds`)
      }.toDF()

      // df.show(false)

      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
行政处罚
*/
  def penalties(dataRDD: RDD[List[(String, Penalties)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Penalties)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
限制高消费
*/
  def restrictedconsumer(dataRDD: RDD[List[(String, Restrictedconsumer)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Restrictedconsumer)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
简易注销公告
*/
  def simplecancellation(dataRDD: RDD[List[(String, Simplecancellation)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._
      val arrMapObjections: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val arrMapResult: ArrayBuffer[mutable.Map[String, String]] = new ArrayBuffer[mutable.Map[String, String]]()
      val map1: mutable.Map[String, String] = mutable.Map[String, String]()
      val map2: mutable.Map[String, String] = mutable.Map[String, String]()

      val data: RDD[(String, Simplecancellation)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df = data.map { x =>
        arrMapObjections.clear()
        arrMapResult.clear()
        val bean: Simplecancellation = x._2
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
        Simplecancellation2(bean.`corpId`, bean.`entName`, bean.`creditNo`, bean.`noticePeriodDate`, bean.`departMent`, arrMapObjections.toArray, bean.`cancelId`, bean.`cancelImageUrl`, bean.`detailUrl`, arrMapResult.toArray, bean.`ds`)
      }.toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
税务违法
*/
  def taxviolation(dataRDD: RDD[List[(String, Taxviolation)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Taxviolation)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
股权冻结
*/
  def stockfreeze(dataRDD: RDD[List[(String, Stockfreeze)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Stockfreeze)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }

  /*
终本案件
*/
  def terminationcase(dataRDD: RDD[List[(String, Terminationcase)]], spark: SparkSession) = {
    if (!dataRDD.isEmpty()) {
      import spark.implicits._

      val data: RDD[(String, Terminationcase)] = dataRDD.flatMap(x => x)
      val tableName: String = Constant.DB + ".ods_" + data.first()._1
      val df: DataFrame = data.map(_._2).toDF()

      // df.show(false)
      df.write.mode("overwrite").insertInto(tableName)
    }
  }
}
