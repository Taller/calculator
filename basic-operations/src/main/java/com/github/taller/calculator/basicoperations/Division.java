package com.github.taller.calculator.basicoperations;


import com.github.taller.calculator.exports.AbstractOperation;
import com.github.taller.calculator.exports.OperationException;
import com.github.taller.calculator.exports.OperationType;

import java.math.BigDecimal;

public class Division extends AbstractOperation {

    @Override
    public OperationType getType() {
        return OperationType.BINARY;
    }

    @Override
    public String binary(String argLeft, String argRight) throws OperationException {

        BigDecimal bd1 = new BigDecimal(argLeft);
        BigDecimal bd2 = new BigDecimal(argRight);

        if (BigDecimal.ZERO.equals(bd2)) {
            throw new OperationException("Division by zero exception.");
        }

        BigDecimal bdResult = bd1.divide(bd2, 6, BigDecimal.ROUND_HALF_UP);
        System.out.println("Division.binary " + bdResult);
        return bdResult.toPlainString();
    }

    @Override
    public String toString() {
        return "/";
    }
}
