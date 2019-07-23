package com.sunian.controller;

import com.sunian.feign.BlogToUserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liull on 2019/7/10.
 */
@RestController
public class blogController {

    @Autowired
    BlogToUserFeignService blogToUserFeignService;

    @PostMapping(value = "/blog",params = "method=com.sunian.blog.sayBlog")
    public String sayBlog(){
        return blogToUserFeignService.getAllDept();
    }
}
