package com.example.weather.controller;

import com.example.weather.bean.Article;
import com.example.weather.bean.City;
import com.example.weather.bean.WeatherInfo;
import com.example.weather.commoms.InitAndDestroy;
import com.example.weather.commoms.WeatherUtils;
import com.example.weather.dao.ArticleDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
public class WeatherController {

    @RequestMapping("/tomap")
    public String returnCity(WeatherInfo weatherInfo, Model model) {
        System.out.println(weatherInfo.getCityname());
        String info = WeatherUtils.GetWeatherData(weatherInfo.getCityname());
        Map<String,WeatherInfo> weatherInfoMap = WeatherUtils.GetWeather(info);
        System.out.println(weatherInfoMap);
        System.out.println(weatherInfoMap.get("weather_day0"));

        model.addAttribute("weatherInfoMap",weatherInfoMap);

        return "weatherIndex";


    }



}
