package com.example.michael.aysms.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.Utils.QuestionnaireData;
import com.example.michael.aysms.model.ProblemsOrIssues;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Michael Connolly on 25/07/2018.
 *
 *  Activity to handle Problems or Issues questionnaire page (and send questionnaire to server)
 */

public class ProblemsOrIssuesActivity extends AppCompatActivity {
    private RadioGroup problemsRG, severityRG, botherRG;
    private LinearLayout extraQuestions;
    private EditText reason;
    private App myApp;
    private ProblemsOrIssues myPI;
    private int problemNo;
    private boolean problemsToReport = false;
    private Dialog myDialog;
    public String postUrl= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/sendquestionnaire_ws.php";
    private ProgressDialog myProgress;
    private QuestionnaireData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems_or_issues);

        problemsRG = (RadioGroup)findViewById(R.id.problemsRG);
        botherRG = (RadioGroup)findViewById(R.id.botherRG);
        severityRG = (RadioGroup)findViewById(R.id.severityRG);
        reason = (EditText) findViewById(R.id.problemEdit);
        extraQuestions = (LinearLayout) findViewById(R.id.extraquestions);

        myApp = (App)getApplicationContext();
        myPI = new ProblemsOrIssues(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());
        problemNo = 0;

        if (myApp.getOtherProbs())
            restoreRGValues();
    }

    public void radioClick(View view) {
        switch (view.getId()) {
            case R.id.problemsYes:
                extraQuestions.setVisibility(View.VISIBLE);
                problemsToReport= true;
                break;
            case R.id.problemsNo:
                extraQuestions.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void yesNoClick(View view) {
        myDialog.dismiss();
        switch (view.getId()) {
            case R.id.noButton:
                sendQuestionnaire(myApp.getQuestionnaireStartDate());
                break;
            case R.id.yesButton:
                problemNo++;
                problemsRG.setVisibility(View.GONE);
                reason.setText("");
                severityRG.clearCheck();
                botherRG.clearCheck();
                break;
        }
    }

    public void yesNoAgainClick(View view) {
        myDialog.dismiss();
        switch (view.getId()) {
            case R.id.noAgainButton:
                Intent i = new Intent(ProblemsOrIssuesActivity.this, MainActivity.class);
                i.putExtra("FINISHEDERROR", true);
                startActivity(i);
                break;
            case R.id.yesAgainButton:
                sendQuestionnaire(myApp.getQuestionnaireStartDate());
                break;
        }
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

    private void saveProblemsOrIssues(String problem, int severityRGId, int botherRGId) {
        myApp.setSharedPreferencesBoolean(Constants.OTHERPROBS, true);
        myApp.setSharedPreferencesInt(Constants.OTHERPROBS_PROBLEMNO, problemNo);
        myApp.setSharedPreferencesString(Constants.OTHERPROBS_PROBLEM, problem);
        myApp.setSharedPreferencesInt(Constants.OTHERPROBS_SEVERITYID, severityRGId);
        myApp.setSharedPreferencesInt(Constants.OTHERPROBS_BOTHERID, botherRGId);
        problemNo++;
    }

    private void restoreRGValues() {
        String problem = myApp.getSharedPreferencesString(Constants.OTHERPROBS_PROBLEM, "");
        int severityRGId = myApp.getSharedPreferencesInt(Constants.OTHERPROBS_SEVERITYID, 0);
        int botherRGId = myApp.getSharedPreferencesInt(Constants.OTHERPROBS_BOTHERID, 0);
        problemNo = myApp.getSharedPreferencesInt(Constants.OTHERPROBS_PROBLEMNO, 0) + 1;

        reason.setText(problem);
        if (severityRGId != -1)
            severityRG.check(severityRGId);
        if (botherRGId != -1)
            botherRG.check(botherRGId);
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    private void sendQuestionnaire(Date startDate) {
        JSONObject postBody = new JSONObject();

        showProgress();

        data = new QuestionnaireData(myApp.getDaoSession(), startDate);
        data.saveQuestionnaire();
        try {
            postBody = data.getQuestionnaireData(myApp.getToken());
        } catch (JSONException e) {
            Log.d("SENDQ JSONException: ", e.getMessage());
        }

        try {
            postRequest(postUrl, postBody, this);
        } catch (IOException e) {
            Log.d("SENDQ IOException: ", e.getMessage());
        }
    }


    /*private void sendQuestionnaire1(Date startDate) {
        JSONObject postBody = new JSONObject();
        int myBodyWidth = 0, myBodyHeight = 0, bitmapWidth = 0, bitmapHeight = 0, buttonCount = 0;
        int button1X = 0, button1Y = 0, button1Severity = 0, button1Bother = 0;
        int button2X = 0, button2Y = 0, button2Severity = 0, button2Bother = 0;
        int button3X = 0, button3Y = 0, button3Severity = 0, button3Bother = 0;
        int button4X = 0, button4Y = 0, button4Severity = 0, button4Bother = 0;
        boolean pain = false, button1Sick = false, button2Sick = false, button3Sick = false, button4Sick = false;
        boolean washed = false, walked = false, activityc = false, activityd = false;
        int activityLevel = 0;
        String alReason = "";
        boolean beingSick = false;
        int bsSeverity = 0, bsBother = 0;
        boolean clexaneInjection = false, unusualBleeding = false, bruising = false, fever = false, swelling = false;

        FeelingSick myFSModel;
        List<FeelingSickEntity> fsRecords;
        ActivityLevel myALModel;
        List<ActivityLevelsEntity> alRecords;
        BeingSick myBSModel;
        List<BeingSickEntity> bsRecords;
        Pain myPainModel;
        List<PainEntity> pRecords;
        ClexaneInjections myCIModel;
        List<ClexaneInjectionsEntity> ciRecords;
        Constipation myCModel;
        List<ConstipationEntity> cRecords;
        Diarrhoea myDModel;
        List<DiarrhoeaEntity> dRecords;
        EatingAndDrinking myEDModel;
        List<EatingAndDrinkingEntity> eadRecords;
        EatingAndDrinkingB myEDBModel;
        List<EatingAndDrinkingBEntity> eadBecords;
        HowDoYouFeel myHDYFModel;
        List<HowDoYouFeelEntity> hdyfRecords;
        SignsOfInfection mySOIModel;
        List<SignsOfInfectionEntity> soiRecords;
        Tiredness myTModel;
        List<TirednessEntity> tRecords;
        ProblemsOrIssues myPOIModel;
        List<ProblemsOrIssuesEntity> poiRecords;
        Body myBodyModel;
        List<BodyEntity> bodyRecords;

        showProgress();

        myALModel = new ActivityLevel(myApp.getDaoSession(), startDate);
        alRecords = myALModel.getALRecordsForDate(startDate);
        if (alRecords.size() > 0) {
            washed = alRecords.get(0).getWashed();
            walked = alRecords.get(0).getWalked();
            activityc = alRecords.get(0).getActivityc();
            activityd = alRecords.get(0).getActivityd();
            activityLevel = alRecords.get(0).getFeelingLevel();
            alReason = alRecords.get(0).getReason();
        }

        myBSModel = new BeingSick(myApp.getDaoSession(), startDate);
        bsRecords = myBSModel.getBSRecordsForDate(startDate);
        if (bsRecords.size() > 0) {
            beingSick = bsRecords.get(0).getBeingSick();
            bsSeverity = bsRecords.get(0).getSeverity();
            bsBother = bsRecords.get(0).getBotherLevel();
        }

        myPainModel = new Pain(myApp.getDaoSession(), startDate);
        pRecords = myPainModel.getPainRecordsForDate(startDate);
        if (pRecords.size() > 0) {
            pain = pRecords.get(0).getPain();
            if (pain) {
                myBodyModel = new Body(myApp.getDaoSession(), startDate);
                bodyRecords = myBodyModel.getBodyRecordsForDate(startDate);
                if (bodyRecords.size() > 0) {
                    myBodyWidth = bodyRecords.get(0).getBodyWidth();
                    myBodyHeight = bodyRecords.get(0).getBodyHeight();
                    bitmapWidth = bodyRecords.get(0).getBitmapWidth();
                    bitmapHeight = bodyRecords.get(0).getBitmapHeight();
                    buttonCount = bodyRecords.get(0).getNButtons();
                    if (buttonCount > 0) {
                        button1X = bodyRecords.get(0).getButton1X();
                        button1Y = bodyRecords.get(0).getButton1Y();
                        button1Sick = bodyRecords.get(0).getButton1NewPain();
                        button1Severity = bodyRecords.get(0).getButton1Severity();
                        button1Bother = bodyRecords.get(0).getButton1BotherLevel();
                    }
                    if (buttonCount > 1) {
                        button2X = bodyRecords.get(0).getButton2X();
                        button2Y = bodyRecords.get(0).getButton2Y();
                        button2Sick = bodyRecords.get(0).getButton2NewPain();
                        button2Severity = bodyRecords.get(0).getButton2Severity();
                        button2Bother = bodyRecords.get(0).getButton2BotherLevel();
                    }
                    if (buttonCount > 2) {
                        button3X = bodyRecords.get(0).getButton3X();
                        button3Y = bodyRecords.get(0).getButton3Y();
                        button3Sick = bodyRecords.get(0).getButton3NewPain();
                        button3Severity = bodyRecords.get(0).getButton3Severity();
                        button3Bother = bodyRecords.get(0).getButton3BotherLevel();
                    }
                    if (buttonCount > 3) {
                        button4X = bodyRecords.get(0).getButton4X();
                        button4Y = bodyRecords.get(0).getButton4Y();
                        button4Sick = bodyRecords.get(0).getButton4NewPain();
                        button4Severity = bodyRecords.get(0).getButton4Severity();
                        button4Bother = bodyRecords.get(0).getButton4BotherLevel();
                    }
                }
            }
        }

        myCIModel = new ClexaneInjections(myApp.getDaoSession(), startDate);
        ciRecords = myCIModel.getCIRecordsForDate(startDate);
        if (ciRecords.size() > 0) {
            clexaneInjection = ciRecords.get(0).getBeingSick();
            unusualBleeding = ciRecords.get(0).getUnusualBleeding();
            bruising = ciRecords.get(0).getBruising();
            fever = ciRecords.get(0).getFever();
            swelling = ciRecords.get(0).getSwelling();
        }

        myCModel = new Constipation(myApp.getDaoSession(), startDate);
        cRecords = myCModel.getCRecordsForDate(startDate);
        boolean constipation = cRecords.get(0).getConstipation();
        int cSeverity = cRecords.get(0).getSeverity();
        int cBother = cRecords.get(0).getBotherLevel();
        int cLastMovement = cRecords.get(0).getLastMovement();

        myDModel = new Diarrhoea(myApp.getDaoSession(), startDate);
        dRecords = myDModel.getDRecordsForDate(startDate);
        boolean diarrhoea = dRecords.get(0).getDiarrhoea();
        int dSeverity = dRecords.get(0).getSeverity();
        int dBother = dRecords.get(0).getBotherLevel();

        myEDModel = new EatingAndDrinking(myApp.getDaoSession(), startDate);
        eadRecords = myEDModel.getEDRecordsForDate(startDate);
        boolean eatingAndDrinking = eadRecords.get(0).getEatingAndDrinking();
        int mouthDry = eadRecords.get(0).getMouthDry();
        int thirsty = eadRecords.get(0).getFeelThirsty();
        int unwell= eadRecords.get(0).getUnwell();
        String eadReason = eadRecords.get(0).getNotDrinkingReason();
        int eadBother = eadRecords.get(0).getBotherLevel();

        myEDBModel = new EatingAndDrinkingB(myApp.getDaoSession(), startDate);
        eadBecords = myEDBModel.getEDBRecordsForDate(startDate);
        boolean eatingAndDrinkingB = eadBecords.get(0).getEatingAndDrinking();
        int eatNormal = eadBecords.get(0).getEatNormal();
        int eatSmall = eadBecords.get(0).getEatSmall();
        int eatAtAll= eadBecords.get(0).getEatAtAll();
        int eadbBother = eadBecords.get(0).getBotherLevel();

        myFSModel = new FeelingSick(myApp.getDaoSession(), startDate);
        fsRecords = myFSModel.getFSRecordsForDate(startDate);
        boolean feelingSick = fsRecords.get(0).getFeelingSick();
        int fsSeverity = fsRecords.get(0).getSeverity();
        int fsBother = fsRecords.get(0).getBotherLevel();

        myHDYFModel = new HowDoYouFeel(myApp.getDaoSession(), startDate);
        hdyfRecords = myHDYFModel.getFRecordsForDate(startDate);
        int howIFeel = hdyfRecords.get(0).getHowIFeel();
        boolean feeling = hdyfRecords.get(0).getFeelingWorried();
        String hdyfReason = hdyfRecords.get(0).getExplainHowIFeel();
        String anxiousReason = hdyfRecords.get(0).getExplainWorried();

        mySOIModel = new SignsOfInfection(myApp.getDaoSession(), startDate);
        soiRecords = mySOIModel.getSOIRecordsForDate(startDate);
        boolean soi = soiRecords.get(0).getSignsOfInfection();
        boolean racing = soiRecords.get(0).getRacing();
        boolean breathless = soiRecords.get(0).getBreathless();
        boolean DORF = soiRecords.get(0).getDORF();
        boolean urine = soiRecords.get(0).getUrine();
        boolean leaking = soiRecords.get(0).getLeaking();
        boolean stoma = soiRecords.get(0).getStoma();
        int soiBother = soiRecords.get(0).getBother();
        int soiSeverity = soiRecords.get(0).getSeverity();

        myTModel = new Tiredness(myApp.getDaoSession(), startDate);
        tRecords = myTModel.getTRecordsForDate(startDate);
        boolean tiredness = tRecords.get(0).getTiredness();
        int tSeverity = tRecords.get(0).getSeverity();
        int tBother = tRecords.get(0).getBotherLevel();
        int bed = tRecords.get(0).getBed();
        int selfCare = tRecords.get(0).getSelfCare();

        myPOIModel = new ProblemsOrIssues(myApp.getDaoSession(), startDate);
        poiRecords = myPOIModel.getPIRecordsForDate(startDate);


        try {
            postBody.put("token", myApp.getToken());
            postBody.put("dateTime", startDate.getTime());

            postBody.put("alWashed", boolToInt(washed));
            postBody.put("alWalked", boolToInt(walked));
            postBody.put("alActivityC", boolToInt(activityc));
            postBody.put("alActivityD", boolToInt(activityd));
            postBody.put("alFeelingLevel", activityLevel);
            postBody.put("alReason", alReason);

            postBody.put("bsBeingSick", boolToInt(beingSick));
            postBody.put("bsSeverity", bsSeverity);
            postBody.put("bsBotherLevel", bsBother);

            postBody.put("ciBeingSick", boolToInt(clexaneInjection));
            postBody.put("ciUnusualBleeding", boolToInt(unusualBleeding));
            postBody.put("ciBruising", boolToInt(bruising));
            postBody.put("ciFever", boolToInt(fever));
            postBody.put("ciSwelling", boolToInt(swelling));

            postBody.put("cConstipation", boolToInt(constipation));
            postBody.put("cSeverity", cSeverity);
            postBody.put("cBotherLevel", cBother);
            postBody.put("cLastMovement", cLastMovement);

            postBody.put("dDiarrhoea", boolToInt(diarrhoea));
            postBody.put("dSeverity", dSeverity);
            postBody.put("dBotherLevel", dBother);

            postBody.put("eatingAndDrinking", boolToInt(eatingAndDrinking));
            postBody.put("eadUnwell", unwell);
            postBody.put("eadBotherLevel", eadBother);
            postBody.put("eadFeelThirsty", thirsty);
            postBody.put("eadNotDrinkingReason", eadReason);
            postBody.put("eadMouthDry", mouthDry);

            postBody.put("eadbEatingAndDrinking", boolToInt(eatingAndDrinkingB));
            postBody.put("eadbEatNormal", eatNormal);
            postBody.put("eadbBotherLevel", eadbBother);
            postBody.put("eadbEatSmall", eatSmall);
            postBody.put("eadbEatAtAll", eatAtAll);

            postBody.put("fsFeelingSick", boolToInt(feelingSick));
            postBody.put("fsSeverity", fsSeverity);
            postBody.put("fsBotherLevel", fsBother);

            postBody.put("hdyfHowFeel", howIFeel);
            postBody.put("hdyfFeelingWorried", boolToInt(feeling));
            postBody.put("hdyfExplainHowIFeel", hdyfReason);
            postBody.put("hdyfExplainWorried", anxiousReason);

            postBody.put("soiSignsOfInfection", boolToInt(soi));
            postBody.put("soiStoma", boolToInt(stoma));
            postBody.put("soiRacing", boolToInt(racing));
            postBody.put("soiBreathless", boolToInt(breathless));
            postBody.put("soiUrine", boolToInt(urine));
            postBody.put("soiLeaking", boolToInt(leaking));
            postBody.put("soiDORF", boolToInt(DORF));
            postBody.put("soiSeverity", soiSeverity);
            postBody.put("soiBother", soiBother);

            postBody.put("tTiredness", boolToInt(tiredness));
            postBody.put("tBed", bed);
            postBody.put("tBotherLevel", tBother);
            postBody.put("tSeverity", tSeverity);
            postBody.put("tSelfCare", selfCare);

            postBody.put("pain", boolToInt(pain));
            postBody.put("bodyWidth", myBodyWidth);
            postBody.put("bodyHeight", myBodyHeight);
            postBody.put("bitmapWidth", bitmapWidth);
            postBody.put("bitmapHeight", bitmapHeight);
            postBody.put("buttonCount", buttonCount);
            postBody.put("button1X", button1X);
            postBody.put("button1Y", button1Y);
            postBody.put("button1Sick", boolToInt(button1Sick));
            postBody.put("button1Severity", button1Severity);
            postBody.put("button1Bother", button1Bother);
            postBody.put("button2X", button2X);
            postBody.put("button2Y", button2Y);
            postBody.put("button2Sick", boolToInt(button2Sick));
            postBody.put("button2Severity", button2Severity);
            postBody.put("button2Bother", button2Bother);
            postBody.put("button3X", button3X);
            postBody.put("button3Y", button3Y);
            postBody.put("button3Sick", boolToInt(button3Sick));
            postBody.put("button3Severity", button3Severity);
            postBody.put("button3Bother", button3Bother);
            postBody.put("button4X", button4X);
            postBody.put("button4Y", button4Y);
            postBody.put("button4Sick", boolToInt(button4Sick));
            postBody.put("button4Severity", button4Severity);
            postBody.put("button4Bother", button4Bother);

            postBody.put("nProblems", poiRecords.size());

            if (poiRecords.size() > 0) {
                JSONArray problems = new JSONArray();
                for (int index = 0; index < poiRecords.size(); index++) {
                    String problem = poiRecords.get(index).getProblem();
                    int poiSeverity = poiRecords.get(index).getSeverity();
                    int poiBother = poiRecords.get(index).getBotherLevel();
                    JSONObject problemObject = new JSONObject();
                    problemObject.put("poiProblemNo", index);
                    problemObject.put("poiProblem", problem);
                    problemObject.put("poiSeverity", poiSeverity);
                    problemObject.put("poiBother", poiBother);
                    problems.put(problemObject);
                }
                postBody.put("problems", problems);
            }

            } catch(JSONException e){
                Log.d("SENDQ JSONException: ", e.getMessage());
            }
            try {
                postRequest(postUrl, postBody, this);
            } catch (IOException e) {
                Log.d("SENDQ IOException: ", e.getMessage());
            }
    }*/

    private void postRequest(String postUrl, JSONObject postBody, final Activity activity) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Log.d("SENDQ postRequest", postBody.toString());
        RequestBody body = RequestBody.create(Constants.JSON, postBody.toString());
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("SENDQ onFailure",  e.getMessage());
                call.cancel();
                dismissProgress();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Network failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("POI onResponse", myResponse);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int questionnaireID;
                        boolean success;
                        dismissProgress();
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            success = json.getBoolean("success");
                            questionnaireID = json.getInt("questionnaireID");
                            Log.d ("POI onResp Success:", Boolean.toString(success));
                            Log.d ("onResponse QID: ", Integer.toString(questionnaireID));
                            if (success) {
                                data.updateQuestionnaire(questionnaireID);
                                Intent i = new Intent(activity, MainActivity.class);
                                i.putExtra("FINISHED", true);
                                startActivity(i);
                            }
                            else {
                                setError("Invalid POST");
                                Log.d("SENDQ POST ERROR: ", "ERROR");
                            }
                        } catch (JSONException e) {
                            setError("Exception error");
                            Log.d("SENDQ onResp Excpn: ", e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void showProgress() {
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("AYsMS");
        myProgress.setMessage("Saving responses ...");
        myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        myProgress.show();
    }

    private void dismissProgress() {
        myProgress.dismiss();
    }

    private void setError(String error) {
        LinearLayout problemsLayout = (LinearLayout) findViewById(R.id.problemsLayout);
        problemsLayout.setVisibility(View.GONE);
    }


    public void nextPage(View view) {
        if (!problemsToReport) {
            sendQuestionnaire(myApp.getQuestionnaireStartDate());
        }
        else {
            myPI.insertPIRecord(problemNo, reason.getText().toString(), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()));
            saveProblemsOrIssues(reason.getText().toString(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());

            myDialog = new Dialog(this, R.style.myDialogTheme);
            myDialog.setContentView(R.layout.layoutdialog);
            myDialog.setTitle("Add Another Problem?");
            myDialog.show();
        }
    }


    public void previousPage(View view) {
        if (problemsToReport) {
            myPI.insertPIRecord(problemNo, reason.getText().toString(), getSeverityRGValue(severityRG.getCheckedRadioButtonId()), getBotherRGValue(botherRG.getCheckedRadioButtonId()));
            saveProblemsOrIssues(reason.getText().toString(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());
        }

        Intent i = new Intent(ProblemsOrIssuesActivity.this, HowDoYouFeelActivity.class);
        startActivity(i);
    }
}
