package com.lamlake.IoCDemo.entity;

import com.lamlake.IoCDemo.annotation.Bean;

@Bean
public class UserInfoImpl implements IUserInfo {
    @Override
    public void getName() {
        System.out.println("My name is GeYou.");
    }
}
