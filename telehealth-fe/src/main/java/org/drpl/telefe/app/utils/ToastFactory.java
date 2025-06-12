package org.drpl.telefe.app.utils;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;

public class ToastFactory {
    private static final VBox toastContainer = new VBox(10);
    private static final Popup toastPopup = new Popup();
    private static final Queue<StackPane> activeToasts = new LinkedList<>();

    static {
        toastContainer.setPadding(new Insets(10));
        toastContainer.setAlignment(Pos.BOTTOM_CENTER);
        toastPopup.getContent().add(toastContainer);
        toastPopup.setAutoFix(true);
        toastPopup.setAutoHide(false);
    }

    public static void showToast(Stage stage, String message, ToastType type) {
        Platform.runLater(() -> {
            Label label = new Label(message);
            label.setTextFill(Color.WHITE);
            label.setFont(Font.font(14));

            StackPane toast = new StackPane(label);
            toast.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-background-radius: 8;
                -fx-padding: 10 20;
                -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 6, 0, 0, 2);
            """, type.color));

            toast.setOpacity(0);
            toastContainer.getChildren().add(toast);
            activeToasts.add(toast);

            if (!toastPopup.isShowing()) {
                toastPopup.show(stage,
                        stage.getX() + (stage.getWidth() / 2) - 150,
                        stage.getY() + stage.getHeight() - 100);
            }

            // Animation
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), toast);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            PauseTransition wait = new PauseTransition(Duration.seconds(2));

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toast);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(e -> {
                toastContainer.getChildren().remove(toast);
                activeToasts.remove(toast);
                if (activeToasts.isEmpty()) toastPopup.hide();
            });

            SequentialTransition sequence = new SequentialTransition(fadeIn, wait, fadeOut);
            sequence.play();
        });
    }
}

