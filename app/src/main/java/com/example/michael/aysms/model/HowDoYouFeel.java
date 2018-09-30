package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.HowDoYouFeelEntity;

/**
 * Created by Michael on 02/08/2018.
 */

public class HowDoYouFeel {
    private HowDoYouFeelEntityDao fDao;
    private HowDoYouFeelEntity fEntity;

    public HowDoYouFeel(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        fEntity = new HowDoYouFeelEntity(startDate, 0, false, "", "");
        getFRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (fDao == null)
            fDao = daoSession.getHowDoYouFeelEntityDao();
    }

    public void insertFRecord(int howIFeelFlag, boolean feelingWorriedFlag, String explainHowIFeel, String explainWorried) {
        fEntity.setHowIFeel(howIFeelFlag);
        fEntity.setFeelingWorried(feelingWorriedFlag);
        fEntity.setExplainHowIFeel(explainHowIFeel);
        fEntity.setExplainWorried(explainWorried);
        fDao.insertOrReplace(fEntity);
    }

    public List<HowDoYouFeelEntity> getFRecords() {
        List<HowDoYouFeelEntity> fRecords = null;
        try {
            fRecords = fDao.queryBuilder().build().list();
            for (HowDoYouFeelEntity f : fRecords) {
                Log.d("Records AL: ", f.getDateTime().toString() + " " + f.getHowIFeel() + " " + " " + f.getFeelingWorried() + f.getExplainHowIFeel() + " " + f.getExplainWorried());
            }
        }
        catch  (SQLiteException e) {
        }
        return fRecords;
    }

    public List<HowDoYouFeelEntity> getFRecordsForDate(Date date) {
        List<HowDoYouFeelEntity> fRecords = fDao.queryBuilder().where(HowDoYouFeelEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (HowDoYouFeelEntity f: fRecords) {
            Log.d("HDYF Records: ", f.getDateTime().toString() + " " + f.getHowIFeel() + " " + f.getExplainHowIFeel() + " " + f.getFeelingWorried() + " " + f.getExplainWorried());
        }
        return fRecords;
    }
}
