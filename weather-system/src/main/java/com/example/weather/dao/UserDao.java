package com.example.weather.dao;

import com.example.weather.bean.User;

import java.util.List;

/*
@作    者：leeza
@时    间：2020-06-24 11:33
@简单说明：
*/
public interface UserDao {

     int userRegister(User user);//用户注册

     List<User> findAllUser();//查询所有用户

     User findUserByUsernameAndPassword(User user);//登录验证

     User findUserByUerid(Integer id);//根据id查询用户对象

     void changeUInfoByNameAndPassword(User user);//x修改用户信息

     void deleteUserByUid(Integer userid);


}
