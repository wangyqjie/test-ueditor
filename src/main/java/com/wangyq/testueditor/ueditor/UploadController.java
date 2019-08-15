package com.wangyq.testueditor.ueditor;

import com.baidu.ueditor.ActionEnter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadController {
    @Value("${root.path}")
    private String rootPath;

    @ResponseBody
    @RequestMapping("/config")
    public Object demo(HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String path = "/images/" + formater.format(new Date());
        String action = request.getParameter("action");
        //读取config.json路径，
        String rPath = rootPath+ "/ueditor/jsp";//.substring(1);
        String sPath = rootPath;
        System.out.println(rPath);
        //uploadimage是对应config.json中图片上传配置
        if (action.equals("uploadimage")) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("upfile");
            String filePath = sPath + path;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String imgName = multipartFile.getOriginalFilename();
            String hz = imgName.substring(imgName.indexOf("."));
            String uuid = UUID.randomUUID().toString();
            String uuid1 = UUID.randomUUID().toString();
            uuid = uuid.replace("-", "");
            uuid1 = uuid1.replace("-", "");
            String name = filePath + "//" + uuid + hz;
            String fileName = filePath + "//" + uuid1 + hz;
            File f = new File(name);
            File f1 = new File(fileName);
            try {
                multipartFile.transferTo(f);
                InputStream inputStream = new FileInputStream(f);
                OutputStream os = new FileOutputStream(f1);
                ImageUtil.resizeImage(inputStream, os, 300, hz.replace(".", ""));
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("original", multipartFile.getOriginalFilename());
            map.put("name", multipartFile.getOriginalFilename());
            map.put("url",path + "/" + uuid1 + hz);
            map.put("size", multipartFile.getSize());
            map.put("type", "." + hz);
            map.put("state", "SUCCESS");
            return map;
        } else {
            response.setContentType("application/json");
            try {
                response.setCharacterEncoding("utf-8");
                request.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(new ActionEnter(request, rPath).exec());
                writer.flush();
                writer.close();
            } catch (Exception r) {
                r.printStackTrace();
            }
        }
        return null;
    }

}