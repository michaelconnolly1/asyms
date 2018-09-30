package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 30/07/2018.
 */
@Entity
public class ClexaneInjectionsEntity {
    @Id
    private Date dateTime;
    private boolean beingSick;
    private boolean unusualBleeding;
    private boolean bruising;
    private boolean fever;
    private boolean swelling;
    @Generated(hash = 1045887776)
    public ClexaneInjectionsEntity(Date dateTime, boolean beingSick,
            boolean unusualBleeding, boolean bruising, boolean fever,
            boolean swelling) {
        this.dateTime = dateTime;
        this.beingSick = beingSick;
        this.unusualBleeding = unusualBleeding;
        this.bruising = bruising;
        this.fever = fever;
        this.swelling = swelling;
    }
    @Generated(hash = 2012962821)
    public ClexaneInjectionsEntity() {
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
    public boolean getUnusualBleeding() {
        return this.unusualBleeding;
    }
    public void setUnusualBleeding(boolean unusualBleeding) {
        this.unusualBleeding = unusualBleeding;
    }
    public boolean getBruising() {
        return this.bruising;
    }
    public void setBruising(boolean bruising) {
        this.bruising = bruising;
    }
    public boolean getFever() {
        return this.fever;
    }
    public void setFever(boolean fever) {
        this.fever = fever;
    }
    public boolean getSwelling() {
        return this.swelling;
    }
    public void setSwelling(boolean swelling) {
        this.swelling = swelling;
    }

}
