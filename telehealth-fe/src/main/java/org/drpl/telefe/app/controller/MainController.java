package org.drpl.telefe.app.controller;

import org.drpl.telefe.app.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {

    private MainApplication mainApplication;

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void handleGoHome(ActionEvent event) {
        showPlaceholderPage("Welcome to the Home Page!");
    }

    @FXML
    private void handleGoProducts(ActionEvent event) {
        showPlaceholderPage("This is the Products Page.");
    }

    @FXML
    private void handleGoSettings(ActionEvent event) {
        showPlaceholderPage("Adjust your Settings here.");
    }

    public void showPlaceholderPage(String message) {
        Label placeholderLabel = new Label(message);
        placeholderLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
        StackPane placeholderPane = new StackPane(placeholderLabel);

        if (mainApplication != null) {
            mainApplication.setPageContent(placeholderPane);
        } else {
            System.err.println("MainApplication reference not set in MainController.");
        }
    }
}