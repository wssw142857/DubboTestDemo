package com.shuueii.publicapi.service;

public class UserServiceStub implements UserService {

    private final UserService userService;

    public UserServiceStub(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getUserMsg() {
        System.out.println("对于请求调用预处理");
        return userService.getUserMsg();
    }
}
