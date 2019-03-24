package cn.mrerror.one.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.mrerror.one.configuration.Applications;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * WebSocket 会话拦截器
 */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {
    /**
     * 握手前处理
     * @param request 请求对象
     * @param response 响应对象
     * @param wsHandler websocket handler
     * @param attributes websocket 容器
     * @return 是否处理握手方法
     * @throws Exception 异常
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                String userName = (String) session.getAttribute(Applications.SESSION_USERNAME);
                if (userName == null) {
                    userName = Applications.PLATEFORM_DEFAULT_USER_NAME;
                }
                attributes.put(Applications.SESSION_USERNAME, userName);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    /**
     * 握手后处理
     * @param request 请求对象
     * @param response 响应对象
     * @param wsHandler websocket handler
     * @param ex 异常
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}