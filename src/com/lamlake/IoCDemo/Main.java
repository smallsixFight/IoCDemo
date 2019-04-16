package com.lamlake.IoCDemo;

import com.lamlake.IoCDemo.entity.Student;
import com.lamlake.IoCDemo.service.Impl.UserServiceImpl;
import com.lamlake.IoCDemo.service.UserService;
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
                simpleIoc.addBean(aClass.newInstance());
            }
        } catch (IOException e) {
            System.err.println("获取资源路径异常！");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return simpleIoc;
    }

    public void test() throws IOException, IllegalAccessException, InstantiationException {
        String pkgName = "com.lamlake.IoCDemo";

        Ioc ioc = initContainer(pkgName);
        UserService userService = (UserService) ioc.getBean(UserService.class.getName());
        userService.serviceWithVoid();
        System.out.println(userService.serviceReturnString());
        userService.serviceWithParam("test");

        UserServiceImpl userService1 = (UserServiceImpl) ioc.getBean(UserServiceImpl.class.getName());
        userService1.serviceWithVoid();
        System.out.println(userService1.serviceReturnString());
        userService1.serviceWithParam("test2");
        userService1.callStudentMethod();

    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        Main main = new Main();
        main.test();
    }
}
