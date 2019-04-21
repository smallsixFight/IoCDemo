package com.lamlake.IoCDemo.aop;

import com.lamlake.IoCDemo.annotation.Bean;

@Bean
public class UserInfoImpl implements IUserInfo {
    @Override
    public void getName() {
        System.out.println("My name is GeYou.");
    }
}
