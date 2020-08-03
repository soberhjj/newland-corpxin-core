package com.newland.corpxin.service;

import com.newland.corpxin.model.BasicInfo;
import com.newland.corpxin.model.BasicInfoRequest;
import com.newland.corpxin.model.BasicInfoRequest2;

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
     * @param request
     * @return com.newland.corpxin.model.BasicInfo
     */
    List<BasicInfo> listBasicInfosByExactMatch(BasicInfoRequest request);

    /**
     * 根据企业名称模糊查询企业基本信息
     * @Author Ljh
     * @Date 2020/7/27 11:12
     * @param request2
     * @return com.newland.corpxin.model.BasicInfo
     */
    List<BasicInfo> listBasicInfosByFuzzyMatch(BasicInfoRequest2 request2);

}
