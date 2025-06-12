package org.drpl.telefe.app.controller.patient;

import javafx.fxml.FXML;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.app.utils.ToastFactory;
import org.drpl.telefe.app.utils.ToastType;

public class HomePageController implements Page {
    private Model model;
    private MainApplication mainApplication;

    @Override
    public void updatePageContent() {

    }

    @Override
    public void setModel(Model model) { this.model = model; }

    @Override
    public void setMainApplication(MainApplication mainApplication) { this.mainApplication = mainApplication; }

    public void handleSignOut() {
        this.model.clear();
        ToastFactory.showToast(mainApplication.getPrimaryStage(), "Successfully Signed Out", ToastType.SUCCESS);
        PageLoader.load(mainApplication, Pages.AUTH_SPLASHSCREEN, model);
    }

    @FXML
    public void toProfile() {
        PageLoader.load(mainApplication, Pages.PATIENT_PROFILE, model);
    }

    @FXML
    public void toConsultation() {
        PageLoader.load(mainApplication, Pages.PATIENT_DOCTORSELECTION, model);
    }

    @FXML
    public void toMedicalRecord() {
        PageLoader.load(mainApplication, Pages.PATIENT_MEDICALRECORD, model);
    }

    @FXML
    public void toPrescription() {
        PageLoader.load(mainApplication, Pages.PATIENT_PRESCRIPTION, model);
    }

    @FXML
    public void toScreening() {
        PageLoader.load(mainApplication, Pages.PATIENT_SCREENING, model);
    }
}
