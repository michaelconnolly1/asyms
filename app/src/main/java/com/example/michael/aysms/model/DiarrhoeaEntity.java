package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 28/07/2018.
 */
@Entity
public class DiarrhoeaEntity {
    @Id
    private Date dateTime;
    private boolean diarrhoea;
    private int severity;
    private int botherLevel;
    @Generated(hash = 2034896057)
    public DiarrhoeaEntity(Date dateTime, boolean diarrhoea, int severity,
            int botherLevel) {
        this.dateTime = dateTime;
        this.diarrhoea = diarrhoea;
        this.severity = severity;
        this.botherLevel = botherLevel;
    }
    @Generated(hash = 1182585475)
    public DiarrhoeaEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getDiarrhoea() {
        return this.diarrhoea;
    }
    public void setDiarrhoea(boolean diarrhoea) {
        this.diarrhoea = diarrhoea;
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
