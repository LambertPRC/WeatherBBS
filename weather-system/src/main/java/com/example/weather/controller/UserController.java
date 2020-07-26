package com.example.weather.controller;

import com.example.weather.bean.Article;
import com.example.weather.bean.User;
import com.example.weather.commoms.InitAndDestroy;
import com.example.weather.dao.ArticleDao;
import com.example.weather.dao.UserDao;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Random;


@Controller
public class UserController {


    //前往注册登录界面
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }


    //注册，成功与否都返回登录界面
    @RequestMapping("/register")
    public String register(User user) throws Exception {

        Random id = new Random();//赋值用户id
        user.setUserid(id.nextInt(99999));

        System.out.println(user.toString());

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();
        userDao.userRegister(user);

        initAndDestroy.destroy();
        return "login";
    }


    //登录
    @RequestMapping("/userToNews")
    public String userToNews(User user, Model model) throws Exception {

        if (user.getUsername() == null || user.getPassword() == null) {

            return "login";
        } else {

            InitAndDestroy initAndDestroy = new InitAndDestroy();
            UserDao userDao = initAndDestroy.init();
            ArticleDao articleDao = initAndDestroy.articleDaoInit();


            User userChecking = userDao.findUserByUsernameAndPassword(user);
            System.out.println(userChecking.toString());
            List<Article> articleList = articleDao.findAllArticleByStatus("已通过审核");
            List<Article> rankList = articleDao.getRankingList();

            model.addAttribute("rankList", rankList);
            model.addAttribute("articleList", articleList);
            initAndDestroy.destroy();

            boolean checking = (userChecking == null);
            if (checking) {
                return "login";
            } else {
                model.addAttribute("user", userChecking);
                return "news";
            }

        }


    }


    //携带用户信息返回论坛主页

    @RequestMapping("/backendToNews")
    public String backendToNews(User user, Model model) throws Exception {
        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();
        User userChecking = userDao.findUserByUerid(user.getUserid());
        List<Article> articleList = articleDao.findAllArticleByStatus("已通过审核");
        List<Article> rankList = articleDao.getRankingList();

        model.addAttribute("rankList", rankList);

        model.addAttribute("articleList", articleList);
        initAndDestroy.destroy();
        System.out.println(userChecking.toString());

        boolean checking = (userChecking == null);
        if (checking) {
            return "login";
        } else {
            model.addAttribute("user", userChecking);
            return "news";
        }

    }


    //用户前往后台
    @RequestMapping("/toBackend")
    public String toBackend(HttpServletRequest request, Model model) throws Exception {
        String userid = request.getParameter("userid");

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();
        User user = userDao.findUserByUerid(Integer.valueOf(userid));

        model.addAttribute("user", user);
        System.out.println(user.getUsername());


        ArticleDao articleDao = initAndDestroy.articleDaoInit();
        List<Article> articleList = articleDao.findAllArticleByUid(Integer.valueOf(userid));
        model.addAttribute("articleList", articleList);

        initAndDestroy.destroy();//关闭流
        return "backendIndex";
    }

    //修改个人信息
    @RequestMapping("/toChangeUserInfo")
    public String toChangeUserInfo(User user, Model model) throws Exception {
        System.out.println(user.toString());

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();
        userDao.changeUInfoByNameAndPassword(user);
        initAndDestroy.destroy();//关闭流

        userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(Integer.valueOf(user.getUserid()));

        model.addAttribute("user", user);

        ArticleDao articleDao = initAndDestroy.articleDaoInit();
        List<Article> articleList = articleDao.findAllArticleByUid(Integer.valueOf(user.getUserid()));
        model.addAttribute("articleList", articleList);

        initAndDestroy.destroy();//关闭流

        return "backendIndex";
    }

    @RequestMapping("/userDeleteArticle")
    public String userDeleteArticle(Article article, User user, Model model) throws Exception {

        //删除
        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();

        articleDao.deleteArticle(Integer.valueOf(article.getArticleid()));
        initAndDestroy.destroy();


        //管理员回显
        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(Integer.valueOf(user.getUserid()));

        //已审核文章回显
        articleDao = initAndDestroy.articleDaoInit();//submit了再次开启，否则不生效
        List<Article> articleList = articleDao.findAllArticleByUid(user.getUserid());
        initAndDestroy.destroy();

      /*  if (articleList.isEmpty()){
            model.addAttribute("user",user);
            return "adminBackendIndex";
        }else {
            model.addAttribute("articleList",articleList);
            model.addAttribute("user",user);
            return "adminBackendIndex";
        }*/
        model.addAttribute("articleList", articleList);
        model.addAttribute("user", user);
        return "backendIndex";


    }
}