package com.lamlake.IoCDemo.aop;

import com.lamlake.IoCDemo.annotation.AopAfter;
import com.lamlake.IoCDemo.annotation.AopAround;
import com.lamlake.IoCDemo.annotation.AopBefore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommonInvokeHandler implements InvocationHandler {

    private Object target;
    private Object aopTarget;
    private String pointMethodName;

    public CommonInvokeHandler(Object target, Object aopTarget, String pointMethodName) {
        this.target = target;
        this.aopTarget = aopTarget;
        this.pointMethodName = pointMethodName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method[] methods = aopTarget.getClass().getMethods();
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> aroundMethods = new ArrayList<>();

        for (Method method1 : methods) {

            if (method1.getAnnotation(AopBefore.class) != null) {
                beforeMethods.add(method1);
            }
            if (method1.getAnnotation(AopAfter.class) != null) {
                afterMethods.add(method1);
            }
            if (method1.getAnnotation(AopAround.class) != null) {
                aroundMethods.add(method1);
            }
        }
        if (pointMethodName == null || pointMethodName.equals("") || !pointMethodName.equals(method.getName())) {
            // 执行代理类的方法
            return method.invoke(target, args);
        }

        for (Method beforeMethod : beforeMethods) {
            beforeMethod.invoke(aopTarget, args);
        }
        for (Method aroundMethod : aroundMethods) {
            aroundMethod.invoke(aopTarget, args);
        }
        // 执行代理类的方法
        Object obj = method.invoke(target, args);

        for (Method aroundMethod : aroundMethods) {
            aroundMethod.invoke(aopTarget, args);
        }
        for (Method afterMethod : afterMethods) {
            afterMethod.invoke(aopTarget, args);
        }
        return obj;
    }
}
