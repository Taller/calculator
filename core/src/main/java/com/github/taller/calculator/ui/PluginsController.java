package com.github.taller.calculator.ui;

import com.github.taller.calculator.model.plugin.Action;
import com.github.taller.calculator.model.plugin.Plugin;
import com.github.taller.calculator.model.plugin.PluginModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class PluginsController {

    private Stage selfStage;

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

        PluginModel.getInstance().removePlugin(p.getUrl());

        PLUGIN_LIST.setItems(null);
        OPERATION_LIST.setItems(null);

        addLoadedPluginsAndOperations();
    }

    @FXML
    public void addPlugin() throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        File fileForLoad = fileChooser.showOpenDialog(selfStage);

        if (fileForLoad != null) {
            PluginModel.getInstance().addPlugin(fileForLoad.toURI().toURL());
        }

        PLUGIN_LIST.setItems(null);
        OPERATION_LIST.setItems(null);

        addLoadedPluginsAndOperations();
    }

    public void setSelfStage(Stage stage) {
        this.selfStage = stage;
    }

    private void addLoadedPluginsAndOperations() {
        PluginModel pluginModel = PluginModel.getInstance();

        ObservableList<Plugin> observablePlugins = FXCollections.<Plugin>observableArrayList();
        observablePlugins.addAll(pluginModel.getPlugins());

        PLUGIN_LIST.setItems(observablePlugins);

        ObservableList<Action> observableActions = FXCollections.<Action>observableArrayList();
        observableActions.addAll(pluginModel.getAvailableActions());

        OPERATION_LIST.setItems(observableActions);
    }
}
