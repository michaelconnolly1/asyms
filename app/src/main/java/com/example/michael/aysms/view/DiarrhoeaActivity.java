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
import com.example.michael.aysms.model.Diarrhoea;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Diarrhoea questionnaire page
 */

public class DiarrhoeaActivity extends AppCompatActivity {
    private RadioGroup sickRG, severityRG, botherRG;
    private LinearLayout extraQuestions;
    private App myApp;
    private Diarrhoea myDSModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarrhoea);

        sickRG = (RadioGroup)findViewById(R.id.sickRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        severityRG = (RadioGroup)findViewById(R.id.severityRG);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        myApp = (App)getApplicationContext();
        myDSModel = new Diarrhoea(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getDiarrhoea())
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
        saveDiarrhoea(sickRG.getCheckedRadioButtonId(), -1, -1);
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

    private void saveDiarrhoea(int sickRGId, int severityRGId, int botherRGId) {
        myApp.setSharedPreferencesBoolean(Constants.DIARRHOEA, true);
        myApp.setSharedPreferencesInt(Constants.DIARRHOEA_SICKYESID, sickRGId);
        myApp.setSharedPreferencesInt(Constants.DIARRHOEA_SEVERITYID, severityRGId);
        myApp.setSharedPreferencesInt(Constants.DIARRHOEA_BOTHERID, botherRGId);
    }

    private void restoreRGValues() {
        int sickRGId = myApp.getSharedPreferencesInt(Constants.DIARRHOEA_SICKYESID, 0);
        int severityRGId = myApp.getSharedPreferencesInt(Constants.DIARRHOEA_SEVERITYID, 0);
        int botherRGId = myApp.getSharedPreferencesInt(Constants.DIARRHOEA_BOTHERID, 0);;
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
        myDSModel.insertDSRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()));

        saveDiarrhoea(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(DiarrhoeaActivity.this, ConstipationActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        myDSModel.insertDSRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()));

        saveDiarrhoea(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(DiarrhoeaActivity.this, BeingSickActivity.class);
        startActivity(i);
    }
}
