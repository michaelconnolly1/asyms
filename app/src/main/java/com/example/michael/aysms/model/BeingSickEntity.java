package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 28/07/2018.
 */
@Entity
public class BeingSickEntity {
    @Id
    private Date dateTime;
    private boolean beingSick;
    private int severity;
    private int botherLevel;
    @Generated(hash = 558985564)
    public BeingSickEntity(Date dateTime, boolean beingSick, int severity,
            int botherLevel) {
        this.dateTime = dateTime;
        this.beingSick = beingSick;
        this.severity = severity;
        this.botherLevel = botherLevel;
    }
    @Generated(hash = 2044918934)
    public BeingSickEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getBeingSick() {
        return this.beingSick;
    }
    public void setBeingSick(boolean beingSick) {
        this.beingSick = beingSick;
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
