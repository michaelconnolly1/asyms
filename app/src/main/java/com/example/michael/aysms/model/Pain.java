package com.example.michael.aysms.model;

import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.PainEntity;

/**
 * Created by Michael on 01/08/2018.
 */

public class Pain {

    private PainEntityDao painDao;
    private PainEntity painEntity;

    public Pain(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        painEntity = new PainEntity(startDate, false);
        getPainRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (painDao == null)
            painDao = daoSession.getPainEntityDao();
    }

    public void insertPainRecord(boolean painFlag) {
        painEntity.setPain(painFlag);
        painDao.insertOrReplace(painEntity);
    }

    public List<PainEntity> getPainRecords() {
        List<PainEntity> painRecords = painDao.queryBuilder().build().list();
        for (PainEntity pain: painRecords) {
            Log.d("Records: ", pain.getDateTime().toString() + " " + pain.getPain() );
        }
        return painRecords;
    }

    public List<PainEntity> getPainRecordsForDate(Date date) {
        List<PainEntity> painRecords = painDao.queryBuilder().where(PainEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (PainEntity p: painRecords) {
            Log.d("PAIN Records: ", p.getDateTime().toString() + " " + p.getPain());
        }
        return painRecords;
    }
}
