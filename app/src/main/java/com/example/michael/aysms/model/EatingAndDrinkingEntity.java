package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 29/07/2018.
 */
@Entity
public class EatingAndDrinkingEntity {
    @Id
    private Date dateTime;
    private boolean eatingAndDrinking;
    private int unwell;
    private int botherLevel;
    private int feelThirsty;
    private String notDrinkingReason;
    private int mouthDry;
    @Generated(hash = 753209751)
    public EatingAndDrinkingEntity(Date dateTime, boolean eatingAndDrinking,
            int unwell, int botherLevel, int feelThirsty, String notDrinkingReason,
            int mouthDry) {
        this.dateTime = dateTime;
        this.eatingAndDrinking = eatingAndDrinking;
        this.unwell = unwell;
        this.botherLevel = botherLevel;
        this.feelThirsty = feelThirsty;
        this.notDrinkingReason = notDrinkingReason;
        this.mouthDry = mouthDry;
    }
    @Generated(hash = 1379506822)
    public EatingAndDrinkingEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getEatingAndDrinking() {
        return this.eatingAndDrinking;
    }
    public void setEatingAndDrinking(boolean eatingAndDrinking) {
        this.eatingAndDrinking = eatingAndDrinking;
    }
    public int getUnwell() {
        return this.unwell;
    }
    public void setUnwell(int unwell) {
        this.unwell = unwell;
    }
    public int getBotherLevel() {
        return this.botherLevel;
    }
    public void setBotherLevel(int botherLevel) {
        this.botherLevel = botherLevel;
    }
    public int getFeelThirsty() {
        return this.feelThirsty;
    }
    public void setFeelThirsty(int feelThirsty) {
        this.feelThirsty = feelThirsty;
    }
    public String getNotDrinkingReason() {
        return this.notDrinkingReason;
    }
    public void setNotDrinkingReason(String notDrinkingReason) {
        this.notDrinkingReason = notDrinkingReason;
    }
    public int getMouthDry() {
        return this.mouthDry;
    }
    public void setMouthDry(int mouthDry) {
        this.mouthDry = mouthDry;
    }
    
}
