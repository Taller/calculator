package com.github.taller.calculator.exports;

public abstract class AbstractOperation implements Operation {

    @Override
    public OperationType getType() {
        throw new RuntimeException("Method is not defined");
    }

    @Override
    public String unary(String arg) throws OperationException {
        throw new RuntimeException("Method is not defined");
    }

    @Override
    public String binary(String argLeft, String argRight) throws OperationException {
        throw new RuntimeException("Method is not defined");
    }

    @Override
    public String function(String... arg) throws OperationException {
        throw new RuntimeException("Method is not defined");
    }

    @Override
    public boolean isFunction() {
        return false;
    }
}
