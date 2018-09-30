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
import com.example.michael.aysms.model.Constipation;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Constipation questionnaire page
 */

public class ConstipationActivity extends AppCompatActivity {
    private RadioGroup sickRG, severityRG, botherRG, lastMovementRG;
    private App myApp;
    private Constipation myCSModel;
    private LinearLayout extraQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constipation);

        sickRG = (RadioGroup) findViewById(R.id.sickRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        severityRG = (RadioGroup)findViewById(R.id.severityRG);
        lastMovementRG = (RadioGroup)findViewById(R.id.lastMovementRG);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        myApp = (App)getApplicationContext();
        myCSModel = new Constipation(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getConstipation())
            restoreRGValues();
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
        saveConstipation(sickRG.getCheckedRadioButtonId(), -1, -1, -1);
    }

    public boolean getSickRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return false;
        else if(checkedButtonID == R.id.sickNo)
            return false;
        else return true;
    }

    public int getSeverityRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.severityMild)
            return 0;
        else if(checkedButtonID == R.id.severityModerate)
            return 1;
        else return 2;
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

    public int getLastMovementRGValue(int checkButtonID){
        if(checkButtonID == -1)
            return 0;
        else if(checkButtonID == R.id.lessThan48)
            return 0;
        else
            return 1;
    }

    private void saveConstipation(int sickRGId, int severityRGId, int botherRGId, int lastMovementRGId) {
        myApp.setSharedPreferencesBoolean(Constants.CONSTIPATION, true);
        myApp.setSharedPreferencesInt(Constants.CONSTIPATION_SICKYESID, sickRGId);
        myApp.setSharedPreferencesInt(Constants.CONSTIPATION_SEVERITYID, severityRGId);
        myApp.setSharedPreferencesInt(Constants.CONSTIPATION_BOTHERID, botherRGId);
        myApp.setSharedPreferencesInt(Constants.CONSTIPATION_MOVEMENTID, lastMovementRGId);
    }

    private void restoreRGValues() {
        int sickRGId = myApp.getSharedPreferencesInt(Constants.CONSTIPATION_SICKYESID, 0);
        int severityRGId = myApp.getSharedPreferencesInt(Constants.CONSTIPATION_SEVERITYID, 0);
        int botherRGId = myApp.getSharedPreferencesInt(Constants.CONSTIPATION_BOTHERID, 0);
        int movementRGId = myApp.getSharedPreferencesInt(Constants.CONSTIPATION_MOVEMENTID, 0);
        if (sickRGId != -1) {
            sickRG.check(sickRGId);
            if (sickRGId == R.id.sickYes)
                extraQuestions.setVisibility(View.VISIBLE);
            else
                extraQuestions.setVisibility(View.INVISIBLE);
        }
        if (severityRGId != -1)
            severityRG.check(severityRGId);
        if (botherRGId != -1)
            botherRG.check(botherRGId);
        if (movementRGId != -1)
            lastMovementRG.check(movementRGId);
    }

    public void nextPage(View view) {
        myCSModel.insertCSRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()), getLastMovementRGValue(lastMovementRG.getCheckedRadioButtonId()));

        saveConstipation(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId(), lastMovementRG.getCheckedRadioButtonId());

        Intent i = new Intent(ConstipationActivity.this, EatingAndDrinkingActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        myCSModel.insertCSRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()), getLastMovementRGValue(lastMovementRG.getCheckedRadioButtonId()));

        saveConstipation(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId(), lastMovementRG.getCheckedRadioButtonId());

        Intent i = new Intent(ConstipationActivity.this, DiarrhoeaActivity.class);
        startActivity(i);
    }
}