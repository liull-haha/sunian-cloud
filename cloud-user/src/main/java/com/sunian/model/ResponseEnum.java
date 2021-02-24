package com.sunian.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author liull
 * @description
 * @date 2021/2/24
 */
@AllArgsConstructor
@Getter
public enum ResponseEnum {
    SUCCESS("0","成功"),
    FAILURE("1","失败"),
    INTERNAL_SERVER_ERROR("500", "服务器异常请联系管理员"),;

    private String code;
    private String message;
}
