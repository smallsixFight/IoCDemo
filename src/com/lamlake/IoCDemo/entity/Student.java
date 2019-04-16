package com.lamlake.IoCDemo.entity;

import com.lamlake.IoCDemo.annotation.Bean;

@Bean
public class Student {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return "I want to be a student again." + description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
