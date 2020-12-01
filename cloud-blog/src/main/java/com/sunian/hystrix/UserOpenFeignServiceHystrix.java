package com.sunian.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.sunian.openFeign.UserOpenFeignService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liull
 * @description
 * @date 2020/12/1
 */
@Component
@Slf4j
public class UserOpenFeignServiceHystrix implements FallbackFactory<UserOpenFeignService> {

    @Override
    public UserOpenFeignService create(Throwable throwable) {
        log.error("调用user_service异常=====" + throwable);
        return new UserOpenFeignService() {
            @Override
            public String sayUser(String name) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("respCode", "8888");
                map.put("respDesc", "调用user_service异常" + throwable);
                return JSONObject.toJSONString(map);
            }
        };
    }
}
