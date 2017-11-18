package com.github.taller.calculator.ui;


import com.github.taller.calculator.Main;
import com.github.taller.calculator.model.Action;
import com.github.taller.calculator.model.CalculatorModel;
import com.github.taller.calculator.model.Plugin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorController {

    private Stage selfStage;

    private String currentData = null;

    @FXML
    private Label DISPLAY;

    @FXML
    private ComboBox<Action> OPERATIONS;

    @FXML
    public void addDigit(MouseEvent event) {
        String pressed = ((Button) event.getSource()).getText();

        if ("•".equals(pressed)) {
            pressed = ".";
        }

        System.out.println("addDigit " + pressed);

        if (currentData == null || ("0".equals(currentData) && !".".equals(pressed)) ) {
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

        if (currentData == null || currentData.isEmpty() || currentData.length() == 1) {
            currentData = "0";
        } else {
            currentData = currentData.substring(0, currentData.length() - 1);
        }

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
    public void openPluginList(Event event) throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ui/PluginList.fxml"));
        Parent root = loader.load();

        PluginsController controller = loader.<PluginsController>getController();
        controller.setSelfStage(stage);

        stage.setScene(new Scene(root));
        stage.setTitle("Plugin list");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(selfStage);
        stage.show();
        stage.setOnCloseRequest(e -> refreshOperations());
    }


    @FXML
    void initialize() throws Exception {
        refreshOperations();
    }

    private void refreshOperations() {
        CalculatorModel calculatorModel = CalculatorModel.getInstance();
        OPERATIONS.setItems(null);

        ObservableList<Action> operationList = FXCollections.observableArrayList(calculatorModel.getAvailableActions());
        OPERATIONS.setItems(operationList);
    }

    public void setSelfStage(Stage stage) {
        this.selfStage = stage;
    }
}
