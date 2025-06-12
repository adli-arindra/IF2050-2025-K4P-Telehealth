package org.drpl.telefe.app.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.controller.Page;

import java.io.IOException;

public class PageLoader {

    public static void load(MainApplication mainApplication, String fxmlPath, Model model) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(fxmlPath));
            Node pageContent = loader.load();

            Object controller = loader.getController();
            if (controller instanceof Page) {
                ((Page) controller).setModel(model);
                ((Page) controller).setMainApplication(mainApplication);
                ((Page) controller).updatePageContent();
            }

            mainApplication.setPageContent(pageContent);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading " + fxmlPath + ": " + e.getMessage());
        }
    }
}
