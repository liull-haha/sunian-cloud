package com.sunian.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liull
 * @description
 * @date 2020/12/4
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/helloMethod")
    public String hello() {
        return "hello";
    }
}