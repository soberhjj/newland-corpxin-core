package com.newland.corpxin.mapper;

import com.newland.corpxin.model.BasicInfo;
import com.newland.corpxin.model.BasicInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasicInfoMapper {
    /**
     * 根据统一社会信用代码查询企业基本信息
     * @Author Ljh
     * @Date 2020/7/27 11:10
     * @param unifiedCode
     * @return com.newland.corpxin.model.BasicInfo
     */
    BasicInfo selectByUnifiedCode(String unifiedCode);
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