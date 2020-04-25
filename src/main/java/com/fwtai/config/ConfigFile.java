package com.fwtai.config;

public final class ConfigFile{

    /**
     * jwtToken过期时间
     */
    public static Long TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;

    /**
     * token请求头名称
     */
    public static String TOKEN_HEADER_NAME = "authorization";

}