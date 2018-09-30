package com.example.michael.aysms.model;

import android.text.format.DateUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.FeelingSickEntity;

/**
 * Created by Michael on 28/07/2018.
 */

public class FeelingSick {

    private FeelingSickEntityDao fsDao;
    private FeelingSickEntity fsEntity;

    public FeelingSick(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        fsEntity = new FeelingSickEntity(startDate, false, 0, 0);
        getFSRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (fsDao == null)
            fsDao = daoSession.getFeelingSickEntityDao();
    }

    public void insertFSRecord(boolean sickFlag, int severityFlag, int botherFlag) {
        fsEntity.setFeelingSick(sickFlag);
        fsEntity.setSeverity(severityFlag);
        fsEntity.setBotherLevel(botherFlag);
        fsDao.insertOrReplace(fsEntity);
    }

    public List<FeelingSickEntity> getFSRecords() {
        List<FeelingSickEntity> fsRecords = fsDao.queryBuilder().build().list();
        for (FeelingSickEntity fs: fsRecords) {
            Log.d("Records: ", fs.getDateTime().toString() + " " + fs.getFeelingSick() + " " + fs.getSeverity() + " " + fs.getBotherLevel());
        }
        return fsRecords;
    }

    public List<FeelingSickEntity> getFSRecords(Date date) {
        List<FeelingSickEntity> fsRecords = fsDao.queryBuilder().where(FeelingSickEntityDao.Properties.DateTime.between(date.getTime(), date.getTime()+ DateUtils.DAY_IN_MILLIS)).list();
        for (FeelingSickEntity fs: fsRecords) {
            Log.d("Records: ", fs.getDateTime().toString() + " " + fs.getFeelingSick() + " " + fs.getSeverity() + " " + fs.getBotherLevel());
        }
        return fsRecords;
    }

    public List<FeelingSickEntity> getFSRecordsForDate(Date date) {
        List<FeelingSickEntity> fsRecords = fsDao.queryBuilder().where(FeelingSickEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (FeelingSickEntity fs: fsRecords) {
            Log.d("FS Records: ", fs.getDateTime().toString() + " " + fs.getFeelingSick() + " " + fs.getSeverity() + " " + fs.getBotherLevel());
        }
        return fsRecords;
    }
}
