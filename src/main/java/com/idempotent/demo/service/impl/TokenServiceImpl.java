package com.idempotent.demo.service.impl;

import com.idempotent.demo.constant.ResponseCode;
import com.idempotent.demo.exception.ServiceException;
import com.idempotent.demo.service.TokenService;
import com.idempotent.demo.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "form_token";

    @Resource
    private RedisStringUtil redisStringUtil;

    @Resource
    private RedisLuaUtil redisLuaUtil;

    @Override
    public ServerResponseUtil createToken() {
        String str = RandomUtil.generateStr(24);
        Long seconds_timeout = 1800L;
        redisStringUtil.setStringValue(str,TimeUtil.getTimeNow(),seconds_timeout);
        return ServerResponseUtil.success(str);
    }

    @Override
    public void checkToken(HttpServletRequest request) {

        String token = request.getHeader(TOKEN_NAME);

        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                //System.out.println("-----no token");
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        //System.out.println("runlua begin");
        List<String> keyList = new ArrayList();
        keyList.add(token);
        String res = redisLuaUtil.runLuaScript("checkidem.lua",keyList);

        if (res.equals("1")) {
            ServerResponseUtil.success("success");
        } else {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }

}