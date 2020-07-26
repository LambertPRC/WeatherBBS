package com.example.weather.commoms;

import com.example.weather.dao.ArticleDao;
import com.example.weather.dao.UserDao;
import com.example.weather.dao.ViewDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;


public class InitAndDestroy {
    private InputStream in;
    private UserDao userDao;
    private SqlSession session;


    private ArticleDao articleDao;
    public ViewDao viewDao;

//============用户流的开关
    public UserDao init() throws Exception {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        userDao = session.getMapper(UserDao.class);

        return userDao;
    }
    public void destroy() throws IOException {
        session.commit();//避雷：没有这个commit是无法提交到数据库的
        session.close();

        in.close();

    }

//文章流的开关
    public ArticleDao articleDaoInit() throws IOException {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        articleDao= session.getMapper(ArticleDao.class);
        return articleDao;


    }

    //景点流的开关
    public ViewDao viewDaoInit() throws IOException {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        viewDao= session.getMapper(ViewDao.class);
        return viewDao;


    }



}
