package com.michalzalecki.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;

/**
 * Created by Michal on 2015-03-21.
 */
public class RootController {
    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void about(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O programie");
        alert.setHeaderText("Kalkulator programisty");
        alert.setContentText("Autor: Michał Załęcki");
        alert.show();
    }
}
