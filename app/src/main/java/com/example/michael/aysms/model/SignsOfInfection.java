package com.example.michael.aysms.model;

import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.SignsOfInfectionEntity;

/**
 * Created by Michael on 31/07/2018.
 */

public class SignsOfInfection {
    private SignsOfInfectionEntityDao soiDao;
    private SignsOfInfectionEntity soiEntity;

    public SignsOfInfection(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        soiEntity = new SignsOfInfectionEntity(startDate, false, false, false, false, false, false, false,0,0);
        getSOIRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (soiDao == null)
            soiDao = daoSession.getSignsOfInfectionEntityDao();
    }

    public void insertSOIRecord(boolean sickFlag, int severityFlag, int botherFlag, boolean racingFlag, boolean leakingFlag, boolean breathlessFlag, boolean DORFFlag, boolean urineFlag, boolean stomaFlag) {
        soiEntity.setSignsOfInfection(sickFlag);
        soiEntity.setSeverity(severityFlag);
        soiEntity.setRacing(racingFlag);
        soiEntity.setBother(botherFlag);
        soiEntity.setStoma(stomaFlag);
        soiEntity.setUrine(urineFlag);
        soiEntity.setBreathless(breathlessFlag);
        soiEntity.setLeaking(leakingFlag);
        soiEntity.setDORF(DORFFlag);
        soiDao.insertOrReplace(soiEntity);
    }

    public List<SignsOfInfectionEntity> getSOIRecords() {
        List<SignsOfInfectionEntity> soiRecords = soiDao.queryBuilder().build().list();
        for (SignsOfInfectionEntity soi: soiRecords) {
            Log.d("Records: ", soi.getDateTime().toString() + " " + soi.getSignsOfInfection() + " " + soi.getSeverity() + " " + soi.getBother());
        }
        return soiRecords;
    }

    public List<SignsOfInfectionEntity> getSOIRecordsForDate(Date date) {
        List<SignsOfInfectionEntity> soiRecords = soiDao.queryBuilder().where(SignsOfInfectionEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (SignsOfInfectionEntity soi: soiRecords) {
            Log.d("SOI Records: ", soi.getDateTime().toString() + " " + soi.getSignsOfInfection() + " " + soi.getSeverity() + " " + soi.getBother() + " " + soi.getRacing() + " " + soi.getBreathless() + " " + soi.getDORF() + " " + soi.getUrine() + " " + soi.getLeaking() + " " + soi.getStoma());
        }
        return soiRecords;
    }
}
