package com.newland.corpxin.model;

import lombok.Data;

import java.util.List;

/**
 * @Description: 企业基本信息传参,用于单个参数模糊匹配
 * @Author: Ljh
 * @Date 2020/8/1 17:54
 */
@Data
public class BasicInfoRequest2 {
    String selectType;
    String selectData;
}
