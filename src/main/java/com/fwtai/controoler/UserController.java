package com.fwtai.controoler;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController{

    // http://127.0.0.1:88/user/login
    @GetMapping("/login")
    public void login(){
        final JSONObject json = new JSONObject();
        json.put("code",209);
        json.put("msg", "操作成功88");
        responseWriter(json.toJSONString());
    }

    // http://127.0.0.1:88/user/user
    @GetMapping("/user")
    @RequiresRoles("admin")
    public void user(final HttpServletResponse response) {
        final JSONObject json = new JSONObject();
        json.put("code",200);
        json.put("msg", "roles success");
        responseWriter(json.toJSONString(),response);
    }

    // http://127.0.0.1:88/user/list
    @GetMapping("/list")
    @RequiresPermissions("system:user:list")
    public void users(final HttpServletResponse response) {
        final JSONObject json = new JSONObject();
        json.put("code",200);
        json.put("msg", "permissions success");
        responseWriter(json.toJSONString(),response);
    }

    public void responseWriter(final String json,final HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()){
            writer.write(json);
            writer.flush();
        } catch (IOException io) {
            System.err.println("responseWriter异常"+ io);
        }
    }

    public final void responseWriter(final String json){
        final HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()){
            writer.write(json);
            writer.flush();
        } catch (IOException io) {
            System.err.println("responseWriter异常"+ io);
        }
    }
}

