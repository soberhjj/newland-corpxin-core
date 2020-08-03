package com.newland.corpxin.service.impl;

import com.newland.corpxin.common.Constant;
import com.newland.corpxin.model.BasicInfoRequest;
import com.newland.corpxin.model.BasicInfoRequest2;
import com.newland.corpxin.service.BasicInfoService;
import com.newland.corpxin.mapper.BasicInfoMapper;
import com.newland.corpxin.model.BasicInfo;
import com.newland.corpxin.model.CreditNo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Ljh
 * @Date 2020/7/20 9:50
 */
@Slf4j
@Service
public class BasicInfoServiceImpl implements BasicInfoService {
    @Autowired
    BasicInfoMapper basicInfoMapper;
    @Autowired
    private RedisTemplate<String,String> creditNoTemplate;
    @Autowired
    private RedisTemplate<String,Object> spiderTemplate;

    @Override
    public List<BasicInfo> listBasicInfosByExactMatch(BasicInfoRequest request) {
        List<String> requestValueList = request.getSelectDatas();
        log.info("requist basic info by exact match,column: {},values: {}",request.getSelectType(),requestValueList);

        // 结果集
        List<BasicInfo> basicInfoList = basicInfoMapper.listBasicInfosByExactMatch(request);

        // 获取实际有结果的请求值(统一社会信用代码)
        List<String> resultValueList = new ArrayList<>();
        for(BasicInfo basicInfo : basicInfoList){
            resultValueList.add(basicInfo.getUnifiedCode());
        }

        // 差集: requestValueList-resultValueList, 得到没找到的值（注意外面传参进来的值变了，只剩下差集的）
        requestValueList.removeAll(resultValueList);

        // 对没得到的值进行处理（目前只对统一社会信用代码特殊处理，其他都是日志输出而已）
        for(String value : requestValueList){
            log.warn("the {}:{} does not exist",request.getSelectType(),value);

            if(Constant.UNIFIEDCODE_TYPE.equals(request.getSelectType())){
                /*非法输入unifiedCode(不是18位)*/
                if(value.length() != Constant.CREDITNO_LENGTH){
                    log.warn("illegal input {}:{}",request.getSelectType(),value);
                } else {
                    if (!creditNoTemplate.opsForSet().isMember(Constant.CREDITNO_CACHE,value)) {
                        //加入到缓冲区
                        creditNoTemplate.opsForSet().add(Constant.CREDITNO_CACHE,value);
                        log.info("redis {} add creditNo: {}",Constant.CREDITNO_CACHE, value);

                        //加入到爬虫队列
                        CreditNo creditNo = new CreditNo(value);
                        spiderTemplate.opsForList().rightPush(Constant.LISTEN_REDIS_KEY, creditNo);
                        log.info("redis {} add data: {}",Constant.LISTEN_REDIS_KEY, creditNo);
                    }
                }
            }

        }

        return basicInfoList;
    }

    @Override
    public List<BasicInfo> listBasicInfosByFuzzyMatch(BasicInfoRequest2 request2) {
        String requestValue = request2.getSelectData();
        log.info("requist basic info by fuzzy match,column: {},values: {}",request2.getSelectType(),requestValue);

        // 结果集
        List<BasicInfo> basicInfoList = basicInfoMapper.listBasicInfosByFuzzyMatch(request2);

        return basicInfoList;
    }
}
