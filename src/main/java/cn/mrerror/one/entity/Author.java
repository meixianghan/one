package cn.mrerror.one.entity;


import java.io.Serializable;

/**
 * 作者
 */
public class Author implements Serializable {

    private int author_id;
    private String author_username;
    private String author_password;
    private String author_email;
    private String author_bio;

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_username() {
        return author_username;
    }

    public void setAuthor_username(String author_username) {
        this.author_username = author_username;
    }

    public String getAuthor_password() {
        return author_password;
    }

    public void setAuthor_password(String author_password) {
        this.author_password = author_password;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthor_bio() {
        return author_bio;
    }

    public void setAuthor_bio(String author_bio) {
        this.author_bio = author_bio;
    }
}