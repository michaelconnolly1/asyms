package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.Questionnaire;

/**
 * Created by laptop on 03/09/2018.
 */

public class QuestionnaireState {

    private QuestionnaireDao qDao;
    private com.example.michael.aysms.model.Questionnaire qEntity;

    public QuestionnaireState(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        qEntity = new com.example.michael.aysms.model.Questionnaire(startDate, 0);
        getQuestionnaireRecords();
    }

    public QuestionnaireState(DaoSession daoSession){
        setDaoSession(daoSession);
        qEntity = new com.example.michael.aysms.model.Questionnaire((Date) null, 0);
        getQuestionnaireRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (qDao == null)
            qDao = daoSession.getQuestionnaireDao();
    }

    public void insertQuestionnaireRecord(int questionnaireID) {
        qEntity.setQuestionnaireID(questionnaireID);
        qDao.insertOrReplace(qEntity);
    }

    public void updateQuestionnaireRecord(int questionnaireID) {
        qEntity.setQuestionnaireID(questionnaireID);
        qDao.insertOrReplace(qEntity);
    }

    public void updateQuestionnaire(com.example.michael.aysms.model.Questionnaire quest) {
        qDao.insertOrReplace(quest);
    }

    public List<com.example.michael.aysms.model.Questionnaire> getQuestionnaireRecords() {
        List<com.example.michael.aysms.model.Questionnaire> qRecords = null;
        try {
            qRecords = qDao.queryBuilder().build().list();
            for (com.example.michael.aysms.model.Questionnaire q : qRecords) {
                Log.d("Records Q: ", q.getDateTime().toString() + " " + q.getQuestionnaireID() );
            }
        }
        catch  (SQLiteException e) {
        }
        return qRecords;
    }

    public List<com.example.michael.aysms.model.Questionnaire> getQuestionaireRecordsForDate(Date date) {
        List<com.example.michael.aysms.model.Questionnaire> qRecords = qDao.queryBuilder().where(QuestionnaireDao.Properties.DateTime.eq(date.getTime())).list();
        for (com.example.michael.aysms.model.Questionnaire q: qRecords) {
            Log.d("Q Records: ", q.getDateTime().toString() + " " + q.getQuestionnaireID());
        }
        return qRecords;
    }

    public List<com.example.michael.aysms.model.Questionnaire> getQuestionaireRecordsForQuestionnaireID() {
        List<com.example.michael.aysms.model.Questionnaire> qRecords = qDao.queryBuilder().where(QuestionnaireDao.Properties.QuestionnaireID.eq(0)).list();
        for (com.example.michael.aysms.model.Questionnaire q: qRecords) {
            Log.d("QID Records: ", q.getDateTime().toString() + " " + q.getQuestionnaireID());
        }
        return qRecords;
    }
}
