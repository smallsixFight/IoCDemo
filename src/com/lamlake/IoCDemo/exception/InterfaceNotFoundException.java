package com.lamlake.IoCDemo.exception;

public class InterfaceNotFoundException extends RuntimeException {
    public InterfaceNotFoundException() {
        super();
    }

    public InterfaceNotFoundException(String className) {
        super("Class " + className + " has not implemented interface");
    }
}
