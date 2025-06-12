package org.drpl.telefe.app.controller;

import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.Pages;
import javafx.fxml.FXML;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.dto.UserType;

public class MainController implements Page {
    private MainApplication mainApplication;
    private Model model;

    @FXML
    private void initialize() {
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
        this.updatePageContent();
    }

    @Override
    public void updatePageContent() {
        System.out.println(model.getUserType());
        if (model.getUserType() == null) {
            PageLoader.load(mainApplication, Pages.AUTH_SPLASHSCREEN, model);
        }
        else if (model.getUserType() == UserType.PATIENT) {
            PageLoader.load(mainApplication, Pages.PATIENT_HOMEPAGE, model);
        }
        else if (model.getUserType() == UserType.DOCTOR) {

        }
        else if (model.getUserType() == UserType.PHARMACIST) {

        }
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

}