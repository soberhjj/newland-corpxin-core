package com.newland.corpxin.service.impl;

import com.newland.corpxin.common.Constant;
import com.newland.corpxin.service.BasicInfoService;
import com.newland.corpxin.mapper.BasicInfoMapper;
import com.newland.corpxin.model.BasicInfo;
import com.newland.corpxin.model.CreditNo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    public BasicInfo getBasicInfo(String unifiedCode) {
        log.info("requist basic info of unifiedCode:{}",unifiedCode);
        BasicInfo basicInfo = null;

        /*非法输入unifiedCode(不是18位)*/
        if(unifiedCode.length() != Constant.CREDITNO_LENGTH){
            log.warn("illegal input unifiedCode:{}",unifiedCode);
            return basicInfo;
        }

        basicInfo = basicInfoMapper.selectByUnifiedCode(unifiedCode);
        /*unifiedCode不在mysql,也不在缓冲区(第一次查询)*/
        if (null == basicInfo && !creditNoTemplate.opsForSet().isMember(Constant.CREDITNO_CACHE,unifiedCode)) {

            //加入到缓冲区
            creditNoTemplate.opsForSet().add(Constant.CREDITNO_CACHE,unifiedCode);
            log.info("redis {} add creditNo: {}",Constant.CREDITNO_CACHE, unifiedCode);

            //加入到爬虫队列
            CreditNo creditNo = new CreditNo(unifiedCode);
            spiderTemplate.opsForList().rightPush(Constant.LISTEN_REDIS_KEY, creditNo);
            log.info("redis {} add data: {}",Constant.LISTEN_REDIS_KEY, creditNo);
        }

        return basicInfo;
    }
}
