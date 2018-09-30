package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.ActivityLevelsEntity;

/**
 * Created by Michael on 01/08/2018.
 */

public class ActivityLevel {
    private ActivityLevelsEntityDao alDao;
    private ActivityLevelsEntity alEntity;

    public ActivityLevel(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        alEntity = new ActivityLevelsEntity(startDate, false, false, false,false, 0, "");
        getALRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (alDao == null)
            alDao = daoSession.getActivityLevelsEntityDao();
    }

    public void insertALRecord(boolean washedFlag, boolean walkedFlag, boolean activitycFlag, boolean activitydFlag, int feelinglevelFlag, String reasonFlag) {
        alEntity.setWashed(washedFlag);
        alEntity.setWalked(walkedFlag);
        alEntity.setActivityc(activitycFlag);
        alEntity.setActivityd(activitydFlag);
        alEntity.setFeelingLevel(feelinglevelFlag);
        alEntity.setReason(reasonFlag);
        alDao.insertOrReplace(alEntity);
    }

    public List<ActivityLevelsEntity> getALRecords() {
        List<ActivityLevelsEntity> alRecords = null;
        try {
            alRecords = alDao.queryBuilder().build().list();
            for (ActivityLevelsEntity al : alRecords) {
                Log.d("Records AL: ", al.getDateTime().toString() + " " + al.getWashed() + " " + " " + al.getWalked() + al.getActivityc() + " " + al.getActivityd() + " " + al.getReason());
            }
        }
        catch  (SQLiteException e) {
        }
        return alRecords;
    }

    public List<ActivityLevelsEntity> getALRecordsForDate(Date date) {
        List<ActivityLevelsEntity> alRecords = alDao.queryBuilder().where(ActivityLevelsEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (ActivityLevelsEntity al: alRecords) {
            Log.d("AL Records: ", al.getDateTime().toString() + " " + al.getWashed() + " " + al.getWalked() + " " + al.getActivityc() + " " + al.getActivityd() + " " + al.getReason() + " " + al.getFeelingLevel());
        }
        return alRecords;
    }
}
