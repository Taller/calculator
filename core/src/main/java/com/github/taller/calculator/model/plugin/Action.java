package com.github.taller.calculator.model.plugin;

import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.exports.OperationType;

import java.net.URL;

public class Action {

    private Operation operation;

    private String displayName;

    private URL source;

    public Action(Class<Operation> clazz, URL source) throws IllegalAccessException, InstantiationException {
        this.operation = clazz.newInstance();
        this.displayName = this.operation.toString();
        this.source = source;

        if (!displayName.trim().endsWith("(")
                && operation.getType() == OperationType.FUNCTION
                && !displayName.trim().endsWith(")")) {
            displayName += "(";
        }
    }

    public Operation getOperation() {
        return operation;
    }

    public String getDisplayName() {
        return displayName;
    }

    public URL getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "{ " + displayName + " }, from " + source;
    }
}
