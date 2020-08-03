package com.newland.corpxin.service;

import com.newland.corpxin.model.BasicInfo;

import java.util.List;

/**
 * @Description:
 * @Author: Ljh
 * @Date 2020/7/20 9:50
 */
public interface BasicInfoService {
    /**
     * 根据统一社会信用代码精确查询企业基本信息
     * @Author Ljh
     * @Date 2020/7/27 11:12
     * @param unifiedCodeList
     * @return com.newland.corpxin.model.BasicInfo
     */
    List<BasicInfo> listBasicInfosByUnifiedCode(List<String> unifiedCodeList);

    /**
     * 根据企业名称模糊查询企业基本信息
     * @Author Ljh
     * @Date 2020/7/27 11:12
     * @param entName
     * @return com.newland.corpxin.model.BasicInfo
     */
    List<BasicInfo> listBasicInfosByEntName(String entName);

}
