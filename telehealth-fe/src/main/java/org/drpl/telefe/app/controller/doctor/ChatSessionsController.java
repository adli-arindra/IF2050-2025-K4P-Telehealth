package org.drpl.telefe.app.controller.doctor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatSessionsController implements Page {

    @FXML private VBox sessionListContainer;
    @FXML private ScrollPane scrollPane;
    @FXML private ListView<Long> patientListView;

    private Model model;
    private MainApplication mainApplication;
    private final ChatFetcher chatFetcher = new ChatFetcher();

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        updatePageContent();
    }

    @Override
    public void updatePageContent() {
        if (model == null || model.getCurrentUser() == null) return;

        Long doctorId = model.getCurrentUser().getId();

        new Thread(() -> {
            try {
                List<ChatSessionResponse> sessions = chatFetcher.getChatSessionsByUserId(doctorId);
                Platform.runLater(() -> {
                    displaySessions(sessions);
                    populatePatientList(sessions, doctorId);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> showError("Failed to load sessions: " + e.getMessage()));
            }
        }).start();
    }

    private void displaySessions(List<ChatSessionResponse> sessions) {
        sessionListContainer.getChildren().clear();

        if (sessions.isEmpty()) {
            sessionListContainer.getChildren().add(new Label("No chat sessions found."));
            return;
        }

        for (ChatSessionResponse session : sessions) {
            HBox row = new HBox(10);
            row.setPadding(new Insets(5));
            row.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-radius: 5;");

            Text sessionText = new Text("Session ID: " + session.getId()
                    + "\nParticipants: " + session.getParticipantIds());

            Button openBtn = new Button("Open Chat");
            openBtn.setOnAction(e -> {
                model.setChatSession(session);
                PageLoader.load(mainApplication, Pages.DOCTOR_CHATROOM, model);
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            row.getChildren().addAll(sessionText, spacer, openBtn);
            sessionListContainer.getChildren().add(row);
        }
    }

    private void populatePatientList(List<ChatSessionResponse> sessions, Long doctorId) {
        Set<Long> patientIds = new HashSet<>();
        for (ChatSessionResponse session : sessions) {
            for (Long id : session.getParticipantIds()) {
                if (!id.equals(doctorId)) {
                    patientIds.add(id);
                }
            }
        }
        patientListView.getItems().setAll(patientIds);
    }

    @FXML
    public void handleStartChat() {
        Long selectedPatientId = patientListView.getSelectionModel().getSelectedItem();

        if (selectedPatientId == null) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Please select a patient", ToastType.ERROR);
            return;
        }

        ChatSessionCreateRequest request = new ChatSessionCreateRequest(
                model.getCurrentUser().getName(),
                List.of(model.getCurrentUser().getId(), selectedPatientId)
        );

        new Thread(() -> {
            try {
                ChatSessionResponse response = chatFetcher.createChatSession(request);
                model.setChatSession(response);
                Platform.runLater(() -> {
                    ToastFactory.showToast(mainApplication.getPrimaryStage(), "Session started: " + response.getId(), ToastType.SUCCESS);
                    PageLoader.load(mainApplication, Pages.DOCTOR_CHATROOM, model);
                });
            } catch (Exception e) {
                Platform.runLater(() -> ToastFactory.showToast(mainApplication.getPrimaryStage(), "Failed: " + e.getMessage(), ToastType.ERROR));
            }
        }).start();
    }

    @FXML
    public void toBack() {
        PageLoader.load(mainApplication, Pages.AUTH_SPLASHSCREEN, model);
    }

    private void showError(String msg) {
        ToastFactory.showToast(mainApplication.getPrimaryStage(), msg, ToastType.ERROR);
    }
}
