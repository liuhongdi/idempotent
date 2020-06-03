package com.idempotent.demo.util;

import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RedisLuaUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /*
    run a lua script
    luaFileName: lua file name,no path
    keyList: list for redis key
    return 0: delete fail
           -1: no this key
           1: delete success
    */
    public String runLuaScript(String luaFileName,List<String> keyList) {
        //System.out.println("redis script begin");
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/"+luaFileName)));
        redisScript.setResultType(String.class);

        String argsone = "none";
        //System.out.println("execute begin");
        String result = stringRedisTemplate.execute(redisScript, keyList,argsone);
        System.out.println("lua result:"+result);

        return result;
    }
}
