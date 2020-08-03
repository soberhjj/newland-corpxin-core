package com.newland.corpxin.mapper;

import com.newland.corpxin.model.BasicInfo;
import java.util.List;

public interface BasicInfoMapper {
    /**
     * 根据统一社会信用代码精确匹配
     * @Author Ljh
     * @Date 2020/8/1 14:44
     * @param unifiedCodeList
     * @return java.util.List<com.newland.corpxin.model.BasicInfo>
     */
    List<BasicInfo> listBasicInfosByUnifiedCode(List<String> unifiedCodeList);

    /**
     * 根据企业名称模糊匹配
     * @Author Ljh
     * @Date 2020/8/3 16:31
     * @param entName
     * @return java.util.List<com.newland.corpxin.model.BasicInfo>
     */
    List<BasicInfo> listBasicInfosByEntName(String entName);
    
//    int countByExample(BasicInfoExample example);
//
//    int deleteByExample(BasicInfoExample example);
//
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(BasicInfo record);
//
//    int insertSelective(BasicInfo record);
//
//    List<BasicInfo> selectByExample(BasicInfoExample example);
//
//    BasicInfo selectByPrimaryKey(Integer id);

//    int updateByExampleSelective(@Param("record") BasicInfo record, @Param("example") BasicInfoExample example);
//
//    int updateByExample(@Param("record") BasicInfo record, @Param("example") BasicInfoExample example);
//
//    int updateByPrimaryKeySelective(BasicInfo record);
//
//    int updateByPrimaryKey(BasicInfo record);
}