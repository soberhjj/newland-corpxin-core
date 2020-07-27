#!/bin/sh
source ~/.bashrc
BASE_SCRIPT_PATH=/data3/baidu-company/scripts
BASE_LOG_PATH=/data3/baidu-company/logs

#工程名称
PROJECT_NAME=baidu-company-realtime
#脚本路径
SCRIPT_PATH="${BASE_SCRIPT_PATH}/${PROJECT_NAME}"
#日志输出目录
LOG_PATH="${BASE_LOG_PATH}/${PROJECT_NAME}"

JAR_FILE_PATH=${SCRIPT_PATH}/baidu-company-realtime-1.0.0-shaded.jar

nohup spark2-submit \
--master yarn \
--deploy-mode client \
--name publicopinion-realtime  \
--conf spark.streaming.stopGracefullyOnShutdown=true \
--conf spark.serializer=org.apache.spark.serializer.KryoSerializer \
--conf spark.streaming.unpersist=true \
--conf spark.dynamicAllocation.enabled=false \
--conf spark.streaming.kafka.maxRetries=2 \
--conf spark.streaming.kafka.maxRatePerPartition=1000 \
--conf spark.yarn.am.attemptFailuresValidityInterval=1h \
--conf spark.yarn.max.executor.failures=3 \
--conf spark.yarn.executor.failuresValidityInterval=1h \
--conf spark.task.maxFailures=8 \
--class com.newland.publicopinion.Main \
${JAR_FILE_PATH} \
-duration 60  -brokers master01:9092,master02:9092 -topic spider_bxin -consumer baidu-company-realtime_2020072301 \
> ${LOG_PATH}/run.log 2>&1 &