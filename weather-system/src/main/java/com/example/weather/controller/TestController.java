package com.example.weather.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class TestController {
    @RequestMapping(value = "/testupload")//测试的url接口
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        File current = new File("");
        try {
            String path = current.getCanonicalPath().replaceAll("\\\\","//")+"//src//main//resources//static//asserts//img//";


            //String uploadPath=path+"//src//main//resources//static//asserts//img//";
            System.err.println("uploadPath：" + path);
            File dest = new File(path + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filename1 = path+ fileName;
            System.out.println(filename1);

        } catch (IOException e) {
            e.printStackTrace();
        }



        return "success";
    }



    @RequestMapping("/go")
    public String goTest(){

        return "adminLogin";
    }

    @RequestMapping("/goAdmin")
    public String goAdminLofin(){

        System.out.println("???");

        return "/commoms/top";
    }
}
