package com.shuueii.consumer.service.impl;

import com.shuueii.publicapi.service.OrderService;
import com.shuueii.publicapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@DubboService
public class OrderServiceImpl implements OrderService {

    @DubboReference(stub = "true", check = false, loadbalance = "random")
    UserService userService;

    @Override
    public String initOrder(String userId) {
        if(!userService.getUserMsg().isEmpty()){
            return "服务启动成功了:" + userService.getUserMsg();
        }
        return null;
    }
}
