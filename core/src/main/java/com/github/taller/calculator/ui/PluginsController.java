package com.github.taller.calculator.ui;

import com.github.taller.calculator.model.Action;
import com.github.taller.calculator.model.CalculatorModel;
import com.github.taller.calculator.model.Plugin;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class PluginsController {

    private Stage selfStage;

    @FXML
    private AnchorPane AP;

    @FXML
    ListView<Action> OPERATION_LIST;

    @FXML
    ListView<Plugin> PLUGIN_LIST;


    @FXML
    public void initialize() {
        addLoadedPluginsAndOperations();
    }

    @FXML
    public void removePlugin() {
        Plugin p = PLUGIN_LIST.getSelectionModel().getSelectedItem();
        if (p == null) {
            return;
        }

        CalculatorModel.getInstance().removePlugin(p.getUrl());

        PLUGIN_LIST.setItems(null);
        OPERATION_LIST.setItems(null);

        addLoadedPluginsAndOperations();
    }

    @FXML
    public void addPlugin() throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        Stage stage = (Stage) AP.getScene().getWindow();

        File f = fileChooser.showOpenDialog(stage);

        if (f != null) {
            CalculatorModel.getInstance().addPlugin(f.toURI().toURL());
        }

        PLUGIN_LIST.setItems(null);
        OPERATION_LIST.setItems(null);

        addLoadedPluginsAndOperations();
    }

    public void setSelfStage(Stage stage) {
        this.selfStage = stage;
    }

    private void addLoadedPluginsAndOperations() {
        CalculatorModel calculatorModel = CalculatorModel.getInstance();

        ObservableList<Plugin> observablePlugins = FXCollections.<Plugin>observableArrayList();
        observablePlugins.addAll(calculatorModel.getPlugins());

        PLUGIN_LIST.setItems(observablePlugins);

        ObservableList<Action> observableActions = FXCollections.<Action>observableArrayList();
        observableActions.addAll(calculatorModel.getAvailableActions());

        OPERATION_LIST.setItems(observableActions);
    }
}
