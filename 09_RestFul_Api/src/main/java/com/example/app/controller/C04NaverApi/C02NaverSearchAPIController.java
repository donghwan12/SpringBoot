package com.example.app.controller.C04NaverApi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("/naver")
public class C02NaverSearchAPIController {

    private String Client_ID = "BBalMEHMGsJEO3RxG3iU";
    private String Secrit_id = "PBwiLib648";

    @GetMapping("/search/main")
    public void main() {
        log.info("GET /naver/search/main...");
    }

    @GetMapping("/search/{query}")
    public  Root search(@PathVariable("query") String query, Model model) {
        log.info("GET /naver/search/keyword ..." + query);

        // URL에 쿼리 파라미터 추가
        String url = "https://openapi.naver.com/v1/search/book.json?query=" + query;

        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", Client_ID);
        headers.add("X-Naver-Client-Secret", Secrit_id);

        // HttpEntity에 헤더 추가
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate로 GET 요청 보내기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Root> response = rt.exchange(url, HttpMethod.GET, entity, Root.class);

        // 응답 출력
        System.out.println(response.getBody());

        model.addAttribute("book",response.getBody());


        return response.getBody();
    }
    @Data
    private static class Item{
        public String title;
        public String link;
        public String image;
        public String author;
        public String discount;
        public String publisher;
        public String pubdate;
        public String isbn;
        public String description;
    }
    @Data
    private static class Root{
        public String lastBuildDate;
        public int total;
        public int start;
        public int display;
        public ArrayList<Item> items;
    }
}
