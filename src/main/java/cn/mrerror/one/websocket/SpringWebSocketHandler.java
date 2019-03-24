package cn.mrerror.one.websocket;

import cn.mrerror.one.configuration.Applications;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Web Socket 消息处理对象
 */
public class SpringWebSocketHandler extends TextWebSocketHandler {

    //会话存储容器
    private static final LinkedList<WebSocketSession> users = new LinkedList<>();
    //日志记录对象
    private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class);

    /**
     * 客户端连接成功触发
     * @param session websocket 会话对象
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("欢迎来到one平台"));
        for (WebSocketSession user : users) {
            if(user.isOpen())
                user.sendMessage(new TextMessage(String.format("%s已上线",session.getAttributes().get(Applications.SESSION_USERNAME))));
        }
        //存储所有的连接会话
        users.add(session);
    }

    /**
     * 关闭连接时触发
     * @param session 会话对象
     * @param closeStatus 关闭状态
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //获取离线用户名
        String username= (String) session.getAttributes().get(Applications.SESSION_USERNAME);
        //移除已离线用户
        users.remove(session);
        for (WebSocketSession user : users) {
            if(user.isOpen())
                user.sendMessage(new TextMessage(String.format("%s已下线",username)));
        }
    }

    /**
     * 处理客户端发送的信息(当前为群发)
     * @param session 会话对象
     * @param message 消息
     * @throws Exception 抛异常
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession user : users) {
            if(session!=user&&user.isOpen())
                    user.sendMessage(message);
        }
    }

    /**
     * 消息传输异常
     * @param session 会话
     * @param exception 异常信息
     * @throws Exception 抛异常
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
            users.remove(session);
        }
        logger.error(exception);//记录异常
    }
    /**
     * 向指定用户发送消息
     * @param userName 用户名
     * @param message 消息
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(Applications.SESSION_USERNAME).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    logger.error(e);//记录异常
                }
                break;
            }
        }
    }

    /**
     * 返回所有在线人数
     * @return 在线用户List
     */
    public List onlinePeople(){
        List<Object> peopleList = new ArrayList<>();
        for (WebSocketSession user : users) {
            peopleList.add(user.getAttributes().get(Applications.SESSION_USERNAME));
        }
        return peopleList;
    }

}
