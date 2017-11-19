package com.github.taller.calculator.ui;


import com.github.taller.calculator.Main;
import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.exports.OperationType;
import com.github.taller.calculator.log.Print;
import com.github.taller.calculator.model.calculation.CalculatorModel;
import com.github.taller.calculator.model.display.DisplayItem;
import com.github.taller.calculator.model.display.DisplayModel;
import com.github.taller.calculator.model.plugin.Action;
import com.github.taller.calculator.model.plugin.PluginModel;
import com.github.taller.calculator.parentheses.LeftParenthesis;
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
import java.util.Iterator;
import java.util.LinkedList;

public class CalculatorController {

    private Stage selfStage;

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

        Print.msg("addDigit " + pressed);

        DisplayModel displayModel = DisplayModel.getInstance();
        displayModel.addCharacter(pressed);

        DISPLAY.setText(displayModel.toString());

        Print.msg(displayModel.toString());
    }

    @FXML
    public void removeDigit(MouseEvent event) {
        Print.msg("removeDigit");

        DisplayModel displayModel = DisplayModel.getInstance();

        DisplayItem lastItem = displayModel.getDisplayItems().peekLast();

        if (lastItem == null) {
            return;
        }

        Action lastAction = lastItem.getAction();

        if (lastAction != null && lastAction.getOperation() instanceof LeftParenthesis) {
            removeRightParenthesisFromOperations();
        }

        displayModel.removeCharacter();

        DISPLAY.setText(displayModel.toString());

        Print.msg(displayModel.toString());
    }

    @FXML
    public void clearData(MouseEvent event) {
        Print.msg("clearData");

        DisplayModel.getInstance().clear();

        DISPLAY.setText("0");
    }

    @FXML
    public void addOperation(MouseEvent event) throws InstantiationException, IllegalAccessException, MalformedURLException, ClassNotFoundException {
        Print.msg("addOperation");

        Action selectedAction = OPERATIONS.getSelectionModel().getSelectedItem();
        if (selectedAction == null) {
            return;
        }

        if (selectedAction.getOperation().getType() == OperationType.FUNCTION && !(selectedAction.getOperation() instanceof RightParenthesis)) {
            addRightParenthesisToOperations(selectedAction);
        }

        if (selectedAction.getOperation() instanceof RightParenthesis) {
            OPERATIONS.getItems().remove(selectedAction);
        }

        DisplayModel displayModel = DisplayModel.getInstance();
        displayModel.addAction(selectedAction);

        DISPLAY.setText(displayModel.toString());

        Print.msg(displayModel.toString());
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

    @SuppressWarnings("unchecked")
    private void addRightParenthesisToOperations(Action selectedAction) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<Operation> clazz = (Class<Operation>) ClassLoader.getSystemClassLoader().loadClass(RightParenthesis.class.getCanonicalName());
        OPERATIONS.getItems().add(new Action(clazz, selectedAction.getSource()));
    }

    private void removeRightParenthesisFromOperations() {
        Iterator<Action> ait = OPERATIONS.getItems().iterator();
        while (ait.hasNext()) {
            Action action = ait.next();

            if (action.getOperation() instanceof  RightParenthesis) {
                ait.remove();
                break;
            }
        }
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
