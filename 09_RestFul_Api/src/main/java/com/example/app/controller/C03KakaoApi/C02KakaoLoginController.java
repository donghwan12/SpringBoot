package com.example.app.controller.C03KakaoAPI;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/kakao")
public class C02KakaoLoginController {

    private String CLIENT_ID="3d94f21ef48759eb36a66c5d7be44732";
    private String REDIRECT_URI="http://localhost:8080/kakao/callback";
    private KakaoResponse kakaoResponse;

    @GetMapping("/getCode")
    public String getCode(){
        log.info("GET /kakao/getCode...");
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
    }


    @GetMapping("/callback")
    public String callBack(@RequestParam("code") String code){
        log.info("GET /kakao/code..." + code);

        //URL
        String url = "https://kauth.kakao.com/oauth/token";

        //REQUEST HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //REQUEST PARAM
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",CLIENT_ID);
        params.add("redirect_uri",REDIRECT_URI);
        params.add("code",code);

        //ENTITY(HEADER + PARAM)
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        KakaoResponse response = rt.postForObject(url,entity,KakaoResponse.class);
        System.out.println(response);
        this.kakaoResponse=response;

        //페이지이동
        return "redirect:/kakao/main";

    }

    @Data
    static class KakaoResponse{
        public String access_token;
        public String token_type;
        public String refresh_token;
        public int expires_in;
        public String scope;
        public int refresh_token_expires_in;
    }

    @GetMapping("/main")
    public void main(){
        log.info("GEt/kakao/main...");

    }

    @GetMapping(value="/profile",produces = MediaType.APPLICATION_JSON_VALUE)
    //페이지 이동이 아닌 리턴된 값을제공
    public @ResponseBody void getProfile(){
        log.info("GET/kakao/getProfile...");

        //URL
        String Url="https://kapi.kakao.com/v2/user/me";
        //Header
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());
        headers.add("Content-type","Content-type: application/x-www-form-urlencoded;charset=utf-8");
        //param(x)

        //entity
        HttpEntity entity=new HttpEntity(headers);

        //request
        RestTemplate rt=new RestTemplate();
        ResponseEntity<String> response=rt.exchange(Url, HttpMethod.POST,entity,String.class);
        System.out.println(response.getBody());
    }
}