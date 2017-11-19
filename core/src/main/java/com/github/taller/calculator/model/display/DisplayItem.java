package com.github.taller.calculator.model.display;

import com.github.taller.calculator.model.plugin.Action;

public class DisplayItem {
    enum Type {NUMBER, ACTION}

    private Type type;

    private String number;

    private Action action;

    public DisplayItem(String number) {
        this.number = number;
        this.type = Type.NUMBER;
    }

    public DisplayItem(Action action) {
        this.action = action;
        this.type = Type.ACTION;
    }

    public Type getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public Action getAction() {
        return action;
    }

    public boolean isNumber() {
        return type == Type.NUMBER;
    }

    public boolean isAction() {
        return type == Type.ACTION;
    }

    @Override
    public String toString() {
        if (isAction()) {
            return action.getDisplayName();
        }

        if (isNumber()) {
            return number;
        }

        return "unknown type";
    }
}
