package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 29/07/2018.
 */
@Entity

public class ConstipationEntity {
    @Id
    private Date dateTime;
    private boolean constipation;
    private int severity;
    private int botherLevel;
    private int lastMovement;
    @Generated(hash = 1992955602)
    public ConstipationEntity(Date dateTime, boolean constipation, int severity,
            int botherLevel, int lastMovement) {
        this.dateTime = dateTime;
        this.constipation = constipation;
        this.severity = severity;
        this.botherLevel = botherLevel;
        this.lastMovement = lastMovement;
    }
    @Generated(hash = 796319828)
    public ConstipationEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getConstipation() {
        return this.constipation;
    }
    public void setConstipation(boolean constipation) {
        this.constipation = constipation;
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
    public int getLastMovement() {
        return this.lastMovement;
    }
    public void setLastMovement(int lastMovement) {
        this.lastMovement = lastMovement;
    }
}
