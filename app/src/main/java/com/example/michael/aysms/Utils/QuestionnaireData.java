package com.example.michael.aysms.Utils;

import android.util.Log;

import com.example.michael.aysms.model.ActivityLevel;
import com.example.michael.aysms.model.ActivityLevelsEntity;
import com.example.michael.aysms.model.BeingSick;
import com.example.michael.aysms.model.BeingSickEntity;
import com.example.michael.aysms.model.Body;
import com.example.michael.aysms.model.BodyEntity;
import com.example.michael.aysms.model.ClexaneInjections;
import com.example.michael.aysms.model.ClexaneInjectionsEntity;
import com.example.michael.aysms.model.Constipation;
import com.example.michael.aysms.model.ConstipationEntity;
import com.example.michael.aysms.model.DaoSession;
import com.example.michael.aysms.model.Diarrhoea;
import com.example.michael.aysms.model.DiarrhoeaEntity;
import com.example.michael.aysms.model.EatingAndDrinking;
import com.example.michael.aysms.model.EatingAndDrinkingB;
import com.example.michael.aysms.model.EatingAndDrinkingBEntity;
import com.example.michael.aysms.model.EatingAndDrinkingEntity;
import com.example.michael.aysms.model.FeelingSick;
import com.example.michael.aysms.model.FeelingSickEntity;
import com.example.michael.aysms.model.HowDoYouFeel;
import com.example.michael.aysms.model.HowDoYouFeelEntity;
import com.example.michael.aysms.model.Pain;
import com.example.michael.aysms.model.PainEntity;
import com.example.michael.aysms.model.ProblemsOrIssues;
import com.example.michael.aysms.model.ProblemsOrIssuesEntity;
import com.example.michael.aysms.model.QuestionnaireState;
import com.example.michael.aysms.model.SignsOfInfection;
import com.example.michael.aysms.model.SignsOfInfectionEntity;
import com.example.michael.aysms.model.Tiredness;
import com.example.michael.aysms.model.TirednessEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by laptop on 02/09/2018.
 */

public class QuestionnaireData {
    private DaoSession daoSession;
    private JSONObject postBody;
    private Date startDate;
    private QuestionnaireState qModel;

    public QuestionnaireData(DaoSession daoSession, Date startDate) {
        this.daoSession = daoSession;
        this.startDate = startDate;
        postBody = new JSONObject();
        qModel =  new QuestionnaireState(daoSession, startDate);
    }

    public JSONObject getQuestionnaireData(String token) throws JSONException {
        int myBodyWidth = 0, myBodyHeight = 0, bitmapWidth = 0, bitmapHeight = 0, buttonCount = 0;
        int button1X = 0, button1Y = 0, button1Severity = 0, button1Bother = 0;
        int button2X = 0, button2Y = 0, button2Severity = 0, button2Bother = 0;
        int button3X = 0, button3Y = 0, button3Severity = 0, button3Bother = 0;
        int button4X = 0, button4Y = 0, button4Severity = 0, button4Bother = 0;
        boolean pain = false, button1Sick = false, button2Sick = false, button3Sick = false, button4Sick = false;
        boolean washed = false, walked = false, activityc = false, activityd = false, clexaneInjection = false, unusualBleeding = false, bruising = false, fever = false, swelling = false;
        boolean beingSick = false, constipation = false, diarrhoea = false, eatingAndDrinking = false, eatingAndDrinkingB = false, feelingSick = false, feeling = false;
        boolean soi = false,  racing = false, breathless = false, DORF = false, urine = false, leaking = false, stoma = false, tiredness = false;
        int activityLevel = 0, bsSeverity = 0, bsBother = 0, cSeverity = 0, cBother = 0, cLastMovement = 0, dSeverity = 0, dBother = 0, mouthDry = 0, thirsty = 0, unwell = 0, eadBother = 0, eatNormal = 0, eatSmall = 0, eatAtAll = 0, eadbBother = 0;
        int fsSeverity = 0, fsBother = 0, howIFeel = 0, soiBother = 0, soiSeverity = 0, tSeverity = 0, tBother = 0, bed = 0, selfCare = 0;
        String alReason = "", eadReason = "", hdyfReason = "", anxiousReason = "";

        FeelingSick myFSModel;
        List<FeelingSickEntity> fsRecords;
        ActivityLevel myALModel;
        List<ActivityLevelsEntity> alRecords;
        BeingSick myBSModel;
        List<BeingSickEntity> bsRecords;
        Pain myPainModel;
        List<PainEntity> pRecords;
        ClexaneInjections myCIModel;
        List<ClexaneInjectionsEntity> ciRecords;
        Constipation myCModel;
        List<ConstipationEntity> cRecords;
        Diarrhoea myDModel;
        List<DiarrhoeaEntity> dRecords;
        EatingAndDrinking myEDModel;
        List<EatingAndDrinkingEntity> eadRecords;
        EatingAndDrinkingB myEDBModel;
        List<EatingAndDrinkingBEntity> eadBRecords;
        HowDoYouFeel myHDYFModel;
        List<HowDoYouFeelEntity> hdyfRecords;
        SignsOfInfection mySOIModel;
        List<SignsOfInfectionEntity> soiRecords;
        Tiredness myTModel;
        List<TirednessEntity> tRecords;
        ProblemsOrIssues myPOIModel;
        List<ProblemsOrIssuesEntity> poiRecords;
        Body myBodyModel;
        List<BodyEntity> bodyRecords;

        myALModel = new ActivityLevel(daoSession, startDate);
        alRecords = myALModel.getALRecordsForDate(startDate);
        if (alRecords.size() > 0) {
            washed = alRecords.get(0).getWashed();
            walked = alRecords.get(0).getWalked();
            activityc = alRecords.get(0).getActivityc();
            activityd = alRecords.get(0).getActivityd();
            activityLevel = alRecords.get(0).getFeelingLevel();
            alReason = alRecords.get(0).getReason();
        }

        myBSModel = new BeingSick(daoSession, startDate);
        bsRecords = myBSModel.getBSRecordsForDate(startDate);
        if (bsRecords.size() > 0) {
            beingSick = bsRecords.get(0).getBeingSick();
            bsSeverity = bsRecords.get(0).getSeverity();
            bsBother = bsRecords.get(0).getBotherLevel();
        }

        myPainModel = new Pain(daoSession, startDate);
        pRecords = myPainModel.getPainRecordsForDate(startDate);
        if (pRecords.size() > 0) {
            pain = pRecords.get(0).getPain();
            if (pain) {
                myBodyModel = new Body(daoSession, startDate);
                bodyRecords = myBodyModel.getBodyRecordsForDate(startDate);
                if (bodyRecords.size() > 0) {
                    myBodyWidth = bodyRecords.get(0).getBodyWidth();
                    myBodyHeight = bodyRecords.get(0).getBodyHeight();
                    bitmapWidth = bodyRecords.get(0).getBitmapWidth();
                    bitmapHeight = bodyRecords.get(0).getBitmapHeight();
                    buttonCount = bodyRecords.get(0).getNButtons();
                    if (buttonCount > 0) {
                        button1X = bodyRecords.get(0).getButton1X();
                        button1Y = bodyRecords.get(0).getButton1Y();
                        button1Sick = bodyRecords.get(0).getButton1NewPain();
                        button1Severity = bodyRecords.get(0).getButton1Severity();
                        button1Bother = bodyRecords.get(0).getButton1BotherLevel();
                    }
                    if (buttonCount > 1) {
                        button2X = bodyRecords.get(0).getButton2X();
                        button2Y = bodyRecords.get(0).getButton2Y();
                        button2Sick = bodyRecords.get(0).getButton2NewPain();
                        button2Severity = bodyRecords.get(0).getButton2Severity();
                        button2Bother = bodyRecords.get(0).getButton2BotherLevel();
                    }
                    if (buttonCount > 2) {
                        button3X = bodyRecords.get(0).getButton3X();
                        button3Y = bodyRecords.get(0).getButton3Y();
                        button3Sick = bodyRecords.get(0).getButton3NewPain();
                        button3Severity = bodyRecords.get(0).getButton3Severity();
                        button3Bother = bodyRecords.get(0).getButton3BotherLevel();
                    }
                    if (buttonCount > 3) {
                        button4X = bodyRecords.get(0).getButton4X();
                        button4Y = bodyRecords.get(0).getButton4Y();
                        button4Sick = bodyRecords.get(0).getButton4NewPain();
                        button4Severity = bodyRecords.get(0).getButton4Severity();
                        button4Bother = bodyRecords.get(0).getButton4BotherLevel();
                    }
                }
            }
        }

        myCIModel = new ClexaneInjections(daoSession, startDate);
        ciRecords = myCIModel.getCIRecordsForDate(startDate);
        if (ciRecords.size() > 0) {
            clexaneInjection = ciRecords.get(0).getBeingSick();
            unusualBleeding = ciRecords.get(0).getUnusualBleeding();
            bruising = ciRecords.get(0).getBruising();
            fever = ciRecords.get(0).getFever();
            swelling = ciRecords.get(0).getSwelling();
        }

        myCModel = new Constipation(daoSession, startDate);
        cRecords = myCModel.getCRecordsForDate(startDate);
        if (cRecords.size() > 0) {
            constipation = cRecords.get(0).getConstipation();
            cSeverity = cRecords.get(0).getSeverity();
            cBother = cRecords.get(0).getBotherLevel();
            cLastMovement = cRecords.get(0).getLastMovement();
        }

        myDModel = new Diarrhoea(daoSession, startDate);
        dRecords = myDModel.getDRecordsForDate(startDate);
        if (dRecords.size() > 0) {
            diarrhoea = dRecords.get(0).getDiarrhoea();
            dSeverity = dRecords.get(0).getSeverity();
            dBother = dRecords.get(0).getBotherLevel();
        }

        myEDModel = new EatingAndDrinking(daoSession, startDate);
        eadRecords = myEDModel.getEDRecordsForDate(startDate);
        if (eadRecords.size() > 0) {
            eatingAndDrinking = eadRecords.get(0).getEatingAndDrinking();
            mouthDry = eadRecords.get(0).getMouthDry();
            thirsty = eadRecords.get(0).getFeelThirsty();
            unwell = eadRecords.get(0).getUnwell();
            eadReason = eadRecords.get(0).getNotDrinkingReason();
            eadBother = eadRecords.get(0).getBotherLevel();
        }

        myEDBModel = new EatingAndDrinkingB(daoSession, startDate);
        eadBRecords = myEDBModel.getEDBRecordsForDate(startDate);
        if (eadBRecords.size() > 0) {
            eatingAndDrinkingB = eadBRecords.get(0).getEatingAndDrinking();
            eatNormal = eadBRecords.get(0).getEatNormal();
            eatSmall = eadBRecords.get(0).getEatSmall();
            eatAtAll = eadBRecords.get(0).getEatAtAll();
            eadbBother = eadBRecords.get(0).getBotherLevel();
        }

        myFSModel = new FeelingSick(daoSession, startDate);
        fsRecords = myFSModel.getFSRecordsForDate(startDate);
        if (fsRecords.size() > 0) {
            feelingSick = fsRecords.get(0).getFeelingSick();
            fsSeverity = fsRecords.get(0).getSeverity();
            fsBother = fsRecords.get(0).getBotherLevel();
        }

        myHDYFModel = new HowDoYouFeel(daoSession, startDate);
        hdyfRecords = myHDYFModel.getFRecordsForDate(startDate);
        if (hdyfRecords.size() > 0) {
            howIFeel = hdyfRecords.get(0).getHowIFeel();
            feeling = hdyfRecords.get(0).getFeelingWorried();
            hdyfReason = hdyfRecords.get(0).getExplainHowIFeel();
            anxiousReason = hdyfRecords.get(0).getExplainWorried();
        }

        mySOIModel = new SignsOfInfection(daoSession, startDate);
        soiRecords = mySOIModel.getSOIRecordsForDate(startDate);
        if (soiRecords.size() > 0) {
            soi = soiRecords.get(0).getSignsOfInfection();
            racing = soiRecords.get(0).getRacing();
            breathless = soiRecords.get(0).getBreathless();
            DORF = soiRecords.get(0).getDORF();
            urine = soiRecords.get(0).getUrine();
            leaking = soiRecords.get(0).getLeaking();
            stoma = soiRecords.get(0).getStoma();
            soiBother = soiRecords.get(0).getBother();
            soiSeverity = soiRecords.get(0).getSeverity();
        }

        myTModel = new Tiredness(daoSession, startDate);
        tRecords = myTModel.getTRecordsForDate(startDate);
        if (soiRecords.size() > 0) {
            tiredness = tRecords.get(0).getTiredness();
            tSeverity = tRecords.get(0).getSeverity();
            tBother = tRecords.get(0).getBotherLevel();
            bed = tRecords.get(0).getBed();
            selfCare = tRecords.get(0).getSelfCare();
        }

        myPOIModel = new ProblemsOrIssues(daoSession, startDate);
        poiRecords = myPOIModel.getPIRecordsForDate(startDate);


        try {
            postBody.put("token", token);
            postBody.put("dateTime", startDate.getTime());

            postBody.put("alWashed", boolToInt(washed));
            postBody.put("alWalked", boolToInt(walked));
            postBody.put("alActivityC", boolToInt(activityc));
            postBody.put("alActivityD", boolToInt(activityd));
            postBody.put("alFeelingLevel", activityLevel);
            postBody.put("alReason", alReason);

            postBody.put("bsBeingSick", boolToInt(beingSick));
            postBody.put("bsSeverity", bsSeverity);
            postBody.put("bsBotherLevel", bsBother);

            postBody.put("ciBeingSick", boolToInt(clexaneInjection));
            postBody.put("ciUnusualBleeding", boolToInt(unusualBleeding));
            postBody.put("ciBruising", boolToInt(bruising));
            postBody.put("ciFever", boolToInt(fever));
            postBody.put("ciSwelling", boolToInt(swelling));

            postBody.put("cConstipation", boolToInt(constipation));
            postBody.put("cSeverity", cSeverity);
            postBody.put("cBotherLevel", cBother);
            postBody.put("cLastMovement", cLastMovement);

            postBody.put("dDiarrhoea", boolToInt(diarrhoea));
            postBody.put("dSeverity", dSeverity);
            postBody.put("dBotherLevel", dBother);

            postBody.put("eatingAndDrinking", boolToInt(eatingAndDrinking));
            postBody.put("eadUnwell", unwell);
            postBody.put("eadBotherLevel", eadBother);
            postBody.put("eadFeelThirsty", thirsty);
            postBody.put("eadNotDrinkingReason", eadReason);
            postBody.put("eadMouthDry", mouthDry);

            postBody.put("eadbEatingAndDrinking", boolToInt(eatingAndDrinkingB));
            postBody.put("eadbEatNormal", eatNormal);
            postBody.put("eadbBotherLevel", eadbBother);
            postBody.put("eadbEatSmall", eatSmall);
            postBody.put("eadbEatAtAll", eatAtAll);

            postBody.put("fsFeelingSick", boolToInt(feelingSick));
            postBody.put("fsSeverity", fsSeverity);
            postBody.put("fsBotherLevel", fsBother);

            postBody.put("hdyfHowFeel", howIFeel);
            postBody.put("hdyfFeelingWorried", boolToInt(feeling));
            postBody.put("hdyfExplainHowIFeel", hdyfReason);
            postBody.put("hdyfExplainWorried", anxiousReason);

            postBody.put("soiSignsOfInfection", boolToInt(soi));
            postBody.put("soiStoma", boolToInt(stoma));
            postBody.put("soiRacing", boolToInt(racing));
            postBody.put("soiBreathless", boolToInt(breathless));
            postBody.put("soiUrine", boolToInt(urine));
            postBody.put("soiLeaking", boolToInt(leaking));
            postBody.put("soiDORF", boolToInt(DORF));
            postBody.put("soiSeverity", soiSeverity);
            postBody.put("soiBother", soiBother);

            postBody.put("tTiredness", boolToInt(tiredness));
            postBody.put("tBed", bed);
            postBody.put("tBotherLevel", tBother);
            postBody.put("tSeverity", tSeverity);
            postBody.put("tSelfCare", selfCare);

            postBody.put("pain", boolToInt(pain));
            postBody.put("bodyWidth", myBodyWidth);
            postBody.put("bodyHeight", myBodyHeight);
            postBody.put("bitmapWidth", bitmapWidth);
            postBody.put("bitmapHeight", bitmapHeight);
            postBody.put("buttonCount", buttonCount);
            postBody.put("button1X", button1X);
            postBody.put("button1Y", button1Y);
            postBody.put("button1Sick", boolToInt(button1Sick));
            postBody.put("button1Severity", button1Severity);
            postBody.put("button1Bother", button1Bother);
            postBody.put("button2X", button2X);
            postBody.put("button2Y", button2Y);
            postBody.put("button2Sick", boolToInt(button2Sick));
            postBody.put("button2Severity", button2Severity);
            postBody.put("button2Bother", button2Bother);
            postBody.put("button3X", button3X);
            postBody.put("button3Y", button3Y);
            postBody.put("button3Sick", boolToInt(button3Sick));
            postBody.put("button3Severity", button3Severity);
            postBody.put("button3Bother", button3Bother);
            postBody.put("button4X", button4X);
            postBody.put("button4Y", button4Y);
            postBody.put("button4Sick", boolToInt(button4Sick));
            postBody.put("button4Severity", button4Severity);
            postBody.put("button4Bother", button4Bother);

            postBody.put("nProblems", poiRecords.size());

            if (poiRecords.size() > 0) {
                JSONArray problems = new JSONArray();
                for (int index = 0; index < poiRecords.size(); index++) {
                    String problem = poiRecords.get(index).getProblem();
                    int poiSeverity = poiRecords.get(index).getSeverity();
                    int poiBother = poiRecords.get(index).getBotherLevel();
                    JSONObject problemObject = new JSONObject();
                    problemObject.put("poiProblemNo", index);
                    problemObject.put("poiProblem", problem);
                    problemObject.put("poiSeverity", poiSeverity);
                    problemObject.put("poiBother", poiBother);
                    problems.put(problemObject);
                }
                postBody.put("problems", problems);
            }
        } catch (JSONException e) {
            Log.d("SENDQ JSONException: ", e.getMessage());
        }

        return postBody;
    }


    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    public void saveQuestionnaire() {
        qModel.insertQuestionnaireRecord(0);
    }

    public void updateQuestionnaire(int questionnaireID) {
        qModel.updateQuestionnaireRecord(questionnaireID);
    }
}
