package com.sunian.exception;

import com.sunian.model.Response;
import com.sunian.model.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liull
 * @description  全局异常捕获
 * @date 2021/2/24
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Response businessExceptionHandler(BusinessException e){
        log.error(e.getMessage(),e);
        return Response.businessExceptionResp(e);
    }

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response otherExceptionHandler(Exception e){
        log.error(e.getMessage(),e);
        return Response.otherExceptionResp(ResponseEnum.INTERNAL_SERVER_ERROR);
    }

    @ModelAttribute("user")
    public String getUserInfo(){
        return "liull";
    }

}
