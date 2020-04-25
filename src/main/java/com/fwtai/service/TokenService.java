package com.fwtai.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fwtai.bean.LoginUser;
import com.fwtai.bean.User;
import com.fwtai.config.ConfigFile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lixiao
 * @version 1.0
 * @date 2020/4/15 11:45
 */
@Service
public class TokenService{

    @Resource
    private UserService userService;

    /**
     * 获取当前登录的User对象
     * @return User
    */
    public LoginUser getLoginUser(final HttpServletRequest request){
        // 获取token
       final String token = getToken(request);
        // 获取手机号
        final String phone = getPhone(token);
        final LoginUser loginUser = new LoginUser();
        if(phone.equals("13765121695")){
            // 获取当前登录用户
            final User user = userService.selectUserByPhone(phone);
            loginUser.setUser(user);
            // 获取当前登录用户所有权限
            final Set<String> permissionsSet = new HashSet<>();
            permissionsSet.add("system:user:list");
            loginUser.setPermissionsSet(permissionsSet);
            // 获取当前登录用户所有角色
            final Set<String> roleSet = new HashSet<>();
            roleSet.add("admin");
            loginUser.setRoleSet(roleSet);
        }
        if(phone.equals("13765090931")){
            // 获取当前登录用户
            final User user = userService.selectUserByPhone(phone);
            loginUser.setUser(user);
            // 获取当前登录用户所有权限
            final Set<String> permissionsSet = new HashSet<>();
            loginUser.setPermissionsSet(permissionsSet);
            // 获取当前登录用户所有角色
            final Set<String> roleSet = new HashSet<>();
            loginUser.setRoleSet(roleSet);
        }
        return loginUser;
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     * @param token token
     * @return token中包含的用户手机号
     */
    public String getPhone(String token) {
        try {
            final DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("phone").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     * @param token token
     * @return token中包含的用户id
     */
    public String getUserId(String token) {
        try {
            final DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取当前登录用户的token
     * @param request HttpServletRequest
     * @return token
     */
    public String getToken(final HttpServletRequest request){
        return request.getHeader(ConfigFile.TOKEN_HEADER_NAME);
    }

    /**
     *
     * @param phone 用户名/手机号
     * @param userId   用户id
     * @param secret   用户的密码
     * @return 加密的token
    */
    public String createToken(final String phone,final Integer userId,final String secret) {
        final Date date = new Date(System.currentTimeMillis() + ConfigFile.TOKEN_EXPIRE_TIME);
        final Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
            .withClaim("phone",phone)
            .withClaim("userId",String.valueOf(userId))
            .withExpiresAt(date)
            .sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public boolean verify(String token, String secret) {
        try {
            // 根据密码生成JWT效验器
            final Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("phone", getPhone(token))
                    .withClaim("userId", getUserId(token))
                    .build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
}