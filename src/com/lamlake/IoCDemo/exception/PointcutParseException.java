package com.lamlake.IoCDemo.exception;

public class PointcutParseException extends RuntimeException {
    public PointcutParseException() {
        super();
    }

    public PointcutParseException(String methodName) {
        super("Pointcut " + methodName + " is parse failed");
    }
}
