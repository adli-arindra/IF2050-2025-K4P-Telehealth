package org.drpl.telefe.app.controller.auth;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.app.utils.ToastFactory;
import org.drpl.telefe.app.utils.ToastType;
import org.drpl.telefe.domain.User;
import org.drpl.telefe.dto.LoginResponse;
import org.drpl.telefe.dto.UserType;
import org.drpl.telefe.fetcher.AuthFetcher;

import java.io.IOException;

public class SignInController implements Page {
    private MainApplication mainApplication;
    private Model model;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void updatePageContent() {

    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @Override
    public void setModel(Model model) { this.model = model; }

    @FXML
    public void toSplashScreen() {
        PageLoader.load(mainApplication, Pages.AUTH_SPLASHSCREEN, model);
    }

    @FXML
    public void toSignUp() {
        PageLoader.load(mainApplication, Pages.AUTH_SIGNUP, model);
    }

    @FXML
    public void signIn() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        AuthFetcher authFetcher = new AuthFetcher();
        try {
            LoginResponse response = authFetcher.login(email, password);
            model.setCurrentUser(response.getUser());
            model.setUserType(response.getUserType());
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Sign-In success!", ToastType.SUCCESS);
            UserType userType = model.getUserType();
            if (userType == UserType.PATIENT) {
                PageLoader.load(mainApplication, Pages.PATIENT_HOMEPAGE, model);
            }
            else if (userType == UserType.DOCTOR) {
                PageLoader.load(mainApplication, Pages.DOCTOR_CHATSESSIONSPAGE, model);
            }
            else if (userType == UserType.PHARMACIST) {
                PageLoader.load(mainApplication, Pages.PHARMACIST_ORDERSPAGE, model);
            }
        } catch (IOException e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Check your credentials!", ToastType.ERROR);
        }
    }
}
