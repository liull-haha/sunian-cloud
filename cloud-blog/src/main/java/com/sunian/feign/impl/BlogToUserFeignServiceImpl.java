package com.sunian.feign.impl;

import com.alibaba.fastjson.JSONObject;
import com.sunian.feign.BlogToUserFeignService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liull on 2019/7/10.
 */
@Component
public class BlogToUserFeignServiceImpl implements BlogToUserFeignService {
    @Override
    public String getAllDept() {
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("respCode","8888");
        map.put("respDesc","blog调用user出错，请稍候再试！");
        return JSONObject.toJSONString(map);
    }
}
