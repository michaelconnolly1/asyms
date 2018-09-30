package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by laptop on 03/09/2018.
 */
@Entity
public class Questionnaire {
    @Id
    private Date dateTime;
    private int questionnaireID;
    @Generated(hash = 1826028800)
    public Questionnaire(Date dateTime, int questionnaireID) {
        this.dateTime = dateTime;
        this.questionnaireID = questionnaireID;
    }
    @Generated(hash = 1622293676)
    public Questionnaire() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public int getQuestionnaireID() {
        return this.questionnaireID;
    }
    public void setQuestionnaireID(int questionnaireID) {
        this.questionnaireID = questionnaireID;
    }
}
