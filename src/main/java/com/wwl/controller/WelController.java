package com.wwl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelController {
    @GetMapping("/")
    public String toLogin(){
        return "login";
    }
}
