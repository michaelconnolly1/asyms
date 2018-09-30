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
import com.example.michael.aysms.model.HowDoYouFeel;

/**
 * Created by Michael Connolly on 17/07/2018.
 *
 *  Activity to handle How Do You Feel questionnaire page
 */

public class HowDoYouFeelActivity extends AppCompatActivity {
    private RadioGroup feelingLevelRG, feelingRG;
    private EditText answerEditText, feelLikeThis;
    private LinearLayout additionalFeelingInfo, additionalAnxiousInfo;
    private App myApp;
    private HowDoYouFeel myFModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_do_you_feel);
        additionalFeelingInfo = (LinearLayout) findViewById(R.id.additionalfeelinginfo);
        additionalAnxiousInfo = (LinearLayout)findViewById(R.id.additionalanxiousinfo);
        answerEditText = (EditText)findViewById(R.id.answerEditText);
        feelLikeThis = (EditText)findViewById(R.id.feelLikeThis);
        feelingLevelRG = (RadioGroup)findViewById(R.id.feelinglevelRG);
        feelingRG = (RadioGroup)findViewById(R.id.feelingRG);

        myApp = (App)getApplicationContext();
        myFModel = new HowDoYouFeel(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getHowDoYouFeel())
            restoreRGValues();
    }

    public void radioFeelingLevel(View view) {
        switch (view.getId()) {
            case R.id.asexpectedfeeling:
                additionalFeelingInfo.setVisibility(View.GONE);
                break;
            case R.id.betterfeeling:
                additionalFeelingInfo.setVisibility(View.VISIBLE);
                break;
            case R.id.worsefeeling:
                additionalFeelingInfo.setVisibility(View.VISIBLE);
                break;
        }
        saveHowDoYouFeel(feelingLevelRG.getCheckedRadioButtonId(), answerEditText.getText().toString(), feelingRG.getCheckedRadioButtonId(), feelLikeThis.getText().toString());
    }

    public boolean getFeelingWorriedRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return false;
        else if(checkedButtonID == R.id.anxiousNo)
            return false;
        else return true;
    }

    public int getfeelingLevelRG(int checkedButtonID) {
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.asexpectedfeeling)
            return 0;
        else if(checkedButtonID == R.id.betterfeeling)
            return 1;
        else return 2;
    }

    public void radioClick(View view) {
        Log.d("radioClick", Integer.toString(view.getId()));
        switch (view.getId()) {
            case R.id.anxiousYes:
                additionalAnxiousInfo.setVisibility(View.VISIBLE);
                break;
            case R.id.anxiousNo:
                additionalAnxiousInfo.setVisibility(View.INVISIBLE);
                break;
        }
        saveHowDoYouFeel(feelingLevelRG.getCheckedRadioButtonId(), "", -1, "");
    }

    private void saveHowDoYouFeel(int feelingLevelRGId, String explanation, int feelingRGId, String reason) {
        myApp.setSharedPreferencesBoolean(Constants.HOWDOYOUFEEL, true);
        myApp.setSharedPreferencesInt(Constants.HOWDOYOUFEEL_FEELID, feelingLevelRGId);
        myApp.setSharedPreferencesString(Constants.HOWDOYOUFEEL_EXPLANATION, explanation);
        myApp.setSharedPreferencesInt(Constants.HOWDOYOUFEEL_WORRIEDID, feelingRGId);
        myApp.setSharedPreferencesString(Constants.HOWDOYOUFEEL_REASON, reason);
    }

    private void restoreRGValues() {
        int feelingLevelRGId = myApp.getSharedPreferencesInt(Constants.HOWDOYOUFEEL_FEELID, 0);
        String explanation = myApp.getSharedPreferencesString(Constants.HOWDOYOUFEEL_EXPLANATION, "");
        int feelingRGId = myApp.getSharedPreferencesInt(Constants.HOWDOYOUFEEL_WORRIEDID, 0);
        String reason = myApp.getSharedPreferencesString(Constants.HOWDOYOUFEEL_REASON, "");

        if (feelingLevelRGId != -1) {
            feelingLevelRG.check(feelingLevelRGId);
            if (feelingLevelRGId == R.id.asexpectedfeeling)
                additionalFeelingInfo.setVisibility(View.GONE);
            else {
                additionalFeelingInfo.setVisibility(View.VISIBLE);
                answerEditText.setText(explanation);
            }
        }
        if (feelingRGId != -1) {
            feelingRG.check(feelingRGId);
            if (feelingRGId == R.id.anxiousNo)
                additionalAnxiousInfo.setVisibility(View.GONE);
            else {
                additionalAnxiousInfo.setVisibility(View.VISIBLE);
                feelLikeThis.setText(reason);
            }
        }
    }

    public void nextPage(View view) {
        myFModel.insertFRecord(getfeelingLevelRG(feelingLevelRG.getCheckedRadioButtonId()), getFeelingWorriedRGValue(feelingRG.getCheckedRadioButtonId()),
                answerEditText.getText().toString(), feelLikeThis.getText().toString());

        saveHowDoYouFeel(feelingLevelRG.getCheckedRadioButtonId(), answerEditText.getText().toString(), feelingRG.getCheckedRadioButtonId(), feelLikeThis.getText().toString());

        Intent i = new Intent(HowDoYouFeelActivity.this, ProblemsOrIssuesActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        myFModel.insertFRecord(getfeelingLevelRG(feelingLevelRG.getCheckedRadioButtonId()), getFeelingWorriedRGValue(feelingRG.getCheckedRadioButtonId()),
                answerEditText.getText().toString(), feelLikeThis.getText().toString());

        saveHowDoYouFeel(feelingLevelRG.getCheckedRadioButtonId(), answerEditText.getText().toString(), feelingRG.getCheckedRadioButtonId(), feelLikeThis.getText().toString());

        Intent i = new Intent(HowDoYouFeelActivity.this, BodySelectActivity.class);
        startActivity(i);
    }
}
