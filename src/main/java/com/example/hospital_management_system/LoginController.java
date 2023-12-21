package com.example.hospital_management_system;

import java.io.IOException;
        import java.net.URL;
        import java.sql.Connection;
        import java.util.ResourceBundle;

        import com.example.hospital_management_system.database.DbFunctions;
        import com.example.hospital_management_system.entities.User;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextField;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;

public class LoginController {
    private DbFunctions dbFunctions;
    private Connection conn;
    private int userId;
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return userId;
    }


    @FXML
    private Text error_text;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Button signin;

    @FXML
    private Button signup;

    @FXML
    void onSignIn(ActionEvent event) {
        if (dbFunctions.isUserExistAndPassword(conn, email.getText(), password.getText())){
            loadPage("patient-accounting-view.fxml", true);
        }
        else {
            error_text.setText("user is already exist!");
        }
    }

    @FXML
    void onSignUp(ActionEvent event) {
        loadPage("register-view.fxml", false);
    }

    @FXML
    void initialize() {
        dbFunctions=new DbFunctions();
        System.out.println("ok1");
        conn=dbFunctions.connect_to_db();
        System.out.println("ok12");

        dbFunctions.createTableUser(conn);
        System.out.println("ok123");



    }
    public void loadPage(String page, boolean v){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            if (v){
                PatientAccountController addTypeController = loader.getController();
                addTypeController.setUserEmail(email.getText());

            }

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна)
            Stage stage = (Stage) signin.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
