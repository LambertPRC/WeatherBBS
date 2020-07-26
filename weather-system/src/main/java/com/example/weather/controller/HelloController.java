package com.example.weather.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("test 111");

        return "success";
    }
    @RequestMapping("/upload")
    public String UPload(){

        System.out.println("提交");
        return "UPload";
    }
}
