package com.example.app.controller.C04NaverApi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/naver")
public class C01NaverLoginAPIController {

    private String Naver_Client_Id="BBalMEHMGsJEO3RxG3iU";
    private String naver_Client_Secreet="PBwiLib648";
    private String Redirect_url="http://localhost:8080/naver/getCode";

    private NaverResponse naverResponse;
    @GetMapping("/login")
    public void login(){
        log.info("GET/naver/login...");
    }

    @PostMapping("/login")
    public String login_post(){
        return "redirect:https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id="+Naver_Client_Id+"&state=STATE_STRING&redirect_uri="+Redirect_url;
    }

    @GetMapping("/getCode")
    public String getCode(@RequestParam("code")String code,@RequestParam("state")String state)
    {
        log.info("GET/naver/getCode...code : "+code+" ,state : "+state);

        //URL
        String url="https://nid.naver.com/oauth2.0/token";
        //HEAder
        HttpHeaders headers=new HttpHeaders();
        //Param
        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",Naver_Client_Id);
        params.add("client_secret",naver_Client_Secreet);
        params.add("code",code);
        params.add("state",state);
        //Entity
        HttpEntity<MultiValueMap<String,String>>entity=new HttpEntity<>(params,headers);
        //REquest
        RestTemplate rt=new RestTemplate();
        ResponseEntity<NaverResponse> response=rt.exchange(url, HttpMethod.POST,entity,NaverResponse.class);
        //Response
        System.out.print(response.getBody());
        this.naverResponse=response.getBody();

        return "redirect:/naver/main";
    }

    @GetMapping("/main")
    public void main(Model model){
        log.info("GET/naver/main...");

        String url="https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer "+naverResponse.getAccess_token());
        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String,String>> entity=new HttpEntity<>(params,headers);

        RestTemplate rt=new RestTemplate();
        ResponseEntity<Root> response=rt.exchange(url,HttpMethod.POST,entity,Root.class);

        System.out.println(response.getBody());
        model.addAttribute("profile",response.getBody());

    }

    @GetMapping("/logout")
    public @ResponseBody void logout(){
        log.info("GET/naver/logout...");
        //URL
        String url="https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="+Naver_Client_Id+"&client_secret="+naver_Client_Secreet+"&access_token="+naverResponse.getAccess_token();
        HttpHeaders headers=new HttpHeaders();
        //PAram
        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
        //Entity
        HttpEntity<MultiValueMap<String,String>>entity=new HttpEntity<>(params,headers);
        //REquest
        RestTemplate rt=new RestTemplate();
        ResponseEntity<String> response=rt.exchange(url, HttpMethod.POST,entity,String.class);
        //Response
        System.out.print(response.getBody());
    }

    @GetMapping("/disconnect")
    public String disconnect() {
        return "redirect:http://nid.naver.com/nidlogin.logout?returl=https://www.naver.com/";

        }
    }
    //naver profile
    @Data
    private static class Response{
        public String id;
        public String nickname;
        public String email;
        public String name;
    }
    @Data
    private static class Root{
        public String resultcode;
        public String message;
        public Response response;
    }


//-----Naver로그인 토큰
    @Data
    private static class NaverResponse{
        public String access_token;
        public String refresh_token;
        public String token_type;
        public String expires_in;
    }
}
