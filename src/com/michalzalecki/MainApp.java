package com.michalzalecki;

import com.michalzalecki.view.CalculatorController;
import com.michalzalecki.view.RootController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Michal on 2015-03-21.
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Scene rootScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Init stage
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kalkulator programisty");
        this.primaryStage.setResizable(false);

        initRoot();
        initCalculator();
    }

    private void initRoot() {
        try {
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("view/RootLayout.fxml"));
            this.rootLayout = rootLoader.load();
            this.rootScene = new Scene(this.rootLayout);
            this.primaryStage.setScene(this.rootScene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCalculator() {
        try {
            FXMLLoader calculatorLoader = new FXMLLoader(getClass().getResource("view/CalculatorLayout.fxml"));
            AnchorPane calculatorLayout = calculatorLoader.load();
            CalculatorController calculatorController = calculatorLoader.getController();
            calculatorController.setRootScene(this.rootScene);
            this.rootLayout.setCenter(calculatorLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
