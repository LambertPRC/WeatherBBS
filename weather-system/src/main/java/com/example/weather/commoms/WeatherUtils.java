package com.example.weather.commoms;

import com.example.weather.bean.WeatherInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;


public class WeatherUtils {
    private static String weatherUrl = "http://wthrcdn.etouch.cn/weather_mini?city=";
    /**
     * 通过城市名称获取该城市的天气信息
     */
    public static String GetWeatherData(String cityname) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;//包装字符流。可以从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
        try {
            URL url = new URL(weatherUrl + cityname);//传过来的参数构成一个url链接
            URLConnection conn = url.openConnection();//表示指向URL指定资源的活动连接
            InputStream is = conn.getInputStream();//形成一个输入流
            GZIPInputStream gzin = new GZIPInputStream(is);//将输入流压缩格式
            // 设置读取流的编码格式，自定义编码
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8");//将经过处理后的url转成
            reader = new BufferedReader(isr);//使用指定的字符集读取字节并将它们解码为字符
            String line = null;
            while((line = reader.readLine()) != null){
                sb.append(line + " ");
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 将JSON格式数据进行解析 ，返回一个weather对象
     */
    public static Map<String,WeatherInfo> GetWeather(String weatherInfobyJson){

        Map<String,WeatherInfo> weatherInfoMap=new HashMap<>();

        JSONObject dataOfJson = JSONObject.fromObject(weatherInfobyJson);   // json天气数据
        if(dataOfJson.getInt("status") != 1000){
            return null;
        }
        // 从json数据中提取数据：城市、温度、小提醒
        dataOfJson = JSONObject.fromObject(dataOfJson.getString("data"));

        // 获取今天的天气预报信息：最高温度、最低温度、天气
        JSONArray forecast = dataOfJson.getJSONArray("forecast");
        JSONObject result = forecast.getJSONObject(0);


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");  // 时间的格式化
        for (int i = 0; i <4 ; i++) {

            // 创建WeatherInfo对象，提取所需的天气信息
            WeatherInfo weatherInfo = new WeatherInfo();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH,i);
            weatherInfo.setDate(sdf1.format(cal.getTime()));                // 时间
            SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
            weatherInfo.setWeek(sdf2.format(cal.getTime()));                // 星期



            weatherInfo.setCityname(dataOfJson.getString("city"));            // 城市
            weatherInfo.setTemp(dataOfJson.getString("wendu"));               // 温度
            weatherInfo.setTips(dataOfJson.getString("ganmao"));              // 小提示


            weatherInfo.setHighTemp(result.getString("high").substring(2));   // 最高气温
            weatherInfo.setLowTemp(result.getString("low").substring(2));     // 最低气温
            weatherInfo.setWeather(result.getString("type"));                 // 天气

            weatherInfoMap.put("weather_day"+i,weatherInfo);//存当天

        }


        return weatherInfoMap;
    }

}