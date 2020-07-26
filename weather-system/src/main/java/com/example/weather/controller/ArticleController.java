package com.example.weather.controller;



import com.example.weather.bean.Article;
import com.example.weather.bean.User;
import com.example.weather.commoms.InitAndDestroy;
import com.example.weather.dao.ArticleDao;
import com.example.weather.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class ArticleController {


    //个人中心携带用户属性到添加文章页面
    @RequestMapping("/toEditPage")
    public String toEditPage(HttpServletRequest request,Article article, Model model) throws Exception {



        String userid = request.getParameter("userid");
        InitAndDestroy initAndDestroy = new InitAndDestroy();
        UserDao userDao = initAndDestroy.init();

        if (article.getArticleid()!=null){
            ArticleDao articleDao=initAndDestroy.articleDaoInit();
            article=articleDao.findArticleByAid(article.getArticleid());
            model.addAttribute("article",article);


        }

        User user = userDao.findUserByUerid(Integer.valueOf(userid));

        model.addAttribute("author", user);

        initAndDestroy.destroy();//关闭流

        return "addArticle";
    }

    @RequestMapping("/saveArticle")
    public String saveArticle(@RequestParam(value = "file") MultipartFile file, Article article, Model model) throws Exception {
        //对图片的处理============
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }

        String fileName = file.getOriginalFilename();  // 文件名

        String dataBasePath = "/asserts/img/articleImg/" + fileName;
        System.out.println(dataBasePath);

        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        File current = new File("");
        try {
            String path = current.getCanonicalPath().replaceAll("\\\\", "//") + "//src//main//resources//static//asserts//img//articleImg//";


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
            System.out.println(filename1);

        } catch (IOException e) {
            e.printStackTrace();
        }

//=================================================
        Random id = new Random();//赋值用户id
        article.setArticleid(id.nextInt(99999));
        article.setClicknum(0);//点击量
        article.setImg(dataBasePath);
       article.setBuildtime(new Date());

        //======交给mubatis====

        //文章的存取
        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();
        articleDao.saveArticle(article);

        List<Article> articleList=articleDao.findAllArticleByUid(Integer.valueOf(article.getUserid()));
        model.addAttribute("articleList",articleList);
        initAndDestroy.destroy();//关闭流

        //用户的再存取
        UserDao userDao = initAndDestroy.init();
        User user = userDao.findUserByUerid(article.getUserid());//获取用户保存到域
        model.addAttribute("user", user);
        initAndDestroy.destroy();//关闭流


        System.out.println(article.toString());
        return "backendIndex";
    }


    //修改文章
    @RequestMapping("/alterArticle")
    public String alterArticle(Article newArticle,Model model) throws Exception {
        System.out.println(newArticle.toString());
        InitAndDestroy initAndDestroy=new InitAndDestroy();
        ArticleDao articleDao=initAndDestroy.articleDaoInit();
        Article article=articleDao.findArticleByAid(Integer.valueOf(newArticle.getArticleid()));
        article.setTitle(newArticle.getTitle());
        article.setContent(newArticle.getContent());
        article.setBuildtime(new Date());

        articleDao.alterArticle(article);
        initAndDestroy.destroy();


        //再次回到后台页面需要的数据
        UserDao userDao = initAndDestroy.init();
        User user = userDao.findUserByUerid(Integer.valueOf(article.getUserid()));

        model.addAttribute("user", user);
        System.out.println(user.getUsername());


         articleDao = initAndDestroy.articleDaoInit();
        List<Article> articleList=articleDao.findAllArticleByUid(Integer.valueOf(article.getUserid()));
        model.addAttribute("articleList",articleList);

        initAndDestroy.destroy();//关闭流
        return "backendIndex";
    }


}
