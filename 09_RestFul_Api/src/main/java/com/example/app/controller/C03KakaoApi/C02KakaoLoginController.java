package com.example.app.controller.C03KakaoAPI;


import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/kakao")
public class C02KakaoLoginController {

    private String CLIENT_ID="3d94f21ef48759eb36a66c5d7be44732";
    private String REDIRECT_URI="http://localhost:8080/kakao/callback";
    private String LOGOUT_REDIRECT_URI="http://localhost:8080/kakao/main";

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

    //--토큰 저장용 객체
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
    public String getProfile(Model model){
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
        KakaoProfile response= rt.exchange(Url, HttpMethod.POST,entity,KakaoProfile.class).getBody();
        System.out.println(response);

        model.addAttribute("response",response);
        return "/kakao/profile";
    }

    @Data
    static class KakaoAccount{
        public boolean profile_nickname_needs_agreement;
        public boolean profile_image_needs_agreement;
        public Profile profile;
        public boolean has_email;
        public boolean email_needs_agreement;
        public boolean is_email_valid;
        public boolean is_email_verified;
        public String email;
    }
    @Data
    static class Profile{
        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public boolean is_default_image;
        public boolean is_default_nickname;
    }
    @Data
    static class Properties{
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }
    @Data
    static class KakaoProfile{
        public long id;
        public Date connected_at;
        public Properties properties;
        public KakaoAccount kakao_account;
    }

    //=======로그아웃(토큰만료)=========================
    @GetMapping("/logout")
    public void logout(){
        log.info("GET/kakao/logout....");
        //url
        String url="https://kapi.kakao.com/v1/user/logout";

        //header
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());
        //param
        //entity
        HttpEntity entity=new HttpEntity(headers);
        //request
        RestTemplate rt=new RestTemplate();
        ResponseEntity<String> response=rt.exchange(url,HttpMethod.POST,entity,String.class);
        System.out.println(response);
    }
    //로그아웃(카카오계정과함께)---
    @GetMapping("/logoutWithkakao")
    public @ResponseBody void logoutWithkakao(HttpServletResponse response) throws IOException {
        log.info("GET/kakao/logoutWithkakao");
        response.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id="+CLIENT_ID+"&logout_redirect_uri="+LOGOUT_REDIRECT_URI);
    }

    //=======연결 끊기(카카오 서버와의)=========================
    @GetMapping("/unlink")
    public void unlink(){
        log.info("GET/kakao/unlink....");
        //url
        String url="https://kapi.kakao.com/v1/user/unlink";
        //header
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization"," Bearer "+kakaoResponse.getAccess_token());
        //param

        //entity
        HttpEntity entity=new HttpEntity(headers);
        //request
        RestTemplate rt=new RestTemplate();
        ResponseEntity<String> response=rt.exchange(url,HttpMethod.POST,entity,String.class);
        System.out.println(response);
    }

    //메세지 보내기
    @GetMapping("/getCodeMsg")
    public String getCodeMsg(){
        log.info("GET/kakao/getCodeMsg...");
        //새로운요청을보냄
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=talk_message";
    }
    @GetMapping("/message/me/{message}")
    public @ResponseBody void sendMessageMe(@PathVariable("message") String message){
        log.info("GET/kakao/message/me..message : "+message);

        //url
        String url="https://kapi.kakao.com/v2/api/talk/memo/default/send";
        //Header
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());
        //param
        JSONObject template_object=new JSONObject();
        template_object.put("object_type","text");
        template_object.put("text",message);
        template_object.put("link",new JSONObject());
        template_object.put("button_title","버튼제목");

        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
        params.add("template_object",template_object.toString() );
        //Entity
        HttpEntity<MultiValueMap> entity=new HttpEntity(params,headers);
        //request
        RestTemplate rt=new RestTemplate();
        ResponseEntity<String> response=rt.exchange(url,HttpMethod.POST,entity,String.class);
        System.out.println(response.getBody());
    }
}