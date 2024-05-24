package com.example.app.controller.C06PortOneAPIController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("/portOne")
public class PortController {

    private PortOneTokenResponse portOneTokenResponse;

    @GetMapping("/main")
    public void main() {
        log.info("GET/portOne/main...");
    }

    // 액세스 토큰 발급받기
    @GetMapping("/getToken")
    public @ResponseBody void getToken() {
        log.info("GET/portOne/getToken....");
        // URL
        String url = "http://api.iamport.kr/users/getToken";
        // Header
        HttpHeaders headers = new HttpHeaders();
        // Param
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", "8040667643402530");
        params.add("imp_secret", "Hj7uHUbalnDF1UzSylkofCqWaJzxqeBdDZXWTMOVUksE3Qg0OSAnsHJCFCe1n7u7yfRdF7NmhcPeAqSp");
        // Entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        // Request
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneTokenResponse> response = rt.exchange(url, HttpMethod.POST, entity, PortOneTokenResponse.class);
        // Response
        log.info(response.getBody().toString());
        this.portOneTokenResponse = response.getBody();
    }

    @Data
    private static class TokenResponse {
        public String access_token;
        public int now;
        public int expired_at;
    }

    @Data
    private static class PortOneTokenResponse {
        public int code;
        public Object message;
        public TokenResponse response;
    }

    // 인증된 사용자 정보 가져오기
    @GetMapping(value = "/getAuthinfo/{imp_uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PortOneAuthinfoResponse getAuthinfo(@PathVariable("imp_uid") String imp_uid) {
        getToken();
        log.info("GET/portOne/getAuthinfo... " + imp_uid);

        // URL
        String url = "http://api.iamport.kr/certifications/" + imp_uid;
        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + portOneTokenResponse.getResponse().getAccess_token());

        // Param
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        // Entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        // Request
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneAuthinfoResponse> response = rt.exchange(url, HttpMethod.GET, entity, PortOneAuthinfoResponse.class);
        // Response
        log.info(response.getBody().toString());

        return response.getBody();
    }

    @Data
    private static class AuthinfoResponse {
        public int birth;
        public Object birthday;
        public boolean certified;
        public int certified_at;
        public boolean foreigner;
        public Object foreigner_v2;
        public Object gender;
        public String imp_uid;
        public String merchant_uid;
        public Object name;
        public String origin;
        public String pg_provider;
        public Object pg_tid;
        public Object unique_in_site;
        public Object unique_key;
    }

    @Data
    private static class PortOneAuthinfoResponse {
        public int code;
        public Object message;
        public AuthinfoResponse response;
    }

    // 결제 정보 확인
    @GetMapping(value = "/selectPay/{imp_uid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Root selectPay(@PathVariable("imp_uid") String imp_uid) {
        log.info("Get/portOne/selectPay..");

        // 토큰 가져오기
        getToken();



        // URL
        String url = "http://api.iamport.kr/payments/" + imp_uid;
        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + portOneTokenResponse.getResponse().getAccess_token());
        // Request
        HttpEntity<?> entity = new HttpEntity<>(headers);
        // RestTemplate로 API 호출
        RestTemplate rt = new RestTemplate();

        ResponseEntity<Root> response=rt.exchange(url,HttpMethod.GET,entity,Root.class);
        System.out.println(response.getBody());

        return response.getBody();
    }
    //결제 json to java
    @Data
    private static class Response{
        public int amount;
        public String apply_num;
        public Object bank_code;
        public Object bank_name;
        public Object buyer_addr;
        public Object buyer_email;
        public Object buyer_name;
        public Object buyer_postcode;
        public String buyer_tel;
        public int cancel_amount;
        public ArrayList<Object> cancel_history;
        public Object cancel_reason;
        public ArrayList<Object> cancel_receipt_urls;
        public int cancelled_at;
        public Object card_code;
        public Object card_name;
        public Object card_number;
        public int card_quota;
        public Object card_type;
        public boolean cash_receipt_issued;
        public String channel;
        public String currency;
        public Object custom_data;
        public Object customer_uid;
        public Object customer_uid_usage;
        public Object emb_pg_provider;
        public boolean escrow;
        public Object fail_reason;
        public int failed_at;
        public String imp_uid;
        public String merchant_uid;
        public String name;
        public int paid_at;
        public String pay_method;
        public String pg_id;
        public String pg_provider;
        public String pg_tid;
        public Object receipt_url;
        public int started_at;
        public String status;
        public String user_agent;
        public Object vbank_code;
        public int vbank_date;
        public Object vbank_holder;
        public int vbank_issued_at;
        public Object vbank_name;
        public Object vbank_num;
    }
    @Data
    private static class Root{
        public int code;
        public Object message;
        public Response response;
    }

    // 결제취소 요청
}
