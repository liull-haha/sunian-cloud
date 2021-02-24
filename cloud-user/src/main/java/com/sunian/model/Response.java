package com.sunian.model;

import com.sunian.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liull
 * @description
 * @date 2021/2/24
 */
@Data
@AllArgsConstructor
public class Response<T> {
    private String code;
    private String message;
    private T data;

    /**
     * 返回自定义异常信息
     * @param e
     * @return
     */
    public static Response businessExceptionResp(BusinessException e){
        return new Response(e.getErrorCode(),e.getErrorMsg(),null);
    }

    /**
     * 返回其他异常信息
     * @param responseEnum
     * @return
     */
    public static Response otherExceptionResp(ResponseEnum responseEnum){
        return new Response(responseEnum.getCode(),responseEnum.getMessage(),null);
    }

}
