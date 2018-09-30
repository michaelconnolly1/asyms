package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.ProblemsOrIssuesEntity;

/**
 * Created by Michael on 31/07/2018.
 */

public class ProblemsOrIssues {
    private ProblemsOrIssuesEntityDao piDao;
    private ProblemsOrIssuesEntity piEntity;

    public ProblemsOrIssues(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        piEntity = new ProblemsOrIssuesEntity(null, startDate, 0, "", 0, 0);
//        getPIRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (piDao == null)
            piDao = daoSession.getProblemsOrIssuesEntityDao();
    }

    public void insertPIRecord(int problemNo, String problem, int severityFlag, int botherFlag) {
        ProblemsOrIssuesEntity newEntity = new ProblemsOrIssuesEntity(null, piEntity.getDateTime(), problemNo, problem, severityFlag, botherFlag);
        piDao.insertOrReplace(newEntity);
        Log.d("Records: ", newEntity.getDateTime().toString() + " " + Integer.toString(newEntity.getProblemNo()) + " " + newEntity.getProblem() + " "  + Integer.toString(newEntity.getSeverity()) + " " + Integer.toString(newEntity.getBotherLevel()));
    }

    public List<ProblemsOrIssuesEntity> getPIRecords() {
        List<ProblemsOrIssuesEntity> piRecords = null;
        try {
            piRecords = piDao.queryBuilder().build().list();
            for (ProblemsOrIssuesEntity pi : piRecords) {
                Log.d("Records: ", pi.getDateTime().toString() + " " + Integer.toString(pi.getProblemNo()) + " " + pi.getProblem() + " "  + Integer.toString(pi.getSeverity()) + " " + Integer.toString(pi.getBotherLevel()));
            }
        }
        catch  (SQLiteException e) {
        }
        return piRecords;
    }

    public List<ProblemsOrIssuesEntity> getPIRecordsForDate(Date date) {
        List<ProblemsOrIssuesEntity> piRecords = piDao.queryBuilder().where(ProblemsOrIssuesEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (ProblemsOrIssuesEntity pi: piRecords) {
            Log.d("PI Records: ", pi.getDateTime().toString() + " " + pi.getProblemID()+ " " + pi.getProblemNo() + " " + pi.getProblem() + " " + pi.getSeverity() + " " + pi.getBotherLevel());
        }
        return piRecords;
    }
}
