package com.lan.miaosha.domain;

import lombok.Data;
import java.util.Date;

@Data
public class MiaoshaUser {
    private Long id;
    private String nickName;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
