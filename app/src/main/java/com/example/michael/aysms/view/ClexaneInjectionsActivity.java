package com.example.michael.aysms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.ClexaneInjections;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Clexane Injections questionnaire page
 */

public class ClexaneInjectionsActivity extends AppCompatActivity {
    private RadioGroup sickRG;
    private CheckBox unusualBleedingCheckbox, bruisingCheckbox, feverCheckbox, swellingCheckbox;
    private LinearLayout extraQuestions;
    private App myApp;
    private ClexaneInjections myCI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clexane_injections);
        sickRG = (RadioGroup) findViewById(R.id.sickRG);
        unusualBleedingCheckbox = (CheckBox) findViewById(R.id.unusualBleedingCheckbox);
        bruisingCheckbox = (CheckBox)findViewById(R.id.bruisingCheckbox);
        feverCheckbox = (CheckBox)findViewById(R.id.feverCheckbox);
        swellingCheckbox = (CheckBox)findViewById(R.id.swellingCheckbox);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        myApp = (App)getApplicationContext();
        myCI = new ClexaneInjections(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getClexane())
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
        saveClexane(sickRG.getCheckedRadioButtonId(), false, false, false, false);

    }

    public boolean getSickRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return false;
        else if(checkedButtonID == R.id.sickNo)
            return false;
        else return true;
    }


    private void saveClexane(int sickRGId, boolean unusualBleeding, boolean bruising, boolean fever, boolean swelling) {
        myApp.setSharedPreferencesBoolean(Constants.CLEXANE, true);
        myApp.setSharedPreferencesInt(Constants.CLEXANE_SICKYESID, sickRGId);
        myApp.setSharedPreferencesBoolean(Constants.CLEXANE_UNUSUALBLEEDING, unusualBleeding);
        myApp.setSharedPreferencesBoolean(Constants.CLEXANE_BRUISING, bruising);
        myApp.setSharedPreferencesBoolean(Constants.CLEXANE_FEVER, fever);
        myApp.setSharedPreferencesBoolean(Constants.CLEXANE_SWELLING, swelling);
    }

    private void restoreRGValues() {
        int sickRGId = myApp.getSharedPreferencesInt(Constants.CLEXANE_SICKYESID, 0);
        boolean unusualBleeding = myApp.getSharedPreferencesBoolean(Constants.CLEXANE_UNUSUALBLEEDING, false);
        boolean bruising = myApp.getSharedPreferencesBoolean(Constants.CLEXANE_BRUISING, false);
        boolean fever= myApp.getSharedPreferencesBoolean(Constants.CLEXANE_FEVER, false);
        boolean swelling = myApp.getSharedPreferencesBoolean(Constants.CLEXANE_SWELLING, false);
        if (sickRGId != -1) {
            sickRG.check(sickRGId);
            if (sickRGId == R.id.sickYes) {
                extraQuestions.setVisibility(View.VISIBLE);
                unusualBleedingCheckbox.setChecked(unusualBleeding);
                bruisingCheckbox.setChecked(bruising);
                feverCheckbox.setChecked(fever);
                swellingCheckbox.setChecked(swelling);
            }
            else
                extraQuestions.setVisibility(View.INVISIBLE);
        }
    }

    public void nextPage(View view) {
        myCI.insertCIRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), unusualBleedingCheckbox.isChecked(), bruisingCheckbox.isChecked(), feverCheckbox.isChecked(), swellingCheckbox.isChecked());

        saveClexane(sickRG.getCheckedRadioButtonId(), unusualBleedingCheckbox.isChecked(), bruisingCheckbox.isChecked(), feverCheckbox.isChecked(), swellingCheckbox.isChecked());

        Intent i = new Intent(ClexaneInjectionsActivity.this, ActivityLevelsActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        myCI.insertCIRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), unusualBleedingCheckbox.isChecked(), bruisingCheckbox.isChecked(), feverCheckbox.isChecked(), swellingCheckbox.isChecked());

        saveClexane(sickRG.getCheckedRadioButtonId(), unusualBleedingCheckbox.isChecked(), bruisingCheckbox.isChecked(), feverCheckbox.isChecked(), swellingCheckbox.isChecked());

        Intent i = new Intent(ClexaneInjectionsActivity.this, SignsOfInfectionActivity.class);
        startActivity(i);
    }
}
