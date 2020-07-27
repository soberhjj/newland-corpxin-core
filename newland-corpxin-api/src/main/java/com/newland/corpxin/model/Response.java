package com.newland.corpxin.model;

import lombok.Data;

@Data
public class Response<T> {
    String code;

    T data;

    String describe;
}