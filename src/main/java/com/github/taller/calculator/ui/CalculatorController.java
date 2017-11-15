package com.github.taller.calculator.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class CalculatorController {

    @FXML
    private Button digit_0;

    @FXML
    public void digit(MouseEvent event) {
        System.out.println(((Button)event.getSource()).getText());
    }


}
