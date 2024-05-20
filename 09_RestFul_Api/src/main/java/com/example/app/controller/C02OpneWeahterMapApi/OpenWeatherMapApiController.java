package com.example.app.controller.C02OpneWeahterMapApi;

import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
@RequestMapping("/openWeather")
public class OpenWeatherMapApiController {

    private String serviceKey="f11ecd7fab088a70b10b298c79c1c672";

    @GetMapping("/get/{lat}/{log}")
    public void currentWeather(
            @PathVariable("lat") String lat,
            @PathVariable("log") String log){
        //URL
        String url="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+log+"&appid="+serviceKey;

        //request
        RestTemplate rt=new RestTemplate();
        ResponseEntity<String> response= rt.exchange(url, HttpMethod.GET,null,String.class);
        System.out.println(response.getBody());

    }

    //------------------
    @Data
    private static class Clouds{
        public int all;
    }
    @Data
    private static class Coord{
        public double lon;
        public double lat;
    }
    @Data
    private static class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }

    //상위클래스
    @Data
    private static class Root{
        public Coord coord;
        public ArrayList<Weather> weather;
        public String base;
        public Main main;
        public int visibility;
        public Wind wind;
        public Clouds clouds;
        public int dt;
        public Sys sys;
        public int timezone;
        public int id;
        public String name;
        public int cod;
    }

    @Data
    private static class Sys{
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }
    @Data
    private static class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }
    @Data
    private static class Wind{
        public double speed;
        public int deg;
    }

}
