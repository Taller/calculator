package com.github.taller.calculator.basicoperations;


import com.github.taller.calculator.exports.AbstractOperation;
import com.github.taller.calculator.exports.OperationType;

import java.math.BigDecimal;

public class Addition extends AbstractOperation {

    @Override
    public OperationType getType() {
        return OperationType.BINARY;
    }

    @Override
    public String binary(String argLeft, String argRight) {
        BigDecimal bd1 = new BigDecimal(argLeft);
        BigDecimal bd2 = new BigDecimal(argRight);
        BigDecimal bdResult = bd1.add(bd2);
        System.out.println("Addition.binary " + bdResult);
        return bdResult.toPlainString();
    }

    @Override
    public String toString() {
        return "+";
    }
}
