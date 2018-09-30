package com.example.michael.aysms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.EatingAndDrinkingB;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Eating and Drinking B questionnaire page
 */

public class EatingAndDrinkingBActivity extends AppCompatActivity {
    private RadioGroup sickRG, eatNormalRG, eatSmallRG, eatAtAllRG, botherRG;
    private App myApp;
    private EatingAndDrinkingB myEDB;
    private LinearLayout extraQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eating_and_drinking_b);
        sickRG = (RadioGroup)findViewById(R.id.sickRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        eatNormalRG = (RadioGroup)findViewById(R.id.eatNormalRG);
        eatSmallRG = (RadioGroup)findViewById(R.id.eatSmallRG);
        eatAtAllRG = (RadioGroup)findViewById(R.id.eatAtAllRG);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        myApp = (App)getApplicationContext();
        myEDB = new EatingAndDrinkingB(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getEatingAndDrinkingB())
            restoreRGValues();
    }

    private void restoreRGValues() {
        Log.d("Restore", "Restore values");
        int sickRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKINGB_SICKYESID, 0);
        int eatNormalRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKINGB_EATNORMALID, 0);
        int eatSmallRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKINGB_EATSMALLERID, 0);
        int eatAtAllRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKINGB_NOTEATMUCHID, 0);
        int botherRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKINGB_BOTHERID, 0);

        if (sickRGId != -1) {
            sickRG.check(sickRGId);
            if (sickRGId == R.id.sickYes)
                extraQuestions.setVisibility(View.VISIBLE);
            else
                extraQuestions.setVisibility(View.INVISIBLE);
        }
        if (eatNormalRGId!= -1)
            eatNormalRG.check(eatNormalRGId);
        if (eatSmallRGId != -1)
            eatSmallRG.check(eatSmallRGId);
        if (eatAtAllRGId != -1)
            eatAtAllRG.check(eatAtAllRGId);
        if (botherRGId != -1)
            botherRG.check(botherRGId);
    }

    public void radioClick(View view) {
        switch (view.getId()) {
            case R.id.sickYes:
                extraQuestions.setVisibility(View.VISIBLE);
                break;
            case R.id.sickNo:
                extraQuestions.setVisibility(View.INVISIBLE);
                break;
        }
        saveEatingAndDrinkingB(sickRG.getCheckedRadioButtonId(), -1, -1, -1, -1);
    }

    public boolean getSickRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return false;
        else if(checkedButtonID == R.id.sickNo)
            return false;
        else return true;
    }

    public int getEatNormalRGVaue(int checkedButtonID){
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.eatNormalYes)
            return 0;
        else
            return 1;
    }

    public int getEatSmallRGVaue(int checkedButtonID){
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.EatSmallYes)
            return 0;
        else
            return 1;
    }

    public int getEatAtAllRGVaue(int checkedButtonID){
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.EatAtAllYes)
            return 0;
        else
            return 1;
    }

    public int getBotherRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.botherNotAtAll)
            return 0;
        else if(checkedButtonID == R.id.botherALittle)
            return 1;
        else if(checkedButtonID == R.id.botherQuiteABit)
            return 2;
        else return 3;
    }

    private void saveEatingAndDrinkingB(int sickRGId, int eatNormalRGId, int eatSmallRGId, int eatAtAllRGId, int botherRGId) {
        myApp.setSharedPreferencesBoolean(Constants.EATINGANDDRINKINGB, true);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKINGB_SICKYESID, sickRGId);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKINGB_EATNORMALID, eatNormalRGId);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKINGB_EATSMALLERID, eatSmallRGId);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKINGB_NOTEATMUCHID, eatAtAllRGId);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKINGB_BOTHERID, botherRGId);
    }

    public void nextPage(View view) {
        myEDB.insertEDBRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getEatNormalRGVaue(eatNormalRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()),
                getEatSmallRGVaue(eatSmallRG.getCheckedRadioButtonId()), getEatAtAllRGVaue(eatAtAllRG.getCheckedRadioButtonId()));

        saveEatingAndDrinkingB(sickRG.getCheckedRadioButtonId(), eatNormalRG.getCheckedRadioButtonId(), eatSmallRG.getCheckedRadioButtonId(), eatAtAllRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(EatingAndDrinkingBActivity.this, SignsOfInfectionActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        myEDB.insertEDBRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getEatNormalRGVaue(eatNormalRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()),
                getEatSmallRGVaue(eatSmallRG.getCheckedRadioButtonId()), getEatAtAllRGVaue(eatAtAllRG.getCheckedRadioButtonId()));

        saveEatingAndDrinkingB(sickRG.getCheckedRadioButtonId(), eatNormalRG.getCheckedRadioButtonId(), eatSmallRG.getCheckedRadioButtonId(), eatAtAllRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(EatingAndDrinkingBActivity.this, EatingAndDrinkingActivity.class);
        startActivity(i);
    }
}

