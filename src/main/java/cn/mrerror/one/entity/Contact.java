package cn.mrerror.one.entity;

import java.io.Serializable;

public class Contact implements Serializable {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
