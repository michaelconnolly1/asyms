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
import com.example.michael.aysms.model.SignsOfInfection;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Signs iof Infection questionnaire page
 */

public class SignsOfInfectionActivity extends AppCompatActivity {
    private RadioGroup sickRG, severityRG, botherRG,racingRG, breathlessRG, DORFRG, urineRG, leakingRG, stomaRG;
    private LinearLayout extraQuestions;
    private App myApp;
    private SignsOfInfection mySOIModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signs_of_infection);

        myApp = (App)getApplicationContext();
        mySOIModel = new SignsOfInfection(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        sickRG = (RadioGroup)findViewById(R.id.sickRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        severityRG = (RadioGroup)findViewById(R.id.severityRG);
        racingRG = (RadioGroup)findViewById(R.id.racingRG);
        breathlessRG = (RadioGroup)findViewById(R.id.breathlessRG);
        DORFRG = (RadioGroup)findViewById(R.id.DORFRG);
        urineRG = (RadioGroup)findViewById(R.id.urineRG);
        leakingRG = (RadioGroup)findViewById(R.id.leakingRG);
        stomaRG = (RadioGroup)findViewById(R.id.stomaRG);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        if (myApp.getSignsOfInfection())
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
        saveSignsOfInfection(sickRG.getCheckedRadioButtonId(), -1, -1, -1, -1, -1, -1, -1, -1);
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

    public boolean getBreathlessRGValue(int checkButtonID){
        if(checkButtonID == -1)
            return false;
        else if(checkButtonID == R.id.breathlessNo)
            return false;
        else
            return true;
    }

    public boolean getDORFRGValue(int checkButtonID){
        if(checkButtonID == -1)
            return false;
        else if(checkButtonID == R.id.DORFNo)
            return false;
        else
            return true;
    }

    public boolean getRacingRGValue(int checkButtonID){
        if(checkButtonID == -1)
            return false;
        else if(checkButtonID == R.id.racingNo)
            return false;
        else
            return true;
    }

    public boolean getUrineRGValue(int checkButtonID){
        if(checkButtonID == -1)
            return false;
        else if(checkButtonID == R.id.UrineNo)
            return false;
        else
            return true;
    }

    public boolean getLeakingRGValue(int checkButtonID){
        if(checkButtonID == -1)
            return false;
        else if(checkButtonID == R.id.LeakingNo)
            return false;
        else
            return true;
    }

    public boolean getStomaRGValue(int checkButtonID){
        if(checkButtonID == -1)
            return false;
        else if(checkButtonID == R.id.StomaNo)
            return false;
        else
            return true;
    }

    private void saveSignsOfInfection(int sickRGId, int severityRGId, int botherRGId, int racingRGId, int breathlessRGId, int dizzyRGId, int urineRGId, int leakingRGId, int rednessStomaRGId) {
        myApp.setSharedPreferencesBoolean(Constants.SIGNSOFINFECTION, true);
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_SICKYESID, sickRGId);
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_SEVERITYID, severityRGId);
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_BOTHERID, botherRGId);
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_HEARTRACINGID,  racingRGId );
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_BREATHLESSID, breathlessRGId);
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_DIZZYID, dizzyRGId );
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_PAINURINEID, urineRGId);
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_LEAKINGID, leakingRGId);
        myApp.setSharedPreferencesInt(Constants.SIGNSOFINFECTION_REDNESSSTOMAID, rednessStomaRGId);
    }

    private void restoreRGValues() {
        int sickRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_SICKYESID, 0);
        int severityRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_SEVERITYID, 0);
        int botherRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_BOTHERID, 0);
        int heartRacingRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_HEARTRACINGID, 0);
        int breathlessRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_BREATHLESSID, 0);
        int dizzyRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_DIZZYID, 0);
        int painRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_PAINURINEID, 0);
        int leakingRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_LEAKINGID, 0);
        int rednessStomaRGId = myApp.getSharedPreferencesInt(Constants.SIGNSOFINFECTION_REDNESSSTOMAID, 0);

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
        if (heartRacingRGId != -1)
            racingRG.check(heartRacingRGId);
        if (breathlessRGId != -1)
            breathlessRG.check(breathlessRGId);

        if (dizzyRGId != -1)
            DORFRG.check(dizzyRGId);
        if (painRGId != -1)
            urineRG.check(painRGId);
        if (leakingRGId != -1)
            leakingRG.check(leakingRGId);
        if (rednessStomaRGId != -1)
            stomaRG.check(rednessStomaRGId);
    }

    public void nextPage(View view) {
        mySOIModel.insertSOIRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()),
                getRacingRGValue(racingRG.getCheckedRadioButtonId()), getLeakingRGValue(leakingRG.getCheckedRadioButtonId()), getBreathlessRGValue(breathlessRG.getCheckedRadioButtonId()), getDORFRGValue(DORFRG.getCheckedRadioButtonId()), getUrineRGValue(urineRG.getCheckedRadioButtonId()), getStomaRGValue(stomaRG.getCheckedRadioButtonId()));

        saveSignsOfInfection(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId(),
                racingRG.getCheckedRadioButtonId(), breathlessRG.getCheckedRadioButtonId(), DORFRG.getCheckedRadioButtonId(), urineRG.getCheckedRadioButtonId(), leakingRG.getCheckedRadioButtonId(), stomaRG.getCheckedRadioButtonId());

        Intent i = new Intent(SignsOfInfectionActivity.this, ClexaneInjectionsActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        mySOIModel.insertSOIRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()),
                getRacingRGValue(racingRG.getCheckedRadioButtonId()), getLeakingRGValue(leakingRG.getCheckedRadioButtonId()), getBreathlessRGValue(breathlessRG.getCheckedRadioButtonId()), getDORFRGValue(DORFRG.getCheckedRadioButtonId()), getUrineRGValue(urineRG.getCheckedRadioButtonId()), getStomaRGValue(stomaRG.getCheckedRadioButtonId()));

        saveSignsOfInfection(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId(),
                racingRG.getCheckedRadioButtonId(), breathlessRG.getCheckedRadioButtonId(), DORFRG.getCheckedRadioButtonId(), urineRG.getCheckedRadioButtonId(), leakingRG.getCheckedRadioButtonId(), stomaRG.getCheckedRadioButtonId());

        Intent i = new Intent(SignsOfInfectionActivity.this, EatingAndDrinkingBActivity.class);
        startActivity(i);
    }
}
