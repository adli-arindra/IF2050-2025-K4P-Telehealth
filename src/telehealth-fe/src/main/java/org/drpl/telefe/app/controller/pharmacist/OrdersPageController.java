package org.drpl.telefe.app.controller.pharmacist;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.dto.OrderRequest;
import org.drpl.telefe.dto.OrderResponse;
import org.drpl.telefe.fetcher.OrderFetcher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class OrdersPageController implements Page {

    @FXML private VBox orderListContainer;
    @FXML private Label orderDetailLabel;

    private final OrderFetcher orderFetcher = new OrderFetcher();
    private Model model;
    private MainApplication mainApplication;

    @FXML
    public void initialize() {
        loadOrders();
    }

    private void loadOrders() {
        new Thread(() -> {
            try {
                Long pharmacistId = model.getCurrentUser().getId();
                List<OrderResponse> orders = orderFetcher.getOrdersByUserId(pharmacistId);

                Platform.runLater(() -> {
                    orderListContainer.getChildren().clear();
                    for (OrderResponse order : orders) {
                        orderListContainer.getChildren().add(createOrderItem(order));
                    }
                });

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                showError("Failed to load orders: " + e.getMessage());
            }
        }).start();
    }

    private HBox createOrderItem(OrderResponse order) {
        Label summary = new Label("Order #" + order.getId() + " - " +
                (order.isPaid() ? "PAID" : "UNPAID") +
                " - Total: $" + order.getTotalPrice());
        summary.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        Button viewBtn = new Button("View");
        viewBtn.setOnAction(e -> showOrderDetails(order));

        Button markPaidBtn = new Button("Mark as Paid");
        markPaidBtn.setOnAction(e -> markOrderAsPaid(order.getId()));
        markPaidBtn.setDisable(order.isPaid());

        HBox item = new HBox(10, summary, viewBtn, markPaidBtn);
        item.setPadding(new Insets(5));
        item.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");
        return item;
    }

    private void showOrderDetails(OrderResponse order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(order.getId()).append("\n");
        sb.append("Patient ID: ").append(order.getPatientId()).append("\n");
        sb.append("Status: ").append(order.isPaid() ? "PAID" : "UNPAID").append("\n");
        sb.append("Total: $").append(order.getTotalPrice()).append("\n");
        sb.append("Date: ").append(order.getOrderDate()).append("\n");

        if (order.getPrescription() != null && order.getPrescription().getMedicines() != null) {
            sb.append("Medicines:\n");
            order.getPrescription().getMedicines().forEach(med ->
                    sb.append(" - ").append(med.getName())
                            .append(" (").append(med.getDosage()).append(")\n")
            );
        } else {
            sb.append("Medicines: None\n");
        }

        orderDetailLabel.setText(sb.toString());
    }

    private void markOrderAsPaid(Long orderId) {
        new Thread(() -> {
            try {
                Optional<OrderResponse> optionalOrder = orderFetcher.getOrderById(orderId);
                if (optionalOrder.isEmpty()) {
                    showError("Order with ID " + orderId + " not found.");
                    return;
                }

                OrderResponse order = optionalOrder.get();
                OrderRequest updatedRequest = new OrderRequest(order.getPatientId(), order.getId(), true);
                orderFetcher.updateOrder(orderId, updatedRequest);
                orderFetcher.finishOrder(orderId);

                Platform.runLater(this::loadOrders);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                showError("Failed to mark order as paid: " + e.getMessage());
            }
        }).start();
    }

    private void showError(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            alert.showAndWait();
        });
    }

    @FXML
    public void toBack() {
        PageLoader.load(mainApplication, Pages.AUTH_SPLASHSCREEN, model);
    }

    @Override
    public void updatePageContent() {
        loadOrders();
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        updatePageContent();
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }
}
