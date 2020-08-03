package com.newland.corpxin.model;

import lombok.Data;

import java.util.List;

/**
 * @Description: 响应体
 * @Author: Ljh
 * @Date 2020/8/3 20:55
 */
@Data
public class Response<T> {
    String code;

    List<T> data;

    String describe;
}