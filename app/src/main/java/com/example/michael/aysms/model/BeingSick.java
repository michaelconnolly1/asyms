package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.BeingSickEntity;
/**
 * Created by Michael on 28/07/2018.
 */

public class BeingSick {
    private BeingSickEntityDao bsDao;
    private BeingSickEntity bsEntity;

    public BeingSick(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        bsEntity = new BeingSickEntity(startDate, false, 0, 0);
//        getBSRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (bsDao == null)
            bsDao = daoSession.getBeingSickEntityDao();
    }

    public void insertBSRecord(boolean sickFlag, int severityFlag, int botherFlag) {
        bsEntity.setBeingSick(sickFlag);
        bsEntity.setSeverity(severityFlag);
        bsEntity.setBotherLevel(botherFlag);
        bsDao.insertOrReplace(bsEntity);
    }

    public List<BeingSickEntity> getBSRecords() {
        List<BeingSickEntity> bsRecords = null;
        try {
            bsRecords = bsDao.queryBuilder().build().list();
            for (BeingSickEntity bs : bsRecords) {
                Log.d("BS Records: ", bs.getDateTime().toString() + " " + bs.getBeingSick() + " " + bs.getSeverity() + " " + bs.getBotherLevel());
            }
        }
        catch  (SQLiteException e) {
            }
        return bsRecords;
    }

    public List<BeingSickEntity> getBSRecordsForDate(Date date) {
        List<BeingSickEntity> bsRecords = bsDao.queryBuilder().where(BeingSickEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (BeingSickEntity bs: bsRecords) {
            Log.d("Records: ", bs.getDateTime().toString() + " " + bs.getBeingSick() + " " + bs.getSeverity() + " " + bs.getBotherLevel());
        }
        return bsRecords;
    }
}
