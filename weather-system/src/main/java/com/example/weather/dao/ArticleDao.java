package com.example.weather.dao;

import com.example.weather.bean.Article;

import java.util.List;

/*
@作    者：leeza
@时    间：2020-06-25 22:27
@简单说明：
*/
public interface ArticleDao {

    int saveArticle(Article article);//保存文章

    List<Article> findAllArticleByUid(Integer userid);

    List<Article> findAllArticleByStatus(String status);//根据文章状态查询文章

    void changeArticleStatusByAid(Article article);//根据文章id携带状态参数改变文章的审核状态

    void deleteArticle(Integer articleid);//根据文章id删除文章

    Article findArticleByAid(Integer articleid);//根据文章id查找文章

    void changeClicNum(Article article);//根据article属性修改点击量，每次加一

   void alterArticle(Article article);//修改文章

    List<Article> getRankingList();//获得排行数据

    List<Article> findArticleByUname(Article article);//根据用户名找文章

}
