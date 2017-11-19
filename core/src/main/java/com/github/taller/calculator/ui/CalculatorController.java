package com.github.taller.calculator.ui;


import com.github.taller.calculator.Main;
import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.exports.OperationType;
import com.github.taller.calculator.model.calculation.CalculatorModel;
import com.github.taller.calculator.model.display.DisplayItem;
import com.github.taller.calculator.model.display.DisplayModel;
import com.github.taller.calculator.model.plugin.Action;
import com.github.taller.calculator.model.plugin.PluginModel;
import com.github.taller.calculator.parentheses.RightParenthesis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;

public class CalculatorController {

    private Stage selfStage;

    private String currentNumber = "0";

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

        if (("0".equals(currentNumber) && !".".equals(pressed))) {
            currentNumber = pressed;
        } else {
            currentNumber += pressed;
        }

        DisplayModel displayModel = DisplayModel.getInstance();
        displayModel.addNewOrEditLastNumber(currentNumber);

        DISPLAY.setText(displayModel.toString());

        System.out.println(displayModel.toString());
    }

    @FXML
    public void removeDigit(MouseEvent event) {
        System.out.println("removeDigit");

        if ( currentNumber.isEmpty() || currentNumber.length() == 1) {
            currentNumber = "0";
        } else {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
        }

        DisplayModel displayModel = DisplayModel.getInstance();

        displayModel.addNewOrEditLastNumber(currentNumber);

        DISPLAY.setText(displayModel.toString());

        System.out.println(displayModel.toString());
    }

    @FXML
    public void clearData(MouseEvent event) {
        System.out.println("clearData");

        DisplayModel.getInstance().clear();

        currentNumber = "0";

        DISPLAY.setText("0");
    }

    @FXML
    public void addOperation(MouseEvent event) throws InstantiationException, IllegalAccessException, MalformedURLException, ClassNotFoundException {
        System.out.println("addOperation");
        System.out.println(((Button) event.getSource()).getText());

        Action selectedAction = OPERATIONS.getSelectionModel().getSelectedItem();
        if (selectedAction == null) {
            return;
        }

        if (selectedAction.getOperation().getType() == OperationType.FUNCTION && !(selectedAction.getOperation() instanceof RightParenthesis) ) {
            Class<Operation> clazz = (Class<Operation>) ClassLoader.getSystemClassLoader().loadClass(RightParenthesis.class.getCanonicalName());
            OPERATIONS.getItems().add(new Action(clazz, selectedAction.getSource()));
        }


        if (selectedAction.getOperation() instanceof RightParenthesis) {
            OPERATIONS.getItems().remove(selectedAction);
        }

        currentNumber = "";

        DisplayModel displayModel = DisplayModel.getInstance();

        displayModel.addAction(selectedAction);

        DISPLAY.setText(displayModel.toString());

        System.out.println(displayModel.toString());
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
        stage.setOnCloseRequest(e -> refreshOperations());

        stage.show();
    }

    @FXML
    public void calculate() {
        LinkedList<DisplayItem> items = DisplayModel.getInstance().getDisplayItems();
        CalculatorModel c = new CalculatorModel(items);
        DISPLAY.setText(c.calc());
    }

    @FXML
    void initialize() throws Exception {
        refreshOperations();
    }

    private void refreshOperations() {
        PluginModel pluginModel = PluginModel.getInstance();
        OPERATIONS.setItems(null);

        ObservableList<Action> operationList = FXCollections.observableArrayList(pluginModel.getAvailableActions());
        OPERATIONS.setItems(operationList);
    }

    public void setSelfStage(Stage stage) {
        this.selfStage = stage;
    }
}
