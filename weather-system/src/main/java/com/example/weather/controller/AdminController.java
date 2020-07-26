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
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {


    //管理员登录，并且登录后把审核信息显示
    @RequestMapping("/toAdminBackend")
    public String toAdminBackend(User user, Model model) throws Exception {

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUsernameAndPassword(user);//登录验证
        if (user == null || user.getUsertype() != 1) {
            return "adminLogin";
        } else {
            model.addAttribute("user", user);
            ArticleDao articleDao = initAndDestroy.articleDaoInit();

            List<Article> articleList = articleDao.findAllArticleByStatus("审核中");


            model.addAttribute("articleList", articleList);
            initAndDestroy.destroy();
            return "adminBackendIndex";
        }
    }

    //后台切换已经审核的文章
    @RequestMapping("/findVerifyedArticle")
    public String findVerifyedArticle(String status, User user, Model model) throws Exception {
        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();
        List<Article> articleList = articleDao.findAllArticleByStatus(status);

        //还要携带管理员的信息
        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(Integer.valueOf(user.getUserid()));
        model.addAttribute("user", user);

        initAndDestroy.destroy();
        model.addAttribute("articleList", articleList);
        return "adminBackendIndex";

       /* if (articleList.isEmpty()) {
            return "remain";
        } else {
            model.addAttribute("articleList", articleList);
            return "adminBackendIndex";
        }*/
    }


    //根据携带的状态参数修改文章状态，然后返回审核中的文章页面
    @RequestMapping("/changeArticleStatus")
    public String articlePermission(Article article, User user, Model model) throws Exception {


        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();

        articleDao.changeArticleStatusByAid(article);
        List<Article> articleList = articleDao.findAllArticleByStatus("审核中");
        initAndDestroy.destroy();//一改变就submit，否则无法生效


        model.addAttribute("articleList", articleList);


//修改完回到之前的页面还要携带管理员的信息
        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(Integer.valueOf(user.getUserid()));
        model.addAttribute("user", user);
        initAndDestroy.destroy();


        return "adminBackendIndex";
    }


    //删除文章
    @RequestMapping("/deleteArticle")
    public String deleteArticle(Article article, User user, Model model) throws Exception {
//删除
        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();

        articleDao.deleteArticle(Integer.valueOf(article.getArticleid()));
        initAndDestroy.destroy();

        articleDao = initAndDestroy.articleDaoInit();//submit了再次开启，否则不生效

        //管理员回显
        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(Integer.valueOf(user.getUserid()));

        //已审核文章回显
        List<Article> articleList = articleDao.findAllArticleByStatus("已通过审核");
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
        return "adminBackendIndex";

    }

    //前往用户管理
    @RequestMapping("/toUsermMenage")
    public String toUsermMenage(User user, Model model) throws Exception {

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(user.getUserid());
        model.addAttribute("user", user);

        List<User> userList = userDao.findAllUser();
        model.addAttribute("userList", userList);

        initAndDestroy.destroy();


        return "userManege";
    }


    //删除用户
    @RequestMapping("/deleteUser")
    public String deleteUser(User user, @RequestParam("duser") String duser, Model model) throws Exception {

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();
        user = userDao.findUserByUerid(user.getUserid());
        model.addAttribute("user", user);

        userDao.deleteUserByUid(Integer.valueOf(duser));
        initAndDestroy.destroy();

         userDao = initAndDestroy.init();
         List<User> userList=userDao.findAllUser();
         model.addAttribute("userList",userList);
         initAndDestroy.destroy();

        return "userManege";
    }
}
