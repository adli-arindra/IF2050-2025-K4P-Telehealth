package org.drpl.telefe.app.controller.auth;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.app.utils.ToastFactory;
import org.drpl.telefe.app.utils.ToastType;
import org.drpl.telefe.domain.User;
import org.drpl.telefe.dto.PatientSignUpRequest;
import org.drpl.telefe.dto.UserType;
import org.drpl.telefe.fetcher.AuthFetcher;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class SignUpController implements Page {
    private Model model;
    private MainApplication mainApplication;

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker birthDatePicker;
    private boolean waitingForBirthDate = false;

    @Override
    public void updatePageContent() {

    }
    @Override
    public void setModel(Model model) { this.model = model; }
    @Override
    public void setMainApplication(MainApplication mainApplication) { this.mainApplication = mainApplication; }

    @FXML
    public void toSplashScreen() { PageLoader.load(mainApplication, Pages.AUTH_SPLASHSCREEN, model); }
    @FXML
    public void toSignIn() { PageLoader.load(mainApplication, Pages.AUTH_SIGNIN, model); }

    private LocalDate showBirthDateDialog() {
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Birth Date");
        dialog.setHeaderText("Select your birth date to continue.");

        // Add buttons
        ButtonType okButtonType = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // DatePicker setup
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Birth Date");
        datePicker.setMaxWidth(Double.MAX_VALUE);

        // Layout using GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Birth Date:"), 0, 0);
        grid.add(datePicker, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable OK button depending on date selection
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> okButton.setDisable(newVal == null));

        // Result converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return datePicker.getValue();
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    @FXML
    public void signUp() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty()) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Please fill out all fields.", ToastType.ERROR);
            return;
        }

        if (!waitingForBirthDate) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Please select your birth date and click Sign Up again.", ToastType.INFO);
            waitingForBirthDate = true;
            return;
        }

        LocalDate birthDate = birthDatePicker.getValue();
        if (birthDate == null) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Try again.", ToastType.INFO);
            return;
        }

        PatientSignUpRequest signUpRequest = new PatientSignUpRequest(
                name, email, password, address, java.sql.Date.valueOf(birthDate)
        );

        AuthFetcher authFetcher = new AuthFetcher();
        try {
            User user = authFetcher.register(signUpRequest);
            model.setCurrentUser(user);
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Sign-Up success!", ToastType.SUCCESS);
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
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "E-mail is already registered!", ToastType.ERROR);
        }

        waitingForBirthDate = false;
    }

}
