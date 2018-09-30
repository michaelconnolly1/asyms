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
import com.example.michael.aysms.model.BeingSick;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Being Sick questionnaire page
 */

public class BeingSickActivity extends AppCompatActivity {
    private RadioGroup sickRG, severityRG, botherRG;
    private LinearLayout extraQuestions;
    private App myApp;
    private BeingSick myBSModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_being_sick1);

        sickRG = (RadioGroup)findViewById(R.id.sickRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        severityRG = (RadioGroup)findViewById(R.id.severityRG);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        myApp = (App)getApplicationContext();
        myBSModel = new BeingSick(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getBeingSick())
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
        saveBeingSick(sickRG.getCheckedRadioButtonId(), -1, -1);
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

    private void saveBeingSick(int sickRGId, int severityRGId, int botherRGId) {
        myApp.setSharedPreferencesBoolean(Constants.BEINGSICK, true);
        myApp.setSharedPreferencesInt(Constants.BEINGSICK_SICKYESID, sickRGId);
        myApp.setSharedPreferencesInt(Constants.BEINGSICK_SEVERITYID, severityRGId);
        myApp.setSharedPreferencesInt(Constants.BEINGSICK_BOTHERID, botherRGId);
    }

    private void restoreRGValues() {
        int sickRGId = myApp.getSharedPreferencesInt(Constants.BEINGSICK_SICKYESID, 0);
        int severityRGId = myApp.getSharedPreferencesInt(Constants.BEINGSICK_SEVERITYID, 0);
        int botherRGId = myApp.getSharedPreferencesInt(Constants.BEINGSICK_BOTHERID, 0);;
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
        saveBeingSick(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        myBSModel.insertBSRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()));

        Intent i = new Intent(BeingSickActivity.this, DiarrhoeaActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        saveBeingSick(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        myBSModel.insertBSRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()));

        Intent i = new Intent(BeingSickActivity.this, FeelingSickActivity.class);
        startActivity(i);
    }
}
