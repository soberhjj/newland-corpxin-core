package com.newland.corpxin.handler
import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.google.gson.Gson
import com.newland.corpxin.model._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import collection.JavaConversions._


/**
 * 针对不同的表结构将数据分类，然后分别入库
 * 大致流程是：
 *  1.先将读取的每行json数据进行分类判断，并映射为对应表的case class，同时新增corpId字段和ds字段。
 *  2.将不同的表分别放入不同的累加器中，之后分别批量的插入hive表
 */
object DataAssortHandler {

  def DataAssort(oriRDD: RDD[String], dt: String, spark: SparkSession) = {

    /*
    这边定义了37个累加器，目的是读取obs文件后判断每一行的数据类型然后放入各自类型的累加器中。
    为什么不用普通集合而要用累加器？ 因为假如你在Driver端定义了普通集合，Executor端往普通集合里添加数据时，
                              Driver端实际上是取不到数据的，这是因为他们之间的序列化问题导致的。
    所以这里的累加器的作用其实和普通集合类似，都是用来存储分类后的数据，唯一的区别就在于Driver最终能获取到累加器的内容，而普通集合不行。
                              除非把普通集合定义在Executor中，就能避免Driver和Executor之间的序列化问题。
     */
    val workrightAcc = spark.sparkContext.collectionAccumulator[(String, Workright)]
    val executedpersonAcc = spark.sparkContext.collectionAccumulator[(String, Executedperson)]
    val abnormalAcc = spark.sparkContext.collectionAccumulator[(String, Abnormal)]
    val getcourtnoticedataAcc = spark.sparkContext.collectionAccumulator[(String, Getcourtnoticedata)]
    val illegalAcc = spark.sparkContext.collectionAccumulator[(String, Illegal)]
    val judicialauctionAcc = spark.sparkContext.collectionAccumulator[(String, Judicialauction)]
    val chattelmortgageAcc = spark.sparkContext.collectionAccumulator[(String, Chattelmortgage)]
    val lawwenshuAcc = spark.sparkContext.collectionAccumulator[(String, Lawwenshu)]
    val clearaccountAcc = spark.sparkContext.collectionAccumulator[(String, Clearaccount)]
    val discreditAcc = spark.sparkContext.collectionAccumulator[(String, Discredit)]
    val equitypledgeAcc = spark.sparkContext.collectionAccumulator[(String, Equitypledge)]
    val patentAcc = spark.sparkContext.collectionAccumulator[(String, Patent)]
    val markAcc = spark.sparkContext.collectionAccumulator[(String, Mark)]
    val icpinfoAcc = spark.sparkContext.collectionAccumulator[(String, Icpinfo)]
    val copyrightAcc = spark.sparkContext.collectionAccumulator[(String, Copyright)]
    val randominspectionAcc = spark.sparkContext.collectionAccumulator[(String, Randominspection)]
    val qualityAcc = spark.sparkContext.collectionAccumulator[(String, Quality)]
    val licenseAcc = spark.sparkContext.collectionAccumulator[(String, License)]
    val foodqualityAcc = spark.sparkContext.collectionAccumulator[(String, Foodquality)]
    val doublecheckupAcc = spark.sparkContext.collectionAccumulator[(String, Doublecheckup)]
    val annualreportdataAcc = spark.sparkContext.collectionAccumulator[(String, Annualreportdata)]
    val shareholdersdataAcc = spark.sparkContext.collectionAccumulator[(String, Shareholdersdata)]
    val investrecorddataAcc = spark.sparkContext.collectionAccumulator[(String, Investrecorddata)]
    val headcompanyAcc = spark.sparkContext.collectionAccumulator[(String, Headcompany)]
    val directorsdataAcc = spark.sparkContext.collectionAccumulator[(String, Directorsdata)]
    val changerecorddataAcc = spark.sparkContext.collectionAccumulator[(String, Changerecorddata)]
    val branchsdataAcc = spark.sparkContext.collectionAccumulator[(String, Branchsdata)]
    val penaltiesAcc = spark.sparkContext.collectionAccumulator[(String, Penalties)]
    val restrictedconsumerAcc = spark.sparkContext.collectionAccumulator[(String, Restrictedconsumer)]
    val stockfreezeAcc = spark.sparkContext.collectionAccumulator[(String, Stockfreeze)]
    val terminationcaseAcc = spark.sparkContext.collectionAccumulator[(String, Terminationcase)]
    val simplecancellationAcc = spark.sparkContext.collectionAccumulator[(String, Simplecancellation)]
    val taxviolationAcc = spark.sparkContext.collectionAccumulator[(String, Taxviolation)]
    val holdsdataAcc = spark.sparkContext.collectionAccumulator[(String, Holdsdata)]
    val basicdataAcc = spark.sparkContext.collectionAccumulator[(String, Basicdata)]
    val filinginfoAcc = spark.sparkContext.collectionAccumulator[(String, Filinginfo)]
    val opennoticeAcc = spark.sparkContext.collectionAccumulator[(String, Opennotice)]

    /*
    遍历obs数据的每一行，将每一行都转为json对象，目的是获取每行数据xwho,xwhat,xcontent的值。
     xwho为企业的id，xwhat为表的类型，xcontent是个json数组，所以要遍历一下它，然后将xcontent中的内容转为对应的case class(理解为javabean)
     最后遍历完所有数据后，同时也把也把每行的数据分类好放进各自的累加器中，等待插入hive表
     建议这边可以照着原始数据看，对照下能看的更明白
     */
    oriRDD.foreachPartition { partitionIter =>
      partitionIter.foreach { json =>
        val jsonStr: JSONObject = JSON.parseObject(json)
        val xwho: String = jsonStr.getString("xwho")
        val xwhat: String = jsonStr.getString("xwhat")
        val arr: JSONArray = jsonStr.getJSONArray("xcontent")

        if("baiduxin_focalPoint_opennotice"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Opennotice])
            data.`corpId` = xwho
            data.`ds` = dt
            // 存入累加器中的同时，把xwhat表名也放进去，方便之后入库提取出表名。
            opennoticeAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_filinginfo"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Filinginfo])
            data.`corpId` = xwho
            data.`ds` = dt
            filinginfoAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_basicData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Basicdata])
            data.`corpId` = xwho
            data.`ds` = dt
            basicdataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_holdsData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Holdsdata])
            data.`corpId` = xwho
            data.`ds` = dt
            holdsdataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_taxviolation"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Taxviolation])
            data.`corpId` = xwho
            data.`ds` = dt
            taxviolationAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_simplecancellation"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Simplecancellation])
            data.`corpId` = xwho
            data.`ds` = dt
            simplecancellationAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_terminationcase"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Terminationcase])
            data.`corpId` = xwho
            data.`ds` = dt
            terminationcaseAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_stockFreeze"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Stockfreeze])
            data.`corpId` = xwho
            data.`ds` = dt
            stockfreezeAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_restrictedConsumer"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Restrictedconsumer])
            data.`corpId` = xwho
            data.`ds` = dt
            restrictedconsumerAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_penalties"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Penalties])
            data.`corpId` = xwho
            data.`ds` = dt
            penaltiesAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_branchsData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Branchsdata])
            data.`corpId` = xwho
            data.`ds` = dt
            branchsdataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_changeRecordData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Changerecorddata])
            data.`corpId` = xwho
            data.`ds` = dt
            changerecorddataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_directorsData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Directorsdata])
            data.`corpId` = xwho
            data.`ds` = dt
            directorsdataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_headCompany"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Headcompany])
            data.`corpId` = xwho
            data.`ds` = dt
            headcompanyAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_investRecordData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Investrecorddata])
            data.`corpId` = xwho
            data.`ds` = dt
            investrecorddataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_shareholdersData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Shareholdersdata])
            data.`corpId` = xwho
            data.`ds` = dt
            shareholdersdataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_basicData_annualReportData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Annualreportdata])
            data.`corpId` = xwho
            data.`ds` = dt
            annualreportdataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_compManage_doublecheckup"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Doublecheckup])
            data.`corpId` = xwho
            data.`ds` = dt
            doublecheckupAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_compManage_foodquality"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Foodquality])
            data.`corpId` = xwho
            data.`ds` = dt
            foodqualityAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_compManage_license"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[License])
            data.`corpId` = xwho
            data.`ds` = dt
            licenseAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_compManage_quality"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Quality])
            data.`corpId` = xwho
            data.`ds` = dt
            qualityAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_compManage_randominspection"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Randominspection])
            data.`corpId` = xwho
            data.`ds` = dt
            randominspectionAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_intellectualProperty_copyright"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Copyright])
            data.`corpId` = xwho
            data.`ds` = dt
            copyrightAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_intellectualProperty_icpinfo"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Icpinfo])
            data.`corpId` = xwho
            data.`ds` = dt
            icpinfoAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_intellectualProperty_mark"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Mark])
            data.`corpId` = xwho
            data.`ds` = dt
            markAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_intellectualProperty_patent"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Patent])
            data.`corpId` = xwho
            data.`ds` = dt
            patentAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_equitypledge"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Equitypledge])
            data.`corpId` = xwho
            data.`ds` = dt
            equitypledgeAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_discredit"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Discredit])
            data.`corpId` = xwho
            data.`ds` = dt
            discreditAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_clearaccount"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Clearaccount])
            data.`corpId` = xwho
            data.`ds` = dt
            clearaccountAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_lawWenshu"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Lawwenshu])
            data.`corpId` = xwho
            data.`ds` = dt
            lawwenshuAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_chattelmortgage"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Chattelmortgage])
            data.`corpId` = xwho
            data.`ds` = dt
            chattelmortgageAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_intellectualProperty_workright"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Workright])
            data.`corpId` = xwho
            data.`ds` = dt
            workrightAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_executedPerson"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Executedperson])
            data.`corpId` = xwho
            data.`ds` = dt
            executedpersonAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_abnormal"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Abnormal])
            data.`corpId` = xwho
            data.`ds` = dt
            abnormalAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_getCourtNoticeData"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Getcourtnoticedata])
            data.`corpId` = xwho
            data.`ds` = dt
            getcourtnoticedataAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_illegal"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Illegal])
            data.`corpId` = xwho
            data.`ds` = dt
            illegalAcc.add((xwhat, data))
          }
        }
        else if("baiduxin_focalPoint_judicialauction"==xwhat) {
          for (i <- 0 until arr.size()) {
            val data = JSON.parseObject(arr.getString(i), classOf[Judicialauction])
            data.`corpId` = xwho
            data.`ds` = dt
            judicialauctionAcc.add((xwhat, data))
          }
        }
      }
    }

    /*
    入库的逻辑主要是把累加器中的值批量插入hive中对应的表结构，但是由于hive表中目前有4中表类型，
    一种是表字段都是string，
    一种是表字段包括map[string,string]
    一种是表字段包括array[string]
    一种是表字段包括array[map[string,string]]
    由于上一步数据分类时转为的所有表的case class里的字段都是string类型，不满足hive中某些表字段里的复杂类型，所以在数据入hive表之前，
    假如hive表中字段是复杂结构，则需要将相应的string转为复杂结构，如string->map[string,string]
     */
    // TODO 数据入库
    DataToHiveHandler.filing(filinginfoAcc.value.toList, spark)
    DataToHiveHandler.hold(holdsdataAcc.value.toList, spark)
    DataToHiveHandler.basic(basicdataAcc.value.toList, spark)
    DataToHiveHandler.branch(branchsdataAcc.value.toList, spark)
    DataToHiveHandler.changerecord(changerecorddataAcc.value.toList, spark)
    DataToHiveHandler.directorsdata(directorsdataAcc.value.toList, spark)
    DataToHiveHandler.headcompany(headcompanyAcc.value.toList, spark)
    DataToHiveHandler.investrecord(investrecorddataAcc.value.toList, spark)
    DataToHiveHandler.shareholders(shareholdersdataAcc.value.toList, spark)
    DataToHiveHandler.annualreport(annualreportdataAcc.value.toList, spark)
    DataToHiveHandler.doublecheckup(doublecheckupAcc.value.toList, spark)
    DataToHiveHandler.license(licenseAcc.value.toList, spark)
    DataToHiveHandler.randominspection(randominspectionAcc.value.toList, spark)
    DataToHiveHandler.mark(markAcc.value.toList, spark)
    DataToHiveHandler.patent(patentAcc.value.toList, spark)
    DataToHiveHandler.workright(workrightAcc.value.toList, spark)
    DataToHiveHandler.abnormal(abnormalAcc.value.toList, spark)
    DataToHiveHandler.chattelmortgage(chattelmortgageAcc.value.toList, spark)
    DataToHiveHandler.clearaccount(clearaccountAcc.value.toList, spark)
    DataToHiveHandler.discredit(discreditAcc.value.toList, spark)
    DataToHiveHandler.equitypledge(equitypledgeAcc.value.toList, spark)
    DataToHiveHandler.executedperson(executedpersonAcc.value.toList, spark)
    DataToHiveHandler.illegal(illegalAcc.value.toList, spark)
    DataToHiveHandler.judicialauction(judicialauctionAcc.value.toList, spark)
    DataToHiveHandler.lawwenshu(lawwenshuAcc.value.toList, spark)
    DataToHiveHandler.penalties(penaltiesAcc.value.toList, spark)
    DataToHiveHandler.restrictedconsumer(restrictedconsumerAcc.value.toList, spark)
    DataToHiveHandler.stockfreeze(stockfreezeAcc.value.toList, spark)
    DataToHiveHandler.terminationcase(terminationcaseAcc.value.toList, spark)
    DataToHiveHandler.taxviolation(taxviolationAcc.value.toList, spark)
    DataToHiveHandler.foodquality(foodqualityAcc.value.toList, spark)
    DataToHiveHandler.quality(qualityAcc.value.toList, spark)
    DataToHiveHandler.copyright(copyrightAcc.value.toList, spark)
    DataToHiveHandler.icpinfo(icpinfoAcc.value.toList, spark)
    DataToHiveHandler.getcourtnoticedata(getcourtnoticedataAcc.value.toList, spark)
    DataToHiveHandler.opennotice(opennoticeAcc.value.toList, spark)
    DataToHiveHandler.simplecancellation(simplecancellationAcc.value.toList, spark)
  }
}