package com.sunian.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunian.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * ceshi
     * @param name
     * @return
     */
    @PostMapping(value = "/user/sayUser")
    public String sayUser(@RequestParam("name")String name, @ModelAttribute("user") String user){
        logger.info("sayUser======={}",name);
        logger.debug("debug sayUser ===== {}",user);
        return "say user"+name+"|"+user;
    }

    /**
     * ceshi
     * @param name
     * @return
     */
    @PostMapping(value = "/user/sayHello")
    public String sayHello(@RequestParam("name")String name, @ModelAttribute("user") String user){
        logger.info("sayUser======={}",name);
        logger.debug("debug sayUser ===== {}",user);
        return "say Hello"+name+"|"+user;
    }

    /**
     * ceshi
     * @param name
     * @return
     */
    @PostMapping(value = "/user/sayHello")
    public String sayHello2(@RequestParam("name")String name, @ModelAttribute("user") String user){
        logger.info("sayUser======={}",name);
        logger.debug("debug sayUser ===== {}",user);
        return "say Hello"+name+"|"+user;
    }

}
