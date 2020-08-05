package com.newland.corpxin.controller;

import com.newland.corpxin.common.Constant;
import com.newland.corpxin.model.Response;
import com.newland.corpxin.service.BasicInfoService;
import com.newland.corpxin.model.BasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Ljh
 * @Date 2020/7/20 9:55
 */
@Slf4j
@RestController
@RequestMapping("/api/corpxin/basic-info")
public class BasicInfoController {

    @Autowired
    BasicInfoService basicInfoService;

    @RequestMapping(value = "/unified-code", method = RequestMethod.POST)
    public Response<BasicInfo> listBasicInfos(@RequestBody List<String> unifiedCodeList) {
        Response<BasicInfo> response = new Response<>();

        // 限制请求数组的长度
        if(unifiedCodeList.size() > Constant.REQUEST_MAX_LENGTH){
            log.info("the length of unified_code List is {}, greater than %s",unifiedCodeList.size(),Constant.REQUEST_MAX_LENGTH);
            response.setCode("List is too long");
            response.setData(new ArrayList<>());
            response.setDescribe(String.format("the length of unified_code List is %s, greater than %s",unifiedCodeList.size(),Constant.REQUEST_MAX_LENGTH));
            return response;
        }

        List<BasicInfo> basicInfoList = basicInfoService.listBasicInfosByUnifiedCode(unifiedCodeList);

        // 注意service层把request的结果改变了，只剩下差集
        if (unifiedCodeList.size() > 0) {
            response.setCode("some unified_code can not be find");
            response.setDescribe(String.format("the unified_code: %s does not exit",unifiedCodeList));
        } else {
            response.setCode("Success");
        }

        response.setData(basicInfoList);

        return response;
    }


    @RequestMapping(value = "/ent-name/{entName}", method = RequestMethod.GET)
    public Response<BasicInfo> listBasicInfos(@PathVariable(name = "entName") String entName) {
        Response<BasicInfo> response = new Response<>();

        List<BasicInfo> basicInfoList = basicInfoService.listBasicInfosByEntName(entName);
        if (basicInfoList.size() == 0) {
            response.setCode(String.format("result is null"));
            response.setDescribe(String.format("ent_name:%s fuzzy match result is null",entName));
        } else {
            response.setCode("Success");
        }

        response.setData(basicInfoList);

        return response;
    }
}
