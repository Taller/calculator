package com.github.taller.calculator.basicoperations;

import com.github.taller.calculator.exports.AbstractOperation;
import com.github.taller.calculator.exports.OperationType;

public class ChangeSign extends AbstractOperation {

    @Override
    public OperationType getType() {
        return OperationType.UNARY;
    }

    @Override
    public String unary(String arg) {
        System.out.println("ChangeSign.unary");
        return "ChangeSign.unary";
    }

}
