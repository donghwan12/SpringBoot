package com.example.app.controller;

import java.io.FileNotFoundException;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.dto.MemoDto;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/memo")
@Slf4j
public class MemoController {

//	@ExceptionHandler(FileNotFoundException.class)
//	public String fileNotFoundExceptionHandler(Exception error,Model model) {
//		log.info("MemoController's @ExceptionHandler..."+error);
//		model.addAttribute("error",error);
//		return "memo/error";
//	}
//	@ExceptionHandler(ArithmeticException.class)
//	public String arithmeticExceptionHandler(Exception error,Model model) {
//		log.info("MemoController's @ExceptionHandler..."+error);
//		model.addAttribute("error",error);
//		return "memo/error";
//	}

    @GetMapping("/add")
    public void memo_get() {
        log.info("GET /memo/add..");
    }


    @PostMapping(value = "/add")
    public void memo_post(@ModelAttribute @Valid MemoDto memoDto, BindingResult bindingResult, Model model) {
        log.info("POST /memo/add..memoDto : " + memoDto + " bindingResult : " + bindingResult);

        if(bindingResult.hasFieldErrors()) {
            //log.info("ValidationCheck Error : "+bindingResult.getFieldError("id").getDefaultMessage());
            for(FieldError error  :bindingResult.getFieldErrors()) {
                log.info("ErrorField : " + error.getField() + " ErrorMsg : " + error.getDefaultMessage());
                model.addAttribute(error.getField(),error.getDefaultMessage());
            }
        }
    }

    @GetMapping("/ex_test1")
    public void ex_test1() throws FileNotFoundException{
        log.info("GET /memo/ex_test1...");
        throw new FileNotFoundException("파일이 존재하지 않습니다...");
    }

    @GetMapping("/ex_test2/{num}/{div}")
    public String ex_test2(@PathVariable("num")int num , @PathVariable("div")int div,Model model) throws ArithmeticException
    {
        log.info("GET /memo/ex_test2...." + (num/div));
        model.addAttribute("result",(num/div));
        return "memo/result";
    }

}