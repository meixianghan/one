package cn.mrerror.one.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户拦截器,判断用户是否登录，权限验证，记录请求信息的日志
 */
public class UserInterceptor implements HandlerInterceptor{

    /**
     *  处理Controller方法之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("请求处理前");
        return true;
    }

    /**
     * 处理完Controller方法调用,但是VIEW层还么有处理
     * 主要用于ModelAndView数据的处理
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("model加载处理,一般处理Model通用属性,与统计分析");
    }

    /**
     * 释放资源调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("请求结束");
    }
}
