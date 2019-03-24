package cn.mrerror.one.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/add")
    @ResponseBody
    public Object addValue(String key,String value){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
        return "success";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object getValue(String key){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }
}