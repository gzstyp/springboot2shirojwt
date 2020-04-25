package com.fwtai.service;

import com.fwtai.bean.User;
import com.fwtai.shiro.CustomizedToken;
import com.fwtai.tool.ToolMD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginService{

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    //使用用户名(手机号)和密码登录
    public String loginByPassword(final String phone,final String password,final HttpServletResponse response) {
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        final CustomizedToken token = new CustomizedToken(phone, password,"PasswordRealm");//指定要认证的类名称,此处是com.fwtai.shiro.PasswordRealm,是在类ShiroConfiguration的方法securityManager注入添加的
        // 3.执行登录方法
        try{
            subject.login(token);
            final Map<String, String> data = returnLoginInitParam(phone);
            response.setHeader("authorization",data.get("token"));
            return data.toString();
        }catch (final UnknownAccountException e) {
            return "用户名不存在";
        } catch (IncorrectCredentialsException e){
            return "密码不正确";
        }
    }

    //使用用户名(手机号)和验证码登录
    public String loginByCode(String phone, String code) {
        // 1.获取Subject
        final Subject subject = SecurityUtils.getSubject();
        final User sysUser = userService.selectUserByPhone(phone);
        // 2.验证码登录，如果该用户不存在则创建该用户
        if (Objects.isNull(sysUser)) {
            // 2.1 注册
            System.out.println("用户密码:"+this.register(phone,null));
        }
        // 3.封装用户数据
        final CustomizedToken token = new CustomizedToken(phone,code,"CodeRealm");//指定要认证的类名称,此处是com.fwtai.shiro.CodeRealm,是在类ShiroConfiguration的方法securityManager注入添加的
        // 4.执行登录方法
        try{
            subject.login(token);
            final Map<String, String> data = returnLoginInitParam(phone);
            return data.toString();
        }catch (UnknownAccountException e) {
            return "用户名不存在";
        }catch (ExpiredCredentialsException e){
            return "验证码无效";
        } catch (IncorrectCredentialsException e){
            return "验证码不正确";
        }
    }

    /**
     * 用户注册,默认密码为手机号后六位
     * @param phone phone
     */
    public String register(final String phone,final String password){
        if(password != null && password.length() > 0){
            return ToolMD5.encryptHash(password,phone);
        }else{
            return ToolMD5.encryptHash(phone.substring(5,11),phone);
        }
    }

    /**
     * 返回登录后初始化参数
     * @param phone phone
     * @return Map<String, Object>
     */
    private Map<String, String> returnLoginInitParam(String phone) {
        final Map<String, String> data = new HashMap<>(1);
        final User user = userService.selectUserByPhone(phone);
        // 生成jwtToken
        String token = tokenService.createToken(phone, user.getUserId(),user.getPassword());
        // token
        data.put("token", token);
        return data;
    }
}