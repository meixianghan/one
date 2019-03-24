package cn.mrerror.one.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * session装饰类
 * 这个装饰类 是为了提供一个接口的重写， 并提供扩展属性与构造方法
 */
public class HttpSessionWrapper implements HttpSession {

    //这里重新定义了一个属性用于存当前的session
    private HttpSession session;
    //这里是构造方法
    public HttpSessionWrapper(HttpSession session) {
        this.session = session;
    }









    @Override
    public long getCreationTime() {
        return this.session.getCreationTime();
    }

    @Override
    public String getId() {
        return this.session.getId();
    }

    @Override
    public long getLastAccessedTime() {
        return this.session.getLastAccessedTime();
    }

    @Override
    public ServletContext getServletContext() {
        return this.session.getServletContext();
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.session.setMaxInactiveInterval(interval);
    }

    @Override
    public int getMaxInactiveInterval() {
        return this.session.getMaxInactiveInterval();
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return this.session.getSessionContext();
    }

    @Override
    public Object getAttribute(String name) {
        return this.session.getAttribute(name);
    }

    @Override
    public Object getValue(String name) {
        return this.session.getValue(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return this.session.getAttributeNames();
    }

    @Override
    public String[] getValueNames() {
        return this.session.getValueNames();
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.session.setAttribute(name,value);
    }

    @Override
    public void putValue(String name, Object value) {
        this.session.putValue(name,value);
    }

    @Override
    public void removeAttribute(String name) {
        this.session.removeAttribute(name);
    }

    @Override
    public void removeValue(String name) {
        this.session.removeValue(name);
    }

    @Override
    public void invalidate() {
        this.session.invalidate();
    }

    @Override
    public boolean isNew() {
        return this.session.isNew();
    }
}
