package cn.mrerror.one.entity;

import java.io.Serializable;

/**
 * 帖子
 */
public class Post implements Serializable {

    private int id;
    private String subject;
    private String body;
    private int blog_id;

    public Post() {
    }

    public Post(int id) {
        this.id = id;
    }

    public Post(int id, String subject, String body) {
        this.id = id;
        this.subject = subject;
        this.body = body;
    }

    public Post(String subject, String body, int blog_id) {
        this.subject = subject;
        this.body = body;
        this.blog_id = blog_id;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
