package com.newland.corpxin.common;

/**
 * @Description: 常量定义
 * @Author: Ljh
 * @Date 2020/8/3 10:25
 */
public interface Constant {
    String CHARSET_UTF8 = "UTF-8";

    String DATE_FORMATE = "yyyyMMdd";

    /**
     * 被替换的字符(其实下面的\\\\t在字符串里面就是\\t的意思,因为被转义了)
     */
    String REGEX_SPECIAL_CHAR = "\t|\r|\n|\\\\t|\\\\r|\\\\n|\001|\002|\003";

    /**
     * 替换成新字符
     */
    String HTML_BR = "//";

    /**
     * \001分隔符
     */
    String SEP_ONE = "\001";

}
