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

    public void addCharacter(String ch) {
        String editing = getEditingNumber();

        if (editing.contains(".") && ".".equals(ch)) {
            return;
        }

        if ("0".equals(editing) && !".".equals(ch)) {
            editing = ch;
        } else {
            editing += ch;
        }

        DisplayItem newItem = new DisplayItem(editing);

        if (isNew()) {
            displayItems.add(newItem);
            initCachedToString();

        } else {
            displayItems.removeLast();
            displayItems.add(newItem);
        }
    }

    public void removeCharacter() {

        if (displayItems.isEmpty()) {
            return;
        }

        if (isNew()) {
            displayItems.removeLast();
            initCachedToString();
            return;
        }

        String editing = getEditingNumber();
        editing = editing.substring(0, editing.length() - 1);

        DisplayItem newItem = new DisplayItem(editing);

        displayItems.removeLast();

        if (editing.length() > 0) {
            displayItems.add(newItem);
        }
    }

    private void initCachedToString() {
        if (displayItems.isEmpty()) {
            cachedToString = "";
            return;
        }

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

    private String getEditingNumber() {
        DisplayItem item = displayItems.peekLast();
        if (item == null || item.isAction()) {
            return "0";
        }

        if (item.isNumber()) {
            return item.getNumber();
        }

        return "0";
    }

    private boolean isNew() {
        DisplayItem item = displayItems.peekLast();
        if (item == null || item.isAction()) {
            return true;
        }

        if (item.isNumber()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        if (displayItems.isEmpty()) {
            return "0";
        }

        if (displayItems.getLast().isAction()) {
            return cachedToString;
        }

        return cachedToString + displayItems.getLast();
    }
}
