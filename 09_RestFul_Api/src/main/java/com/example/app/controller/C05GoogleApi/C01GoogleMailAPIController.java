package com.example.app.controller.C05GoogleApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/google")
public class C01GoogleMailAPIController {

    @GetMapping("/mail/main")
    public void main(){
        log.info("GET/google/mail/main...");


    }
    @GetMapping("/mail/req")
    public void req(@RequestParam("email")String email,@RequestParam("text")String text){
        log.info("GET/google/mail/req...email : "+email+" text : "+text);


    }

}
