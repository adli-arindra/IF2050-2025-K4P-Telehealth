package org.drpl.telefe.app.controller.doctor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.app.utils.ToastFactory;
import org.drpl.telefe.app.utils.ToastType;
import org.drpl.telefe.domain.Medicine;
import org.drpl.telefe.dto.ChatMessageResponse;
import org.drpl.telefe.dto.ChatMessageSendRequest;
import org.drpl.telefe.dto.PrescriptionRequest;
import org.drpl.telefe.fetcher.ChatFetcher;
import org.drpl.telefe.fetcher.PrescriptionFetcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatRoomController implements Page {
    private Model model;
    private MainApplication mainApplication;
    private final Set<String> shownMessageIds = new HashSet<>();
    private Timeline refreshTimeline;

    @FXML
    private VBox chatContainer;
    @FXML
    private TextField messageField;
    @FXML
    private ScrollPane chatScrollPane;

    @FXML private VBox prescriptionPopup;
    @FXML private TextField medicineNameField;
    @FXML private TextField medicineDescriptionField;
    @FXML private TextField medicineDosageField;
    @FXML private TextField medicinePriceField;

    private final List<Medicine> prescriptionList = new ArrayList<>();

    @Override
    public void updatePageContent() {
        ChatFetcher chatFetcher = new ChatFetcher();
        try {
            List<ChatMessageResponse> responses = chatFetcher.getChatMessages(model.getChatSession().getId());
            for (ChatMessageResponse response : responses) {
                String messageId = response.getId().toString();
                if (shownMessageIds.contains(messageId)) continue;

                shownMessageIds.add(messageId);

                boolean isSender = response.getSenderId().equals(model.getCurrentUser().getId());
                String message = response.getMessage();
                addMessageBubble(message, isSender);
            }
        } catch (Exception e) {
            ToastFactory.showToast(
                    mainApplication.getPrimaryStage(),
                    e.getMessage(),
                    ToastType.ERROR
            );
        }
    }


    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        updatePageContent(); // initial load

        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> updatePageContent()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }


    @FXML
    public void handleBack() {
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
        PageLoader.load(mainApplication, Pages.DOCTOR_CHATSESSIONSPAGE, model);
    }


    @FXML
    public void handleSend() {
        String messageText = messageField.getText().trim();
        if (!messageText.isEmpty()) {
            messageField.clear();

            ChatFetcher chatFetcher = new ChatFetcher();
            ChatMessageSendRequest request = new ChatMessageSendRequest(
                    model.getCurrentUser().getId(),
                    messageText,
                    null);

            try {
                chatFetcher.sendChatMessage(model.getChatSession().getId(), request);
            } catch (Exception e) {
                ToastFactory.showToast(
                        mainApplication.getPrimaryStage(),
                        e.getMessage(),
                        ToastType.ERROR);
            }
        }
    }

    public void customSend(String messageText) {
        if (!messageText.isEmpty()) {
            ChatFetcher chatFetcher = new ChatFetcher();
            ChatMessageSendRequest request = new ChatMessageSendRequest(
                    model.getCurrentUser().getId(),
                    messageText,
                    null);

            try {
                chatFetcher.sendChatMessage(model.getChatSession().getId(), request);
            } catch (Exception e) {
                ToastFactory.showToast(
                        mainApplication.getPrimaryStage(),
                        e.getMessage(),
                        ToastType.ERROR);
            }
        }
    }

    private void addMessageBubble(String message, boolean isSender) {
        Text text = new Text(message);
        text.setStyle("-fx-fill: black;");

        TextFlow bubble = new TextFlow(text);
        bubble.setStyle("-fx-background-color: " + (isSender ? "#d0f0c0" : "#ffffff") + "; -fx-background-radius: 15; -fx-padding: 8;");
        bubble.setPadding(new Insets(5));
        bubble.setMaxWidth(300);

        HBox messageBox = new HBox(bubble);
        messageBox.setAlignment(isSender ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        chatContainer.getChildren().add(messageBox);

        chatScrollPane.layout();
        chatScrollPane.setVvalue(1.0);
    }

    @FXML
    public void showPrescriptionPopup() {
        prescriptionPopup.setVisible(true);
        prescriptionPopup.setManaged(true);
    }

    @FXML
    public void handleAddMedicine() {
        try {
            String name = medicineNameField.getText().trim();
            String desc = medicineDescriptionField.getText().trim();
            String dosage = medicineDosageField.getText().trim();
            BigDecimal price = new BigDecimal(medicinePriceField.getText().trim());

            if (name.isEmpty() || dosage.isEmpty()) {
                ToastFactory.showToast(mainApplication.getPrimaryStage(), "Name and Dosage required", ToastType.ERROR);
                return;
            }

            Medicine med = new Medicine();
            med.setName(name);
            med.setDescription(desc);
            med.setDosage(dosage);
            med.setPrice(price);
            prescriptionList.add(med);

            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Medicine Added", ToastType.SUCCESS);
            medicineNameField.clear();
            medicineDescriptionField.clear();
            medicineDosageField.clear();
            medicinePriceField.clear();

        } catch (NumberFormatException e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Invalid price format", ToastType.ERROR);
        }
    }


    @FXML
    public void handleSendPrescription() {
        if (prescriptionList.isEmpty()) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "No medicines added", ToastType.INFO);
            return;
        }

        Long patientId = model.getChatSession()
                .getParticipantIds()
                .stream()
                .filter(id -> !id.equals(model.getCurrentUser().getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No other participant found"));        Long doctorId = model.getCurrentUser().getId();

        PrescriptionRequest request = new PrescriptionRequest(patientId, doctorId, prescriptionList);

        try {
            PrescriptionFetcher prescriptionFetcher = new PrescriptionFetcher();
            prescriptionFetcher.createPrescription(request);
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Prescription sent", ToastType.SUCCESS);
            handleCancelPrescription();
            prescriptionList.clear();
        } catch (Exception e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), e.getMessage(), ToastType.ERROR);
        }
    }


    @FXML
    public void handleCancelPrescription() {
        prescriptionPopup.setVisible(false);
        prescriptionPopup.setManaged(false);
        medicineNameField.clear();
        medicineDescriptionField.clear();
        medicineDosageField.clear();
        medicinePriceField.clear();
    }


}
