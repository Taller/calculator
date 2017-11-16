package com.github.taller.calculator.ui;


import com.github.taller.calculator.model.CalculatorModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CalculatorController {

    private CalculatorModel model = new CalculatorModel();

    private String currentData = null;

    @FXML
    private Label DISPLAY;

    @FXML
    public void addDigit(MouseEvent event) {
        String pressed = ((Button) event.getSource()).getText();

        if ("•".equals(pressed)) {
            pressed = ".";
        }

        System.out.println("addDigit " + pressed);

        if (currentData == null) {
            currentData = pressed;
        } else {
            currentData += pressed;
        }

        DISPLAY.setText(currentData);

        System.out.println(currentData);
    }

    @FXML
    public void removeDigit(MouseEvent event) {
        System.out.println("removeDigit");

        if (currentData == null || currentData.isEmpty()) {
            return;
        }

        currentData = currentData.substring(0, currentData.length() - 1);
        DISPLAY.setText(currentData);

        System.out.println(currentData);
    }

    @FXML
    public void clearData(MouseEvent event) {
        System.out.println("clearData");

        currentData = null;

        DISPLAY.setText("0");
    }

    @FXML
    public void addOperation(MouseEvent event) {
        System.out.println("addOperation");
        System.out.println(((Button) event.getSource()).getText());
    }


    @FXML
    void initialize() throws Exception {
    }
}
