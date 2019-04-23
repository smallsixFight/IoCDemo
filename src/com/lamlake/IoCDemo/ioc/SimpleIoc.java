package com.lamlake.IoCDemo.ioc;

import com.lamlake.IoCDemo.annotation.Bean;
import com.lamlake.IoCDemo.annotation.Inject;
import com.lamlake.IoCDemo.bean.BeanDefine;
import com.lamlake.IoCDemo.ioc.Ioc;

import java.lang.reflect.Field;
import java.util.*;

public class SimpleIoc implements Ioc {

    private final Map<String, BeanDefine> pool = new HashMap<>(32);

    @Override
    public void addBean(Object bean) {
        addBean(bean.getClass().getName(), bean);
    }

    @Override
    public void addProxyBean(Object target, Object proxyBean) {
        // 注入依赖
        injectInstanceToBean(proxyBean);
        // 将 bean 放入 pool
        BeanDefine beanDefine = new BeanDefine(proxyBean);
//        for (Annotation annotation : target.getClass().getAnnotations()) {
//            pool.put(annotation.getClass().getName(), beanDefine);
//        }
//        pool.put(target.getClass().getName(), beanDefine);
        // 将 bean 实现的接口放入 pool，bean 作为具体实现类
        addProxyBeanWithInterface(target, beanDefine);
    }

    private void addBean(String name, Object bean) {
        // 注入依赖
        injectInstanceToBean(bean);
        // 将 bean 放入 pool
        BeanDefine beanDefine = new BeanDefine(bean);
        if (pool.put(name, beanDefine) != null)
            System.err.println("the Bean was exists.");
        // 将 bean 实现的接口放入 pool，bean 作为具体实现类
        addBeanWithInterface(beanDefine);
    }

    private void addProxyBeanWithInterface(Object target, BeanDefine beanDefine) {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        if (interfaces.length > 0) {
            for (Class<?> interfaceClazz : interfaces) {
                if (pool.put(interfaceClazz.getName(), beanDefine) != null)
                    System.err.println("旧的 bean 实例被覆盖，名称为 " + interfaceClazz.getName());
            }
        }
    }

    private void addBeanWithInterface(BeanDefine beanDefine) {
        Class<?>[] interfaces = beanDefine.getType().getInterfaces();
        if (interfaces.length > 0) {
            for (Class<?> interfaceClazz : interfaces) {
                pool.putIfAbsent(interfaceClazz.getName(), beanDefine);
//                if (pool.put(interfaceClazz.getName(), beanDefine) != null)
//                    System.err.println("旧的 bean 实例被覆盖，名称为 " + interfaceClazz.getName());
            }
        }
    }

    @Override
    public Object createBean(Class<?> type) {
        try {
            Object object = type.newInstance();
            return new BeanDefine(object, type);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getBean(String name) {
        BeanDefine beanDefine = pool.get(name);
        return beanDefine == null ? null : beanDefine.getBean();
    }

    @Override
    public void remove(String name) {
        pool.remove(name);
    }

    @Override
    public void remove(Class<?> type) {
        pool.remove(type.getSimpleName());
    }

    @Override
    public void clearAll() {
        pool.clear();
    }

    @Override
    public List<Object> getBeans() {
        Set<String> beanNames   = this.getBeanNames();
        List<Object> beans      = new ArrayList<>(beanNames.size());
        for (String beanName : beanNames) {
            Object bean = this.getBean(beanName);
            if (null != bean) {
                beans.add(bean);
            }
        }
        return beans;
    }

    @Override
    public Set<String> getBeanNames() {
        return pool.keySet();
    }

    private void injectInstanceToBean(Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Inject.class) != null) {
                if (field.getType().getAnnotation(Bean.class) == null)
                    System.err.println(field.getName() + " 没有使用注解声明为 Bean 类，但却被执行了注入。");
                try {
                    Object obj = pool.get(field.getType().getName()).getBean();
                    // pool 还不存在该 bean 则创建实例
                    if (obj == null) {
                        obj = field.getType().newInstance();
                        BeanDefine beanDefine = new BeanDefine(obj);

                        pool.put(field.getType().getName(), beanDefine);
                    }
                    field.setAccessible(true);
                    // 注入
                    field.set(bean, obj);
                    field.setAccessible(false);
                } catch (InstantiationException | IllegalAccessException e) {
                    System.err.println("注入失败！");
                    e.printStackTrace();
                }
            }
        }
    }
}
