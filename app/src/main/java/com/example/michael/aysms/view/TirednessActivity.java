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
import com.example.michael.aysms.model.Tiredness;

/**
 * Created by Michael Connolly on 17/07/2018.
 *
 *  Activity to handle Tiredness questionnaire page
 */

public class TirednessActivity extends AppCompatActivity {
    private RadioGroup sickRG, bedRG, selfCareRG, severityRG, botherRG;
    private LinearLayout extraQuestions;
    private App myApp;
    private Tiredness myTA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiredness);
        sickRG = (RadioGroup)findViewById(R.id.sickRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        bedRG = (RadioGroup)findViewById(R.id.bedRG);
        selfCareRG = (RadioGroup)findViewById(R.id.selfCareRG);
        severityRG = (RadioGroup)findViewById(R.id.severityRG);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        myApp = (App)getApplicationContext();
        myTA = new Tiredness(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getTiredness())
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
        saveTiredness(sickRG.getCheckedRadioButtonId(), -1, -1);
    }

    public boolean getSickRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return false;
        else if(checkedButtonID == R.id.sickNo)
            return false;
        else return true;
    }

    public int getBedRGVaue(int checkedButtonID){
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.bedYes)
            return 0;
        else
            return 1;
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

    public int getSelfCareRGVaue(int checkedButtonID){
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.selfCareYes)
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

    private void saveTiredness(int sickRGId, int severityRGId, int botherRGId) {
        myApp.setSharedPreferencesBoolean(Constants.TIREDNESS, true);
        myApp.setSharedPreferencesInt(Constants.TIREDNESS_SICKYESID, sickRGId);
        myApp.setSharedPreferencesInt(Constants.TIREDNESS_SEVERITYID, severityRGId);
        myApp.setSharedPreferencesInt(Constants.TIREDNESS_BOTHERID, botherRGId);
    }

    private void restoreRGValues() {
        int sickRGId = myApp.getSharedPreferencesInt(Constants.TIREDNESS_SICKYESID, 0);
        int severityRGId = myApp.getSharedPreferencesInt(Constants.TIREDNESS_SEVERITYID, 0);
        int botherRGId = myApp.getSharedPreferencesInt(Constants.TIREDNESS_BOTHERID, 0);;
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
    }

    public void nextPage(View view) {
        myTA.insertTARecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSelfCareRGVaue(selfCareRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()), getBedRGVaue(bedRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()));

        saveTiredness(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(TirednessActivity.this, PainActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        myTA.insertTARecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSelfCareRGVaue(selfCareRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()), getBedRGVaue(bedRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()));

        saveTiredness(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(TirednessActivity.this, ActivityLevelsActivity.class);
        startActivity(i);
    }
}

