package com.example.michael.aysms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.Pain;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Pain questionnaire page
 */

public class PainActivity extends AppCompatActivity {
    RadioGroup painRG;
    private App myApp;
    private Pain myPainModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain);

        painRG = (RadioGroup)findViewById(R.id.painRG);

        myApp = (App)getApplicationContext();
        myPainModel = new Pain(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getPain())
            restoreRGValues();
    }

    public void radioClick(View view) {
        Log.d("radioClick", Integer.toString(view.getId()));
        switch (view.getId()) {
            case R.id.sickYes:
                myPainModel.insertPainRecord(getPainRGValue(painRG.getCheckedRadioButtonId()));
                savePain(painRG.getCheckedRadioButtonId());
                Intent i = new Intent(PainActivity.this, BodySelectActivity.class);
                startActivity(i);
                break;
            case R.id.sickNo:
                break;
        }
    }

    public boolean getPainRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return false;
        else if(checkedButtonID == R.id.sickNo)
            return false;
        else return true;
    }

    private void savePain(int painRGId) {
        myApp.setSharedPreferencesBoolean(Constants.PAIN, true);
        myApp.setSharedPreferencesInt(Constants.PAIN_PAINYESID, painRGId);
        Log.d("SavePain", Integer.toString(painRGId));
    }

    private void restoreRGValues() {
        int painRGId = myApp.getSharedPreferencesInt(Constants.PAIN_PAINYESID, 0);
        if (painRGId != -1) {
            painRG.check(painRGId);
        }
    }

    public void nextPage(View view) {

        myPainModel.insertPainRecord(getPainRGValue(painRG.getCheckedRadioButtonId()));

        savePain(painRG.getCheckedRadioButtonId());

        Intent i = new Intent(PainActivity.this, HowDoYouFeelActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {

        myPainModel.insertPainRecord(getPainRGValue(painRG.getCheckedRadioButtonId()));

        savePain(painRG.getCheckedRadioButtonId());

        Intent i = new Intent(PainActivity.this, TirednessActivity.class);
        startActivity(i);
    }
}