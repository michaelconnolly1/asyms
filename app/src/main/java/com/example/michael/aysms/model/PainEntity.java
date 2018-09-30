package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 01/08/2018.
 */

@Entity
public class PainEntity {
    @Id
    private Date dateTime;
    private boolean pain;
    @Generated(hash = 665313369)
    public PainEntity(Date dateTime, boolean pain) {
        this.dateTime = dateTime;
        this.pain = pain;
    }
    @Generated(hash = 1857280933)
    public PainEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getPain() {
        return this.pain;
    }
    public void setPain(boolean pain) {
        this.pain = pain;
    }

}
