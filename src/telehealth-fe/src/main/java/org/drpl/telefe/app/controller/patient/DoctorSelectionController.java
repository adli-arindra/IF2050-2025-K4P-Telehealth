package org.drpl.telefe.app.controller.patient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.app.utils.ToastFactory;
import org.drpl.telefe.app.utils.ToastType;
import org.drpl.telefe.domain.Doctor;
import org.drpl.telefe.dto.ChatSessionCreateRequest;
import org.drpl.telefe.dto.ChatSessionResponse;
import org.drpl.telefe.fetcher.ChatFetcher;
import org.drpl.telefe.fetcher.DoctorFetcher;

import java.io.IOException;
import java.util.List;

public class DoctorSelectionController implements Page {
    private Model model;
    private MainApplication mainApplication;

    @FXML
    private ListView<Doctor> doctorListView;
    private List<Doctor> doctorList;

    @FXML
    private Button resumeChatButton;

    @Override
    public void updatePageContent() {
        DoctorFetcher doctorFetcher = new DoctorFetcher();
        try {
            doctorList = doctorFetcher.getAllDoctors();
        } catch (IOException e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Connecting to server failed", ToastType.ERROR);
            return;
        }
        doctorListView.getItems().clear();
        doctorListView.getItems().addAll(doctorList);

        doctorListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Doctor doctor, boolean empty) {
                super.updateItem(doctor, empty);
                if (empty || doctor == null) {
                    setText(null);
                } else {
                    setText(doctor.getName() + " - " + doctor.getSpecialization());
                }
            }
        });

        boolean hasSession = model.getChatSession() != null;
        resumeChatButton.setVisible(hasSession);
        resumeChatButton.setManaged(hasSession);
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
        this.updatePageContent();
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    public void handleStartChat() {
        Doctor selectedDoctor = doctorListView.getSelectionModel().getSelectedItem();

        if (selectedDoctor == null) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Please select a doctor", ToastType.ERROR);
            return;
        }

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

    @FXML
    public void toBack() {
        PageLoader.load(mainApplication, Pages.PATIENT_HOMEPAGE, model);
    }

    @FXML
    public void onResumeChat() {
        PageLoader.load(mainApplication, Pages.PATIENT_CHATROOM, model);
    }
}
