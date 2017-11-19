package com.github.taller.calculator.model.calculation;

import com.github.taller.calculator.basicoperations.Addition;
import com.github.taller.calculator.basicoperations.Division;
import com.github.taller.calculator.basicoperations.Multiplication;
import com.github.taller.calculator.basicoperations.Subtraction;
import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.model.display.DisplayItem;
import com.github.taller.calculator.parentheses.LeftParenthesis;
import com.github.taller.calculator.parentheses.RightParenthesis;

import java.util.LinkedList;

public class CalculatorModel {

    enum FunctionType {ONE, TWO, THREE, FOUR, FIVE, SIX}

    private LinkedList<DisplayItem> t = new LinkedList<>();

    private LinkedList<DisplayItem> e = new LinkedList<>();

    private LinkedList<DisplayItem> in = new LinkedList<>();

    public CalculatorModel(LinkedList<DisplayItem> in) {
        this.in = in;
    }

    public String calc() {
        while (!(in.isEmpty() && t.isEmpty())) {
            DisplayItem item = in.pollFirst();
            if (item == null) {
                FunctionType type = functionCondition(item);
                runFunction(type, item);
                continue;
            }

            if (item.isNumber()) {
                e.add(item);
            }

            if (item.isAction()) {
                FunctionType type = functionCondition(item);
                runFunction(type, item);
            }
        }

        return e.toString();
    }

    private void runFunction(FunctionType type, DisplayItem item) {
        switch (type) {
            case ONE:
                t.add(item);
                break;
            case TWO:
                DisplayItem var21 = e.pollLast();
                DisplayItem var22 = e.pollLast();
                Operation tFirst2 = t.pollLast().getAction().getOperation();
                String result2 = tFirst2.binary(var21.getNumber(), var22.getNumber());
                DisplayItem var23 = new DisplayItem(result2);
                e.add(var23);
                t.add(item);
                break;
            case THREE:
                t.removeLast();
                break;
            case FOUR:
                DisplayItem var41 = e.pollLast();
                DisplayItem var42 = e.pollLast();
                Operation tFirst4 = t.pollLast().getAction().getOperation();
                String result4 = tFirst4.binary(var41.getNumber(), var42.getNumber());
                DisplayItem var3 = new DisplayItem(result4);
                e.add(var3);
                runFunction(functionCondition(item), item);
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
            if (t.isEmpty()) {
                return FunctionType.SIX;
            }

            Operation tFirstOperation = t.peekLast().getAction().getOperation();

            if (tFirstOperation instanceof LeftParenthesis){
                return FunctionType.FIVE;
            }

            return FunctionType.FOUR;
        }

        Operation nextOperation = nextItem.getAction().getOperation();
        if (nextOperation instanceof LeftParenthesis) {
            return FunctionType.ONE;
        }

        if (nextOperation instanceof RightParenthesis) {
            if (t.isEmpty()) {
                return FunctionType.FIVE;
            }

            Operation tFirstOperation = t.peekLast().getAction().getOperation();

            if (tFirstOperation instanceof LeftParenthesis){
                return FunctionType.THREE;
            }

            return FunctionType.FOUR;
        }

        if (nextOperation instanceof Addition || nextOperation instanceof Subtraction) {
            if (t.isEmpty()) {
                return FunctionType.ONE;
            }

            Operation tFirstOperation = t.peekLast().getAction().getOperation();
            if (tFirstOperation instanceof LeftParenthesis) {
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
            if (t.isEmpty()) {
                return FunctionType.ONE;
            }

            Operation tFirstOperation = t.peekLast().getAction().getOperation();
            if (tFirstOperation instanceof LeftParenthesis || tFirstOperation instanceof Addition || tFirstOperation instanceof Subtraction) {
                return FunctionType.ONE;
            }

            if (tFirstOperation instanceof Multiplication || tFirstOperation instanceof Division) {
                return FunctionType.TWO;
            }
        }

        return FunctionType.FIVE;

    }

}
