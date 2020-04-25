package com.fwtai.shiro;

import com.alibaba.fastjson.JSONObject;
import com.fwtai.config.ConfigFile;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 所有的请求都会先经过Filter，所以我们继承官方的BasicHttpAuthenticationFilter，并且重写鉴权的方法。
 *
 * @执行流程 代码的执行流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 *
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020/4/18 8:39
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class JwtFilter extends BasicHttpAuthenticationFilter{

    /**
     * 1.对跨域提供支持
    */
    @Override
    protected boolean preHandle(final ServletRequest request,final ServletResponse response) throws Exception {
        System.err.println("执行顺序1-除了在类ShiroConfiguration的方法shiroFilter()配置放行的资源外都走这个方法");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        //httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        //httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 2.执行带token认证有效
     * @param request ServletRequest
     * @param response ServletResponse
     * @param mappedValue mappedValue
     * @return 是否成功
     */
    @Override
    protected boolean isAccessAllowed(final ServletRequest request,final ServletResponse response,final Object mappedValue) {
        //WebUtils.toHttp(request);
        //WebUtils.toHttp(response);
        final String token = ((HttpServletRequest) request).getHeader(ConfigFile.TOKEN_HEADER_NAME);
        if (token != null) {
            return executeLogin(request, response);
        }
        // 如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }
 
    /**
     * ３.执行登录
     */
    @Override
    protected boolean executeLogin(final ServletRequest request,final ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(ConfigFile.TOKEN_HEADER_NAME);
        final JwtToken jwtToken = new JwtToken(token);
        try {
            // 提交给realm进行登入，如果错误他会抛出异常并被捕获
            getSubject(request, response).login(jwtToken);//getSubject(request, response).login(token);这一步就是提交给了realm进行处理
            // 如果没有抛出异常则代表登入成功，返回true
        } catch (final IncorrectCredentialsException e) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter writer = response.getWriter()) {
                final JSONObject o = new JSONObject();
                o.put("msg", e.getMessage());
                writer.write(o.toString());
                writer.flush();
                return false;
            } catch (IOException e1) {
                System.out.println("返回token校验失败异常"+ e1);
            }
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    @Override
    public void afterCompletion(final ServletRequest request,final ServletResponse response,final Exception exception) throws Exception{
        System.err.println("执行顺序3-最后执行");
        super.afterCompletion(request,response,exception);
    }
}