package com.lamlake.IoCDemo;

import java.util.List;
import java.util.Set;

public interface Ioc {
    void addBean(Object bean) throws InstantiationException, IllegalAccessException;

    Object createBean(Class<?> type);

    Object getBean(String name);

    void remove(String name);

    void remove(Class<?> type);

    void clearAll();

    List<Object> getBeans();

    Set<String> getBeanNames();

}
