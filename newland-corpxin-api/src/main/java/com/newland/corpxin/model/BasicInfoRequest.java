package com.newland.corpxin.model;

import lombok.Data;

import java.util.List;

/**
 * @Description: 企业基本信息传参,用于多个参数精确匹配
 * @Author: Ljh
 * @Date 2020/7/31 17:02
 */
@Data
public class BasicInfoRequest {
    String selectType;
    List<String> selectDatas;
}
