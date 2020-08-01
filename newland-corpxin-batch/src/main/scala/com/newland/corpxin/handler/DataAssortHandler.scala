package com.newland.corpxin.handler

import java.text.SimpleDateFormat
import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.newland.corpxin.model._
import org.apache.spark.rdd.RDD
import scala.collection.mutable.ListBuffer

/**
 * 针对不同的表结构将数据分类
 * 大致流程是：将读取的每行json数据进行分类判断，并映射为对应表的case class，同时新增corpId字段和ds字段。
 */
object DataAssortHandler {

  /*
作品著作权
*/
  def workrightHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Workright)] = ListBuffer[(String, Workright)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_intellectualProperty_workright" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Workright])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
被执行人
*/
  def executedpersonHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Executedperson)] = ListBuffer[(String, Executedperson)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_executedPerson" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Executedperson])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
经营异常
*/
  def abnormalHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Abnormal)] = ListBuffer[(String, Abnormal)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_abnormal" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Abnormal])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
法院公告
*/
  def getcourtnoticedataHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Getcourtnoticedata)] = ListBuffer[(String, Getcourtnoticedata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_getCourtNoticeData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Getcourtnoticedata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
严重违法
*/
  def illegalHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Illegal)] = ListBuffer[(String, Illegal)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_illegal" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Illegal])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
司法拍卖
*/
  def judicialauctionHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Judicialauction)] = ListBuffer[(String, Judicialauction)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_judicialauction" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Judicialauction])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
动产抵押
*/
  def chattelmortgageHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Chattelmortgage)] = ListBuffer[(String, Chattelmortgage)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_chattelmortgage" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Chattelmortgage])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
裁判文书
*/
  def lawWenshuHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Lawwenshu)] = ListBuffer[(String, Lawwenshu)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_lawWenshu" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Lawwenshu])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
清算组信息
*/
  def clearaccountHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Clearaccount)] = ListBuffer[(String, Clearaccount)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_clearaccount" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Clearaccount])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
失信被执行人
*/
  def discreditHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Discredit)] = ListBuffer[(String, Discredit)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_discredit" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Discredit])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
股权出质
*/
  def equitypledgeHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Equitypledge)] = ListBuffer[(String, Equitypledge)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_equitypledge" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Equitypledge])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
专利信息
*/
  def patentHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Patent)] = ListBuffer[(String, Patent)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_intellectualProperty_patent" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Patent])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
商标信息
*/
  def markHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Mark)] = ListBuffer[(String, Mark)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_intellectualProperty_mark" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Mark])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
网站备案
*/
  def icpinfoHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Icpinfo)] = ListBuffer[(String, Icpinfo)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_intellectualProperty_icpinfo" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Icpinfo])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
软件著作权信息
*/
  def copyrightHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Copyright)] = ListBuffer[(String, Copyright)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_intellectualProperty_copyright" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Copyright])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
抽查检查
*/
  def randominspectionHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Randominspection)] = ListBuffer[(String, Randominspection)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_compManage_randominspection" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Randominspection])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
质量监督检查
*/
  def qualityHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Quality)] = ListBuffer[(String, Quality)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_compManage_quality" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Quality])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
行政许可
*/
  def licenseHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, License)] = ListBuffer[(String, License)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_compManage_license" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[License])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
食品抽检
*/
  def foodqualityHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Foodquality)] = ListBuffer[(String, Foodquality)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_compManage_foodquality" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Foodquality])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
双随机抽检
*/
  def doublecheckupHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Doublecheckup)] = ListBuffer[(String, Doublecheckup)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_compManage_doublecheckup" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Doublecheckup])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
企业年报
*/
  def annualreportHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Annualreportdata)] = ListBuffer[(String, Annualreportdata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_annualReportData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Annualreportdata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
股东信息
*/
  def shareholdersHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Shareholdersdata)] = ListBuffer[(String, Shareholdersdata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_shareholdersData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Shareholdersdata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
对外投资
*/
  def investrecordHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Investrecorddata)] = ListBuffer[(String, Investrecorddata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_investRecordData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Investrecorddata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
总公司
*/
  def headcompanyHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Headcompany)] = ListBuffer[(String, Headcompany)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_headCompany" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Headcompany])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
主要人员
*/
  def directorsHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Directorsdata)] = ListBuffer[(String, Directorsdata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_directorsData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Directorsdata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
  变更记录
*/
  def changerecordHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Changerecorddata)] = ListBuffer[(String, Changerecorddata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_changeRecordData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Changerecorddata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
分支机构
 */
  def branchHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Branchsdata)] = ListBuffer[(String, Branchsdata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_branchsData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Branchsdata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
行政处罚
 */
  def penaltiesHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Penalties)] = ListBuffer[(String, Penalties)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_penalties" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Penalties])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
限制高消费
 */
  def restrictedConsumerHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Restrictedconsumer)] = ListBuffer[(String, Restrictedconsumer)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_restrictedConsumer" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Restrictedconsumer])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
股权冻结
 */
  def stockFreezeHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Stockfreeze)] = ListBuffer[(String, Stockfreeze)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_stockFreeze" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Stockfreeze])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
终本案件
 */
  def terminationcaseHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Terminationcase)] = ListBuffer[(String, Terminationcase)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_terminationcase" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Terminationcase])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
简易注销公告
*/
  def simplecancellationHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Simplecancellation)] = ListBuffer[(String, Simplecancellation)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_simplecancellation" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Simplecancellation])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
税务违法
*/
  def taxviolationHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Taxviolation)] = ListBuffer[(String, Taxviolation)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_taxviolation" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Taxviolation])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
  控股企业
   */
  def holdHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Holdsdata)] = ListBuffer[(String, Holdsdata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_holdsData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data: Holdsdata = JSON.parseObject(arr.getString(i), classOf[Holdsdata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
  工商注册
   */
  def basicHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Basicdata)] = ListBuffer[(String, Basicdata)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_basicData_basicData" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data: Basicdata = JSON.parseObject(arr.getString(i), classOf[Basicdata])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
  立案信息
   */
  def filinginfoHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Filinginfo)] = ListBuffer[(String, Filinginfo)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_filinginfo" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data: Filinginfo = JSON.parseObject(arr.getString(i), classOf[Filinginfo])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }

  /*
开庭公告
*/
  def opennoticeHandler(oriRDD: RDD[String],dateTime:String) = {

    oriRDD.mapPartitions(iter => {
      iter.map { json => {
        var ll: ListBuffer[(String, Opennotice)] = ListBuffer[(String, Opennotice)]()
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")
        if ("baiduxin_focalPoint_opennotice" == xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Opennotice])
            data.`corpId` = xwho
            data.`ds` = dateTime
            ll.append((xwhat, data))
          }
        }
        ll.toList
      }
      }.filter(_.length > 0)
    })
  }
}
