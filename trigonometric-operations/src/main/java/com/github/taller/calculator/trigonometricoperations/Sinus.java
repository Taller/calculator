package com.github.taller.calculator.trigonometricoperations;


import com.github.taller.calculator.exports.AbstractOperation;
import com.github.taller.calculator.exports.OperationType;

public class Sinus extends AbstractOperation {

    @Override
    public OperationType getType() {
        return OperationType.FUNCTION;
    }

    @Override
    public String function(String... arg) {

        double sinArg = Double.parseDouble(arg[0]);

        double value = Math.sin(sinArg);

        return String.valueOf(value);
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    @Override
    public String toString() {
        return "Sinus";
    }
}
