package com.newland.corpxin.entity;

import lombok.Data;

/**
 * @Description: 公共资源交易平台实体
 * @Author: Ljh
 * @Date 2020/8/3 10:08
 */
@Data
public class Trade {
    private String id;
    private int crawlTime;
    private String url;
    private String projectName;
    private String projectNum;
    private String informationType;
    private String businessType;
    private String province;
    private String fromUrl;
    private String industry;
    private String origin;
    private String title;
    private String createdAt;
    private String content;
    private String orginalUrl;
    private String dataSource;
}
