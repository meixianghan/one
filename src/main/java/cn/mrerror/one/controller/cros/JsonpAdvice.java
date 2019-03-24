package cn.mrerror.one.controller.cros;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;


/**
 * 配置jsonp跨域解决对哪些Controller起作用
 */
@ControllerAdvice(basePackages = "cn.com.one.controller.jsonp")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

    /**
     * super("callback", "jsonp");
     * 当请求参数中包含callback 或 jsonp参数
     * 就会返回jsonp协议的数据
     * 其value就作为回调函数的名称。
     */
    public JsonpAdvice() {
        super("callback", "jsonp");
    }

}
