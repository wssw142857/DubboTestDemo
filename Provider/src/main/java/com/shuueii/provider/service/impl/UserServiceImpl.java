package com.shuueii.provider.service.impl;

import com.shuueii.publicapi.entity.User;
import com.shuueii.publicapi.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getUserMsg() {

        return new User("123456",
                "张三",
                "男",
                16,
                "北京一环中南海").toString();
    }
}
