package com.lan.miaosha.domain;

import lombok.Data;

@Data
public class Test {
    private String username;
    private String passwd;
    private Integer number;

    public String getUsername() {
        return username;
    }

    public Test setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPasswd() {
        return passwd;
    }

    public Test setPasswd(String passwd) {
        this.passwd = passwd;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public Test setNumber(Integer number) {
        this.number = number;
        return this;
    }
}
