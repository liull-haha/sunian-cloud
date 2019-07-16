package com.sunian.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liull on 2019/7/10.
 */
@RestController
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/user")
    public String sayUser(@RequestParam("name")String name){
        logger.info("sayUser======={}",name);
        logger.debug("debug sayUser ===== {}",name);
        return "say user"+name;
    }

}
