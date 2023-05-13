package com.example.lab_darbas_4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    protected void markAttendanceMode(ActionEvent event) throws IOException {
        openStage(event, "attendance-view.fxml", "Mark Attendance");
    }

    @FXML
    protected void editGroupMode(ActionEvent event) throws IOException {
        openStage(event, "edit-view.fxml", "Edit Data");
    }

    @FXML
    protected void dataMode(ActionEvent event) throws IOException {
        openStage(event, "data-view.fxml", "Import / Export Data");
    }

    private void openStage(ActionEvent event, String fxmlName, String title) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}