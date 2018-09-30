package com.example.michael.aysms.model;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;
import java.util.List;
import com.example.michael.aysms.model.BodyEntity;

/**
 * Created by Michael on 01/08/2018.
 */

public class Body {
    private BodyEntityDao bodyDao;
    private BodyEntity bodyEntity;

    public Body(DaoSession daoSession, Date startDate){
        setDaoSession(daoSession);
        bodyEntity = new BodyEntity(startDate, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, false, 0, 0, 0, 0, false, 0, 0, 0, 0, false, 0, 0);
//        getBodyRecords();
    }

    private void setDaoSession(DaoSession daoSession) {
        if (bodyDao == null)
            bodyDao = daoSession.getBodyEntityDao();
    }

    public void insertBodyRecord(int bodyWidth, int bodyHeight, int bitmapWidth, int bitmapHeight, int nButtons,
                                 int button1X, int button1Y, boolean button1NewPain, int button1Severity, int button1BotherLevel,
                                 int button2X, int button2Y, boolean button2NewPain, int button2Severity, int button2BotherLevel,
                                 int button3X, int button3Y, boolean button3NewPain, int button3Severity, int button3BotherLevel,
                                 int button4X, int button4Y, boolean button4NewPain, int button4Severity, int button4BotherLevel) {
        bodyEntity.setBodyWidth(bodyWidth);
        bodyEntity.setBodyHeight(bodyHeight);
        bodyEntity.setBitmapWidth(bitmapWidth);
        bodyEntity.setBitmapHeight(bitmapHeight);
        bodyEntity.setNButtons(nButtons);

        bodyEntity.setButton1X(button1X);
        bodyEntity.setButton1Y(button1Y);
        bodyEntity.setButton1NewPain(button1NewPain);
        bodyEntity.setButton1Severity(button1Severity);
        bodyEntity.setButton1BotherLevel(button1BotherLevel);

        bodyEntity.setButton2X(button2X);
        bodyEntity.setButton2Y(button2Y);
        bodyEntity.setButton2NewPain(button2NewPain);
        bodyEntity.setButton2Severity(button2Severity);
        bodyEntity.setButton2BotherLevel(button2BotherLevel);

        bodyEntity.setButton3X(button3X);
        bodyEntity.setButton3Y(button3Y);
        bodyEntity.setButton3NewPain(button3NewPain);
        bodyEntity.setButton3Severity(button3Severity);
        bodyEntity.setButton3BotherLevel(button3BotherLevel);

        bodyEntity.setButton4X(button4X);
        bodyEntity.setButton4Y(button4Y);
        bodyEntity.setButton4NewPain(button4NewPain);
        bodyEntity.setButton4Severity(button4Severity);
        bodyEntity.setButton4BotherLevel(button4BotherLevel);

        Log.d("Body insert", Integer.toString(bodyWidth) + " " + Integer.toString(bodyHeight) + " " + Integer.toString(bitmapWidth)+ " " + Integer.toString(bitmapHeight) + " " + Integer.toString(nButtons));
        Log.d("Body insert", Integer.toString(button1X) + " " + Integer.toString(button1Y) + " " + button1NewPain + " " + Integer.toString(button1Severity) + " " + Integer.toString(button1BotherLevel));
        Log.d("Body insert", Integer.toString(button2X) + " " + Integer.toString(button2Y) + " " + button2NewPain + " " + Integer.toString(button2Severity) + " " + Integer.toString(button2BotherLevel));
        Log.d("Body insert", Integer.toString(button3X) + " " + Integer.toString(button3Y) + " " + button3NewPain + " " + Integer.toString(button3Severity) + " " + Integer.toString(button3BotherLevel));
        Log.d("Body insert", Integer.toString(button4X) + " " + Integer.toString(button4Y) + " " + button4NewPain + " " + Integer.toString(button4Severity) + " " + Integer.toString(button4BotherLevel));
        bodyDao.insertOrReplace(bodyEntity);
    }

    public List<BodyEntity> getBodyRecords() {
        List<BodyEntity> bodyRecords = null;
        try {
            bodyRecords = bodyDao.queryBuilder().build().list();
            for (BodyEntity body : bodyRecords) {
                Log.d("Records Body: ", body.getDateTime().toString() + " " + body.getNButtons() + " " + body.getBodyWidth() + " " + body.getBodyHeight());
            }
        }
        catch  (SQLiteException e) {
        }
        return bodyRecords;
    }

    public List<BodyEntity> getBodyRecordsForDate(Date date) {
        List<BodyEntity> bodyRecords = bodyDao.queryBuilder().where(BodyEntityDao.Properties.DateTime.eq(date.getTime())).list();
        for (BodyEntity b: bodyRecords) {
            Log.d("BODY Records: ", b.getDateTime().toString() + " " + b.getNButtons() + " " + b.getButton1Severity() + " " + b.getButton1BotherLevel());
        }
        return bodyRecords;
    }
}
