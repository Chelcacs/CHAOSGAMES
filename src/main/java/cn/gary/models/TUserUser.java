package cn.gary.models;

import org.apache.ibatis.annotations.ConstructorArgs;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * T_User_User 实体类
 */
public class TUserUser {
    //属性封装
    private Integer user_id;
    private String user_name;
    private String user_pwd;
    private String user_email;
    private String user_paid;
    private String user_phnum;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date user_birthday;
    private String user_avatar;
    private Integer friend_num;

    public Integer getFriend_num() {
        return friend_num;
    }

    public void setFriend_num(Integer friend_num) {
        this.friend_num = friend_num;
    }


    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public Integer getUse_id() {
        return user_id;
    }

    public void setUse_id(Integer use_id) {
        this.user_id = use_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_paid() {
        return user_paid;
    }

    public void setUser_paid(String user_paid) {
        this.user_paid = user_paid;
    }

    public String getUser_phnum() {
        return user_phnum;
    }

    public void setUser_phnum(String user_phnum) {
        this.user_phnum = user_phnum;
    }

    public Date getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(Date user_birthday) {
        this.user_birthday = user_birthday;
    }
}
