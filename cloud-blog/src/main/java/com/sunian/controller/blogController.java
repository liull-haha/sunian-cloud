package com.sunian.controller;

import com.sunian.openFeign.UserOpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liull
 * @description
 * @date 2020/12/1
 */
@RestController
@RequestMapping(value = "/blog")
public class blogController {
    @Autowired
    UserOpenFeignService userOpenFeignService;

    @PostMapping(value = "/sayBlog")
    public String sayBlog(@RequestParam("name")String name){
        return userOpenFeignService.sayUser(name);
    }
}
