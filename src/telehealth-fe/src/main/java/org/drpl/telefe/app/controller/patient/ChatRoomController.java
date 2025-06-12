package org.drpl.telefe.app.controller.patient;

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
import org.drpl.telefe.dto.ChatMessageResponse;
import org.drpl.telefe.dto.ChatMessageSendRequest;
import org.drpl.telefe.fetcher.AuthFetcher;
import org.drpl.telefe.fetcher.ChatFetcher;

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
        updatePageContent();

        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> updatePageContent()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    @FXML
    public void handleBack() {
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
        PageLoader.load(mainApplication, Pages.PATIENT_HOMEPAGE, model);
    }

    @FXML
    public void handleSend() {
        System.out.println(model.getChatSession().toString());
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
}
