package org.drpl.telefe.app.controller.patient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.domain.User;
import org.drpl.telefe.fetcher.AuthFetcher;

import java.io.IOException;

public class ProfilePageController implements Page {
    private Model model;
    private MainApplication mainApplication;

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label alamatLabel;
    @FXML
    private Label tanggalLahirLabel;

    @Override
    public void updatePageContent() {
        AuthFetcher authFetcher = new AuthFetcher();
        try {
            User user = authFetcher.getUserById(model.getCurrentUser().getId());
            model.setCurrentUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (model.getCurrentUser() != null) {
            idLabel.setText(String.valueOf(model.getCurrentUser().getId()));
            nameLabel.setText(model.getCurrentUser().getName());
            emailLabel.setText(model.getCurrentUser().getEmail());
            alamatLabel.setText(model.getCurrentUser().getAlamat());
            tanggalLahirLabel.setText(model.getCurrentUser().getTanggalLahir().toString());
        }
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        this.updatePageContent();
    }

    @FXML
    public void onBack() {
        System.out.println("pressed back button");
        PageLoader.load(mainApplication, Pages.PATIENT_HOMEPAGE, model);
    }
}
