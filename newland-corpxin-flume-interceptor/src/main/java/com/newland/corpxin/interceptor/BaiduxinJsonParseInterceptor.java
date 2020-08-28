package com.newland.corpxin.interceptor;

import com.newland.corpxin.util.StringUtils;
import com.newland.framework.common.date.DateUtils;
import com.newland.framework.common.parser.JSON;
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
 * @Author: sober  2020-07-28 15:25
 */
public class BaiduxinJsonParseInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(BaiduxinJsonParseInterceptor.class);
    private static final String FIELD_CRAWL_TIME = "xwhen";

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        // 获取文本信息
        String eventData = null;
        try {
            eventData=new String(event.getBody(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("转码失败 ",e);
            return null;
        }

        /**
         * 获取json中的xwhen字段，xwhen表示爬取这条数据时的时间戳
         */
        Map<String,Object> jsonObj=null;
        try {
            jsonObj= JSON.toBean(eventData, Map.class);
        } catch (Exception e) {
            logger.error("解析失败 ",e);
        }
        //获取FIELD_CRAWL_TIME的长度，超过10位的就默认10位(因为int长度只能为10位)
        int tmpLength = jsonObj.get(FIELD_CRAWL_TIME).toString().length();
        int length = tmpLength>10 ? 10 : tmpLength;
        //时间戳(只截取前10位，int最多10位)
        int ts = StringUtils.strToInt(jsonObj.get(FIELD_CRAWL_TIME).toString().substring(0,length));
        //将时间戳装换为表示日期的字符串，格式为yyyyMMdd
        String when = DateUtils.dateToString(DateUtils.getDate(ts), "yyyyMMdd");

        // 修改headers信息,可以在sink的时候,写入到特定目录
        Map<String, String> headers = event.getHeaders();
        String hdfsPath="/"+ when;
        headers.put("hdfspath",hdfsPath);

        return event;
    }

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
     * 构建拦截器
     */
    public static class Builder implements Interceptor.Builder {
        @Override
        public void configure(Context arg0) {
        }

        @Override
        public Interceptor build() {
            return new BaiduxinJsonParseInterceptor();
        }
    }

    @Override
    public void close() {

    }
}
