package com.github.taller.calculator.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class AlertOnClose extends Alert {

    public enum HideOrClose {
        CLOSE,
        CANCELL
    }

    private ButtonType yesButton = new ButtonType("Yes");

    private ButtonType cancelButton = new ButtonType("Cancel");

    public AlertOnClose(Stage primaryStage) {
        super(AlertType.CONFIRMATION);
        setTitle("Confirmation");
        setHeaderText("Are you sure you want to close the application?");
        initStyle(StageStyle.UTILITY);
        initOwner(primaryStage);
        getButtonTypes().setAll(yesButton, cancelButton);
    }

    public HideOrClose waitAnswer() {
        Optional<ButtonType> result = super.showAndWait();
        if (result.get() == yesButton) {
            return HideOrClose.CLOSE;
        } else if (result.get() == cancelButton) {
            return HideOrClose.CANCELL;
        } else {
            return null;
        }
    }
}
