package com.sunian.openFeign;

import com.sunian.hystrix.UserOpenFeignServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liull
 * @description
 * @date 2020/12/1
 */
@FeignClient(name = "USER-SERVICE",fallbackFactory = UserOpenFeignServiceHystrix.class)
public interface UserOpenFeignService {

    @PostMapping(value = "/user/sayUser")
    public String sayUser(@RequestParam("name")String name);
}
