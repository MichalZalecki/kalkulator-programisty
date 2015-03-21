package com.michalzalecki;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private AnchorPane calculatorLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Init stage
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kalkulator programisty");

        initRoot();
        initCalculator();
    }

    private void initRoot() {
        try {
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("view/RootLayout.fxml"));
            this.rootLayout = rootLoader.load();
            this.primaryStage.setScene(new Scene(this.rootLayout));
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCalculator() {
        try {
            FXMLLoader calculatorLoader = new FXMLLoader(getClass().getResource("view/CalculatorLayout.fxml"));
            this.calculatorLayout = calculatorLoader.load();
            this.rootLayout.setCenter(this.calculatorLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
