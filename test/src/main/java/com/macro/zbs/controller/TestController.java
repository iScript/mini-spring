package com.macro.zbs.controller;

import com.macro.fw.beans.AutoWired;
import com.macro.fw.web.mvc.Controller;
import com.macro.fw.web.mvc.RequestMapping;
import com.macro.fw.web.mvc.RequestParam;
import com.macro.zbs.service.TestService;

@Controller
public class TestController {

    @AutoWired
    private TestService testService;

    @RequestMapping("/gettest")
    public int getTest(@RequestParam("name") String name,@RequestParam("experience") String experience){

        return testService.tests();
    }
}
