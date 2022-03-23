package com.shuueii.consumer.controller;

import com.shuueii.publicapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/initOrder")
    public String initOrder(@RequestParam String userId){
        log.info("请求进来了, userId = {}", userId);
       return orderService.initOrder(userId);
    }

}
