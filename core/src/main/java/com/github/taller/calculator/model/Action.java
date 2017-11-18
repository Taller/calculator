package com.github.taller.calculator.model;

import com.github.taller.calculator.exports.Operation;

import java.net.URL;

public class Action {

    private Operation operation;

    private String displayName;

    private URL source;

    public Action(Class<Operation> clazz, URL source) throws IllegalAccessException, InstantiationException {
        this.operation = clazz.newInstance();
        this.displayName = this.operation.toString();
        this.source = source;
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
