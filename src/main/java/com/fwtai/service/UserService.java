package com.fwtai.service;

import com.fwtai.bean.User;
import org.springframework.stereotype.Service;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-04-19 16:56
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
@Service
public class UserService{

    public User selectUserByPhone(final String phone){
        final User user = new User();
        if(phone.equals("13765121695")){
            user.setName(phone);
            user.setPhone(phone);
            user.setSalt(phone);
            user.setUserId(10242048);
            user.setPassword("28a07bcf84ad57f599bca41855627f54dcaf1006");//666666
            return user;
        }
        if(phone.equals("13765090931")){
            user.setName(phone);
            user.setPhone(phone);
            user.setSalt(phone);
            user.setUserId(20483092);
            user.setPassword("26e5fd4c4c6d4207c47ee8a20f2da48875ce4140");//888888
            return user;
        }
        return null;
    }
}