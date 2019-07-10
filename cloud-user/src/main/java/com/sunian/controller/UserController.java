package com.sunian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liull on 2019/7/10.
 */
@RestController
public class UserController {

    @PostMapping(value = "/user",params = "method=sayUser")
    public String sayUser(@RequestParam("name")String name){
        return "say user"+name;
    }

}
