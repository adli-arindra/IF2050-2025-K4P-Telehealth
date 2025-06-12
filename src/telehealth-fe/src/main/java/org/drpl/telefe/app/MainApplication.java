package org.drpl.telefe.app;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import org.drpl.telefe.Global;
import org.drpl.telefe.domain.User;
import org.drpl.telefe.fetcher.AuthFetcher;

public class MainApplication extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Model model;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Telehealth+");

        this.primaryStage.setResizable(false);
        this.model = new Model();

        AuthFetcher authFetcher = new AuthFetcher();

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/MainView.fxml"));
            rootLayout = loader.load();

            rootLayout.setPrefSize(Global.WIDTH, Global.HEIGHT);

            MainController mainController = loader.getController();
            mainController.setMainApplication(this);
            mainController.setModel(model);

            Scene scene = new Scene(rootLayout, Global.WIDTH, Global.HEIGHT);
            scene.getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm()
            );
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading MainView.fxml: " + e.getMessage());
        }
    }

    public void setPageContent(javafx.scene.Node pageContent) {
        rootLayout.setCenter(pageContent);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
