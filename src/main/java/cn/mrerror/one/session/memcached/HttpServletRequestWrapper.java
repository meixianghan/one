package cn.mrerror.one.session.memcached;

import cn.mrerror.one.cache.SpyMemcachedManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Request 包装类
 * 赋予一个集群SESSION容器
 */
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper  {
    
    private String sid = "";
    private SpyMemcachedManager spyMemcachedManager;

    public HttpServletRequestWrapper(String sid, HttpServletRequest request, SpyMemcachedManager spyMemcachedManager) {
        super(request);
        this.sid = sid;
        this.spyMemcachedManager = spyMemcachedManager;
    }

    public HttpSession getSession(boolean create) {
        return new HttpSessionIdWrapper(this.sid, super.getSession(create), this.spyMemcachedManager);
    }

    public HttpSession getSession() {
        return new HttpSessionIdWrapper(this.sid, super.getSession(true), this.spyMemcachedManager);
    }

}
