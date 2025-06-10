package org.drpl.telefe.app.controller;

import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import org.drpl.telefe.utils.PageLoader;

import java.io.IOException;

public class MainController implements PageUpdateable {
    private MainApplication mainApplication;
    private Model model;

    public void setModel(Model model) {
        this.model = model;
        this.updatePageContent(this.model);
    }

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void initialize() {
    }

    @Override
    public void updatePageContent(Model model) {
        if (model.getCurrentUser() == null) {
            System.out.println("redirected to splashscreen");
            PageLoader.load(mainApplication, Pages.AUTH_SPLASHSCREEN, model);
        } else {
            System.out.println("redirected to homepage");
        }
    }

}