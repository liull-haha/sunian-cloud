package com.sunian.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunian.openFeign.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liull on 2019/7/10.
 */
@RestController
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping(value = "/user/sayUser")
    public String sayUser(@RequestParam("name")String name){
        logger.info("sayUser======={}",name);
        logger.debug("debug sayUser ===== {}",name);
        return "say user"+name;
    }

    @PostMapping(value = "/user",params = "method=com.sunian.user.getAllUser")
    public String getAllUser(){
        Map<String,Object> returnMap = new HashMap<>();
        try {
            returnMap = userService.getAllUser();
        }catch (Exception e){
            returnMap.put("respCode","8888");
            returnMap.put("respDesc","getAllUser失败："+e.toString());
            logger.error("getAllUser失败",e);
        }
        return JSONObject.toJSONString(returnMap);
    }

    @PostMapping(value = "/user",params = "method=com.sunian.user.getAllDept")
    public String getAllDept(){
        Map<String,Object> returnMap = new HashMap<>();
        try {
            returnMap = userService.getAllDept();
        }catch (Exception e){
            returnMap.put("respCode","8888");
            returnMap.put("respDesc","getAllDept失败："+e.toString());
            logger.error("getAllDept失败",e);
        }
        return JSONObject.toJSONString(returnMap);
    }

    public static void main(String[] args) {
        String a = "1111/";
        System.out.println(a.charAt(a.length()-1));
    }

}
