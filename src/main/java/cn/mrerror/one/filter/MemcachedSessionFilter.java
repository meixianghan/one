package cn.mrerror.one.filter;

import cn.mrerror.one.cache.SpyMemcachedManager;
import cn.mrerror.one.configuration.Applications;
import cn.mrerror.one.session.memcached.HttpServletRequestWrapper;
import cn.mrerror.one.utils.KeyUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *
 *  每个请求都会经过该过滤器 确定用户身份
 *
 */
public class MemcachedSessionFilter extends HttpServlet implements Filter {

    private String cookieDomain = "";
    private String cookiePath = "/";
    private SpyMemcachedManager memcachedManager;

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie cookies[] = request.getCookies();
        Cookie sCookie = null;
        String sid = "";

        //--------------------------------------------------------------------------------------------------------------

        //这里是  拦截所有请求 拿去全局共享sessionID   确定唯一身份

        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                sCookie = cookies[i];
                if (sCookie.getName().equals(Applications.PLATFORM_CROSS_SESSION_ID)) {
                    sid = sCookie.getValue();
                }
            }
        }



        if (sid == null || sid.length() == 0) {
            sid = KeyUtils.getUUID();
            Cookie  mycookies = new Cookie(Applications.PLATFORM_CROSS_SESSION_ID, sid);
                    mycookies.setMaxAge(-1);
                    mycookies.setPath("/");
                    mycookies.setHttpOnly(true);
                    mycookies.setDomain(Applications.PLATFORM_DEFAULT_COOKIE_DOMAIN);
                    response.addCookie(mycookies);
        }
        //______________________________________________________________________________________________________________

        memcachedManager = getBean(request);

        Object object=request.getSession();
        /**
         * request装饰模式
         */
        filterChain.doFilter(new HttpServletRequestWrapper(sid, request, memcachedManager),servletResponse);
    }

    /**
     * 通过IOC容器中获取memcached manager对象
     * @param request
     * @return
     */
    private SpyMemcachedManager getBean(HttpServletRequest request) {
        if (null == memcachedManager) {
            memcachedManager = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean(SpyMemcachedManager.class);
        }
        return memcachedManager;
    }

    /**
     * 获取Filter 参数
     * @param filterConfig 过滤器配置对象
     * @throws ServletException 异常
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        /**
         * 获取COOKIE_PATH
         */
        this.cookiePath = filterConfig.getInitParameter("cookiePath");
        if (this.cookiePath == null || this.cookiePath.length() == 0) {
            this.cookiePath = Applications.PLATFORM_DEFAULT_COOKIE_PATH;
        }
        /**
         * 获取COOKIE_PATH
         */
        this.cookieDomain = filterConfig.getInitParameter("cookieDomain");
        if (this.cookieDomain == null) {
            this.cookieDomain = Applications.PLATFORM_DEFAULT_COOKIE_DOMAIN;
        }
    }

    public SpyMemcachedManager getSpyMemcachedManager() {
        return memcachedManager;
    }

    public void setSpyMemcachedManager(SpyMemcachedManager spyMemcachedManager) {
        this.memcachedManager = spyMemcachedManager;
    }
}