package com.example.hospital_management_system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import com.example.hospital_management_system.database.DbFunctions;
import com.example.hospital_management_system.entities.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PatientAccountController {

    private DbFunctions dbFunctions;
    private Connection conn;

    @FXML
    private Text userEmail;

    public void setUserEmail(String email){
        userEmail.setText(email);
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addPaticientAccount;

    @FXML
    private TableColumn<Patient, String> address;

    @FXML
    private TableColumn<Patient, Integer> count;

    @FXML
    private TableColumn<Patient, String> deseases;

    @FXML
    private TableColumn<Patient, String> doctors_name;

    @FXML
    private TableColumn<Patient, Long> id;

    @FXML
    private TableColumn<Patient, String> patient_name;

    @FXML
    private TableColumn<Patient, String> phone;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Patient, String> time;

    @FXML
    private TableView<Patient> all_patients;

    @FXML
    private Button logout;

    @FXML
    private TableColumn<Patient, Void> delete;

    @FXML
    private TableColumn<Patient, Void> update;

    @FXML
    void onLogOut(ActionEvent event) {
        loadPage("register-view.fxml", false);
    }


    @FXML
    void onAdding(ActionEvent event) {
        loadPage("add-patient-view.fxml", true);
    }

    private void loadPatients(Connection conn) {
        DbFunctions dbFunctions = new DbFunctions();
        List<Patient> patients = dbFunctions.selectPatients(conn); // Replace with your actual method
        all_patients.getItems().addAll(patients);
        System.out.println("size of patients:"+patients.size());

        // Clear existing items

        // Set cell values for each column
       }



    @FXML
    void initialize() {
        dbFunctions = new DbFunctions();
        conn = dbFunctions.connect_to_db();

        initializeDeleteColumn();
        initializeUpdateColumn();


        load();

        loadPatients(conn);
    }
    public void deletePatient(Connection conn, Patient patient) {
        dbFunctions.delete_row_by_id(conn,"patient", Math.toIntExact(patient.getId()));
        loadPageAfterDelete("patient-accounting-view.fxml");
    }
    public void updatePatient(Connection conn, Patient patient) {
        //dbFunctions.updatePatient(conn,"patient", Math.toIntExact(patient.getId()));
            loadPageUpdate("patient-accounting-update-view.fxml", patient);
    }

    private void loadPageAfterDelete(String s) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
                Parent root = loader.load();

                PatientAccountController patientAccountController = loader.getController();
                patientAccountController.setUserEmail(userEmail.getText());


                Scene scene = new Scene(root, 730, 400);
                Stage stage = (Stage) logout.getScene().getWindow();
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    private void loadPageUpdate(String s, Patient patient) {
        System.out.println("the patient name is:" + patient.getPatient_name());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
            Parent root = loader.load(); // Load the FXML file and obtain the root and controller

            UpdateController updateController = loader.getController(); // Get the controller instance

            updateController.setUserEmail(userEmail.getText());
            System.out.println("000" + patient.getPatient_name());
            updateController.setPatient(patient);

            Scene scene = new Scene(root, 730, 400);
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeDeleteColumn() {
        delete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setOnAction(event -> {
                    Patient goods = getTableView().getItems().get(getIndex());
                    deletePatient(conn,goods);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }
    private void initializeUpdateColumn() {
        update.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("update");

            {
                updateButton.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    System.out.println("the name1):"+patient.getPatient_name());
                    updatePatient(conn,patient);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });
    }

    public void load(){
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        patient_name.setCellValueFactory(cellData -> cellData.getValue().patient_nameProperty());
        phone.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        address.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        doctors_name.setCellValueFactory(cellData -> cellData.getValue().doctor_nameProperty());
        deseases.setCellValueFactory(cellData -> cellData.getValue().diseasesProperty());
        count.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        time.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
    }
    public void loadPage(String page, boolean v){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            if (v){
                AddPatientController addTypeController = loader.getController();
                addTypeController.setUserEmail(userEmail.getText());
            }

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна)
            Stage stage = (Stage) addPaticientAccount.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
