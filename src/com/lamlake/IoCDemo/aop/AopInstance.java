package com.lamlake.IoCDemo.aop;

import com.lamlake.IoCDemo.annotation.*;

@Aop
public class AopInstance {

    @Pointcut("com.lamlake.IoCDemo.service.Impl.UserInfoImpl.getName")
    public void point(){}

    @AopBefore
    public void befoire() {
        System.out.println("before...");
    }
    @AopAfter
    public void after() {
        System.out.println("after...");
    }
    @AopAround
    public void around() {
        System.out.println("around...");
    }

}
