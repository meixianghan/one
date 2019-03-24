package cn.mrerror.one.session.memcached;


import cn.mrerror.one.cache.SpyMemcachedManager;
import cn.mrerror.one.configuration.Applications;
import cn.mrerror.one.session.HttpSessionWrapper;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * session id 装饰类
 */
public class HttpSessionIdWrapper extends HttpSessionWrapper {
    private String sessionId = "";
    private Map map = new HashMap();
    private SpyMemcachedManager spyMemcachedManager;

    public HttpSessionIdWrapper(String sessionId, HttpSession session, SpyMemcachedManager spyMemcachedManager) {
        super(session);

        if (spyMemcachedManager == null) {
            return;
        }
        this.spyMemcachedManager = spyMemcachedManager;
        this.sessionId = sessionId;
        Map memSession = (Map)spyMemcachedManager.get(sessionId);
        if (memSession == null) {
            memSession = new HashMap();
            spyMemcachedManager.set(sessionId, Applications.MEMCACHED_COOKIE_EXPTIME,memSession);
            if (session != null) {
                Enumeration<String> names = session.getAttributeNames();
                while (names.hasMoreElements()) {
                    String key = (String) names.nextElement();
                    memSession.put(key, session.getAttribute(key));
                }
            }
        }
        this.map = memSession;
    }

    /**
     * 获取属性
     * @param key 键
     * @return 值
     */
    public Object getAttribute(String key) {
        if (this.map != null && this.map.containsKey(key)) {
            spyMemcachedManager.touch(sessionId, Applications.MEMCACHED_COOKIE_EXPTIME);
            return this.map.get(key);
        } else {
            return null;
        }
    }

    public Enumeration getAttributeNames() {
        return new Enumeration(){
            Iterator iterator = map.keySet().iterator();
             @Override
             public boolean hasMoreElements() {
                 return iterator.hasNext();
             }

             @Override
             public Object nextElement() {
                 return iterator.next();
             }
         };
    }

    public void invalidate() {
        this.map.clear();
        spyMemcachedManager.remove(sessionId);
    }

    public void removeAttribute(String name) {
        this.map.remove(name);
        attributeChange();
    }

    public void setAttribute(String name, Object value) {
        this.map.put(name, value);
        attributeChange();
    }

    /**
     * 每次本地MAP的改变需要重新通过二进制传输并覆盖memcache内容
     */
    private void attributeChange() {
        spyMemcachedManager.set(sessionId, Applications.MEMCACHED_COOKIE_EXPTIME,this.map);
    }
}
