package org.drpl.telefe.app;

import org.drpl.telefe.app.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("My JavaFX App (Placeholder Pages)");

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/MainView.fxml"));
            rootLayout = loader.load();

            MainController mainController = loader.getController();
            mainController.setMainApplication(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            mainController.showPlaceholderPage("Home Page Placeholder");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading MainView.fxml: " + e.getMessage());
        }
    }

    public void setPageContent(javafx.scene.Node pageContent) {
        rootLayout.setCenter(pageContent);
    }

    public static void main(String[] args) {
        launch(args);
    }
}