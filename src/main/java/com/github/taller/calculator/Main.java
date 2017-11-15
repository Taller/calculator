package com.github.taller.calculator;


import com.github.taller.calculator.ui.AlertOnClose;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private boolean firstTime;

    public static void main(String[] args) {
        Main ui = new Main();
        ui.launchUI();
    }

    public void launchUI() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws AWTException, IOException {
        primaryStage.setOnCloseRequest(event -> {

            event.consume();

            AlertOnClose alert = new AlertOnClose(primaryStage);
            AlertOnClose.HideOrClose answer = alert.waitAnswer();
            if (answer == AlertOnClose.HideOrClose.CLOSE) {
                Platform.exit();
                System.exit(0);
            } else if (answer == AlertOnClose.HideOrClose.CANCELL) {
                //log.debug("Cancel pressed.");
            }
        });

        firstTime = true;
        Platform.setImplicitExit(false);

        URL url = Main.class.getResource("ui/ViewCalculator.fxml");
        FXMLLoader baseLoader = new FXMLLoader(url);
        baseLoader.setRoot(new AnchorPane());

        Scene scene = new Scene(baseLoader.load(), WIDTH, HEIGHT);

        primaryStage.setTitle("JavaFX calculator");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
