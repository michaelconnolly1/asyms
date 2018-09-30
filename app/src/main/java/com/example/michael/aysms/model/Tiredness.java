package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.TirednessEntity;

/**
 * Created by Michael on 30/07/2018.
 */

public class Tiredness {
    private TirednessEntityDao taDao;
    private TirednessEntity taEntity;

    public Tiredness(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        taEntity = new TirednessEntity(startDate, false, 0, 0,0,0);
        getTARecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (taDao == null)
            taDao = daoSession.getTirednessEntityDao();
    }

    public void insertTARecord(boolean sickFlag, int bedFlag, int botherFlag, int severityFlag, int selfCareFlag) {
        taEntity.setTiredness(sickFlag);
        taEntity.setBed(bedFlag);
        taEntity.setBotherLevel(botherFlag);
        taEntity.setSeverity(severityFlag);
        taEntity.setSelfCare(selfCareFlag);
        taDao.insertOrReplace(taEntity);
    }

    public List<TirednessEntity> getTARecords() {
        List<TirednessEntity> taRecords = null;
        try {
            taRecords = taDao.queryBuilder().build().list();
            for (TirednessEntity ta : taRecords) {
                Log.d("Records: ", ta.getDateTime().toString() + " " + ta.getTiredness() + " " + ta.getBed() + " " + ta.getBotherLevel());
            }
        }
        catch  (SQLiteException e) {
        }
        return taRecords;
    }

    public List<TirednessEntity> getTRecordsForDate(Date date) {
        List<TirednessEntity> tRecords = taDao.queryBuilder().where(TirednessEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (TirednessEntity t: tRecords) {
            Log.d("T Records: ", t.getDateTime().toString() + " " + t.getTiredness() + " " + t.getSeverity() + " " + t.getBotherLevel() + " " + t.getSelfCare() + " " + t.getBed());
        }
        return tRecords;
    }
}
