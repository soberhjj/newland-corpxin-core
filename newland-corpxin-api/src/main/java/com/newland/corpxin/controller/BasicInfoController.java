package com.newland.corpxin.controller;

import com.newland.corpxin.model.BasicInfoRequest;
import com.newland.corpxin.model.BasicInfoRequest2;
import com.newland.corpxin.model.Response;
import com.newland.corpxin.service.BasicInfoService;
import com.newland.corpxin.model.BasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/exact", method = RequestMethod.POST)
    public Response<BasicInfo> listBasicInfos(@RequestBody BasicInfoRequest request) {
        Response<BasicInfo> response = new Response<>();

        List<BasicInfo> basicInfoList = basicInfoService.listBasicInfosByExactMatch(request);

        // 注意service层把request的selectDatas改变了，只剩下差集
        if (request.getSelectDatas().size() > 0) {
            response.setCode(String.format("some %s can not be find",request.getSelectType()));
            response.setDescribe(String.format("the %s: %s does not exit",request.getSelectType(),request.getSelectDatas()));
        } else {
            response.setCode("Success");
        }

        response.setData(basicInfoList);

        return response;
    }

    @RequestMapping(value = "/fuzzy", method = RequestMethod.POST)
    public Response<BasicInfo> listBasicInfos(@RequestBody BasicInfoRequest2 request2) {
        Response<BasicInfo> response = new Response<>();

        List<BasicInfo> basicInfoList = basicInfoService.listBasicInfosByFuzzyMatch(request2);
        if (basicInfoList.size() == 0) {
            response.setCode(String.format("result is null"));
            response.setDescribe(String.format("%s:%s fuzzy match result is null",request2.getSelectType(),request2.getSelectData()));
        } else {
            response.setCode("Success");
        }

        response.setData(basicInfoList);

        return response;
    }
}
