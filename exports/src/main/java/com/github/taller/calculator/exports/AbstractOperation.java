package com.github.taller.calculator.exports;

public abstract class AbstractOperation implements Operation {

    @Override
    public OperationType getType() {
        throw new RuntimeException("Method is not defined");
    }

    @Override
    public String unary(String arg) {
        throw new RuntimeException("Method is not defined");
    }

    @Override
    public String binary(String argLeft, String argRight) {
        throw new RuntimeException("Method is not defined");
    }

    @Override
    public String function(String... arg) {
        throw new RuntimeException("Method is not defined");
    }
}
