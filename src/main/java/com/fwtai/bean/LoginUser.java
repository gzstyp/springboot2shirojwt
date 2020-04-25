package com.fwtai.bean;

import java.util.Set;

public class LoginUser{

    /**
     * 角色列表
     */
    private Set<String> roleSet;

    /**
     * 权限列表
     */
    private Set<String> permissionsSet;

    /**
     * User
     */
    private User user;

    public Set<String> getRoleSet(){
        return roleSet;
    }

    public void setRoleSet(Set<String> roleSet){
        this.roleSet = roleSet;
    }

    public Set<String> getPermissionsSet(){
        return permissionsSet;
    }

    public void setPermissionsSet(Set<String> permissionsSet){
        this.permissionsSet = permissionsSet;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    @Override
    public String toString(){
        return "LoginUser{" + "roleSet=" + roleSet + ", permissionsSet=" + permissionsSet + ", user=" + user + '}';
    }
}