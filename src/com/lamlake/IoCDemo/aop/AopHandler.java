package com.lamlake.IoCDemo.aop;

import com.lamlake.IoCDemo.ioc.Ioc;
import com.lamlake.IoCDemo.annotation.Bean;
import com.lamlake.IoCDemo.annotation.Pointcut;
import com.lamlake.IoCDemo.exception.InterfaceNotFoundException;
import com.lamlake.IoCDemo.exception.PointcutParseException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopHandler {
    private AopHandler(){}

    public static void addProxyToIoc(Ioc ioc, Object aopTarget) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (Method method : aopTarget.getClass().getDeclaredMethods()) {
            Annotation annotation = method.getAnnotation(Pointcut.class);
            if (annotation != null) {
                String value = ((Pointcut) annotation).value();
                int methodNameStart = value.lastIndexOf(".");
                if (methodNameStart == -1) {
                    // 抛出异常
                    throw new PointcutParseException(value);
                }
                String clazzName = value.substring(0, methodNameStart);
                String methodName = value.substring(methodNameStart +1);

                Class clazz = Class.forName(clazzName);
                if (clazz.getInterfaces().length == 0) {
                    // 抛出异常
                    System.err.println("没有父接口");
                    throw new InterfaceNotFoundException(clazzName);
                }

                if (clazz.getAnnotation(Bean.class) == null) {
                    System.err.printf("Class %s 并未注解为 Bean 类\n", clazz.getName());
                }

                Object target = clazz.newInstance();
                InvocationHandler handler = new CommonInvokeHandler(target, aopTarget, methodName);

                Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(),clazz.getInterfaces(), handler);

                ioc.addProxyBean(target, proxy);

            }
        }
    }
}
