package com.sunian.feign;

import com.sunian.feign.impl.BlogToUserFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liull on 2019/7/10.
 */
@FeignClient(value = "user-service",fallback = BlogToUserFeignServiceImpl.class)
public interface BlogToUserFeignService {

    //不支持@GetMapping类似注解声明请求，需使用@RequestMapping(value = "url",method = RequestMethod.GET)
    //无法识别@RequestMapping中params属性值
    @RequestMapping(value = "/user?method=com.sunian.user.getAllDept",method = RequestMethod.POST)
    String getAllDept();
}
