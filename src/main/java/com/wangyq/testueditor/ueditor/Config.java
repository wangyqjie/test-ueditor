package com.wangyq.testueditor.ueditor;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//@Controller
public class Config {

    @RequestMapping(value = "/config")
    public void config(HttpServletRequest request, HttpServletResponse response){

        response.setContentType("application/json");
        String rootPath = "E:/wyq/idea/test-ueditor/out/production/resources/static/ueditor/jsp";
        response.setHeader("Content-Type" , "text/html");
        try{
            response.setCharacterEncoding( "utf-8" );
            String exec = new ActionEnter( request, rootPath ).exec();
            System.out.println(exec);
            PrintWriter writer = response.getWriter();
            System.out.println(rootPath);
            writer.write(new ActionEnter( request, rootPath ).exec());
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
