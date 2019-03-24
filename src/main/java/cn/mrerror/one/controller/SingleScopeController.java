package cn.mrerror.one.controller;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
/**
 * 单例控制
 */
public class SingleScopeController {

    @ResponseBody
    @RequestMapping("/single")
    public Object single(){
        return this;
    }
}
