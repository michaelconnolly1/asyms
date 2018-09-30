package com.example.michael.aysms.view;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.BodyButton;
import com.example.michael.aysms.model.Body;
import com.example.michael.aysms.model.BodyEntity;
import com.example.michael.aysms.model.HowDoYouFeel;
import com.example.michael.aysms.model.HowDoYouFeelEntity;
import com.example.michael.aysms.model.Pain;
import com.example.michael.aysms.model.PainEntity;
import com.example.michael.aysms.model.ProblemsOrIssues;
import com.example.michael.aysms.model.ProblemsOrIssuesEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Michael Connolly on 15/08/2018.
 *
 *  Activity to handle ViewQuestionnaire page 2
 */

public class ViewQuestionnairePage2Activity extends AppCompatActivity {
    private Button nextButton;
    private App myApp;
    private Date viewDate;
    private LinearLayout extraQuestions, llOverall;
    private RadioGroup sickRG, severityRG, botherRG;
    private ImageView myBody;
    private Button touchButton, touchButton1, touchButton2, touchButton3;
    private int myBodyWidth, myBodyHeight, bitmapWidth, bitmapHeight, centreX, centreY;
    private boolean painFlag;
    private int severityFlag, botherFlag;
    private Dialog myDialog;
    private int buttonCount;
    private Body myBodyModel;
    private boolean restored, buttonClicked;
    private int buttonNoClicked;
    private List<BodyButton> buttons;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questionnaire_page2);

        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setVisibility(View.GONE);

        Intent intent = getIntent();
        Long extraDate= intent.getLongExtra("DATE", -1);
        viewDate = new Date();
        viewDate.setTime(extraDate);
        userID = intent.getIntExtra("userID", 0);
        Log.d("View Date", viewDate.toString() + " " + userID);

        myApp = (App)getApplicationContext();

        buttonClicked = false;
        buttonCount = 0;
        buttons = new ArrayList<BodyButton>();
        myBody = (ImageView)findViewById(R.id.body);
        touchButton = (Button)findViewById(R.id.touchButton);
        touchButton.setVisibility(View.GONE);
        touchButton1 = (Button)findViewById(R.id.touchButton1);
        touchButton1.setVisibility(View.GONE);
        touchButton2 = (Button)findViewById(R.id.touchButton2);
        touchButton2.setVisibility(View.GONE);
        touchButton3 = (Button)findViewById(R.id.touchButton3);
        touchButton3.setVisibility(View.GONE);
        llOverall = (LinearLayout) findViewById(R.id.linearLayout);

        llOverall.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                displayPain(myApp, viewDate);
            }
        });

        displayHowDoYouFeel(myApp, viewDate);
        displayProblemsOrIssues(myApp, viewDate);
    }

    private void displayPain(App myApp, Date viewDate) {
        Pain myPainModel;
        List<PainEntity> records;
        myPainModel = new Pain(myApp.getDaoSession(), new Date());
        records = myPainModel.getPainRecordsForDate(viewDate);
        Log.d("Body", myBody.getX() + " " + myBody.getY());
        Log.d("Pain Record", Integer.toString(records.size()));
        if (records.size() > 0) {
            if (records.get(0).getPain() == true) {
                LinearLayout bodyLinearLayout = (LinearLayout) findViewById(R.id.bodyLayout);
                bodyLinearLayout.setVisibility(View.VISIBLE);
                displayButtons(myApp, viewDate);
            }
        }
    }

    private void displayButtons(App myApp, Date viewDate) {
        Body myBodyModel;
        List<BodyEntity> bodyRecords;

        myBodyModel = new Body(myApp.getDaoSession(), new Date());
        bodyRecords = myBodyModel.getBodyRecordsForDate(viewDate);
        Log.d("Body Record", Integer.toString(bodyRecords.size()));

        if (bodyRecords.size() > 0) {
            myBodyWidth = bodyRecords.get(0).getBodyWidth();
            myBodyHeight = bodyRecords.get(0).getBodyHeight();
            bitmapWidth = bodyRecords.get(0).getBitmapWidth();
            bitmapHeight = bodyRecords.get(0).getBitmapHeight();
            buttonCount = bodyRecords.get(0).getNButtons();
            int yDiff = (int)myBody.getY() - bodyRecords.get(0).getBitmapHeight();
            Log.d("Place", myBody.getY() + " " + bodyRecords.get(0).getBitmapHeight() + " " + yDiff);
            if (buttonCount > 0) {
                centreX = bodyRecords.get(0).getButton1X();
                centreY = bodyRecords.get(0).getButton1Y() + yDiff;
                Log.d("Restore1", Integer.toString(centreX) + " " + Integer.toString(centreY));
                touchButton.setVisibility(View.VISIBLE);
                touchButton.setX(centreX);
                touchButton.setY(centreY);

                addButton(centreX, centreY, getSickValue(bodyRecords.get(0).getButton1NewPain()),
                        getSeverityValue(bodyRecords.get(0).getButton1Severity()),
                        getBotherValue(bodyRecords.get(0).getButton1BotherLevel()));
            }
            if (buttonCount > 1) {
                centreX = bodyRecords.get(0).getButton2X();
                centreY = bodyRecords.get(0).getButton2Y() + yDiff;
                Log.d("Restore2", Integer.toString(centreX) + " " + Integer.toString(centreY));
                touchButton1.setVisibility(View.VISIBLE);
                touchButton1.setX(centreX);
                touchButton1.setY(centreY);

                addButton(centreX, centreY, getSickValue(bodyRecords.get(0).getButton2NewPain()),
                        getSeverityValue(bodyRecords.get(0).getButton2Severity()),
                        getBotherValue(bodyRecords.get(0).getButton2BotherLevel()));
            }
            if (buttonCount > 2) {
                centreX = bodyRecords.get(0).getButton3X();
                centreY = bodyRecords.get(0).getButton3Y() + yDiff;
                Log.d("Restore3", Integer.toString(centreX) + " " + Integer.toString(centreY));
                touchButton2.setVisibility(View.VISIBLE);
                touchButton2.setX(centreX);
                touchButton2.setY(centreY);

                addButton(centreX, centreY, getSickValue(bodyRecords.get(0).getButton3NewPain()),
                        getSeverityValue(bodyRecords.get(0).getButton3Severity()),
                        getBotherValue(bodyRecords.get(0).getButton3BotherLevel()));
            }
            if (buttonCount > 3) {
                centreX = bodyRecords.get(0).getButton4X();
                centreY = bodyRecords.get(0).getButton4Y() + yDiff;
                Log.d("Restore4", Integer.toString(centreX) + " " + Integer.toString(centreY));
                touchButton3.setVisibility(View.VISIBLE);
                touchButton3.setX(centreX);
                touchButton3.setY(centreY);

                addButton(centreX, centreY, getSickValue(bodyRecords.get(0).getButton4NewPain()),
                        getSeverityValue(bodyRecords.get(0).getButton4Severity()),
                        getBotherValue(bodyRecords.get(0).getButton4BotherLevel()));
            }
        }
    }

    private int getSickValue(boolean sickValue) {
        if(sickValue == false)
            return R.id.sickNo;
        else return R.id.sickYes;
    }

    private int getSeverityValue(int severityID) {
       if(severityID == 0)
            return R.id.severityMild;
        else if(severityID == 1)
            return R.id.severityModerate;
        else return R.id.severitySevere;
    }

    private int getBotherValue(int botherID) {
        if (botherID == 0)
            return R.id.botherNotAtAll;
        else if (botherID == 1)
            return R.id.botherALittle;
        else if (botherID == 2)
            return R.id.botherQuiteABit;
        else return R.id.botherVeryMuch;
    }

    private void addButton(int centreX, int centreY, int painRGValue, int severityRGValue, int botherRGValue) {
        painFlag = getSickRGValue(painRGValue);
        severityFlag = getSeverityRGValue(severityRGValue);
        botherFlag = getBotherRGValue(botherRGValue);

        BodyButton newButton = new BodyButton(centreX, centreY, painFlag, severityFlag, botherFlag,
                painRGValue, severityRGValue, botherRGValue);
        buttons.add(newButton);
    }

    public void buttonClick(View view) {
        int painRGValue = -1, severityRGValue = -1, botherRGValue = -1;
        buttonClicked = true;
        switch (view.getId()) {
            case R.id.touchButton:
                Log.d("buttonClick", "first button");
                buttonNoClicked = 0;
                break;
            case R.id.touchButton1:
                Log.d("buttonClick", "second button");
                buttonNoClicked = 1;
                break;
            case R.id.touchButton2:
                Log.d("buttonClick", "third button");
                buttonNoClicked = 2;
                break;
            case R.id.touchButton3:
                Log.d("buttonClick", "fourth button");
                buttonNoClicked = 3;
                break;
        }
        myDialog = new Dialog(this, R.style.myDialogTheme);
        myDialog.setContentView(R.layout.activity_pain1);

        painRGValue = buttons.get(buttonNoClicked).getPainRG();
        severityRGValue = buttons.get(buttonNoClicked).getSeverityRG();
        botherRGValue = buttons.get(buttonNoClicked).getBotherRG();

        setDialogValues(painRGValue, severityRGValue, botherRGValue);

        myDialog.setTitle("Add Pain Details");
        myDialog.show();
    }

    private void setDialogValues(int painRGValue, int severityRGValue, int botherRGValue){
        sickRG = (RadioGroup)myDialog.findViewById(R.id.sickRG);
        botherRG = (RadioGroup)myDialog.findViewById(R.id.botherRG);
        severityRG = (RadioGroup)myDialog.findViewById(R.id.severityRG);
        extraQuestions = (LinearLayout) myDialog.findViewById(R.id.extraquestions);

        if (painRGValue != -1) {
            sickRG.check(painRGValue);
            if (painRGValue == R.id.sickYes)
                extraQuestions.setVisibility(View.VISIBLE);
            else
                extraQuestions.setVisibility(View.INVISIBLE);
        }
        if (severityRGValue != -1)
            severityRG.check(severityRGValue);
        if (botherRGValue != -1)
            botherRG.check(botherRGValue);
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
        else if (checkedButtonID == R.id.botherNotAtAll)
            return 0;
        else if (checkedButtonID == R.id.botherALittle)
            return 1;
        else if (checkedButtonID == R.id.botherQuiteABit)
            return 2;
        else return 3;
    }

    public void continueClick(View view) {
        myDialog.dismiss();
        sickRG = (RadioGroup)myDialog.findViewById(R.id.sickRG);
        botherRG = (RadioGroup)myDialog.findViewById(R.id.botherRG);
        severityRG = (RadioGroup)myDialog.findViewById(R.id.severityRG);
        painFlag = getSickRGValue(sickRG.getCheckedRadioButtonId());
        severityFlag = getSeverityRGValue(severityRG.getCheckedRadioButtonId());
        botherFlag = getBotherRGValue(botherRG.getCheckedRadioButtonId());

        if (buttonClicked) {
            buttons.get(buttonNoClicked).setPainRG(sickRG.getCheckedRadioButtonId());
            buttons.get(buttonNoClicked).setSeverityRG(severityRG.getCheckedRadioButtonId());
            buttons.get(buttonNoClicked).setBotherRG(botherRG.getCheckedRadioButtonId());
        }
        else {
            BodyButton newButton = new BodyButton(centreX, centreY, painFlag, severityFlag, botherFlag,
                    sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());
            buttons.add(newButton);
            buttonCount++;
        }
        buttonClicked = false;
    }

    private void displayHowDoYouFeel(App myApp, Date viewDate) {
        HowDoYouFeel myHDYFModel;
        List<HowDoYouFeelEntity> records;
        myHDYFModel = new HowDoYouFeel(myApp.getDaoSession(), new Date());
        records = myHDYFModel.getFRecordsForDate(viewDate);
        Log.d("HDYF Record", Integer.toString(records.size()));
        LinearLayout additionalFeelingInfo = (LinearLayout) findViewById(R.id.additionalfeelinginfo);
        LinearLayout additionalAnxiousInfo = (LinearLayout) findViewById(R.id.additionalanxiousinfo);
        RadioGroup feelingLevelRG = (RadioGroup)findViewById(R.id.feelinglevelRG);
        RadioGroup feelingRG = (RadioGroup)findViewById(R.id.feelingRG);
        EditText answerEditText = (EditText)findViewById(R.id.answerEditText);
        EditText anxiousAnswerEditText = (EditText)findViewById(R.id.feelLikeThis);
        if (records.size() > 0) {
            int howIFeel = records.get(0).getHowIFeel();
            boolean feeling = records.get(0).getFeelingWorried();
            String reason = records.get(0).getExplainHowIFeel();
            String anxiousReason = records.get(0).getExplainWorried();
            Log.d("HDYF", records.get(0).getDateTime().toString() + " "  + howIFeel + " " + Boolean.toString(feeling) + " " + reason + " " + anxiousReason);

            displayRG3Value(feelingLevelRG, howIFeel, R.id.asexpectedfeeling, R.id.betterfeeling, R.id.worsefeeling);
            if (howIFeel != 0) {
                additionalFeelingInfo.setVisibility(View.VISIBLE);
                answerEditText.setText(reason);
            }
            displayRGBoolean(feelingRG, feeling, additionalAnxiousInfo, R.id.anxiousYes, R.id.anxiousNo);
            anxiousAnswerEditText.setText(anxiousReason);
        }
    }


    private void displayRGBoolean(RadioGroup fsRG, boolean value, LinearLayout fsExtra, int trueValue, int falseValue) {
        if (value) {
            fsRG.check(trueValue);
            findViewById(trueValue).setEnabled(true);
            fsExtra.setVisibility(View.VISIBLE);
        }
        else {
            fsRG.check(falseValue);
            findViewById(falseValue).setEnabled(true);
        }
    }

    private void displayRG2Boolean(RadioGroup fsRG, boolean value, int trueValue, int falseValue) {
        if (value) {
            fsRG.check(trueValue);
            findViewById(trueValue).setEnabled(true);
        }
        else {
            fsRG.check(falseValue);
            findViewById(falseValue).setEnabled(true);
        }
    }

    private void displayRG2Value(RadioGroup fsRG, int value, int zeroValue, int oneValue) {
        if (value == 0) {
            fsRG.check(zeroValue);
            findViewById(zeroValue).setEnabled(true);
        }
        else {
            fsRG.check(oneValue);
            findViewById(oneValue).setEnabled(true);
        }
    }

    private void displayRG3ChildValue(RadioGroup fsRG, int severityValue, View child, int mild, int moderate, int severe) {
        if (severityValue == 0) {
            fsRG.check(mild);
            child.findViewById(mild).setEnabled(true);
        }
        else if(severityValue == 1) {
            fsRG.check(moderate);
            child.findViewById(moderate).setEnabled(true);
        }
        else {
            fsRG.check(severe);
            child.findViewById(severe).setEnabled(true);
        }
    }

    private void displayRG3Value(RadioGroup fsRG, int severityValue, int mild, int moderate, int severe) {
        if (severityValue == 0) {
            fsRG.check(mild);
            findViewById(mild).setEnabled(true);
        }
        else if(severityValue == 1) {
            fsRG.check(moderate);
            findViewById(moderate).setEnabled(true);
        }
        else {
            fsRG.check(severe);
            findViewById(severe).setEnabled(true);
        }
    }

    private void displayRG4ChildValue(RadioGroup fsRG, int value, View child, int zeroValue, int oneValue, int twoValue, int threeValue) {
        if (value == 0) {
            fsRG.check(zeroValue);
            child.findViewById(zeroValue).setEnabled(true);
        }
        else if(value == 1) {
            fsRG.check(oneValue);
            child.findViewById(oneValue).setEnabled(true);
        }
        else if(value == 2) {
            fsRG.check(twoValue);
            child.findViewById(twoValue).setEnabled(true);
        }
        else {
            fsRG.check(threeValue);
            child.findViewById(threeValue).setEnabled(true);
        }
    }

    private void displayProblemsOrIssues(App myApp, Date viewDate) {
        ProblemsOrIssues myPOIModel;
        List<ProblemsOrIssuesEntity> records;
        myPOIModel = new ProblemsOrIssues(myApp.getDaoSession(), new Date());
        records = myPOIModel.getPIRecordsForDate(viewDate);
        //records = myPOIModel.getPIRecords();
        Log.d("POI Record", Integer.toString(records.size()));
        if (records.size() > 0) {
            LinearLayout problems = (LinearLayout) findViewById(R.id.extraquestions);
            for (int index = 0; index < records.size(); index++) {
                View child = getLayoutInflater().inflate(R.layout.problems_layout, null);
                String problem = records.get(index).getProblem();
                int severity = records.get(index).getSeverity();
                int bother = records.get(index).getBotherLevel();
                EditText problemET = (EditText) child.findViewById(R.id.problemEdit);
                RadioGroup severityRG = (RadioGroup)child.findViewById(R.id.severityRG);
                RadioGroup botherRG = (RadioGroup)child.findViewById(R.id.botherRG);

                problemET.setText(problem);
                displayRG3ChildValue(severityRG, severity, child, R.id.severityMild, R.id.severityModerate, R.id.severitySevere);
                displayRG4ChildValue(botherRG, bother, child, R.id.botherNotAtAll, R.id.botherALittle, R.id.botherQuiteABit, R.id.botherVeryMuch);
                problems.addView(child);
            }
        }
    }

    public void previousPage(View view) {
        Intent i = new Intent(ViewQuestionnairePage2Activity.this, ViewQuestionnaireActivity.class);
        i.putExtra("DATE", viewDate.getTime());
        i.putExtra("userID", userID);
        startActivity(i);
    }
}
