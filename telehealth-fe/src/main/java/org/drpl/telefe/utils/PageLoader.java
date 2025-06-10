package org.drpl.telefe.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;
import org.drpl.telefe.app.controller.PageUpdateable;

import java.io.IOException;

public class PageLoader {

    public static void load(MainApplication app, String fxmlPath, Model model) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(fxmlPath));
            Node pageContent = loader.load();

            Object controller = loader.getController();
            if (controller instanceof PageUpdateable) {
                ((PageUpdateable) controller).updatePageContent(model);
            }

            app.setPageContent(pageContent);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading " + fxmlPath + ": " + e.getMessage());
        }
    }
}
