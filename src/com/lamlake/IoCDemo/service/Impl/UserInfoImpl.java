package com.lamlake.IoCDemo.service.Impl;

import com.lamlake.IoCDemo.annotation.Bean;
import com.lamlake.IoCDemo.service.IUserInfo;

@Bean
public class UserInfoImpl implements IUserInfo {
    @Override
    public void getName() {
        System.out.println("My name is GeYou.");
    }
}
