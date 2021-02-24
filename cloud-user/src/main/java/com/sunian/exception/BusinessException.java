package com.sunian.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liull
 * @description
 * @date 2021/2/24
 */
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException{

    private String errorCode;
    private String errorMsg;

}
