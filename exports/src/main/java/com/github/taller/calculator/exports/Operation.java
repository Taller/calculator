package com.github.taller.calculator.exports;

public interface Operation {

    OperationType getType();

    String unary(String arg) throws OperationException;

    String binary(String argLeft, String argRight) throws OperationException;

    String function(String ... arg) throws OperationException;

    boolean isFunction();
}
