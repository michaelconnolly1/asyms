package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 28/07/2018.
 */
@Entity
public class FeelingSickEntity {
    @Id
    private Date dateTime;
    private boolean feelingSick;
    private int severity;
    private int botherLevel;
    @Generated(hash = 1956802296)
    public FeelingSickEntity(Date dateTime, boolean feelingSick, int severity,
            int botherLevel) {
        this.dateTime = dateTime;
        this.feelingSick = feelingSick;
        this.severity = severity;
        this.botherLevel = botherLevel;
    }
    @Generated(hash = 1060778870)
    public FeelingSickEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getFeelingSick() {
        return this.feelingSick;
    }
    public void setFeelingSick(boolean feelingSick) {
        this.feelingSick = feelingSick;
    }
    public int getSeverity() {
        return this.severity;
    }
    public void setSeverity(int severity) {
        this.severity = severity;
    }
    public int getBotherLevel() {
        return this.botherLevel;
    }
    public void setBotherLevel(int botherLevel) {
        this.botherLevel = botherLevel;
    }
}
