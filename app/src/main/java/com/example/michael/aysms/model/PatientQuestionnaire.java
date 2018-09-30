package com.example.michael.aysms.model;

import java.util.Date;

/**
 * Created by laptop on 22/09/2018.
 */

public class PatientQuestionnaire {
    int userID;
    int questionnaireID;
    String dateTime;

    public PatientQuestionnaire(int userID, int questionnaireID, String dateTime) {
        this.userID = userID;
        this.questionnaireID = questionnaireID;
        this.dateTime = dateTime;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getQuestionnaireID() {
        return questionnaireID;
    }

    public void setQuestionnaireID(int questionnaireID) {
        this.questionnaireID = questionnaireID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
