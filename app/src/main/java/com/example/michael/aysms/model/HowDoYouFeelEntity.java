package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 02/08/2018.
 */
@Entity
public class HowDoYouFeelEntity {
    @Id
    private Date dateTime;
    private int howIFeel;
    private boolean feelingWorried;
    private String explainHowIFeel;
    private String explainWorried;
    @Generated(hash = 1148705937)
    public HowDoYouFeelEntity(Date dateTime, int howIFeel, boolean feelingWorried,
            String explainHowIFeel, String explainWorried) {
        this.dateTime = dateTime;
        this.howIFeel = howIFeel;
        this.feelingWorried = feelingWorried;
        this.explainHowIFeel = explainHowIFeel;
        this.explainWorried = explainWorried;
    }
    @Generated(hash = 1915993011)
    public HowDoYouFeelEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public int getHowIFeel() {
        return this.howIFeel;
    }
    public void setHowIFeel(int howIFeel) {
        this.howIFeel = howIFeel;
    }
    public boolean getFeelingWorried() {
        return this.feelingWorried;
    }
    public void setFeelingWorried(boolean feelingWorried) {
        this.feelingWorried = feelingWorried;
    }
    public String getExplainHowIFeel() {
        return this.explainHowIFeel;
    }
    public void setExplainHowIFeel(String explainHowIFeel) {
        this.explainHowIFeel = explainHowIFeel;
    }
    public String getExplainWorried() {
        return this.explainWorried;
    }
    public void setExplainWorried(String explainWorried) {
        this.explainWorried = explainWorried;
    }
}
