package com.fwtai.tool;

import org.apache.shiro.crypto.hash.SimpleHash;

public final class ToolMD5{

    public static final String ALGORITHMNAME = "SHA-1";
    public static final int HASHITERATIONS = 2;
    public final static String SALT = "Www.Fwtai.Com";

    /**
     * 加密,不可逆
     * @param password 密码
     * @param userName 盐值
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2020/4/17 17:00
     */
    public final static String encryptHash(final String password,final String userName){
        return String.valueOf(new SimpleHash(ALGORITHMNAME, password,userName+SALT,HASHITERATIONS));
    }
}