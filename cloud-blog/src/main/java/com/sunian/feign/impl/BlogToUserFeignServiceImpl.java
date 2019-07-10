package com.sunian.feign.impl;

import com.sunian.feign.BlogToUserFeignService;
import org.springframework.stereotype.Component;

/**
 * Created by liull on 2019/7/10.
 */
@Component
public class BlogToUserFeignServiceImpl implements BlogToUserFeignService {
    @Override
    public String sayUser(String name) {
        return "blog调用user出错，请稍候再试！";
    }
}
