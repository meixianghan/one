package cn.mrerror.one.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * 博客
 */
@JsonIgnoreProperties(value = {"handler","fieldHandler"}) //忽略mybaits lazy自动添加的handler属性不能转json
public class Blog implements Serializable {
    private int blog_id;
    private String blog_title;
    private Author author;
    private Author coAuthor;
    private List<Post> posts;

    public Blog() {
    }

    public Blog(int blog_id) {
        this.blog_id = blog_id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Author getCoAuthor() {
        return coAuthor;
    }
    public void setCoAuthor(Author coAuthor) {
        this.coAuthor = coAuthor;
    }
    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
