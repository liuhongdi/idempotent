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


    public String runLuaScript(String luaFileName,List<String> keyList) {
        System.out.println("redis script begin");
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/"+luaFileName)));
        redisScript.setResultType(String.class);

        /*
        List<String> keyList = new ArrayList();
        keyList.add("count");
        keyList.add("rate.limiting:127.0.0.1");
        */
        /*
        Map<String,Object> argvMap = new HashMap<String,Object>();
        argvMap.put("expire",10000);
        argvMap.put("times",10);
        */
        String argsone = "none";
        System.out.println("execute begin");
        String result = stringRedisTemplate.execute(redisScript, keyList,argsone);
        System.out.println("lua result:"+result);
        /*
        //时间字串，用来区分秒杀成功的订单
        int START = 100000;
        int END = 900000;
        int rand_num = ThreadLocalRandom.current().nextInt(END - START + 1) + START;
        String order_time = TimeUtil.getTimeNowStr()+"-"+System.nanoTime()+"-"+rand_num;

        //执行lua脚本
        String result = stringRedisTemplate.execute(longDefaultRedisScript, Collections.singletonList(skuId),String.valueOf(buyNum),userId,actId,order_time);
        */
        return result;
    }

}
