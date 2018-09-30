package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.EatingAndDrinkingEntity;

/**
 * Created by Michael on 29/07/2018.
 */

public class EatingAndDrinking {
    private EatingAndDrinkingEntityDao edDao;
    private EatingAndDrinkingEntity edEntity;

    public EatingAndDrinking(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        edEntity = new EatingAndDrinkingEntity(startDate, false, 0, 0, 0, "", 0);
        getEDRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (edDao == null)
            edDao = daoSession.getEatingAndDrinkingEntityDao();
    }

    public void insertEDRecord(boolean sickFlag, int thirstyFlag, int botherFlag, int unwellFlag, int mouthFlag, String notDrinkingReason) {
        edEntity.setEatingAndDrinking(sickFlag);
        edEntity.setUnwell(unwellFlag);
        edEntity.setBotherLevel(botherFlag);
        edEntity.setFeelThirsty(thirstyFlag);
        edEntity.setMouthDry(mouthFlag);
        edEntity.setNotDrinkingReason(notDrinkingReason);
        edDao.insertOrReplace(edEntity);
    }

    public List<EatingAndDrinkingEntity> getEDRecords() {
        List<EatingAndDrinkingEntity> edRecords = null;
        try {
            edRecords = edDao.queryBuilder().build().list();
            for (EatingAndDrinkingEntity ed : edRecords) {
                Log.d("Records: ", ed.getDateTime().toString() + " " + ed.getEatingAndDrinking() + " " + ed.getUnwell() + " " + ed.getBotherLevel());
            }
        }
        catch  (SQLiteException e) {
        }
        return edRecords;
    }

    public List<EatingAndDrinkingEntity> getEDRecordsForDate(Date date) {
        List<EatingAndDrinkingEntity> edRecords = edDao.queryBuilder().where(EatingAndDrinkingEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (EatingAndDrinkingEntity ed: edRecords) {
            Log.d("EANDD Records: ", ed.getDateTime().toString() + " " + ed.getEatingAndDrinking() + " " + ed.getMouthDry() + " " + ed.getFeelThirsty() + " " + ed.getUnwell() + " " + ed.getNotDrinkingReason() + " " + ed.getBotherLevel());
        }
        return edRecords;
    }
}
