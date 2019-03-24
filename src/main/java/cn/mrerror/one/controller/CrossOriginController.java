package cn.mrerror.one.controller;

import cn.mrerror.one.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 跨域请求
 * Access-Control-Allow-Origin  允许的访问来源
 * Access-Control-Allow-Headers  允许的访问头字段
 * Access-Control-Expose-Headers 允许显露的头字段
 * Access-Control-Allow-Credentials=true 是否允许携带证书
 */

@RestController
@RequestMapping("/cros")
public class CrossOriginController {

    @CrossOrigin(origins = {"http://localhost:8080","*"}, allowedHeaders = "*",maxAge = 3600)
    @RequestMapping("/users")
    @ResponseBody
    public User users() {
        return new User(1, "zhangsan", "zhangsan");
    }
}