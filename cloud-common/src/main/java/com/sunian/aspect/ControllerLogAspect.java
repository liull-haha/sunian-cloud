package com.sunian.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liull on 2019/7/12.
 *
 *
 * 整个表达式可以分为五个部分：

 1、execution(): 表达式主体。

 2、第一个*号：表示返回类型，*号表示所有的类型。

 3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。

 4、第二个*号：表示类名，*号表示所有的类。

 5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
 *
 * */
@Component
@Aspect
public class ControllerLogAspect {

    private static Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    /*
    * 以 controller 包下定义的所有请求为切入点
    * */
    @Pointcut("execution(public * com.sunian.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 打印请求相关参数
        logger.info("请求开始=================================");
        // 打印请求 url
        logger.info("URL       : {}", request.getRequestURL().toString());
        // 打印 Http method
        logger.info("请求方式   : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("调用方法   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求入参
        logger.info("请求入参   : {}", new Gson().toJson(joinPoint.getArgs()));
    }

    @After("webLog()")
    public void doAfter() throws Throwable {
        logger.info("请求结束====================================");
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //调用方法
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        logger.info("请求出参  : {}", new Gson().toJson(result));
        // 执行耗时
        logger.info("执行消耗时间 : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }
}
