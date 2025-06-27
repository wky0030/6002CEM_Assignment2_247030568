package com.forsale.mini.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 * @author pan_junbiao
 **/
@Controller
public class IndexController{
    @RequestMapping("/test")
    public String test3()
    {
        return "test";
    }
}