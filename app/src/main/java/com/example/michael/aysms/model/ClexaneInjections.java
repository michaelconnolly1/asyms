package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.ClexaneInjectionsEntity;

/**
 * Created by Michael on 30/07/2018.
 */

public class ClexaneInjections {
    private ClexaneInjectionsEntityDao ciDao;
    private ClexaneInjectionsEntity ciEntity;

    public ClexaneInjections(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        ciEntity = new ClexaneInjectionsEntity(startDate, false, false, false, false, false);
        getCIRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (ciDao == null)
            ciDao = daoSession.getClexaneInjectionsEntityDao();
    }

    public void insertCIRecord(boolean sickFlag, boolean unusualBleeding, boolean bruising, boolean fever, boolean swelling) {
        ciEntity.setBeingSick(sickFlag);
        ciEntity.setUnusualBleeding(unusualBleeding);
        ciEntity.setBruising(bruising);
        ciEntity.setFever(fever);
        ciEntity.setSwelling(swelling);
        ciDao.insertOrReplace(ciEntity);
    }

    public List<ClexaneInjectionsEntity> getCIRecords() {
        List<ClexaneInjectionsEntity> ciRecords = null;
        try {
            ciRecords = ciDao.queryBuilder().build().list();
            for (ClexaneInjectionsEntity ci : ciRecords) {
                Log.d("Records: ", ci.getDateTime().toString() + " " + ci.getBeingSick() + " " + ci.getUnusualBleeding() + " " + ci.getBruising() + " " + ci.getFever() + " " + ci.getSwelling() );
            }
        }
        catch  (SQLiteException e) {
        }
        return ciRecords;
    }

    public List<ClexaneInjectionsEntity> getCIRecordsForDate(Date date) {
        List<ClexaneInjectionsEntity> ciRecords = ciDao.queryBuilder().where(ClexaneInjectionsEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (ClexaneInjectionsEntity ci: ciRecords) {
            Log.d("CI Records: ", ci.getDateTime().toString() + " " + ci.getBeingSick() + " " + ci.getUnusualBleeding() + " " + ci.getBruising() + " " + ci.getFever() + " " + ci.getSwelling() );
        }
        return ciRecords;
    }
}
