package com.airbnb99.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/verify")
public class JWTVerificationController {
    @PostMapping("/addJWT")
    public String addJWT(){
        return "done";
    }
}
