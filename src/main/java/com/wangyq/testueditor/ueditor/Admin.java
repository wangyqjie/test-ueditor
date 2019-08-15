package com.wangyq.testueditor.ueditor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Admin {

    @RequestMapping(value = "/test")
    public String index(){

        return "admin/index";
    }
}
