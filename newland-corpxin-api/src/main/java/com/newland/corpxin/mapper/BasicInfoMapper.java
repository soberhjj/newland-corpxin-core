package com.newland.corpxin.mapper;

import com.newland.corpxin.model.BasicInfo;
import java.util.List;

import com.newland.corpxin.model.BasicInfoRequest;
import com.newland.corpxin.model.BasicInfoRequest2;
import org.apache.ibatis.annotations.Param;

public interface BasicInfoMapper {
    /**
     * 根据精确匹配方式返回查询结果
     * @Author Ljh
     * @Date 2020/8/1 14:44
     * @param request
     * @return java.util.List<com.newland.corpxin.model.BasicInfo>
     */
    List<BasicInfo> listBasicInfosByExactMatch(@Param("request") BasicInfoRequest request);

    List<BasicInfo> listBasicInfosByFuzzyMatch(@Param("request2") BasicInfoRequest2 request2);

//    /**
//     * 根据模糊匹配方式返回查询结果
//     * @Author Ljh
//     * @Date 2020/8/1 14:44
//     *
//     * @return
//     */
//    List<BasicInfo> listBasicInfosByFuzzyMatch(BasicInfoRequest request);
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