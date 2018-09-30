package com.example.michael.aysms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.FeelingSick;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Feeling Sick questionnaire page
 */

public class FeelingSickActivity extends AppCompatActivity {
    private Button previousButton;
    private LinearLayout extraQuestions;
    private RadioGroup sickRG, severityRG, botherRG;
    private App myApp;
    private FeelingSick myFSModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_sick);
        previousButton = (Button)findViewById(R.id.previousButton);
        previousButton.setVisibility(View.GONE);

        myApp = (App)getApplicationContext();
        myFSModel = new FeelingSick(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());

        sickRG = (RadioGroup)findViewById(R.id.sickRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        severityRG = (RadioGroup)findViewById(R.id.severityRG);
        extraQuestions = (LinearLayout)findViewById(R.id.extraquestions);
        TextView botherNotAtAll = (TextView)findViewById(R.id.botherNotAtAll);
        String emoji = new String(Character.toChars(0x1F60A));
        botherNotAtAll.setText(emoji + " " + botherNotAtAll.getText().toString());
        TextView botherALittle = (TextView)findViewById(R.id.botherALittle);
        emoji = new String(Character.toChars(0x1F611));
        botherALittle.setText(emoji + " " + botherALittle.getText().toString());
        TextView botherQuiteABit = (TextView)findViewById(R.id.botherQuiteABit);
        emoji = new String(Character.toChars(0x1F61E));
        botherQuiteABit.setText(emoji + " " + botherQuiteABit.getText().toString());
        TextView botherVeryMuch = (TextView)findViewById(R.id.botherVeryMuch);
        emoji = new String(Character.toChars(0x1F622));
        botherVeryMuch.setText(emoji + " " + botherVeryMuch.getText().toString());

        if (myApp.getFeelingSick())
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
        saveFeelingSick(sickRG.getCheckedRadioButtonId(), -1, -1);
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

    private void saveFeelingSick(int sickRGId, int severityRGId, int botherRGId) {
        myApp.setSharedPreferencesBoolean(Constants.FEELINGSICK, true);
        myApp.setSharedPreferencesInt(Constants.FEELINGSICK_SICKYESID, sickRGId);
        myApp.setSharedPreferencesInt(Constants.FEELINGSICK_SEVERITYID, severityRGId);
        myApp.setSharedPreferencesInt(Constants.FEELINGSICK_BOTHERID, botherRGId);
    }

    private void restoreRGValues() {
        int sickRGId = myApp.getSharedPreferencesInt(Constants.FEELINGSICK_SICKYESID, 0);
        int severityRGId = myApp.getSharedPreferencesInt(Constants.FEELINGSICK_SEVERITYID, 0);
        int botherRGId = myApp.getSharedPreferencesInt(Constants.FEELINGSICK_BOTHERID, 0);;
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

        myFSModel.insertFSRecord(getSickRGValue(sickRG.getCheckedRadioButtonId()), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()));

        saveFeelingSick(sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

        Intent i = new Intent(FeelingSickActivity.this, BeingSickActivity.class);
        startActivity(i);
    }
}
