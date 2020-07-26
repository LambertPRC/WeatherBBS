package com.example.weather.controller;



import com.example.weather.bean.User;
import com.example.weather.bean.View;
import com.example.weather.commoms.InitAndDestroy;
import com.example.weather.dao.UserDao;
import com.example.weather.dao.ViewDao;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.soap.Addressing;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Controller
public class ViewController {

    //后台前往景点管理，携带管理员信息和景点信息
    @RequestMapping("/toViewMenage")
    public String toViewMenage(User user, Model model) throws Exception {


        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ViewDao viewDao = initAndDestroy.viewDaoInit();

        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(user.getUserid());

        List<View> viewList = viewDao.findAllView();
        initAndDestroy.destroy();

        model.addAttribute("user", user);
        model.addAttribute("viewList", viewList);

        return "viewManege";
    }

    @RequestMapping("/toAddView")
    public String toAddView(User user,View view, Model model) throws Exception {

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();

        model.addAttribute("view",view);

        user = userDao.findUserByUerid(user.getUserid());
        model.addAttribute("user", user);


        return "addView";
    }

    @RequestMapping("/saveview")
    public String saveview(@RequestParam(value = "file") MultipartFile file, User user, View view, Model model) throws Exception {
        //对图片的处理============
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }

        String fileName = file.getOriginalFilename();  // 文件名

        String dataBasePath = "/asserts/img/viewImg/" + fileName;

        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        File current = new File("");
        try {
            String path = current.getCanonicalPath().replaceAll("\\\\", "//") + "//src//main//resources//static//asserts//img//viewImg//";


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
            String filename1 = path + fileName;

        } catch (IOException e) {
            e.printStackTrace();
        }

        //=======

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(user.getUserid());
        model.addAttribute("user", user);

        view.setViewimg(dataBasePath);
        Random viewid = new Random();//赋值用户id
        view.setViewid(viewid.nextInt(99999));

        ViewDao viewDao = initAndDestroy.viewDaoInit();
        viewDao.saveView(view);
        initAndDestroy.destroy();

        viewDao=initAndDestroy.viewDaoInit();
        List<View> viewList=viewDao.findAllView();
        model.addAttribute("viewList",viewList);



        return "viewManege";
    }

    @RequestMapping("/deleteView")
    public String deleteView(User user,View view,Model model) throws Exception {
        InitAndDestroy initAndDestroy =new InitAndDestroy();
        UserDao userDao=initAndDestroy.init();
        user=userDao.findUserByUerid(user.getUserid());
        model.addAttribute("user",user);

        ViewDao viewDao=initAndDestroy.viewDaoInit();
        viewDao.deleteView(Integer.valueOf(view.getViewid()));
        initAndDestroy.destroy();

        viewDao=initAndDestroy.viewDaoInit();
        List<View> viewList=viewDao.findAllView();
        model.addAttribute("viewList",viewList);
        initAndDestroy.destroy();

        return "viewManege";


    }


    @RequestMapping("/toAlterView")
    public String alterView(User user,View view,Model model) throws Exception {

        InitAndDestroy initAndDestroy =new InitAndDestroy();
        UserDao userDao=initAndDestroy.init();
        user=userDao.findUserByUerid(user.getUserid());
        model.addAttribute("user",user);

        ViewDao viewDao=initAndDestroy.viewDaoInit();
        view=viewDao.findViewByVid(view.getViewid());
        initAndDestroy.destroy();
        model.addAttribute("view",view);


        return "addView";
    }

    @RequestMapping("/alterView")
    public String alterView(User user,Model model,View view) throws Exception {
        InitAndDestroy initAndDestroy =new InitAndDestroy();
        UserDao userDao=initAndDestroy.init();
        user=userDao.findUserByUerid(user.getUserid());
        model.addAttribute("user",user);



        ViewDao viewDao=initAndDestroy.viewDaoInit();
        viewDao.alterView(view);
        initAndDestroy.destroy();

        viewDao=initAndDestroy.viewDaoInit();
        List<View> viewList=viewDao.findAllView();
        model.addAttribute("viewList",viewList);
        initAndDestroy.destroy();



        return "viewManege";
    }

    @RequestMapping("/tourismRecom")
    public String tourismRecom(View view,Model model) throws IOException {
        InitAndDestroy initAndDestroy=new InitAndDestroy();
        ViewDao viewDao=initAndDestroy.viewDaoInit();

        List<View> viewList=viewDao.findViewByWeather(view);
        initAndDestroy.destroy();
      model.addAttribute("viewList",viewList);
        return "viewRecom";
    }

@RequestMapping("/toview")
    public String toview(View view,Model model) throws IOException {

        InitAndDestroy initAndDestroy=new InitAndDestroy();
        ViewDao viewDao=initAndDestroy.viewDaoInit();

        view=viewDao.findViewByVid(view.getViewid());
        model.addAttribute("view",view);


        return "view";
    }
}
