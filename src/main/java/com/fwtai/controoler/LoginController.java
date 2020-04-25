package com.fwtai.controoler;

import com.fwtai.service.LoginService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
@Validated
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class LoginController{

    @Resource
    private LoginService loginService;

    // http://127.0.0.1:88/login/register?phone=13765121695&password=666666
    // http://127.0.0.1:88/login/register?phone=13765090931&password=888888
    /*密码登录*/
    @GetMapping("/register")
    public String register(final String phone,final String password){
        return loginService.register(phone,password);
    }

    // http://127.0.0.1:88/login/password?phone=13765121695&password=666666
    // http://127.0.0.1:88/login/password?phone=13765090931&password=888888
    /*密码登录*/
    @GetMapping("/password")
    public String loginByPassword(final String phone,final String password,final HttpServletResponse response){
        return loginService.loginByPassword(phone, password,response);
    }

    // http://127.0.0.1:88/login/code?phone=13765121695&code=666666
    // http://127.0.0.1:88/login/code?phone=13765090931&code=888888
    /*验证码登录*/
    @GetMapping("/code")
    public String loginByCode(final String phone,final String code){
        return loginService.loginByCode(phone,code);
    }
}