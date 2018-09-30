package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 31/07/2018.
 */
@Entity
public class SignsOfInfectionEntity {
    @Id
    private Date dateTime;
    private boolean signsOfInfection;
    private boolean stoma;
    private boolean racing;
    private boolean breathless;
    private boolean urine;
    private boolean leaking;
    private boolean DORF;
    private int severity;
    private int bother;
    @Generated(hash = 1964135545)
    public SignsOfInfectionEntity(Date dateTime, boolean signsOfInfection,
            boolean stoma, boolean racing, boolean breathless, boolean urine,
            boolean leaking, boolean DORF, int severity, int bother) {
        this.dateTime = dateTime;
        this.signsOfInfection = signsOfInfection;
        this.stoma = stoma;
        this.racing = racing;
        this.breathless = breathless;
        this.urine = urine;
        this.leaking = leaking;
        this.DORF = DORF;
        this.severity = severity;
        this.bother = bother;
    }
    @Generated(hash = 1411423615)
    public SignsOfInfectionEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getSignsOfInfection() {
        return this.signsOfInfection;
    }
    public void setSignsOfInfection(boolean signsOfInfection) {
        this.signsOfInfection = signsOfInfection;
    }
    public boolean getStoma() {
        return this.stoma;
    }
    public void setStoma(boolean stoma) {
        this.stoma = stoma;
    }
    public boolean getRacing() {
        return this.racing;
    }
    public void setRacing(boolean racing) {
        this.racing = racing;
    }
    public boolean getBreathless() {
        return this.breathless;
    }
    public void setBreathless(boolean breathless) {
        this.breathless = breathless;
    }
    public boolean getUrine() {
        return this.urine;
    }
    public void setUrine(boolean urine) {
        this.urine = urine;
    }
    public boolean getLeaking() {
        return this.leaking;
    }
    public void setLeaking(boolean leaking) {
        this.leaking = leaking;
    }
    public boolean getDORF() {
        return this.DORF;
    }
    public void setDORF(boolean DORF) {
        this.DORF = DORF;
    }
    public int getSeverity() {
        return this.severity;
    }
    public void setSeverity(int severity) {
        this.severity = severity;
    }
    public int getBother() {
        return this.bother;
    }
    public void setBother(int bother) {
        this.bother = bother;
    }
}
