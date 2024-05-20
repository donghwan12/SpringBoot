package com.example.app.controller.C01OpneDataApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/openData")
public class FoodController {

    @GetMapping(value = "/food/{addr}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getFoodInfo(@PathVariable("addr") String addr) throws JsonProcessingException, ParseException {
        log.info("GET/opendata/food"+addr);

        //요청 URL 지정
        String url="https://www.daegufood.go.kr/kor/api/tasty.html?mode=json&addr="+addr;

        //Request
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,null,String.class);
        System.out.println(response);
        System.out.println();
        System.out.println(response.getBody());

        //String->JSOn 변환
        ObjectMapper objectMapper=new ObjectMapper();
        JSONObject result=objectMapper.readValue(response.getBody(),JSONObject.class);
        System.out.println();
        System.out.println(result);

        //Json Body부분을 다시 JSON 형태로 parse
//        System.out.println(result.get("data"));
        JSONParser parser=new JSONParser();
        JSONObject data_init= (JSONObject) parser.parse(response.getBody());
        JSONArray data= (JSONArray)data_init.get("data");

        Map<String,String> map=new HashMap();
        data.forEach(el->{
            JSONObject obj=(JSONObject)el;
            System.out.println(obj.get("BZ_NM")+" : "+obj.get("GNG_CS"));
            map.put(obj.get("BZ_NM").toString(),obj.get("GNG_CS").toString());
        });

        return null;
    }
}
