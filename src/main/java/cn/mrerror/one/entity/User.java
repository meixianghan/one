package cn.mrerror.one.entity;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class User implements Serializable {

    private int id;
    private String account;
    private String password;
    private Contact contact;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public User(int id, String account, String password) {
        super();
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public User() {
        super();
    }

    public User(int id) {
        this.id = id;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", contact=" + contact +
                '}';
    }
}
