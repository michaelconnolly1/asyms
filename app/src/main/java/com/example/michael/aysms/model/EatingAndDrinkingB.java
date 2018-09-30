package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.EatingAndDrinkingBEntity;

/**
 * Created by Michael on 30/07/2018.
 */

public class EatingAndDrinkingB {
    private EatingAndDrinkingBEntityDao edbDao;
    private EatingAndDrinkingBEntity edbEntity;

    public EatingAndDrinkingB(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        edbEntity = new EatingAndDrinkingBEntity(startDate, false, 0, 0,0,0);
        getEDBRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (edbDao == null)
            edbDao = daoSession.getEatingAndDrinkingBEntityDao();
    }

    public void insertEDBRecord(boolean sickFlag, int eatNormalFlag, int botherFlag, int eatSmallFlag, int eatAtAllFlag) {
        edbEntity.setEatingAndDrinking(sickFlag);
        edbEntity.setEatNormal(eatNormalFlag);
        edbEntity.setBotherLevel(botherFlag);
        edbEntity.setEatSmall(eatSmallFlag);
        edbEntity.setEatAtAll(eatAtAllFlag);
        edbDao.insertOrReplace(edbEntity);
    }

    public List<EatingAndDrinkingBEntity> getEDBRecords() {
        List<EatingAndDrinkingBEntity> edbRecords = null;
        try {
            edbRecords = edbDao.queryBuilder().build().list();
            for (EatingAndDrinkingBEntity edb : edbRecords) {
                Log.d("Records: ", edb.getDateTime().toString() + " " + edb.getEatingAndDrinking() + " " + edb.getEatNormal() + " " + edb.getBotherLevel());
            }
        }
        catch  (SQLiteException e) {
        }
        return edbRecords;
    }

    public List<EatingAndDrinkingBEntity> getEDBRecordsForDate(Date date) {
        List<EatingAndDrinkingBEntity> edbRecords = edbDao.queryBuilder().where(EatingAndDrinkingBEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (EatingAndDrinkingBEntity edb: edbRecords) {
            Log.d("FS Records: ", edb.getDateTime().toString() + " " + edb.getEatingAndDrinking() + " " + edb.getEatNormal() + " " + edb.getEatSmall() + " " + edb.getEatAtAll() + " " + edb.getBotherLevel());
    }
        return edbRecords;
    }
}
