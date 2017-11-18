package com.github.taller.calculator.basicoperations;


import com.github.taller.calculator.exports.AbstractOperation;
import com.github.taller.calculator.exports.OperationType;

public class Multiplication extends AbstractOperation {

    @Override
    public OperationType getType() {
        return OperationType.BINARY;
    }

    @Override
    public String binary(String argLeft, String argRight) {
        System.out.println("Multiplication.binary");
        return "Multiplication.binary";
    }

    @Override
    public String toString() {
        return "*";
    }
}
