package com.github.taller.calculator.parentheses;

import com.github.taller.calculator.exports.AbstractOperation;
import com.github.taller.calculator.exports.OperationType;

public class LeftParenthesis extends AbstractOperation {

    @Override
    public OperationType getType() {
        return OperationType.FUNCTION;
    }

    @Override
    public String function(String... arg) {
        if (arg.length >= 1) {
            return arg[0];
        }

        return "0";
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    @Override
    public String toString() {
        return " ( ";
    }
}
