package cn.mrerror.one.controller;

import javax.servlet.http.HttpSession;
import cn.mrerror.one.configuration.Applications;
import cn.mrerror.one.websocket.SpringWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;

@Controller
@RequestMapping("/websocket")
public class WebsocketController {

    @Bean //这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }

    /**
     * 登录
     * @param username 用户名
     * @param session 会话
     * @return 返回视图
     */
    @RequestMapping("/login")
    public ModelAndView login(String username,HttpSession session){
                    session.setAttribute(Applications.SESSION_USERNAME, username);
        System.out.println(username+"登录");
        return new ModelAndView("websocket");
    }

    /**
     * 向执行用户发送消息
     * @param username 用户名
     */
    @RequestMapping("/send")
    @ResponseBody
    public int send(String username) {
        infoHandler().sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
        return 1;
    }

    /**
     * 在线人数
     * @return
     */
    @RequestMapping("/online")
    @ResponseBody
    public Object showOnlinePeople(){
        HashMap<String,Object> peoples = new HashMap<String,Object>();
                               peoples.put("peoples",infoHandler().onlinePeople());
        return peoples;
    }
}