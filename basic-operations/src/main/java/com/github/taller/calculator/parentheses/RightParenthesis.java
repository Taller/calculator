package com.github.taller.calculator.parentheses;

import com.github.taller.calculator.exports.AbstractOperation;
import com.github.taller.calculator.exports.OperationType;

public class RightParenthesis extends AbstractOperation {

    @Override
    public OperationType getType() {
        return OperationType.FUNCTION;
    }

    @Override
    public String function(String... arg) {
        return super.function(arg);
    }

    @Override
    public String toString() {
        return " ) ";
    }
}
