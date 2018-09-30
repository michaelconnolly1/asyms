package com.example.michael.aysms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.EatingAndDrinking;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Eating and Drinking questionnaire page
 */

public class EatingAndDrinkingActivity extends AppCompatActivity {
    private RadioGroup sickRG, mouthRG, thirstyRG, unwellRG, botherRG;
    private LinearLayout extraQuestions;
    private EditText notDrinkingReasonET;
    private App myApp;
    private EatingAndDrinking myED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eating_and_drinking);

        sickRG = (RadioGroup)findViewById(R.id.sickRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        mouthRG = (RadioGroup)findViewById(R.id.mouthRG);
        thirstyRG = (RadioGroup)findViewById(R.id.thirstyRG);
        unwellRG = (RadioGroup)findViewById(R.id.unwellRG);
        notDrinkingReasonET = (EditText) findViewById(R.id.notDrinkingReason);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        myApp = (App)getApplicationContext();
        myED = new EatingAndDrinking(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getEatingAndDrinking())
            restoreRGValues();
    }

    private void restoreRGValues() {
        int sickRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKING_SICKYESID, 0);
        int mouthDryRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKING_MOUTHDRYID, 0);
        int thirstyRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKING_THIRSTYID, 0);
        int unwellRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKING_UNWELLID, 0);
        String reason = myApp.getSharedPreferencesString(Constants.EATINGANDDRINKING_REASON, "");
        int botherRGId = myApp.getSharedPreferencesInt(Constants.EATINGANDDRINKING_BOTHERID, 0);

        if (sickRGId != -1) {
            sickRG.check(sickRGId);
            if (sickRGId == R.id.sickNo)
                extraQuestions.setVisibility(View.VISIBLE);
            else
                extraQuestions.setVisibility(View.INVISIBLE);
        }
        if (mouthDryRGId != -1)
            mouthRG.check(mouthDryRGId);
        if (thirstyRGId != -1)
            thirstyRG.check(thirstyRGId);
        if (unwellRGId != -1)
            unwellRG.check(unwellRGId);
        notDrinkingReasonET.setText(reason);
        if (botherRGId != -1)
            botherRG.check(botherRGId);
    }

    public void radioClick(View view) {
        Log.d("radioClick", Integer.toString(view.getId()));
        switch (view.getId()) {
            case R.id.sickYes:
                myED.insertEDRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getThirstyRGVaue(thirstyRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()),
                        getUnwellRGVaue(unwellRG.getCheckedRadioButtonId()), getMouthDryRGVaue(mouthRG.getCheckedRadioButtonId()), notDrinkingReasonET.getText().toString());
                saveEatingAndDrinking(sickRG.getCheckedRadioButtonId(), mouthRG.getCheckedRadioButtonId(), thirstyRG.getCheckedRadioButtonId(), unwellRG.getCheckedRadioButtonId(), notDrinkingReasonET.getText().toString(), botherRG.getCheckedRadioButtonId());
                Intent i = new Intent(EatingAndDrinkingActivity.this, EatingAndDrinkingBActivity.class);
                startActivity(i);
                break;
            case R.id.sickNo:
                extraQuestions.setVisibility(View.VISIBLE);
                saveEatingAndDrinking(sickRG.getCheckedRadioButtonId(), -1, -1, -1, "", -1);
                break;
        }
    }

    public boolean getSickRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return false;
        else if(checkedButtonID == R.id.sickNo)
            return false;
        else return true;
    }

    public int getUnwellRGVaue(int checkedButtonID){
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.unwellYes)
            return 0;
        else
            return 1;
    }

    public int getThirstyRGVaue(int checkedButtonID){
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.thirstyYes)
            return 0;
        else
            return 1;
    }

    public int getMouthDryRGVaue(int checkedButtonID){
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.mouthDryYes)
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

    private void saveEatingAndDrinking(int sickRGId, int mouthRGId, int thirstyRGId, int unwellRGId, String notDrinkingReason, int botherRGId) {
        myApp.setSharedPreferencesBoolean(Constants.EATINGANDDRINKING, true);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKING_SICKYESID, sickRGId);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKING_MOUTHDRYID, mouthRGId);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKING_THIRSTYID, thirstyRGId);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKING_UNWELLID, unwellRGId);
        myApp.setSharedPreferencesString(Constants.EATINGANDDRINKING_REASON, notDrinkingReason);
        myApp.setSharedPreferencesInt(Constants.EATINGANDDRINKING_BOTHERID, botherRGId);
    }

    public void nextPage(View view) {
        myED.insertEDRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getThirstyRGVaue(thirstyRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()),
               getUnwellRGVaue(unwellRG.getCheckedRadioButtonId()), getMouthDryRGVaue(mouthRG.getCheckedRadioButtonId()), notDrinkingReasonET.getText().toString());

        saveEatingAndDrinking(sickRG.getCheckedRadioButtonId(), mouthRG.getCheckedRadioButtonId(), thirstyRG.getCheckedRadioButtonId(), unwellRG.getCheckedRadioButtonId(), notDrinkingReasonET.getText().toString(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(EatingAndDrinkingActivity.this, EatingAndDrinkingBActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        myED.insertEDRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getThirstyRGVaue(thirstyRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()),
                getUnwellRGVaue(unwellRG.getCheckedRadioButtonId()), getMouthDryRGVaue(mouthRG.getCheckedRadioButtonId()), notDrinkingReasonET.getText().toString());

        saveEatingAndDrinking(sickRG.getCheckedRadioButtonId(), mouthRG.getCheckedRadioButtonId(), thirstyRG.getCheckedRadioButtonId(), unwellRG.getCheckedRadioButtonId(), notDrinkingReasonET.getText().toString(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(EatingAndDrinkingActivity.this, ConstipationActivity.class);
        startActivity(i);
    }
}
