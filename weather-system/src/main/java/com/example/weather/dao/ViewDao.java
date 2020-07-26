package com.example.weather.dao;

import com.example.weather.bean.View;

import java.util.List;

/*
@作    者：leeza
@时    间：2020-06-28 18:41
@简单说明：
*/
public interface ViewDao {


    List<View> findAllView();//后台查询所有景点

    void saveView(View view);//保存景点

    void deleteView(Integer viewid);//删点

    View findViewByVid(Integer viewid);//找点

    void alterView(View view);//改点

    List<View> findViewByWeather(View view);//根据天气类型找景点
}
