package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 01/08/2018.
 */

@Entity
public class ActivityLevelsEntity {
    @Id
    private Date dateTime;
    private boolean washed;
    private boolean walked;
    private boolean activityc;
    private boolean activityd;
    private int feelingLevel;
    private String reason;
    @Generated(hash = 1143797284)
    public ActivityLevelsEntity(Date dateTime, boolean washed, boolean walked,
            boolean activityc, boolean activityd, int feelingLevel, String reason) {
        this.dateTime = dateTime;
        this.washed = washed;
        this.walked = walked;
        this.activityc = activityc;
        this.activityd = activityd;
        this.feelingLevel = feelingLevel;
        this.reason = reason;
    }
    @Generated(hash = 2061457256)
    public ActivityLevelsEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getWashed() {
        return this.washed;
    }
    public void setWashed(boolean washed) {
        this.washed = washed;
    }
    public boolean getWalked() {
        return this.walked;
    }
    public void setWalked(boolean walked) {
        this.walked = walked;
    }
    public boolean getActivityc() {
        return this.activityc;
    }
    public void setActivityc(boolean activityc) {
        this.activityc = activityc;
    }
    public boolean getActivityd() {
        return this.activityd;
    }
    public void setActivityd(boolean activityd) {
        this.activityd = activityd;
    }
    public int getFeelingLevel() {
        return this.feelingLevel;
    }
    public void setFeelingLevel(int feelingLevel) {
        this.feelingLevel = feelingLevel;
    }
    public String getReason() {
        return this.reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
}
