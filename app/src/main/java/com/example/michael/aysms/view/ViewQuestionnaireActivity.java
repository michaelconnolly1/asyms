package com.example.michael.aysms.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.ActivityLevel;
import com.example.michael.aysms.model.ActivityLevelsEntity;
import com.example.michael.aysms.model.BeingSick;
import com.example.michael.aysms.model.BeingSickEntity;
import com.example.michael.aysms.model.ClexaneInjections;
import com.example.michael.aysms.model.ClexaneInjectionsEntity;
import com.example.michael.aysms.model.Constipation;
import com.example.michael.aysms.model.ConstipationEntity;
import com.example.michael.aysms.model.Diarrhoea;
import com.example.michael.aysms.model.DiarrhoeaEntity;
import com.example.michael.aysms.model.EatingAndDrinking;
import com.example.michael.aysms.model.EatingAndDrinkingB;
import com.example.michael.aysms.model.EatingAndDrinkingBEntity;
import com.example.michael.aysms.model.EatingAndDrinkingEntity;
import com.example.michael.aysms.model.FeelingSick;
import com.example.michael.aysms.model.FeelingSickEntity;
import com.example.michael.aysms.model.SignsOfInfection;
import com.example.michael.aysms.model.SignsOfInfectionEntity;
import com.example.michael.aysms.model.Tiredness;
import com.example.michael.aysms.model.TirednessEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by Michael Connolly on 15/08/2018.
 *
 *  Activity to handle ViewQuestionnaire page
 */

public class ViewQuestionnaireActivity extends AppCompatActivity {
    private Button previousButton;
    private App myApp;
    private Date viewDate;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questionnaire);
//        previousButton = findViewById(R.id.previousButton);
//        previousButton.setVisibility(View.GONE);

        Intent intent = getIntent();
        Long extraDate= intent.getLongExtra("DATE", -1);
        viewDate = new Date();
        viewDate.setTime(extraDate);
        userID = intent.getIntExtra("userID", 0);
        Log.d("View Date", viewDate.toString() + " " + userID);

        myApp = (App)getApplicationContext();

        displayFeelingSick(myApp, viewDate);
        displayBeingSick(myApp, viewDate);
        displayDiarrhoea(myApp, viewDate);
        displayConstipation(myApp, viewDate);
        displayEatingAndDrinking(myApp, viewDate);
        displayEatingAndDrinkingB(myApp, viewDate);
        displaySignsOfInfection(myApp, viewDate);
        displayClexaneInjection(myApp, viewDate);
        displayActivityLevels(myApp, viewDate);
        displayTiredness(myApp, viewDate);
    }

    private void displayFeelingSick(App myApp, Date viewDate) {
        FeelingSick myFSModel;
        List<FeelingSickEntity> records;

        myFSModel = new FeelingSick(myApp.getDaoSession(), new Date());
        records = myFSModel.getFSRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup fsRG = (RadioGroup)findViewById(R.id.fsSickRG);
        RadioGroup fsSeverityRG = (RadioGroup)findViewById(R.id.fsSeverityRG);
        RadioGroup fsBotherRG = (RadioGroup)findViewById(R.id.fsBotherRG);
        LinearLayout fsExtra = (LinearLayout) findViewById(R.id.fsExtraquestions);
        if (records.size() > 0) {
            boolean feelingSick = records.get(0).getFeelingSick();
            int severity = records.get(0).getSeverity();
            int bother = records.get(0).getBotherLevel();
            Log.d("FEELING SICK", records.get(0).getDateTime().toString() + " " + records.get(0).getDateTime().getTime() + " " + Boolean.toString(feelingSick)+ " " + Integer.toString(severity) + " " + Integer.toString(bother));
            displayRGBoolean(fsRG, feelingSick, fsExtra, R.id.fsSickYes, R.id.fsSickNo);
            displayRG3Value(fsSeverityRG, severity, R.id.fsSeverityMild, R.id.fsSeverityModerate, R.id.fsSeveritySevere);
            displayRG4Value(fsBotherRG, bother, R.id.fsBotherNotAtAll, R.id.fsBotherALittle, R.id.fsBotherQuiteABit, R.id.fsBotherVeryMuch);
        }
    }

    private void displayBeingSick(App myApp, Date viewDate) {
        BeingSick myBSModel;
        List<BeingSickEntity> records;

        myBSModel = new BeingSick(myApp.getDaoSession(), new Date());
        records = myBSModel.getBSRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup bsRG = (RadioGroup)findViewById(R.id.bsSickRG);
        RadioGroup bsSeverityRG = (RadioGroup)findViewById(R.id.bsSeverityRG);
        RadioGroup bsBotherRG = (RadioGroup)findViewById(R.id.bsBotherRG);
        LinearLayout bsExtra = (LinearLayout) findViewById(R.id.bsExtraquestions);
        if (records.size() > 0) {
            boolean beingSick = records.get(0).getBeingSick();
            int severity = records.get(0).getSeverity();
            int bother = records.get(0).getBotherLevel();
            Log.d("BEING SICK", records.get(0).getDateTime().toString() + " " + Boolean.toString(beingSick)+ " " + Integer.toString(severity) + " " + Integer.toString(bother));
            displayRGBoolean(bsRG, beingSick, bsExtra, R.id.bsSickYes, R.id.bsSickNo);
            displayRG3Value(bsSeverityRG, severity, R.id.bsSeverityMild, R.id.bsSeverityModerate, R.id.bsSeveritySevere);
            displayRG4Value(bsBotherRG, bother, R.id.bsBotherNotAtAll, R.id.bsBotherALittle, R.id.bsBotherQuiteABit, R.id.bsBotherVeryMuch);
        }
    }

    private void displayDiarrhoea(App myApp, Date viewDate) {
        Diarrhoea myDModel;
        List<DiarrhoeaEntity> records;

        myDModel = new Diarrhoea(myApp.getDaoSession(), new Date());
        records = myDModel.getDRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup dRG = (RadioGroup)findViewById(R.id.diarrhoeaRG);
        RadioGroup dSeverityRG = (RadioGroup)findViewById(R.id.dSeverityRG);
        RadioGroup dBotherRG = (RadioGroup)findViewById(R.id.dBotherRG);
        LinearLayout dExtra = (LinearLayout) findViewById(R.id.dExtraquestions);
        if (records.size() > 0) {
            boolean beingSick = records.get(0).getDiarrhoea();
            int severity = records.get(0).getSeverity();
            int bother = records.get(0).getBotherLevel();
            Log.d("DIARRHOEA", records.get(0).getDateTime().toString() + " " + Boolean.toString(beingSick)+ " " + Integer.toString(severity) + " " + Integer.toString(bother));
            displayRGBoolean(dRG, beingSick, dExtra, R.id.diarrhoeaYes, R.id.diarrhoeaNo);
            displayRG3Value(dSeverityRG, severity, R.id.dSeverityMild, R.id.dSeverityModerate, R.id.dSeveritySevere);
            displayRG4Value(dBotherRG, bother, R.id.dBotherNotAtAll, R.id.dBotherALittle, R.id.dBotherQuiteABit, R.id.dBotherVeryMuch);
        }
    }

    private void displayConstipation(App myApp, Date viewDate) {
        Constipation myCModel;
        List<ConstipationEntity> records;

        myCModel = new Constipation(myApp.getDaoSession(), new Date());
        records = myCModel.getCRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup cRG = (RadioGroup)findViewById(R.id.constipationRG);
        RadioGroup cSeverityRG = (RadioGroup)findViewById(R.id.cSeverityRG);
        RadioGroup cBotherRG = (RadioGroup)findViewById(R.id.cBotherRG);
        RadioGroup cLastMovementRG = (RadioGroup)findViewById(R.id.lastMovementRG);
        LinearLayout cExtra = (LinearLayout) findViewById(R.id.cExtraquestions);
        if (records.size() > 0) {
            boolean constipation = records.get(0).getConstipation();
            int severity = records.get(0).getSeverity();
            int bother = records.get(0).getBotherLevel();
            int lastMovement = records.get(0).getLastMovement();
            Log.d("CONSTIPATION", records.get(0).getDateTime().toString() + " " + Boolean.toString(constipation)+ " " + Integer.toString(severity) + " " + Integer.toString(bother));
            displayRGBoolean(cRG, constipation, cExtra, R.id.constipationYes, R.id.constipationNo);
            displayRG3Value(cSeverityRG, severity, R.id.cSeverityMild, R.id.cSeverityModerate, R.id.cSeveritySevere);
            displayRG2Value(cLastMovementRG, lastMovement, R.id.cLessThan48, R.id.cMoreThan48);
            displayRG4Value(cBotherRG, bother, R.id.cBotherNotAtAll, R.id.cBotherALittle, R.id.cBotherQuiteABit, R.id.cBotherVeryMuch);
        }
    }

    private void displayEatingAndDrinking(App myApp, Date viewDate) {
        EatingAndDrinking myEDModel;
        List<EatingAndDrinkingEntity> records;

        myEDModel = new EatingAndDrinking(myApp.getDaoSession(), new Date());
        records = myEDModel.getEDRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup eanddRG = (RadioGroup)findViewById(R.id.eanddRG);
        RadioGroup eanddMouthDryRG = (RadioGroup)findViewById(R.id.eanddMouthRG);
        RadioGroup eanddThirstyRG = (RadioGroup)findViewById(R.id.eanddThirstyRG);
        RadioGroup eanddUnwellRG = (RadioGroup)findViewById(R.id.eanddUnwellRG);
        RadioGroup eanddBotherRG = (RadioGroup)findViewById(R.id.eanddBotherRG);
        EditText reasonET = (EditText)findViewById(R.id.eanddNotDrinkingReason);
        LinearLayout eanddExtra = (LinearLayout) findViewById(R.id.eanddExtraquestions);
        if (records.size() > 0) {
            boolean eatingAndDrinking = records.get(0).getEatingAndDrinking();
            int mouthDry = records.get(0).getMouthDry();
            int thirsty = records.get(0).getFeelThirsty();
            int unwell= records.get(0).getUnwell();
            String reason = records.get(0).getNotDrinkingReason();
            int bother = records.get(0).getBotherLevel();
            Log.d("EANDD", records.get(0).getDateTime().toString() + " " + eatingAndDrinking + " " + Integer.toString(mouthDry) + " " + Integer.toString(thirsty) + " " + Integer.toString(unwell) + " " + reason + " " + Integer.toString(bother));
            displayRGBoolean(eanddRG, !eatingAndDrinking, eanddExtra, R.id.eanddNo, R.id.eanddYes);
            displayRG2Value(eanddMouthDryRG, mouthDry, R.id.mouthDryYes, R.id.mouthDryNo);
            displayRG2Value(eanddThirstyRG, mouthDry, R.id.thirstyYes, R.id.thirstyNo);
            displayRG2Value(eanddUnwellRG, unwell, R.id.unwellYes, R.id.unwellNo);
            reasonET.setText(reason);
            displayRG4Value(eanddBotherRG, bother, R.id.eanddBotherNotAtAll, R.id.eanddBotherALittle, R.id.eanddBotherQuiteABit, R.id.eanddBotherVeryMuch);
        }
    }

    private void displayEatingAndDrinkingB(App myApp, Date viewDate) {
        EatingAndDrinkingB myEDBModel;
        List<EatingAndDrinkingBEntity> records;

        myEDBModel = new EatingAndDrinkingB(myApp.getDaoSession(), new Date());
        records = myEDBModel.getEDBRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup eanddbRG = (RadioGroup)findViewById(R.id.eanddbRG);
        RadioGroup eanddbEatNormalRG = (RadioGroup)findViewById(R.id.eanddbEatNormalRG);
        RadioGroup eanddbEatSmallerRG = (RadioGroup)findViewById(R.id.eanddbEatSmallRG);
        RadioGroup eanddbEatAtAllRG = (RadioGroup)findViewById(R.id.eanddbEatAtAllRG);
        RadioGroup eanddBotherRG = (RadioGroup)findViewById(R.id.eanddbBotherRG);
        LinearLayout eanddbExtra = (LinearLayout) findViewById(R.id.eanddbExtraquestions);
        if (records.size() > 0) {
            boolean eatingAndDrinkingB = records.get(0).getEatingAndDrinking();
            int eatNormal = records.get(0).getEatNormal();
            int eatSmall = records.get(0).getEatSmall();
            int eatAtAll= records.get(0).getEatAtAll();
            int bother = records.get(0).getBotherLevel();
            Log.d("EANDDB", records.get(0).getDateTime().toString() + " " + Boolean.toString(eatingAndDrinkingB)+ " " + Integer.toString(eatNormal) + " " + Integer.toString(eatSmall) + " " + Integer.toString(eatAtAll) + " " + Integer.toString(bother));
            displayRGBoolean(eanddbRG, eatingAndDrinkingB, eanddbExtra, R.id.eanddbYes, R.id.eanddbNo);
            displayRG2Value(eanddbEatNormalRG, eatNormal, R.id.eanddbEatNormalYes, R.id.eanddbEatNormalNo);
            displayRG2Value(eanddbEatSmallerRG, eatSmall, R.id.eanddbEatSmallYes, R.id.eanddbEatSmallNo);
            displayRG2Value(eanddbEatAtAllRG, eatAtAll, R.id.eanddbEatAtAllYes, R.id.eanddbEatAtAllNo);
            displayRG4Value(eanddBotherRG, bother, R.id.eanddbBotherNotAtAll, R.id.eanddbBotherALittle, R.id.eanddbBotherQuiteABit, R.id.eanddbBotherVeryMuch);
        }
    }

    private void displaySignsOfInfection(App myApp, Date viewDate) {
        SignsOfInfection mySOIModel;
        List<SignsOfInfectionEntity> records;

        mySOIModel = new SignsOfInfection(myApp.getDaoSession(), new Date());
        records = mySOIModel.getSOIRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup soiRG = (RadioGroup)findViewById(R.id.soiRG);
        RadioGroup soiSeverityRG = (RadioGroup)findViewById(R.id.soiSeverityRG);
        RadioGroup soiBotherRG = (RadioGroup)findViewById(R.id.soiBotherRG);
        RadioGroup soiRacingRG = (RadioGroup)findViewById(R.id.soiRacingRG);
        RadioGroup soiBreathlessRG = (RadioGroup)findViewById(R.id.soiBreathlessRG);
        RadioGroup soiDORFRG = (RadioGroup)findViewById(R.id.soiDORFRG);
        RadioGroup soiUrineRG = (RadioGroup)findViewById(R.id.soiUrineRG);
        RadioGroup soiLeakingRG = (RadioGroup)findViewById(R.id.soiLeakingRG);
        RadioGroup soiStomaRG = (RadioGroup)findViewById(R.id.soiStomaRG);
        LinearLayout soiExtra = (LinearLayout) findViewById(R.id.soiExtraquestions);
        if (records.size() > 0) {
            boolean soi = records.get(0).getSignsOfInfection();
            boolean racing = records.get(0).getRacing();
            boolean breathless = records.get(0).getBreathless();
            boolean DORF = records.get(0).getDORF();
            boolean urine = records.get(0).getUrine();
            boolean leaking = records.get(0).getLeaking();
            boolean stoma = records.get(0).getStoma();
            int bother = records.get(0).getBother();
            int severity = records.get(0).getSeverity();
            Log.d("SOI", records.get(0).getDateTime().toString() + " " + Boolean.toString(soi)+ " " + Boolean.toString(racing) + " " + Boolean.toString(breathless) + " " + Boolean.toString(DORF) + " " + Boolean.toString(urine) + " " + Boolean.toString(leaking) + " " + Boolean.toString(stoma) +  " " + Integer.toString(severity) +  " " + Integer.toString(bother));
            displayRGBoolean(soiRG, soi, soiExtra, R.id.soiYes, R.id.soiNo);
            displayRG2Boolean(soiRacingRG, racing, R.id.soiRacingYes, R.id.soiRacingNo);
            displayRG2Boolean(soiBreathlessRG, breathless, R.id.soiBreathlessYes, R.id.soiBreathlessNo);
            displayRG2Boolean(soiDORFRG, DORF, R.id.soiDORFYes, R.id.soiDORFNo);
            displayRG2Boolean(soiUrineRG, urine, R.id.soiUrineYes, R.id.soiUrineNo);
            displayRG2Boolean(soiLeakingRG, leaking, R.id.soiLeakingYes, R.id.soiLeakingNo);
            displayRG2Boolean(soiStomaRG, stoma, R.id.soiStomaYes, R.id.soiStomaNo);
            displayRG3Value(soiSeverityRG, severity, R.id.soiSeverityMild, R.id.soiSeverityModerate, R.id.soiSeveritySevere);
            displayRG4Value(soiBotherRG, bother, R.id.soiBotherNotAtAll, R.id.soiBotherALittle, R.id.soiBotherQuiteABit, R.id.soiBotherVeryMuch);
        }
    }

    private void displayClexaneInjection(App myApp, Date viewDate) {
        ClexaneInjections myCIModel;
        List<ClexaneInjectionsEntity> records;

        myCIModel = new ClexaneInjections(myApp.getDaoSession(), new Date());
        records = myCIModel.getCIRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup ciRG = (RadioGroup)findViewById(R.id.ciRG);
        CheckBox unusualBleedingCheckbox = (CheckBox)findViewById(R.id.unusualBleedingCheckbox);
        CheckBox bruisingCheckbox = (CheckBox)findViewById(R.id.bruisingCheckbox);
        CheckBox feverCheckbox = (CheckBox)findViewById(R.id.feverCheckbox);
        CheckBox swellingCheckbox = (CheckBox)findViewById(R.id.swellingCheckbox);
        LinearLayout ciExtra = (LinearLayout) findViewById(R.id.ciExtraquestions);
        if (records.size() > 0) {
            boolean clexaneInjection = records.get(0).getBeingSick();
            boolean unusualBleeding = records.get(0).getUnusualBleeding();
            boolean bruising = records.get(0).getBruising();
            boolean fever = records.get(0).getFever();
            boolean swelling = records.get(0).getSwelling();
            Log.d("CI", records.get(0).getDateTime().toString() + " " + Boolean.toString(clexaneInjection) + " " + Boolean.toString(unusualBleeding) + " " + Boolean.toString(bruising) + " " + Boolean.toString(fever) + " " + Boolean.toString(swelling));
            displayRGBoolean(ciRG, clexaneInjection, ciExtra, R.id.ciYes, R.id.ciNo);
            unusualBleedingCheckbox.setChecked(unusualBleeding);
            bruisingCheckbox.setChecked(bruising);
            feverCheckbox.setChecked(fever);
            swellingCheckbox.setChecked(swelling);
        }
    }

    private void displayActivityLevels(App myApp, Date viewDate) {
        ActivityLevel myALModel;
        List<ActivityLevelsEntity> records;

        myALModel = new ActivityLevel(myApp.getDaoSession(), new Date());
        records = myALModel.getALRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        LinearLayout additionalInfo = (LinearLayout) findViewById(R.id.alAdditionalinfo);
        RadioGroup activityLevelsRG = (RadioGroup)findViewById(R.id.alRG);
        CheckBox washedCheckbox = (CheckBox)findViewById(R.id.alWashedCheckbox);
        CheckBox walkedCheckbox = (CheckBox)findViewById(R.id.alWalkedCheckbox);
        CheckBox activitycCheckbox = (CheckBox)findViewById(R.id.alActivitycCheckbox);
        CheckBox activitydCheckbox = (CheckBox)findViewById(R.id.alActivitydCheckbox);
        EditText answerEditText = (EditText)findViewById(R.id.alAnswerEditText);
        if (records.size() > 0) {
            boolean washed = records.get(0).getWashed();
            boolean walked = records.get(0).getWalked();
            boolean activityc = records.get(0).getActivityc();
            boolean activityd = records.get(0).getActivityd();
            int activityLevel = records.get(0).getFeelingLevel();
            String reason = records.get(0).getReason();
            Log.d("AL", records.get(0).getDateTime().toString() + " " + Boolean.toString(washed) + " " + Boolean.toString(walked) + " " + Boolean.toString(activityc) + " " + Boolean.toString(activityc) + " " + Boolean.toString(activityd) + " " + Integer.toString(activityLevel) + " " + reason);
            washedCheckbox.setChecked(washed);
            walkedCheckbox.setChecked(walked);
            activitycCheckbox.setChecked(activityc);
            activitydCheckbox.setChecked(activityd);
            displayRG3Value(activityLevelsRG, activityLevel, R.id.alHappyActive, R.id.alMoreActive, R.id.alStruggledActive);
            if (activityLevel != 0) {
                additionalInfo.setVisibility(View.VISIBLE);
                answerEditText.setText(reason);
            }
        }
    }

    private void displayTiredness(App myApp, Date viewDate) {
        Tiredness myTModel;
        List<TirednessEntity> records;

        myTModel = new Tiredness(myApp.getDaoSession(), new Date());
        records = myTModel.getTRecordsForDate(viewDate);
        Log.d("Record", Integer.toString(records.size()));
        RadioGroup tRG = (RadioGroup)findViewById(R.id.tirednessRG);
        RadioGroup tSeverityRG = (RadioGroup)findViewById(R.id.tSeverityRG);
        RadioGroup tBotherRG = (RadioGroup)findViewById(R.id.tBotherRG);
        RadioGroup tBedRG = (RadioGroup)findViewById(R.id.tBedRG);
        RadioGroup tSelfCareRG = (RadioGroup)findViewById(R.id.tSelfCareRG);
        LinearLayout tExtra = (LinearLayout) findViewById(R.id.tExtraquestions);
        if (records.size() > 0) {
            boolean tiredness = records.get(0).getTiredness();
            int severity = records.get(0).getSeverity();
            int bother = records.get(0).getBotherLevel();
            int bed = records.get(0).getBed();
            int selfCare = records.get(0).getSelfCare();
            Log.d("TIREDNESS", records.get(0).getDateTime().toString() + " " + Boolean.toString(tiredness)+ " " + Integer.toString(severity) + " " + Integer.toString(bother) + " " + Integer.toString(bed) + " " + Integer.toString(selfCare));
            displayRGBoolean(tRG, tiredness, tExtra, R.id.tirednessYes, R.id.tirednessNo);
            displayRG3Value(tSeverityRG, severity, R.id.tSeverityMild, R.id.tSeverityModerate, R.id.tSeveritySevere);
            displayRG4Value(tBotherRG, bother, R.id.tBotherNotAtAll, R.id.tBotherALittle, R.id.tBotherQuiteABit, R.id.tBotherVeryMuch);
            displayRG2Value(tBedRG, bed, R.id.tBedYes, R.id.tBedNo);
            displayRG2Value(tSelfCareRG, selfCare, R.id.tSelfCareYes, R.id.tSelfCareNo);
        }
    }


    private void displayRGBoolean(RadioGroup fsRG, boolean value, LinearLayout fsExtra, int trueValue, int falseValue) {
        if (value) {
            fsRG.check(trueValue);
            findViewById(trueValue).setEnabled(true);
            fsExtra.setVisibility(View.VISIBLE);
        }
        else {
            fsRG.check(falseValue);
            findViewById(falseValue).setEnabled(true);
        }
    }

    private void displayRG2Boolean(RadioGroup fsRG, boolean value, int trueValue, int falseValue) {
        if (value) {
            fsRG.check(trueValue);
            findViewById(trueValue).setEnabled(true);
        }
        else {
            fsRG.check(falseValue);
            findViewById(falseValue).setEnabled(true);
        }
    }

    private void displayRG2Value(RadioGroup fsRG, int value, int zeroValue, int oneValue) {
        if (value == 0) {
            fsRG.check(zeroValue);
            findViewById(zeroValue).setEnabled(true);
        }
        else {
            fsRG.check(oneValue);
            findViewById(oneValue).setEnabled(true);
        }
    }

    private void displayRG3Value(RadioGroup fsRG, int severityValue, int mild, int moderate, int severe) {
        if (severityValue == 0) {
            fsRG.check(mild);
            findViewById(mild).setEnabled(true);
        }
        else if(severityValue == 1) {
            fsRG.check(moderate);
            findViewById(moderate).setEnabled(true);
        }
        else {
            fsRG.check(severe);
            findViewById(severe).setEnabled(true);
        }
    }

    private void displayRG4Value(RadioGroup fsRG, int value, int zeroValue, int oneValue, int twoValue, int threeValue) {
        if (value == 0) {
            fsRG.check(zeroValue);
            findViewById(zeroValue).setEnabled(true);
        }
        else if(value == 1) {
            fsRG.check(oneValue);
            findViewById(oneValue).setEnabled(true);
        }
        else if(value == 2) {
            fsRG.check(twoValue);
            findViewById(twoValue).setEnabled(true);
        }
        else {
            fsRG.check(threeValue);
            findViewById(threeValue).setEnabled(true);
        }
    }

    public void nextPage(View view) {
        Intent i = new Intent(ViewQuestionnaireActivity.this, ViewQuestionnairePage2Activity.class);
        i.putExtra("DATE", viewDate.getTime());
        i.putExtra("userID", userID);
        startActivity(i);
    }

    public void previousPage(View view) {
        Intent i;
        if (myApp.getRoleID() == Constants.ROLEID_PATIENT)
            i = new Intent(ViewQuestionnaireActivity.this, QuestionnaireActivity.class);
        else {
            i = new Intent(ViewQuestionnaireActivity.this, QuestionnaireListActivity.class);
            i.putExtra("userID", userID);
        }
        startActivity(i);
    }
}
