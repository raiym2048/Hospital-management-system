package com.example.hospital_management_system.entities;

import javafx.beans.property.*;

public class Patient {
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty patient_name = new SimpleStringProperty();
    private final StringProperty number = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty doctor_name = new SimpleStringProperty();
    private final StringProperty diseases = new SimpleStringProperty();
    private final IntegerProperty count = new SimpleIntegerProperty();
    private final StringProperty time = new SimpleStringProperty();

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getPatient_name() {
        return patient_name.get();
    }

    public StringProperty patient_nameProperty() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name.set(patient_name);
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getDoctor_name() {
        return doctor_name.get();
    }

    public StringProperty doctor_nameProperty() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name.set(doctor_name);
    }

    public String getDiseases() {
        return diseases.get();
    }

    public StringProperty diseasesProperty() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases.set(diseases);
    }

    public int getCount() {
        return count.get();
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }
}
