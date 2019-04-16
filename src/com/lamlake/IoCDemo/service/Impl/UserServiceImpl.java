package com.lamlake.IoCDemo.service.Impl;

import com.lamlake.IoCDemo.annotation.Bean;
import com.lamlake.IoCDemo.annotation.Inject;
import com.lamlake.IoCDemo.entity.Student;
import com.lamlake.IoCDemo.service.UserService;

@Bean
public class UserServiceImpl implements UserService {

    @Inject
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public void serviceWithVoid() {
        System.out.println("call Service With Void");
    }

    @Override
    public void serviceWithParam(String str) {
        System.out.println("Param is " + str);
    }

    @Override
    public String serviceReturnString() {
        return "IoC";
    }

    public void callStudentMethod() {
        System.out.println(student.getDescription());
    }
}
