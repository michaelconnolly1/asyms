package com.example.michael.aysms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.ActivityLevel;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Activity Levels questionnare page
 */

public class ActivityLevelsActivity extends AppCompatActivity {
    private LinearLayout additionalInfo;
    private RadioGroup activityLevelsRG;
    private CheckBox washedCheckbox, walkedCheckbox, activitycCheckbox, activitydCheckbox;
    private EditText answerEditText;
    private App myApp;
    private ActivityLevel myALModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        additionalInfo = (LinearLayout)findViewById(R.id.additionalinfo);
        activityLevelsRG = (RadioGroup)findViewById(R.id.activitylevelRG);
        washedCheckbox = (CheckBox)findViewById(R.id.washedCheckbox);
        walkedCheckbox = (CheckBox)findViewById(R.id.walkedCheckbox);
        activitycCheckbox = (CheckBox)findViewById(R.id.activitycCheckbox);
        activitydCheckbox = (CheckBox)findViewById(R.id.activitydCheckbox);
        answerEditText = (EditText)findViewById(R.id.answerEditText);

        myApp = (App)getApplicationContext();
        myALModel = new ActivityLevel(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        if (myApp.getActivityLevels())
            restoreRGValues();
    }

    public void radioActivityLevel(View view) {
        Log.d("radioActivityLevel", Integer.toString(view.getId()));
        switch (view.getId()) {
            case R.id.happyactive:
                additionalInfo.setVisibility(View.INVISIBLE);
                break;
            case R.id.moreactive:
                additionalInfo.setVisibility(View.VISIBLE);
                break;
            case R.id.struggledactive:
                additionalInfo.setVisibility(View.VISIBLE);
                break;
        }
    }

    public int getALRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.happyactive)
            return 0;
        else if(checkedButtonID == R.id.moreactive)
            return 1;
        else return 2;
    }

    private void saveActivityLevels(boolean washed, boolean walked, boolean activityc, boolean activityd, int activityLevelRGId, String reason) {
        myApp.setSharedPreferencesBoolean(Constants.ACTIVITYLEVELS, true);
        myApp.setSharedPreferencesBoolean(Constants.ACTIVITYLEVELS_WASHED, washed);
        myApp.setSharedPreferencesBoolean(Constants.ACTIVITYLEVELS_WALKED, walked);
        myApp.setSharedPreferencesBoolean(Constants.ACTIVITYLEVELS_ACTIVITYC, activityc);
        myApp.setSharedPreferencesBoolean(Constants.ACTIVITYLEVELS_ACTIVITYD, activityd);
        myApp.setSharedPreferencesInt(Constants.ACTIVITYLEVELS_FEELID, activityLevelRGId);
        myApp.setSharedPreferencesString(Constants.ACTIVITYLEVELS_REASON, reason);
    }

    private void restoreRGValues() {
        boolean washed= myApp.getSharedPreferencesBoolean(Constants.ACTIVITYLEVELS_WASHED, false);
        boolean walked= myApp.getSharedPreferencesBoolean(Constants.ACTIVITYLEVELS_WALKED, false);
        boolean activityc= myApp.getSharedPreferencesBoolean(Constants.ACTIVITYLEVELS_ACTIVITYC, false);
        boolean activityd= myApp.getSharedPreferencesBoolean(Constants.ACTIVITYLEVELS_ACTIVITYD, false);
        int activityLevelRGId = myApp.getSharedPreferencesInt(Constants.ACTIVITYLEVELS_FEELID, 0);
        String reason = myApp.getSharedPreferencesString(Constants.ACTIVITYLEVELS_REASON, "");
        washedCheckbox.setChecked(washed);
        walkedCheckbox.setChecked(walked);
        activitycCheckbox.setChecked(activityc);
        activitydCheckbox.setChecked(activityd);

        if (activityLevelRGId != -1) {
            activityLevelsRG.check(activityLevelRGId);
            if (activityLevelRGId == R.id.happyactive)
                additionalInfo.setVisibility(View.INVISIBLE);
            else {
                additionalInfo.setVisibility(View.VISIBLE);
                answerEditText.setText(reason);
            }
        }
    }

    public void nextPage(View view) {

        myALModel.insertALRecord(washedCheckbox.isChecked(), walkedCheckbox.isChecked(), activitycCheckbox.isChecked(), activitydCheckbox.isChecked(), getALRGValue(activityLevelsRG.getCheckedRadioButtonId()), answerEditText.getText().toString());

        saveActivityLevels(washedCheckbox.isChecked(), walkedCheckbox.isChecked(), activitycCheckbox.isChecked(), activitydCheckbox.isChecked(), activityLevelsRG.getCheckedRadioButtonId(), answerEditText.getText().toString());

        Intent i = new Intent(ActivityLevelsActivity.this, TirednessActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {

        myALModel.insertALRecord(washedCheckbox.isChecked(), walkedCheckbox.isChecked(), activitycCheckbox.isChecked(), activitydCheckbox.isChecked(), getALRGValue(activityLevelsRG.getCheckedRadioButtonId()), answerEditText.getText().toString());

        saveActivityLevels(washedCheckbox.isChecked(), walkedCheckbox.isChecked(), activitycCheckbox.isChecked(), activitydCheckbox.isChecked(), activityLevelsRG.getCheckedRadioButtonId(), answerEditText.getText().toString());

        Intent i = new Intent(ActivityLevelsActivity.this, ClexaneInjectionsActivity.class);
        startActivity(i);
    }
}