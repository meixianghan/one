package cn.mrerror.one.interceptor;

import cn.mrerror.one.annotation.SameURLToken;
import cn.mrerror.one.exception.TokenRepeatCommitException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据传递过来的参数是否相同判断是否重复提交
 */
public class SameURLTokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            SameURLToken annotation = method.getAnnotation(SameURLToken.class);
            if (annotation != null) {
                if (repeatTokenValidator(request))
                    new TokenRepeatCommitException(method.getName()+"禁止重复提交");
                else
                    return true;
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 验证同一个url是否存在重复提交
     */
    public boolean repeatTokenValidator(HttpServletRequest httpServletRequest) {
        //请求地址
        String url = httpServletRequest.getRequestURI();
        //请求参数
        Map<String, Integer> map = new HashMap<String, Integer>();
                             map.put(url, httpServletRequest.getParameterMap().hashCode());
        //当前参数值
        String nowUrlParams = map.toString();
        //前一个值
        Object preUrlParams = httpServletRequest.getSession().getAttribute("repeatToken");
        //申请重复提交令牌
        if (preUrlParams == null){
            httpServletRequest.getSession().setAttribute("repeatToken", nowUrlParams);
            return false;
            //验证是否重复提交
        }else if (preUrlParams.toString().equals(nowUrlParams)){//重复提交
            return true;
        } else {//非重复提交
            httpServletRequest.getSession().setAttribute("repeatToken", nowUrlParams);
            return false;
        }
    }
}