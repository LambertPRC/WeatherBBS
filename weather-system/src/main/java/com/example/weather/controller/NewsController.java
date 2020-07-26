package com.example.weather.controller;



import com.example.weather.bean.Article;
import com.example.weather.commoms.InitAndDestroy;
import com.example.weather.dao.ArticleDao;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class NewsController {

    @RequestMapping("/toNews")
    public String toNes(Model model) throws IOException {

        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();

        List<Article> articleList = articleDao.findAllArticleByStatus("已通过审核");

        List<Article> rankList = articleDao.getRankingList();

        model.addAttribute("rankList", rankList);
        model.addAttribute("articleList", articleList);

        initAndDestroy.destroy();
        return "news";
    }

    //前往文章
    @RequestMapping("toArticle")
    public String toArticle(Article article, Model model) throws IOException {


        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();

        article = articleDao.findArticleByAid(article.getArticleid());
        articleDao.changeClicNum(article);
        initAndDestroy.destroy();
        System.out.println(article.toString());
        model.addAttribute("article", article);

        Date date = new Date();
        System.out.println(date);
        model.addAttribute("date", date);
        return "article";
    }


    //根据用户名查询文章
    @RequestMapping("findArticleBaurhor")
    public String findArticleBaurhor(Article article, Model model) throws IOException {
        InitAndDestroy initAndDestroy = new InitAndDestroy();
        ArticleDao articleDao = initAndDestroy.articleDaoInit();

        List<Article> articleList = articleDao.findArticleByUname(article);
        List<Article> rankList = articleDao.getRankingList();

        model.addAttribute("rankList", rankList);
        model.addAttribute("articleList", articleList);

        initAndDestroy.destroy();
        return "news";

    }

}
