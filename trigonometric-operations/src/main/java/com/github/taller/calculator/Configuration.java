package com.github.taller.calculator;

import com.github.taller.calculator.exports.OperationsConfiguration;
import com.github.taller.calculator.trigonometricoperations.Cosine;
import com.github.taller.calculator.trigonometricoperations.Sinus;


@OperationsConfiguration(provides = {Cosine.class, Sinus.class})
public class Configuration {
}
