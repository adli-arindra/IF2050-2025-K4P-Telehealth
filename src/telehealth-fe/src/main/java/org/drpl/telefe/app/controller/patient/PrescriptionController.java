package org.drpl.telefe.app.controller.patient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.Pages;
import org.drpl.telefe.app.controller.Page;
import org.drpl.telefe.app.utils.PageLoader;
import org.drpl.telefe.app.utils.ToastFactory;
import org.drpl.telefe.app.utils.ToastType;
import org.drpl.telefe.domain.Medicine;
import org.drpl.telefe.domain.Order;
import org.drpl.telefe.dto.OrderRequest;
import org.drpl.telefe.dto.OrderResponse;
import org.drpl.telefe.dto.PrescriptionResponse;
import org.drpl.telefe.fetcher.OrderFetcher;
import org.drpl.telefe.fetcher.PrescriptionFetcher;

import java.util.List;

public class PrescriptionController implements Page {
    private Model model;
    private MainApplication mainApplication;

    @FXML
    private Accordion prescriptionAccordion;
    @FXML
    private Label medicineNameLabel;
    @FXML
    private Label medicineDescLabel;
    @FXML
    private Label medicineDosageLabel;
    @FXML
    private Label medicinePriceLabel;
    @FXML
    private ListView<String> ordersListView;

    private PrescriptionResponse selectedPrescription;

    private void loadOrders() {
        try {
            OrderFetcher orderFetcher = new OrderFetcher();
            List<OrderResponse> responses = orderFetcher.getOrdersByUserId(model.getCurrentUser().getId());

            ordersListView.getItems().clear();
            for (OrderResponse response : responses) {
                StringBuilder summary = new StringBuilder();
                summary.append("Order Date: ").append(response.getOrderDate().toLocalDate()).append("\n");
                summary.append("Paid: ").append(response.isPaid() ? "Yes" : "No").append("\n");
                summary.append("Total Price: $").append(response.getTotalPrice()).append("\n");
                summary.append("Medicines:\n");

//                for (Medicine med : response.getPrescription().getMedicines()) {
//                    summary.append("- ").append(med.getName())
//                            .append(" (").append(med.getDosage()).append(")\n");
//                }

                ordersListView.getItems().add(summary.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePageContent() {
        // Existing prescription loading
        PrescriptionFetcher fetcher = new PrescriptionFetcher();
        try {
            List<PrescriptionResponse> responses = fetcher.getPrescriptionsByUserId(model.getCurrentUser().getId());
            prescriptionAccordion.getPanes().clear();

            for (PrescriptionResponse response : responses) {
                VBox medicineBox = new VBox(5);
                for (Medicine med : response.getMedicines()) {
                    Label medLabel = new Label(med.getName());
                    medLabel.setOnMouseClicked(event -> showMedicineDetails(med));
                    medicineBox.getChildren().add(medLabel);
                }

                TitledPane pane = new TitledPane("Prescription on " + response.getDate().toLocalDate(), medicineBox);
                pane.setUserData(response);
                pane.setOnMouseClicked(event -> selectedPrescription = response);
                prescriptionAccordion.getPanes().add(pane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadOrders();
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    private void showMedicineDetails(Medicine med) {
        medicineNameLabel.setText("Name: " + med.getName());
        medicineDescLabel.setText("Description: " + med.getDescription());
        medicineDosageLabel.setText("Dosage: " + med.getDosage());
        medicinePriceLabel.setText("Price: $" + med.getPrice());
    }

    @FXML
    private void onOrder() {
        if (selectedPrescription == null) return;

        try {
            OrderFetcher fetcher = new OrderFetcher();
            OrderRequest request = new OrderRequest(
                    model.getCurrentUser().getId(),
                    selectedPrescription.getId(),
                    false
            );
            fetcher.createOrder(request);
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Prescription ordered successfully", ToastType.SUCCESS);
        } catch (Exception e) {
            ToastFactory.showToast(mainApplication.getPrimaryStage(), "Failed to order prescription", ToastType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void toBack() {
        PageLoader.load(mainApplication, Pages.PATIENT_HOMEPAGE, model);
    }
}
