package com.lamlake.IoCDemo.utils;

import com.lamlake.IoCDemo.annotation.Aop;
import com.lamlake.IoCDemo.annotation.Bean;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ScannerBeanUtil {

    // 获取所有类
    public static List<Class<?>> getBeanClassByPackageName(String pkgName, Enumeration<URL> dirs) {
        List<Class<?>> classes = new ArrayList<>();
//            System.out.println("dirs->" + dirs.hasMoreElements());
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            findAndAddClassesInPackageByFile(pkgName, url.getFile(), classes);
        }
    return classes;
    }

    private static void findAndAddClassesInPackageByFile(String packageName, String filePath, List<Class<?>> classes) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("not exists");
            return;
        }
        File[] dirFiles = dir.listFiles(file -> file.isDirectory() || (file.getName().endsWith(".class")));
        assert dirFiles != null;
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + '/' + file.getName(), file.getAbsolutePath(), classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class clazz = Class.forName(packageName.replace('/', '.') + '.' + className);
                    if (clazz.getAnnotation(Bean.class) != null || clazz.getAnnotation(Aop.class) != null)
                        classes.add(clazz);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
