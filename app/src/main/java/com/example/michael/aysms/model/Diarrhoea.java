package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.DiarrhoeaEntity;

/**
 * Created by Michael on 28/07/2018.
 */

public class Diarrhoea {
    private DiarrhoeaEntityDao dsDao;
    private DiarrhoeaEntity dsEntity;

    public Diarrhoea(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        dsEntity = new DiarrhoeaEntity(startDate, false, 0, 0);
        getDSRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (dsDao == null)
            dsDao = daoSession.getDiarrhoeaEntityDao();
    }

    public void insertDSRecord(boolean sickFlag, int severityFlag, int botherFlag) {
        dsEntity.setDiarrhoea(sickFlag);
        dsEntity.setSeverity(severityFlag);
        dsEntity.setBotherLevel(botherFlag);
        dsDao.insertOrReplace(dsEntity);
    }

    public List<DiarrhoeaEntity> getDSRecords() {
        List<DiarrhoeaEntity> dsRecords = null;
        try {
            dsRecords = dsDao.queryBuilder().build().list();
            for (DiarrhoeaEntity ds : dsRecords) {
                Log.d("Records: ", ds.getDateTime().toString() + " " + ds.getDiarrhoea() + " " + ds.getSeverity() + " " + ds.getBotherLevel());
            }
        }
        catch  (SQLiteException e) {
        }
        return dsRecords;
    }

    public List<DiarrhoeaEntity> getDRecordsForDate(Date date) {
        List<DiarrhoeaEntity> dRecords = dsDao.queryBuilder().where(DiarrhoeaEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (DiarrhoeaEntity d: dRecords) {
            Log.d("D Records: ", d.getDateTime().toString() + " " + d.getDiarrhoea() + " " + d.getSeverity() + " " + d.getBotherLevel());
        }
        return dRecords;
    }
}
