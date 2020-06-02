package com.idempotent.demo.service;

import com.idempotent.demo.util.ServerResponseUtil;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    public ServerResponseUtil createToken();
    public void checkToken(HttpServletRequest request);
}
