package com.github.taller.calculator.model.display;

import com.github.taller.calculator.model.plugin.Action;

import java.util.LinkedList;

public final class DisplayModel {

    private LinkedList<DisplayItem> displayItems = new LinkedList<>();

    private String cachedToString = "";


    private DisplayModel() {
    }

    static class HOLDER {
        private static final DisplayModel MODEL = new DisplayModel();
    }

    public static DisplayModel getInstance() {
        return HOLDER.MODEL;
    }

    public void addAction(Action action) {
        DisplayItem di = new DisplayItem(action);

        displayItems.add(di);

        initCachedToString();
    }

    public void removeLast() {
        if (displayItems.isEmpty()) {
            return;
        }

        displayItems.removeLast();

        initCachedToString();
    }

    public void addNewOrEditLastNumber(String newNumber) {
        boolean add = false;

        if (displayItems.isEmpty() || displayItems.getLast().isNumber()) {
            add = true;
        }

        if (!displayItems.isEmpty() && displayItems.getLast().isNumber()) {
            displayItems.removeLast();
        }

        DisplayItem di = new DisplayItem(newNumber);

        displayItems.add(di);

        if (add) {
            initCachedToString();
        }
    }

    private void initCachedToString() {
        StringBuilder sb = new StringBuilder();
        DisplayItem lastItem = displayItems.getLast();
        for (DisplayItem di : displayItems) {
            if (lastItem.isNumber() && di.equals(lastItem)) {
                continue;
            }

            sb.append(di.toString().trim()).append(" ");
        }

        cachedToString = sb.toString();
    }

    public void clear() {
        displayItems = new LinkedList<>();

        cachedToString = "";
    }

    public LinkedList<DisplayItem> getDisplayItems() {
        return new LinkedList<>(displayItems);
    }

    @Override
    public String toString() {
        if (displayItems.isEmpty()) {
            return "";
        }

        if (displayItems.getLast().isAction()) {
            return cachedToString;
        }

        return cachedToString + displayItems.getLast();
    }
}
