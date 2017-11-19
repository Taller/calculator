package com.github.taller.calculator.exports;

public interface Operation {

    OperationType getType();

    String unary(String arg);

    String binary(String argLeft, String argRight);

    String function(String ... arg);

    boolean isFunction();
}
