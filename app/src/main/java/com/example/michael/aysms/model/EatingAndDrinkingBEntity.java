package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 30/07/2018.
 */
@Entity
public class EatingAndDrinkingBEntity {
    @Id
    private Date dateTime;
    private boolean eatingAndDrinking;
    private int eatNormal;
    private int botherLevel;
    private int eatSmall;
    private int eatAtAll;
    @Generated(hash = 1649267470)
    public EatingAndDrinkingBEntity(Date dateTime, boolean eatingAndDrinking,
            int eatNormal, int botherLevel, int eatSmall, int eatAtAll) {
        this.dateTime = dateTime;
        this.eatingAndDrinking = eatingAndDrinking;
        this.eatNormal = eatNormal;
        this.botherLevel = botherLevel;
        this.eatSmall = eatSmall;
        this.eatAtAll = eatAtAll;
    }
    @Generated(hash = 987831498)
    public EatingAndDrinkingBEntity() {
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
    public int getEatNormal() {
        return this.eatNormal;
    }
    public void setEatNormal(int eatNormal) {
        this.eatNormal = eatNormal;
    }
    public int getBotherLevel() {
        return this.botherLevel;
    }
    public void setBotherLevel(int botherLevel) {
        this.botherLevel = botherLevel;
    }
    public int getEatSmall() {
        return this.eatSmall;
    }
    public void setEatSmall(int eatSmall) {
        this.eatSmall = eatSmall;
    }
    public int getEatAtAll() {
        return this.eatAtAll;
    }
    public void setEatAtAll(int eatAtAll) {
        this.eatAtAll = eatAtAll;
    }
}
