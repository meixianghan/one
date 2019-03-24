package cn.mrerror.one.controller.jsonp;

import cn.mrerror.one.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * JSONP 跨域解决控制器
 */
@RequestMapping("/jsonp")
@Controller
public class JsonpController {

    @RequestMapping("/users")
    @ResponseBody
    public User jsonp2() {
        return new User(1, "zhangsan", "zhangsan");
    }
}
