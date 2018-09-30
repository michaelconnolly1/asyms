package com.example.michael.aysms.model;

/**
 * Created by laptop on 18/09/2018.
 */

public class Patient {
    private int userID;
    private String patientName;
    private String DOB;
    private String gender;
    private String phoneNo;
    private String CHINo;
    private String email;

    public Patient(int userID,String patientName, String DOB, String gender, String phoneNo, String CHINo, String email) {
        this.userID = userID;
        this.patientName = patientName;
        this.DOB = DOB;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.CHINo = CHINo;
        this.email = email;
    }

    public int getUserID() { return userID; }

    public String getPatientName() { return patientName; }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCHINo() {
        return CHINo;
    }

    public void setCHINo(String CHINo) {
        this.CHINo = CHINo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
