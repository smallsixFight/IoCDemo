package com.lamlake.IoCDemo;

import com.lamlake.IoCDemo.annotation.Aop;
import com.lamlake.IoCDemo.annotation.Bean;
import com.lamlake.IoCDemo.aop.AopHandler;
import com.lamlake.IoCDemo.ioc.Ioc;
import com.lamlake.IoCDemo.ioc.SimpleIoc;
import com.lamlake.IoCDemo.service.IUserInfo;
import com.lamlake.IoCDemo.service.Impl.UserInfoImpl;
import com.lamlake.IoCDemo.utils.ScannerBeanUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

public class Main {

    public Ioc initContainer(String packageName) {
        packageName = packageName.replace('.', '/');
        SimpleIoc simpleIoc = null;
        try {
            Enumeration<URL> dirs = getClass().getClassLoader().getResources(packageName);
            List<Class<?>> classList = ScannerBeanUtil.getBeanClassByPackageName(packageName, dirs);
            simpleIoc = new SimpleIoc();
            for (Class<?> aClass : classList) {
                if (aClass.getAnnotation(Bean.class) != null) {
                    simpleIoc.addBean(aClass.newInstance());
                } else if (aClass.getAnnotation(Aop.class) != null) {
                    AopHandler.addProxyToIoc(simpleIoc, aClass.newInstance());
                }

            }
        } catch (IOException e) {
            System.err.println("获取资源路径异常！");
            e.printStackTrace();
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
        return simpleIoc;
    }

    private void test() {
        String pkgName = "com.lamlake.IoCDemo";

        Ioc ioc = initContainer(pkgName);
//        UserService userService = (UserService) ioc.getBean(UserService.class.getName());
//        userService.serviceWithVoid();
//        System.out.println(userService.serviceReturnString());
//        userService.serviceWithParam("test");
//
//        UserServiceImpl userService1 = (UserServiceImpl) ioc.getBean(UserServiceImpl.class.getName());
//        userService1.serviceWithVoid();
//        System.out.println(userService1.serviceReturnString());
//        userService1.serviceWithParam("test2");
//        userService1.callStudentMethod();

//        for (String s : ioc.getBeanNames()) {
//            System.out.println(s);
//        }

        IUserInfo userInfo = (IUserInfo)ioc.getBean(IUserInfo.class.getName());
        UserInfoImpl userInfo2 = (UserInfoImpl)ioc.getBean(UserInfoImpl.class.getName());

        System.out.println();
        userInfo.getName();
        userInfo2.getName();

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.test();
    }
}
