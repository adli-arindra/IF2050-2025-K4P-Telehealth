package org.drpl.telefe.app.controller;

import org.drpl.telefe.Model;
import org.drpl.telefe.app.MainApplication;

public interface Page {
    void updatePageContent();
    void setModel(Model model);
    void setMainApplication(MainApplication mainApplication);
}
