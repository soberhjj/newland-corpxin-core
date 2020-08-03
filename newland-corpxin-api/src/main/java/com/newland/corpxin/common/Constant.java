package com.newland.corpxin.common;

/**
 * @Description: 常量设置
 * @Author: Ljh
 * @Date 2020/7/24 10:49
 */
public interface Constant {
    /**
     * 爬虫监听队列
     */
    String LISTEN_REDIS_KEY = "spider:bxin:baidu";

    /**
     * 统一社会信用代码缓冲区
     */
    String CREDITNO_CACHE = "spider:bxin:baidu:creditno";

    /**
     * 统一社会信用代码长度：18位
     */
    int CREDITNO_LENGTH = 18;

    /**
     * 统一社会信用代码标识
     */
    String UNIFIEDCODE_TYPE = "unified_code";

    /**
     * 公司标识
     */
    String ENT_NAME = "ent_name";

}
