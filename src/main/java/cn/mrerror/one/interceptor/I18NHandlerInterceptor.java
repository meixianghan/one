package cn.mrerror.one.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际化拦截器
 */
public class I18NHandlerInterceptor implements HandlerInterceptor {

    /**
     * 通过Cookie手动设置
     */
    private static final String COOKIE_LANGUAGE = "cookie_language";
    /**
     * 指定系统当前的语言
     */
    private static final String SYSTEM_LANGUAGE = "system_language";



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String systemLanguage = null;
        Cookie[] cookies = request.getCookies();
        /**
         * 从Cookies中获得客户端本地语言
         */
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_LANGUAGE.equals(cookie.getName())) {
                    systemLanguage = cookie.getValue();
                    break;
                }
            }
        }
        if (systemLanguage == null || "".equals(systemLanguage)) {
            /**
             * 从协议头获取Accept-Language 判断浏览器当前支持的语言
             */
            systemLanguage = request.getLocale().toString();
        }

        if (systemLanguage == null || "".equals(systemLanguage)) {
            /**
             * 获取本地JVM语言
             */
            systemLanguage = Locale.getDefault().toString();
        }

        request.setAttribute(SYSTEM_LANGUAGE, systemLanguage);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
