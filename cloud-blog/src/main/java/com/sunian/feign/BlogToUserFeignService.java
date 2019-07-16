package com.sunian.feign;

import com.sunian.feign.impl.BlogToUserFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by liull on 2019/7/10.
 */
@FeignClient(value = "user-service",fallback = BlogToUserFeignServiceImpl.class)
public interface BlogToUserFeignService {

    @PostMapping(value = "/user")
    String sayUser(@RequestParam("name")String name);
}
