package com.newland.corpxin.interceptor;

import com.alibaba.fastjson.JSON;
import com.newland.corpxin.common.Constant;
import com.newland.corpxin.entity.Trade;
import com.newland.framework.common.date.DateUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 公共资源交易平台时间拦截器(根据抓取时间分区)
 * @Author: Ljh
 * @Date 2020/8/3 10:22
 */
public class TradeTimeParseInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(TradeTimeParseInterceptor.class);
    private static final String FIELD_CRAWL_TIME = "crawl_time";

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }

    /**
     * 逐条解析
     * @Author Ljh
     * @Date 2020/8/3 10:53
     * @param event
     * @return org.apache.flume.Event
     */
    @Override
    public Event intercept(Event event) {
        // 获取文本信息
        String eventData = null;
        try {
            eventData = new String(event.getBody(), Constant.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e1) {
            logger.error("转码失败 ",e1);
            return null;
        }
        // 修改headers信息,可以在sink的时候,写入到特定目录
        Map<String, String> headers = event.getHeaders();
        String hdfsPath = "";

        try {
            hdfsPath = parse(eventData);
        } catch (Exception e) {
            logger.error(String.format("parse data:%s fail,reason: %s", eventData, e.getMessage()));
            e.printStackTrace();
            hdfsPath = "error";
        }

        headers.put("hdfspath", hdfsPath);
        return event;
    }

    /**
     * @Description: 批量解析
     * @Author Ljh
     * @Date 2019/12/11 16:10
     * @param events
     * @return java.util.List<org.apache.flume.Event>
     */
    @Override
    public List<Event> intercept(List<Event> events) {
        List<Event> intercepted = new ArrayList<Event>(events.size());
        for (Event event : events) {
            Event interceptedEvent = intercept(event);
            if (interceptedEvent != null) {
                intercepted.add(interceptedEvent);
            }
        }
        return intercepted;
    }

    /**
     * @Description: 构建拦截器
     * @Author Ljh
     * @Date 2019/12/11 16:15
     *
     * @return
     */
    public static class Builder implements Interceptor.Builder {
        @Override
        public void configure(Context arg0) {
        }

        @Override
        public Interceptor build() {
            return new TradeTimeParseInterceptor();
        }
    }

    @Override
    public void close() {

    }

    /**
     * 根据时间分区，原样数据不做变动
     * @Author Ljh
     * @Date 2020/8/3 10:55
     * @param message
     * @return java.lang.String
     */
    private String parse(String message){

        Trade trade = JSON.parseObject(message,Trade.class);

        int crawlTime = trade.getCrawlTime();

        return DateUtils.dateToString(DateUtils.getDate(crawlTime),Constant.DATE_FORMATE);


    }
}
