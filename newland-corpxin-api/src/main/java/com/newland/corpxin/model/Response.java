package com.newland.corpxin.model;

import lombok.Data;

import java.util.List;

@Data
public class Response<T> {
    String code;

    List<T> data;

    String describe;
}