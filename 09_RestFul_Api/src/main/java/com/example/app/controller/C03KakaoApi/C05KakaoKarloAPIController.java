package com.example.app.controller.C03KakaoApi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("/kakao/karlo")
public class C05KakaoKarloAPIController {

    private String rest_Key="3d94f21ef48759eb36a66c5d7be44732";
    @GetMapping("/index")
    public void index(){
        log.info("GET /kakao/karlo/index");
    }

    @GetMapping("/req/{prompt}")
    public @ResponseBody KarloResponse req(@PathVariable("prompt") String prompt){


        log.info("GET /kakao/karlo/req..." + prompt);

        //URL
        String url="https://api.kakaobrain.com/v2/inference/karlo/t2i";
        //HEADER
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","KakaoAK "+rest_Key);
        headers.add("Content-Type","application/json");
        //PARAM
        JSONObject params=new JSONObject();
        params.put("version","v2.1");
        params.put("prompt",prompt);
        params.put("width","1024");
        params.put("height","1024");
        //ENTITY
        HttpEntity<JSONObject> entity=new HttpEntity(params,headers);
        //REQUEST
        RestTemplate rt=new RestTemplate();
        ResponseEntity<KarloResponse> response=rt.exchange(url, HttpMethod.POST,entity,KarloResponse.class);
        //RESPONSE
        System.out.println(response.getBody());

        return response.getBody();

    }
    @GetMapping("/main")
    public void main(){
        log.info("GET/naver/main...");

    }


    @Data
    private static class Image {
        public String id;
        public String image;
        public int seed;
        public Object nsfw_content_detected;
        public Object nsfw_score;
    }
    @Data
    private static class KarloResponse{
        public String id;
        public String model_version;
        public ArrayList<Image> images;
    }


}