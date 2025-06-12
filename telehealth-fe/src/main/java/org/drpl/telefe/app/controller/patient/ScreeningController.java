package org.drpl.telefe.app.controller.patient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.app.utils.ToastFactory;
import org.drpl.telefe.app.utils.ToastType;
import org.drpl.telefe.dto.ChatSessionCreateRequest;
import org.drpl.telefe.dto.ChatSessionResponse;
import org.drpl.telefe.fetcher.ChatFetcher;
import org.drpl.telefe.fetcher.LLMFetcher;
import org.drpl.telefe.fetcher.MedicalRecordFetcher;
import org.drpl.telefe.fetcher.DoctorFetcher;
import org.drpl.telefe.domain.Doctor;
import org.drpl.telefe.domain.MedicalRecord;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScreeningController implements Page {

    private Model model;
    private MainApplication mainApplication;

    @FXML
    private TextArea promptArea;
    @FXML
    private Label specializationLabel;
    @FXML
    private ListView<String> doctorList;
    @FXML
    private Button startConsultationBtn;

    private List<Doctor> fetchedDoctors;

    @FXML
    private void onSubmitPrompt() {
        long patientId = model.getCurrentUser().getId();
        MedicalRecordFetcher medicalRecordFetcher = new MedicalRecordFetcher();
        MedicalRecord record;

        try {
            record = medicalRecordFetcher.getByUserId(patientId);
            if (record == null) {
                PageLoader.load(mainApplication, Pages.PATIENT_MEDICALRECORD, model);
                return;
            }
        } catch (IOException e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Failed to connect to server.", ToastType.ERROR);
            return;
        }

        String userPrompt = promptArea.getText().trim();
        if (userPrompt.isEmpty()) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Prompt cannot be empty.", ToastType.INFO);
            return;
        }

        String combinedPrompt = String.format(
                "I have a condition of %s. And I am currently getting the treatment of %s. %s Please tell me what doctor specialization I need to consult.",
                record.getDiagnosis(),
                record.getTreatment(),
                userPrompt
        );

        LLMFetcher llmFetcher = new LLMFetcher();
        String specialization;

        try {
            specialization = llmFetcher.sendQuery(combinedPrompt, patientId)
                    .replaceAll("\\\\n", "")  // remove literal \n
                    .trim();
        } catch (IOException | InterruptedException e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Failed to query LLM.", ToastType.ERROR);
            return;
        }

        specializationLabel.setText(specialization);

        DoctorFetcher doctorFetcher = new DoctorFetcher();
        List<Doctor> doctors;
        try {
            doctors = doctorFetcher.getDoctorsBySpecialization(specialization);
        } catch (IOException e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Failed to fetch doctors.", ToastType.ERROR);
            return;
        }

        List<String> doctorNames = doctors.stream()
                .map(doc -> doc.getName() + " - " + doc.getEmail())
                .collect(Collectors.toList());
        fetchedDoctors = doctors;

        doctorList.setItems(FXCollections.observableArrayList(doctorNames));
    }

    @Override
    public void updatePageContent() {
        // Optional: preload anything here
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        updatePageContent();
    }

    @FXML
    public void onBack() {
        PageLoader.load(mainApplication, Pages.PATIENT_HOMEPAGE, model);
    }

    @FXML
    private void onDoctorSelected() {
        if (!doctorList.getSelectionModel().isEmpty()) {
            startConsultationBtn.setVisible(true);
        }
    }

    @FXML
    public void onStartChat() {
        int selectedIndex = doctorList.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || fetchedDoctors == null || fetchedDoctors.size() <= selectedIndex) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Please select a doctor", ToastType.ERROR);
            return;
        }

        Doctor selectedDoctor = fetchedDoctors.get(selectedIndex);

        ChatFetcher chatFetcher = new ChatFetcher();
        ChatSessionCreateRequest request = new ChatSessionCreateRequest(
                model.getCurrentUser().getName(),
                List.of(model.getCurrentUser().getId(), selectedDoctor.getId()));
        try {
            ChatSessionResponse response = chatFetcher.createChatSession(request);
            model.setChatSession(response);
            ToastFactory.showToast(mainApplication.getPrimaryStage(), response.toString(), ToastType.SUCCESS);
            PageLoader.load(mainApplication, Pages.PATIENT_CHATROOM, model);
        } catch (Exception e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), e.getMessage(), ToastType.ERROR);
        }
    }


}
