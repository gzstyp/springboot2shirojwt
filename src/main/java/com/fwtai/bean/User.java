package com.fwtai.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User implements Serializable {

    private Integer userId;

    private String phone;

    private String salt;

    private String password;

    private String name;

    private Integer sex;

    private LocalDate birth;

    private LocalDateTime registerTime;

    private LocalDateTime updateTime;

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getSalt(){
        return salt;
    }

    public void setSalt(String salt){
        this.salt = salt;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getSex(){
        return sex;
    }

    public void setSex(Integer sex){
        this.sex = sex;
    }

    public LocalDate getBirth(){
        return birth;
    }

    public void setBirth(LocalDate birth){
        this.birth = birth;
    }

    public LocalDateTime getRegisterTime(){
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime){
        this.registerTime = registerTime;
    }

    public LocalDateTime getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime){
        this.updateTime = updateTime;
    }

    @Override
    public String toString(){
        return "User{" + "userId=" + userId + ", phone='" + phone + '\'' + ", salt='" + salt + '\'' + ", password='" + password + '\'' + ", name='" + name + '\'' + ", sex=" + sex + ", birth=" + birth + ", registerTime=" + registerTime + ", updateTime=" + updateTime + '}';
    }
}
