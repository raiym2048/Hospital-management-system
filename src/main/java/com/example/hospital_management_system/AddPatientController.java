package com.example.hospital_management_system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.example.hospital_management_system.database.DbFunctions;
import com.example.hospital_management_system.entities.Patient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddPatientController {
    private DbFunctions dbFunctions;
    private Connection conn;

    @FXML
    private Text userEmail;

    public void setUserEmail(String email){
        userEmail.setText(email);
    }
    public String getUserEmail(){
        return  userEmail.getText();
    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logOut;


    @FXML
    private Button add;

    @FXML
    private TextField address;

    @FXML
    private TextField count;

    @FXML
    private TextField deseases;

    @FXML
    private TextField doctors_name;

    @FXML
    private TextField patient_name;

    @FXML
    private TextField phone;

    @FXML
    private Text timelyText;

    @FXML
    private Button main;


    @FXML
    void onMain(ActionEvent event) {
        loadPage("patient-accounting-view.fxml", true);
    }


    @FXML
    void onLogOut(ActionEvent event) {
        loadPage("register-view.fxml", false);
    }


    @FXML
    void onSave(ActionEvent event) {
        Patient patient = new Patient();
        patient.setPatient_name(patient_name.getText());
        patient.setAddress(address.getText());
        patient.setCount(Integer.parseInt(count.getText()));
        patient.setDiseases(deseases.getText());
        patient.setNumber(phone.getText());
        patient.setDoctor_name(doctors_name.getText());
        patient.setTime(LocalDateTime.now().toString());
        dbFunctions.insert_into_patient(conn, patient);

        //the text should be empty after button clicked after 2 second
    }

    @FXML
    void initialize() {
        dbFunctions = new DbFunctions();
        conn = dbFunctions.connect_to_db();
        dbFunctions.createTablePatient(conn);

        // Set up a Timeline with a KeyFrame
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(4), event -> clearText())
        );

        // Set the timeline to only trigger once
        timeline.setCycleCount(1);

        // Initialize the timeline, but don't start it yet
        timeline.setAutoReverse(false);

        // Assign the timeline to the add button
        add.setOnAction(event -> {
            Patient patient = new Patient();
            patient.setPatient_name(patient_name.getText());
            patient.setAddress(address.getText());
            patient.setCount(Integer.parseInt(count.getText()));
            patient.setDiseases(deseases.getText());
            patient.setNumber(phone.getText());
            patient.setDoctor_name(doctors_name.getText());
            patient.setTime(LocalDateTime.now().toString());
            dbFunctions.insert_into_patient(conn, patient);
            timelyText.setText("added successfully!");
            // Start the timeline when the button is clicked

            timeline.play();
        });
    }

    private void clearText() {
        // Clear the text fields after 2 seconds
        patient_name.clear();
        address.clear();
        count.clear();
        deseases.clear();
        phone.clear();
        doctors_name.clear();
        timelyText.setText("");
    }
    public void loadPage(String page, boolean v){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            // Создание новой сцены
            Scene scene = new Scene(root, 730,400);

            if (v){
                PatientAccountController patientAccountController = loader.getController();
                patientAccountController.setUserEmail(userEmail.getText());
            }

            // Получение текущего Stage (окна)
            Stage stage = (Stage) logOut.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
