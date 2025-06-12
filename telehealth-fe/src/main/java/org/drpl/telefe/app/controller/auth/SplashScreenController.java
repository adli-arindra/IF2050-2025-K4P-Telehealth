package org.drpl.telefe.app.controller.auth;

import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;

public class SplashScreenController implements Page {

    private MainApplication mainApplication;
    private Model model;

    @Override
    public void updatePageContent() {

    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }


    @FXML
    private void handleSignUp(ActionEvent event) {
        System.out.println("Sign Up button clicked.");
        if (mainApplication != null) {
            PageLoader.load(mainApplication, Pages.AUTH_SIGNUP, model);
//            mainApplication.setPageContent(mainApplication.getController().loadPlaceholderPage("Sign Up Page Placeholder"));
//            if (model != null) {
//                model.setAppStatus("Navigating to Sign Up");
//                // You might refresh MainView header here if needed
//                mainApplication.getController().refreshMainViewHeader(); // Assuming MainController has a public getter for itself or a refresh method
//            }
        }
    }

    @FXML
    private void handleSignIn(ActionEvent event) {
        System.out.println("Sign In button clicked. Navigating to Auth Page.");
        if (mainApplication != null) {
            PageLoader.load(mainApplication, Pages.AUTH_SIGNIN, model);
//            mainApplication.getController().loadPage("view/AuthView.fxml", "Authentication Page");
//            if (model != null) {
//                model.setAppStatus("Navigating to Sign In");
//                mainApplication.getController().refreshMainViewHeader();
//            }
        }
    }

    @FXML
    private void initialize() {
        System.out.println("Initializing SplashScreenController.");
    }
}