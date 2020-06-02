package com.idempotent.demo.controller;

import com.idempotent.demo.annotation.ApiIdempotent;
import com.idempotent.demo.service.TokenService;
import com.idempotent.demo.util.ServerResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")

public class OrderController {

    @Resource
    private TokenService tokenService;

    @ApiIdempotent
    @GetMapping("/addorder")
    public String addOrder() {
        return "add a order";
    }

    @GetMapping("/gettoken")
    public ServerResponseUtil getToken() {
        return tokenService.createToken();
    }
}
