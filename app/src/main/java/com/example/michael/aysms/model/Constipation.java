package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.ConstipationEntity;

/**
 * Created by Michael on 29/07/2018.
 */

public class Constipation {
    private ConstipationEntityDao csDao;
    private ConstipationEntity csEntity;

    public Constipation(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        csEntity = new ConstipationEntity(startDate, false, 0, 0,0);
        getCSRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (csDao == null)
            csDao = daoSession.getConstipationEntityDao();
    }

    public void insertCSRecord(boolean sickFlag, int severityFlag, int botherFlag, int lastMovementFlag) {
        csEntity.setConstipation(sickFlag);
        csEntity.setSeverity(severityFlag);
        csEntity.setBotherLevel(botherFlag);
        csEntity.setLastMovement(lastMovementFlag);
        csDao.insertOrReplace(csEntity);
    }

    public List<ConstipationEntity> getCSRecords() {
        List<ConstipationEntity> csRecords = null;
        try {
            csRecords = csDao.queryBuilder().build().list();
            for (ConstipationEntity cs : csRecords) {
                Log.d("Records: ", cs.getDateTime().toString() + " " + cs.getConstipation() + " " + cs.getSeverity() + " " + cs.getBotherLevel() + " " + cs.getLastMovement());
            }
        }
        catch  (SQLiteException e) {
        }
        return csRecords;
    }

    public List<ConstipationEntity> getCRecordsForDate(Date date) {
        List<ConstipationEntity> cRecords = csDao.queryBuilder().where(ConstipationEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (ConstipationEntity c: cRecords) {
            Log.d("C Records: ", c.getDateTime().toString() + " " + c.getConstipation() + " " + c.getLastMovement() + " " + c.getSeverity() + " " + c.getBotherLevel());
        }
        return cRecords;
    }
}
