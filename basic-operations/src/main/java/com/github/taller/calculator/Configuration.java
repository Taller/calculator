package com.github.taller.calculator;

import com.github.taller.calculator.basicoperations.Addition;
import com.github.taller.calculator.basicoperations.Division;
import com.github.taller.calculator.basicoperations.Multiplication;
import com.github.taller.calculator.basicoperations.Subtraction;
import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.exports.OperationsConfiguration;
import com.github.taller.calculator.parentheses.LeftParenthesis;


@OperationsConfiguration(provides = {Addition.class, Subtraction.class, Multiplication.class, Division.class, LeftParenthesis.class})
public class Configuration {
}

