package cn.mrerror.one.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 产品实体
 */
@JsonIgnoreProperties(value = {"handler","fieldHandler"}) //忽略mybaits lazy自动添加的handler属性不能转json
public class Product implements Serializable {
    private int id;
    private String version;
    private String name;
    private Category category;
    private Integer user_id;
    private Integer sale_count;
    private Integer comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSale_count() {
        return sale_count;
    }

    public void setSale_count(Integer sale_count) {
        this.sale_count = sale_count;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
