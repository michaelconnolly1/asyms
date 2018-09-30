package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 30/07/2018.
 */
@Entity
public class TirednessEntity {
    @Id
    private Date dateTime;
    private boolean tiredness;
    private int bed;
    private int botherLevel;
    private int severity;
    private int selfCare;
    @Generated(hash = 1021650257)
    public TirednessEntity(Date dateTime, boolean tiredness, int bed,
            int botherLevel, int severity, int selfCare) {
        this.dateTime = dateTime;
        this.tiredness = tiredness;
        this.bed = bed;
        this.botherLevel = botherLevel;
        this.severity = severity;
        this.selfCare = selfCare;
    }
    @Generated(hash = 74268815)
    public TirednessEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getTiredness() {
        return this.tiredness;
    }
    public void setTiredness(boolean tiredness) {
        this.tiredness = tiredness;
    }
    public int getBed() {
        return this.bed;
    }
    public void setBed(int bed) {
        this.bed = bed;
    }
    public int getBotherLevel() {
        return this.botherLevel;
    }
    public void setBotherLevel(int botherLevel) {
        this.botherLevel = botherLevel;
    }
    public int getSeverity() {
        return this.severity;
    }
    public void setSeverity(int severity) {
        this.severity = severity;
    }
    public int getSelfCare() {
        return this.selfCare;
    }
    public void setSelfCare(int selfCare) {
        this.selfCare = selfCare;
    }
}
