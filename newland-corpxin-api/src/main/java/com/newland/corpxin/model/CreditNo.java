package com.newland.corpxin.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: redis队列的统一社会信用代码
 * @Author: Ljh
 * @Date 2020/7/24 10:21
 */
@Data
public class CreditNo implements Serializable {
    String type = "credit_no";
    String value;
    public CreditNo(String value){
        this.value = value;
    }
}
