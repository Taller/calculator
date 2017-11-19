package com.github.taller.calculator.model.calculation;

import com.github.taller.calculator.basicoperations.Addition;
import com.github.taller.calculator.basicoperations.Division;
import com.github.taller.calculator.basicoperations.Multiplication;
import com.github.taller.calculator.basicoperations.Subtraction;
import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.model.display.DisplayItem;
import com.github.taller.calculator.parentheses.RightParenthesis;

import java.util.LinkedList;

public class CalculatorModel {

    enum FunctionType {ONE, TWO, THREE, FOUR, FIVE, SIX}

    private LinkedList<DisplayItem> actions = new LinkedList<>();

    private LinkedList<DisplayItem> numbers = new LinkedList<>();

    private LinkedList<DisplayItem> input = new LinkedList<>();

    public CalculatorModel(LinkedList<DisplayItem> input) {
        this.input = input;
    }

    public String calc() {
        while (!(input.isEmpty() && actions.isEmpty())) {
            DisplayItem item = input.pollFirst();
            if (item == null) {
                FunctionType type = functionCondition(item);
                runFunction(type, item);
                continue;
            }

            if (item.isNumber()) {
                numbers.add(item);
            }

            if (item.isAction()) {
                FunctionType type = functionCondition(item);
                runFunction(type, item);
            }
        }

        return numbers.toString();
    }

    private void runFunction(FunctionType type, DisplayItem item) {
        switch (type) {
            case ONE:
                actions.add(item);
                break;
            case TWO:
                DisplayItem var21 = numbers.pollLast();
                DisplayItem var22 = numbers.pollLast();
                Operation tFirst2 = actions.pollLast().getAction().getOperation();
                String result2 = tFirst2.binary(var22.getNumber(), var21.getNumber());
                DisplayItem var23 = new DisplayItem(result2);
                numbers.add(var23);
                actions.add(item);
                break;
            case THREE:
                DisplayItem var31 = numbers.pollLast();
                Operation op31 = actions.pollLast().getAction().getOperation();
                String result31 = op31.function(var31.getNumber());
                DisplayItem var32 = new DisplayItem(result31);
                numbers.add(var32);
                break;
            case FOUR:
                DisplayItem var41 = numbers.pollLast();
                DisplayItem var42 = numbers.pollLast();
                Operation tFirst4 = actions.pollLast().getAction().getOperation();
                String result4 = tFirst4.binary(var42.getNumber(), var41.getNumber());
                DisplayItem var43 = new DisplayItem(result4);
                numbers.add(var43);
                input.add(item);
                break;
            case FIVE:
                System.err.println("ERROR!");
                break;
            case SIX:
                System.out.println("FINAL!");
                break;
        }

    }


    private FunctionType functionCondition(DisplayItem nextItem) {
        if (nextItem == null) {
            if (actions.isEmpty()) {
                return FunctionType.SIX;
            }

            Operation tFirstOperation = actions.peekLast().getAction().getOperation();

            if (tFirstOperation.isFunction()){
                return FunctionType.FIVE;
            }

            return FunctionType.FOUR;
        }

        Operation nextOperation = nextItem.getAction().getOperation();
        if (nextOperation.isFunction()) {
            return FunctionType.ONE;
        }

        if (nextOperation instanceof RightParenthesis) {
            if (actions.isEmpty()) {
                return FunctionType.FIVE;
            }

            Operation tFirstOperation = actions.peekLast().getAction().getOperation();

            if (tFirstOperation.isFunction()){
                return FunctionType.THREE;
            }

            return FunctionType.FOUR;
        }

        if (nextOperation instanceof Addition || nextOperation instanceof Subtraction) {
            if (actions.isEmpty()) {
                return FunctionType.ONE;
            }

            Operation tFirstOperation = actions.peekLast().getAction().getOperation();
            if (tFirstOperation.isFunction()) {
                return FunctionType.ONE;
            }

            if (tFirstOperation instanceof Addition || tFirstOperation instanceof Subtraction) {
                return FunctionType.TWO;
            }

            if (tFirstOperation instanceof Multiplication || tFirstOperation instanceof Division) {
                return FunctionType.FOUR;
            }
        }

        if (nextOperation instanceof Multiplication || nextOperation instanceof Division) {
            if (actions.isEmpty()) {
                return FunctionType.ONE;
            }

            Operation tFirstOperation = actions.peekLast().getAction().getOperation();
            if (tFirstOperation.isFunction() || tFirstOperation instanceof Addition || tFirstOperation instanceof Subtraction) {
                return FunctionType.ONE;
            }

            if (tFirstOperation instanceof Multiplication || tFirstOperation instanceof Division) {
                return FunctionType.TWO;
            }
        }

        return FunctionType.FIVE;
    }

}
