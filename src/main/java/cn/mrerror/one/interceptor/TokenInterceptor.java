package cn.mrerror.one.interceptor;

import cn.mrerror.one.annotation.Token;
import cn.mrerror.one.exception.TokenRepeatCommitException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 防止重复提价拦截器
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取调用的controller方法
            Method method = handlerMethod.getMethod();
            //获取方法上的token注解
            Token annotation = method.getAnnotation(Token.class);
            //有防重操作注解
            if (annotation != null) {
                //-----------------------创建令牌流程----------------------------
                boolean saveSession = annotation.apply();
                //需要暂时存储token值
                if (saveSession) {
                    //创建UUID防重令牌
                    request.getSession().setAttribute("token", UUID.randomUUID().toString());
                }
                //-------------------------验证令牌流程---------------------------
                boolean removeSession = annotation.consumer();
                if(removeSession){
                    //验证是否重复提交
                    if (isRepeatSubmit(request)){
                        //重复提交抛异常
                        throw new TokenRepeatCommitException(method.getName()+"禁止重复提交");
                    }
                    //验证通过删除令牌
                    request.getSession().removeAttribute("token");
                }
            }
            return true;
        } else {
            //放行
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 根据值判断是否重复提交
     * @param request
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession().getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        //获取客户端传过来的token值
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;

        //客户端与Session的token进行对比,不相同则重复提交
        }else if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}