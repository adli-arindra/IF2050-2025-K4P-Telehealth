package org.drpl.telefe.app.controller.patient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.domain.MedicalRecord;
import org.drpl.telefe.fetcher.MedicalRecordFetcher;
import org.drpl.telefe.app.utils.ToastFactory;
import org.drpl.telefe.app.utils.ToastType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MedicalRecordController implements Page {

    @FXML private VBox recordDisplayBox;
    @FXML private VBox recordFormBox;

    @FXML private Label diagnosisLabel;
    @FXML private Label treatmentLabel;
    @FXML private Label recordDateLabel;

    @FXML private TextField diagnosisField;
    @FXML private TextField treatmentField;

    private Model model;
    private MainApplication mainApplication;

    @Override
    public void updatePageContent() {
        MedicalRecordFetcher fetcher = new MedicalRecordFetcher();
        try {
            MedicalRecord record = fetcher.getByUserId(model.getCurrentUser().getId());
            if (record != null) {
                showRecord(record);
            } else {
                showForm();
            }
        } catch (Exception e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Failed to load record", ToastType.ERROR);
            showForm();
        }
    }

    private void showRecord(MedicalRecord record) {
        diagnosisLabel.setText("Diagnosis: " + record.getDiagnosis());
        treatmentLabel.setText("Treatment: " + record.getTreatment());
        recordDateLabel.setText("Date: " + record.getRecordDate().toString());
        recordDisplayBox.setVisible(true);
        recordFormBox.setVisible(false);
    }

    private void showForm() {
        recordDisplayBox.setVisible(false);
        recordFormBox.setVisible(true);
    }

    @FXML
    public void handleSave() {
        String diagnosis = diagnosisField.getText();
        String treatment = treatmentField.getText();

        if (diagnosis.isBlank() || treatment.isBlank()) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "All fields are required", ToastType.INFO);
            return;
        }

        MedicalRecord record = new MedicalRecord(diagnosis, treatment, new Date());
        MedicalRecordFetcher fetcher = new MedicalRecordFetcher();
        try {
            fetcher.save(model.getCurrentUser().getId(), record);
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Record saved", ToastType.SUCCESS);
            updatePageContent(); // Refresh
        } catch (Exception e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Failed to save record", ToastType.ERROR);
        }
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
        updatePageContent();
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    public void toBack() {
        PageLoader.load(mainApplication, Pages.PATIENT_HOMEPAGE, model);
    }
}
