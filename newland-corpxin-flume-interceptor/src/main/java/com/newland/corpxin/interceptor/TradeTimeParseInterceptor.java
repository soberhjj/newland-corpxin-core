package com.newland.corpxin.interceptor;

import com.alibaba.fastjson.JSON;
import com.newland.corpxin.common.Constant;
import com.newland.corpxin.entity.Trade;
import com.newland.corpxin.util.StringUtils;
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
 * @Description: 公共资源交易平台拦截器(根据抓取时间分区,解析json数据)
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

        String hdfsPath = null;
        String[] dataArr = null;
        try {
            dataArr = parse(eventData);
            if(StringUtils.isEmpty(dataArr[0]) || StringUtils.isEmpty(dataArr[1])) {
                throw new Exception("解析失败，解析结果为空");
            }
            hdfsPath = "/" + dataArr[0];
            event.setBody(dataArr[1].getBytes());

        } catch (Exception e) {
            logger.error(String.format("parse data:%s fail,reason: %s", eventData, e.getMessage()));
            hdfsPath = "/error";
            event.setBody(eventData.getBytes());
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
     * 根据时间分区，解析json数据
     * @Author Ljh
     * @Date 2020/8/3 10:55
     * @param message
     * @return java.lang.String
     */
    private String[] parse(String message){

        String[] resultArr = new String[2];
        Trade trade = JSON.parseObject(StringUtils.replace(message, Constant.REGEX_SPECIAL_CHAR, Constant.HTML_BR),Trade.class);

        StringBuilder sb = new StringBuilder();
        sb.append(trade.getId()).append(Constant.SEP_ONE)
                .append(trade.getCrawlTime()).append(Constant.SEP_ONE)
                .append(trade.getUrl()).append(Constant.SEP_ONE)
                .append(trade.getProjectName()).append(Constant.SEP_ONE)
                .append(trade.getProjectNum()).append(Constant.SEP_ONE)
                .append(trade.getInformationType()).append(Constant.SEP_ONE)
                .append(trade.getBusinessType()).append(Constant.SEP_ONE)
                .append(trade.getProvince()).append(Constant.SEP_ONE)
                .append(trade.getFromUrl()).append(Constant.SEP_ONE)
                .append(trade.getIndustry()).append(Constant.SEP_ONE)
                .append(trade.getOrigin()).append(Constant.SEP_ONE)
                .append(trade.getTitle()).append(Constant.SEP_ONE)
                .append(trade.getCreatedAt()).append(Constant.SEP_ONE)
                .append(trade.getContent()).append(Constant.SEP_ONE)
                .append(trade.getOrginalUrl()).append(Constant.SEP_ONE)
                .append(trade.getDataSource());

        resultArr[0] = DateUtils.dateToString(DateUtils.getDate(trade.getCrawlTime()),"yyyyMMdd");
        resultArr[1] = sb.toString();

        return resultArr;

    }
}
