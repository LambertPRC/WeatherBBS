package com.example.weather;

import com.example.weather.bean.User;
import com.example.weather.dao.UserDao;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
@作    者：leeza
@时    间：2020-06-24 11:47
@简单说明：
*/
public class QuickTest {


    private InputStream in;
    private UserDao userDao;
    private SqlSession session;


    @BeforeEach
    public void init() throws Exception {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂

        System.out.println("111");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        userDao = session.getMapper(UserDao.class);
        System.out.println(userDao == null);
        System.out.println(session == null);

    }

    @AfterEach
    public void destroy() throws IOException {
        session.commit();//避雷：没有这个commit是无法提交到数据库的
        session.close();

        in.close();

    }


    @Test
    public void random() {
        Random id = new Random();
        System.out.println(id.nextInt(99999));

    }

    /*    @Test
        public void testRegister(){

            User user=new User();
            user.setUserid(5555);
            user.setPassword("sdfasdf");
            user.setPhone(13325545);
            user.setUsername("saidj");
            user.setUsertype(1);

      int i= userDao.userRegister(user);
            System.out.println(i);

        }*/
    @Test
    public void testFindAllUser() {
        List<User> list = userDao.findAllUser();
        for (User user : list) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void testPath() throws Exception {

        File file = new File("");
        try {
            String path4 = file.getCanonicalPath();
            System.err.println("path4：" + path4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @Test
    public void testTimeFomat(){
        Date date=new Date();
        System.out.println(date);




    }

@Test
    public void testgetDate(){

/*
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=sdf.pa
        System.out.println("格式化当天"+date);

        Calendar calendar=Calendar.getInstance();
        System.out.println("格式化后"+calendar);
*/



        // 获取当前日期：日期、星期
        Calendar cal = Calendar.getInstance();    							     // Calendar类的实例化
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");  // 时间的格式化
    cal.add(Calendar.DAY_OF_MONTH,0);

        System.out.println(sdf1.format(cal.getTime()));
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        System.out.println(sdf2.format(cal.getTime()));


/*        //下一天
    cal.add(Calendar.DAY_OF_MONTH,1);
    System.out.println(sdf1.format(cal.getTime()));
    System.out.println(sdf2.format(cal.getTime()));*/

    }


    @Test
    public void testMap(){

        Map<String,Integer> map=new HashedMap();
        for (int i = 234; i <239 ; i++) {

            map.put("value"+i,i);
        }

        System.out.println(map.get("value235"));

    }
    @Test
    public void testGrtIp() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(InetAddress.getByName("www.sina.com.cn"));

    }


    @Test
    public void test1(){


        System.out.println("测试");
    }
}
