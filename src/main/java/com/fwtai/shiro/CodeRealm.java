package com.fwtai.shiro;

import com.fwtai.bean.User;
import com.fwtai.service.UserService;
import com.fwtai.tool.ToolMD5;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * 验证码登录时的验证
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020/4/19 18:20
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
public class CodeRealm extends AuthorizingRealm{

    @Resource
    private UserService userService;

    /*必须重写,是个大坑,该方法是为了判断这个主体能否被本Realm处理，判断的方法是查看token是否为同一个类型*/
    @Override
    public boolean supports(final AuthenticationToken token) {
        return token instanceof CustomizedToken;
    }

    /**
     * 获取授权信息，这个方法是用来添加身份信息的，本项目计划为管理员提供网站后台，所以这里不需要身份信息，返回一个简单的即可
     * @param principals
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2020/4/19 18:22
    */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        return null;
    }

    /**
     * 获取认证身份信息,返回安全数据的身份信息，在需要验证身份进行登录时，会通过这个接口，调用本方法进行审核，将身份信息返回，有误则抛出异常，在外层拦截
     * @param authenticationToken
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2020/4/19 18:21
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken) throws AuthenticationException{
        final CustomizedToken token = (CustomizedToken) authenticationToken;
        System.out.println("CodeRealm"+token.getUsername()+"开始身份认证");
        // 根据手机号查询用户
        final User user = userService.selectUserByPhone(token.getUsername());
        if (user == null) {
            // 抛出账号不存在异常
            throw new UnknownAccountException();
        }
        // 1.principal：认证的实体信息，可以是手机号，也可以是数据表对应的用户的实体类对象
        // 2.从redis中获取登录验证码
        final String credentials = user.getPassword();//从数据库获取已加密的密码
        // 3.盐,取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
        final ByteSource credentialsSalt = ByteSource.Util.bytes(user.getPhone() + ToolMD5.SALT);//用户名+盐值
        //在这里将principal换为用户的user,即已认证的身份信息
        return new SimpleAuthenticationInfo(user, credentials, credentialsSalt,"codeRealm");//password 是已加密的密码,是在登录方法那里[new CustomizedToken(phone,pwd,"CodeRealm")]处理加密后的值
    }
}
