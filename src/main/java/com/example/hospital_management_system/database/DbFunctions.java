package com.example.hospital_management_system.database;


import com.example.hospital_management_system.entities.Patient;
import com.example.hospital_management_system.entities.User;
import javafx.scene.Parent;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DbFunctions {
    public Connection connect_to_db(){
        Connection conn=null;
        try{
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+"hospital_management_system","postgres","1234");
            if(conn!=null){
                System.out.println("Connection Established");
            }
            else{
                System.out.println("Connection Failed");
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return conn;
    }
    public void delete_row_by_id(Connection conn,String table_name, int id){
        Statement statement;
        try{
            String query=String.format("delete from %s where id= %s",table_name,id);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Deleted");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createTableUser(Connection conn) {
        Statement statement;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "user", null);

            if (!resultSet.next()) {
                String query = "CREATE TABLE \"user\" ("
                        + "id SERIAL PRIMARY KEY, "
                        + "email VARCHAR(255), "
                        + "password VARCHAR(255) );";
                statement = conn.createStatement();
                statement.executeUpdate(query);
                System.out.println("Table Created");
            } else {
                System.out.println("Table already exists");
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void createTablePatient(Connection conn) {
        Statement statement;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "patient", null);

            if (!resultSet.next()) {
                String query = "CREATE TABLE \"patient\" ("
                        + "id SERIAL PRIMARY KEY, "
                        + "name VARCHAR(255), "
                        + "phone VARCHAR(255), "
                        + "address VARCHAR(255), "
                        + "doctor_name VARCHAR(255), "
                        + "diseases VARCHAR(255), "
                        + "count VARCHAR(255), "
                        + "time VARCHAR(255) );";
                statement = conn.createStatement();
                statement.executeUpdate(query);
                System.out.println("Table Created");
            } else {
                System.out.println("Table already exists");
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insert_into_user(Connection conn, User user) {
        Statement statement;
        try {
            String query = String.format("INSERT INTO \"user\" (\"email\", \"password\") VALUES ('%s', '%s');", user.getEmail(), user.getPassword());
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void updatePatient(Connection conn, Patient patient) {
        String query = "UPDATE patient SET name=?, phone=?, address=?, doctor_name=?, diseases=?, count=?, time=? WHERE id=?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, patient.getPatient_name());
            preparedStatement.setString(2, patient.getNumber());
            preparedStatement.setString(3, patient.getAddress());
            preparedStatement.setString(4, patient.getDoctor_name());
            preparedStatement.setString(5, patient.getDiseases());
            preparedStatement.setInt(6, patient.getCount());
            preparedStatement.setString(7, LocalDateTime.now().toString());
            preparedStatement.setLong(8, patient.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Row Updated");
            } else {
                System.out.println("No rows were updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insert_into_patient(Connection conn, Patient patient) {
        String query = "INSERT INTO patient (name, phone, address, doctor_name, diseases, count, time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, patient.getPatient_name());
            preparedStatement.setString(2, patient.getNumber());
            preparedStatement.setString(3, patient.getAddress());
            preparedStatement.setString(4, patient.getDoctor_name());
            preparedStatement.setString(5, patient.getDiseases());
            preparedStatement.setString(6, String.valueOf(patient.getCount()));
            preparedStatement.setString(7, LocalDateTime.now().toString());

            preparedStatement.executeUpdate();
            System.out.println("Row Inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isUserExist(Connection conn, String email) {
        Statement statement;
        ResultSet rs=null;
        boolean isExist = false;
        try {
            String query = String.format("SELECT * FROM \"user\" WHERE email = '%s'", email);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                isExist = true;
                }

        }
        catch (Exception e){
            System.out.println(e);
        }
        return isExist;
    }
    public boolean isUserExistAndPassword(Connection conn, String email, String password) {
        Statement statement;
        ResultSet rs=null;
        boolean isExist = false;
        try {
            String query = String.format("SELECT * FROM \"user\" WHERE email = '%s' AND password = '%s' ", email, password);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                isExist = true;
                }

        }
        catch (Exception e){
            System.out.println(e);
        }
        return isExist;
    }

    public List<Patient> selectPatients(Connection conn) {
        Statement statement;
        List<Patient> patients = new ArrayList<>();

        try {
            String query = "select * from patient";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setTime(resultSet.getString("time"));
                patient.setNumber(resultSet.getString("phone"));
                patient.setPatient_name(resultSet.getString("name"));
                patient.setAddress(resultSet.getString("address"));
                patient.setDiseases(resultSet.getString("diseases"));
                patient.setDoctor_name(resultSet.getString("doctor_name"));
                patient.setCount(resultSet.getInt("count"));

                patients.add(patient);
            }

            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return patients;
    }

}