package com.newland.corpxin.service;

import com.newland.corpxin.model.BasicInfo;

/**
 * @Description:
 * @Author: Ljh
 * @Date 2020/7/20 9:50
 */
public interface BasicInfoService {
    /**
     * 根据统一社会信用代码查询企业基本信息
     * @Author Ljh
     * @Date 2020/7/27 11:12
     * @param unifiedCode
     * @return com.newland.corpxin.model.BasicInfo
     */
    BasicInfo getBasicInfo(String unifiedCode);
}
