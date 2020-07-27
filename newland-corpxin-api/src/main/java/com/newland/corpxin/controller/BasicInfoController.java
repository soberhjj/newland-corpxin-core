package com.newland.corpxin.controller;

import com.newland.corpxin.model.Response;
import com.newland.corpxin.service.BasicInfoService;
import com.newland.corpxin.model.BasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Ljh
 * @Date 2020/7/20 9:55
 */
@Slf4j
@RestController
@RequestMapping("/api/pool_baidu/company/basic_info")
public class BasicInfoController {

    @Autowired
    BasicInfoService basicInfoService;

    @RequestMapping(value = "/{unifiedCode}", method = RequestMethod.GET)
    public Response<BasicInfo> getBasicInfoByCreditNo(@PathVariable(name = "unifiedCode") String unifiedCode) {
        Response<BasicInfo> response = new Response<>();

        BasicInfo basicInfo = basicInfoService.getBasicInfo(unifiedCode);
        if (null == basicInfo) {
            log.info("the unifiedCode: {} does not exit",unifiedCode);
            response.setCode("NoSuchUnifiedCode");
            response.setDescribe(String.format("the unifiedCode: %s does not exit",unifiedCode));
        } else {
            response.setCode("Success");
        }

        response.setData(basicInfo);

        return response;
    }

}
