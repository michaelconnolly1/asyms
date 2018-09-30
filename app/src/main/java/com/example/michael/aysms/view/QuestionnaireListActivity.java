package com.example.michael.aysms.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.ActivityLevel;
import com.example.michael.aysms.model.BeingSick;
import com.example.michael.aysms.model.Body;
import com.example.michael.aysms.model.ClexaneInjections;
import com.example.michael.aysms.model.Constipation;
import com.example.michael.aysms.model.Diarrhoea;
import com.example.michael.aysms.model.EatingAndDrinking;
import com.example.michael.aysms.model.EatingAndDrinkingB;
import com.example.michael.aysms.model.FeelingSick;
import com.example.michael.aysms.model.HowDoYouFeel;
import com.example.michael.aysms.model.Pain;
import com.example.michael.aysms.model.PatientQuestionnaire;
import com.example.michael.aysms.model.ProblemsOrIssues;
import com.example.michael.aysms.model.SignsOfInfection;
import com.example.michael.aysms.model.Tiredness;
import com.example.michael.aysms.patients.QuestionnaireAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Michael Connolly on 15/09/2018.
 *
 *  Activity to handle QuestionnaireList activity page to allow clinician to choose a particular questionnaire (uses a recycler view to show questionnaires)
 */

public class QuestionnaireListActivity extends AppCompatActivity {
    public String postUrl= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/questionnaires_ws.php";
    public String postUrl1= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/questionnaireproblems_ws.php";

    private App  myApp;
    private int userID = 0;
    private ProgressDialog myProgress;
    private boolean networkError;
    private int nRecords;
    private List<PatientQuestionnaire> questionnaires;
    private RecyclerView mQuestionnaireRecycler;
    private QuestionnaireAdapter mQuestionnaireAdapter;
    private JSONObject json;
    private ProblemsOrIssues myPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_list);

        myApp = (App) getApplicationContext();
        questionnaires = new ArrayList<PatientQuestionnaire>();

        Intent mIntent = getIntent();
        if (mIntent != null) {
            userID = mIntent.getIntExtra("userID", 0);
            getQuestionnaires(userID);
        }
        Log.d("USERID", Integer.toString(userID));
    }


    private void getQuestionnaires(int userID) {
        showProgress();

        JSONObject postBody = new JSONObject();
        try {
            postBody.put("token", myApp.getToken());
            postBody.put("userID", userID);
            postBody.put("startID", 0);
        } catch (JSONException e) {
            Log.d("SENDQ JSONException: ", e.getMessage());
        }
        try {
            postRequest(postUrl, postBody, this);
        } catch (IOException e) {
            Log.d("SENDQ IOException: ", e.getMessage());
        }
    }

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
                dismissProgress();
                Toast.makeText(activity, "Network failure", Toast.LENGTH_SHORT).show();
                Log.d("SENDQ onFailure",  e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("SENDQ onResponse", myResponse);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean success;
                        dismissProgress();
                        try {
                            json = new JSONObject(myResponse);
                            success = json.getBoolean("success");
                            nRecords = json.getInt("nRows");
                            Log.d ("QLIST onResp Success:", Boolean.toString(success));
                            Log.d ("onResponse QID: ", Integer.toString(nRecords));
                            if (success) {
                                setUpQuestionnaires(json);
                            }
                            else {
                                Log.d("SENDQ POST ERROR: ", "ERROR");
                            }
                        } catch (JSONException e) {
                            Log.d("SENDQ onResp Excpn: ", e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void setUpQuestionnaires(JSONObject json) {
        try {
            int nRows = json.getInt("nRows");
            JSONArray questionnaireList = json.getJSONArray("questionnaires");
            for (int index = 0; index < nRows; index++) {
                JSONObject questionnaire = questionnaireList.getJSONObject(index);
                int userID = questionnaire.getInt("userID");
                int questionnaireID= questionnaire.getInt("questionnaireID");
                String date = questionnaire.getString("dateTime");
                Log.d("ADD", userID + " " + questionnaireID + " " + date);
                PatientQuestionnaire q = new PatientQuestionnaire(userID, questionnaireID, date);
                questionnaires.add(q);
            }
            displayMessages(questionnaires);
        } catch  (JSONException e) {
            Log.d("setUpQuests Expn: ", e.getMessage());
        }
    }

    private void displayMessages(List<PatientQuestionnaire> questionnaires) {
        mQuestionnaireRecycler = (RecyclerView) findViewById(R.id.reyclerview_patient_list);
        mQuestionnaireAdapter = new QuestionnaireAdapter(this, questionnaires);
        mQuestionnaireRecycler.setAdapter(mQuestionnaireAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mQuestionnaireRecycler.setLayoutManager(llm);
    }

    private void showProgress() {
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("AYsMS");
        myProgress.setMessage("Getting questionnaires ...");
        myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        myProgress.show();
    }

    private void dismissProgress() {
        myProgress.dismiss();
    }

    public void chooseQuestionnaire(int position) {
        FeelingSick myFSModel;
        BeingSick myBSModel;
        Diarrhoea myDSModel;
        Constipation myCSModel;
        EatingAndDrinking myED;
        EatingAndDrinkingB myEDB;
        SignsOfInfection mySOIModel;
        ClexaneInjections myCI;
        ActivityLevel myALModel;
        Tiredness myTA;
        Pain myPainModel;
        Body myBodyModel;
        HowDoYouFeel myFModel;

        Log.d("CHOSEN", Integer.toString(position));
        try {
            JSONArray questionnaireList = json.getJSONArray("questionnaires");
            if (position < questionnaireList.length()) {
                JSONObject questionnaire = questionnaireList.getJSONObject(position);
                Log.d("CHOSEN", Integer.toString(questionnaire.getInt("questionnaireID")));
                int questionnaireID = questionnaire.getInt("questionnaireID");
                String date = questionnaire.getString("dateTime");
                Date dateTime = convertStringToDate(date);
                myFSModel = new FeelingSick(myApp.getDaoSession(), dateTime);
                myFSModel.insertFSRecord(IntToBool(questionnaire.getInt("fsFeelingSick")), questionnaire.getInt("fsSeverity"), questionnaire.getInt("fsBotherLevel"));

                myBSModel = new BeingSick(myApp.getDaoSession(), dateTime);
                myBSModel.insertBSRecord(IntToBool(questionnaire.getInt("bsBeingSick")), questionnaire.getInt("bsSeverity"), questionnaire.getInt("bsBotherLevel"));

                myDSModel = new Diarrhoea(myApp.getDaoSession(), dateTime);
                myDSModel.insertDSRecord(IntToBool(questionnaire.getInt("dDiarrhoea")), questionnaire.getInt("dSeverity"), questionnaire.getInt("dBotherLevel"));

                myCSModel = new Constipation(myApp.getDaoSession(), dateTime);
                myCSModel.insertCSRecord(IntToBool(questionnaire.getInt("cConstipation")), questionnaire.getInt("cSeverity"), questionnaire.getInt("cBotherLevel"), questionnaire.getInt("cLastMovement"));

                myED = new EatingAndDrinking(myApp.getDaoSession(), dateTime);
                myED.insertEDRecord(IntToBool(questionnaire.getInt("eatingAndDrinking")), questionnaire.getInt("eadFeelThirsty"), questionnaire.getInt("eadBotherLevel"), questionnaire.getInt("eadUnwell"), questionnaire.getInt("eadMouthDry"), questionnaire.getString("eadNotDrinkingReason"));
                myEDB = new EatingAndDrinkingB(myApp.getDaoSession(), dateTime);
                myEDB.insertEDBRecord(IntToBool(questionnaire.getInt("eadbEatingAndDrinking")), questionnaire.getInt("eadbEatNormal"), questionnaire.getInt("eadbBotherLevel"), questionnaire.getInt("eadbEatSmall"), questionnaire.getInt("eadbEatAtAll"));

                mySOIModel = new SignsOfInfection(myApp.getDaoSession(), dateTime);
                mySOIModel.insertSOIRecord(IntToBool(questionnaire.getInt("soiSignsOfInfection")), questionnaire.getInt("soiSeverity"), questionnaire.getInt("soiBother"), IntToBool(questionnaire.getInt("soiRacing")), IntToBool(questionnaire.getInt("soiLeaking")), IntToBool(questionnaire.getInt("soiBreathless")), IntToBool(questionnaire.getInt("soiDORF")), IntToBool(questionnaire.getInt("soiUrine")), IntToBool(questionnaire.getInt("soiStoma")));

                myCI = new ClexaneInjections(myApp.getDaoSession(), dateTime);
                myCI.insertCIRecord(IntToBool(questionnaire.getInt("ciBeingSick")), IntToBool(questionnaire.getInt("ciUnusualBleeding")), IntToBool(questionnaire.getInt("ciBruising")), IntToBool(questionnaire.getInt("ciFever")), IntToBool(questionnaire.getInt("ciSwelling")));

                myALModel = new ActivityLevel(myApp.getDaoSession(), dateTime);
                myALModel.insertALRecord(IntToBool(questionnaire.getInt("alWashed")), IntToBool(questionnaire.getInt("alWalked")), IntToBool(questionnaire.getInt("alActivityC")), IntToBool(questionnaire.getInt("alActivityD")), questionnaire.getInt("alFeelingLevel"), questionnaire.getString("alReason"));

                myTA = new Tiredness(myApp.getDaoSession(), dateTime);
                myTA.insertTARecord(IntToBool(questionnaire.getInt("tTiredness")), questionnaire.getInt("tBed"), questionnaire.getInt("tBotherLevel"), questionnaire.getInt("tSeverity"), questionnaire.getInt("tSelfCare"));

                myFModel = new HowDoYouFeel(myApp.getDaoSession(), dateTime);
                myFModel.insertFRecord(questionnaire.getInt("hdyfHowFeel"), IntToBool(questionnaire.getInt("hdyfFeelingWorried")), questionnaire.getString("hdyfExplainHowIFeel"), questionnaire.getString("hdyfExplainWorried"));

                myPainModel = new Pain(myApp.getDaoSession(), dateTime);
                myPainModel.insertPainRecord(IntToBool(questionnaire.getInt("pain")));

                myPI = new ProblemsOrIssues(myApp.getDaoSession(), dateTime);

                int nButtons = questionnaire.optInt("beNButtons", -1);
                Log.d("NBUTTONS", Integer.toString(nButtons));
                if (nButtons != -1) {
                    int myBodyWidth = questionnaire.optInt("beBodyWidth", -1);
                    int myBodyHeight = questionnaire.optInt("beBodyHeight", -1);
                    int myBodyStartX = questionnaire.optInt("beBitmapWidth", -1);
                    int myBodyStartY = questionnaire.optInt("beBitmapHeight", -1);
                    int beButton1X = questionnaire.optInt("beButton1X", -1);
                    int beButton1Y = questionnaire.optInt("beButton1Y", -1);
                    int beButton1NewPain = questionnaire.optInt("beButton1NewPain", 0);
                    int beButton1Severity = questionnaire.optInt("beButton1Severity", -1);
                    int beButton1BotherLevel = questionnaire.optInt("beButton1BotherLevel", -1);
                    int beButton2X = questionnaire.optInt("beButton2X", -1);
                    int beButton2Y = questionnaire.optInt("beButton2Y", -1);
                    int beButton2NewPain = questionnaire.optInt("beButton2NewPain", 0);
                    int beButton2Severity = questionnaire.optInt("beButton2Severity", -1);
                    int beButton2BotherLevel = questionnaire.optInt("beButton2BotherLevel", -1);
                    int beButton3X = questionnaire.optInt("beButton3X", -1);
                    int beButton3Y = questionnaire.optInt("beButton3Y", -1);
                    int beButton3NewPain = questionnaire.optInt("beButton3NewPain", 0);
                    int beButton3Severity = questionnaire.optInt("beButton3Severity", -1);
                    int beButton3BotherLevel = questionnaire.optInt("beButton3BotherLevel", -1);
                    int beButton4X = questionnaire.optInt("beButton4X", -1);
                    int beButton4Y = questionnaire.optInt("beButton4Y", -1);
                    int beButton4NewPain = questionnaire.optInt("beButton4NewPain", 0);
                    int beButton4Severity = questionnaire.optInt("beButton4Severity", -1);
                    int beButton4BotherLevel = questionnaire.optInt("beButton4BotherLevel", -1);
                    Log.d("NEWPAIN", beButton1NewPain + " " + beButton2NewPain + " " + beButton3NewPain + " " + beButton4NewPain);

                    myBodyModel = new Body(myApp.getDaoSession(), dateTime);
                    myBodyModel.insertBodyRecord(myBodyWidth, myBodyHeight, myBodyStartX, myBodyStartY, nButtons,
                            beButton1X, beButton1Y, IntToBool(beButton1NewPain), beButton1Severity, beButton1BotherLevel,
                            beButton2X, beButton2Y, IntToBool(beButton2NewPain), beButton2Severity, beButton2BotherLevel,
                            beButton3X, beButton3Y, IntToBool(beButton3NewPain), beButton3Severity, beButton3BotherLevel,
                            beButton4X, beButton4Y, IntToBool(beButton4NewPain), beButton4Severity, beButton4BotherLevel);
                }

                getQuestionnaireProblems(questionnaireID);

                Intent intent = new Intent(QuestionnaireListActivity.this, ViewQuestionnaireActivity.class);
                Long longDate = dateTime.getTime();
                intent.putExtra("DATE", longDate);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        } catch  (JSONException e) {
            Log.d("chooseQuest Expn: ", e.getMessage());
        }
    }

    private Date convertStringToDate(String stringDate) {
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = dateFormat.parse(stringDate);

        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    private Boolean IntToBool(int value) { return (value != 0); }

    private void getQuestionnaireProblems(int questionnaireID) {
        showProgress();

        JSONObject postBody = new JSONObject();
        try {
            postBody.put("questionnaireID", questionnaireID);
        } catch (JSONException e) {
            Log.d("SENDQ JSONException: ", e.getMessage());
        }
        try {
            postRequest1(postUrl1, postBody, this);
        } catch (IOException e) {
            Log.d("SENDQ IOException: ", e.getMessage());
        }
    }

    private void postRequest1(String postUrl, JSONObject postBody, final Activity activity) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Log.d("SENDQ1 postRequest", postBody.toString());
        RequestBody body = RequestBody.create(Constants.JSON, postBody.toString());
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dismissProgress();
                Toast.makeText(activity, "Network failure", Toast.LENGTH_SHORT).show();
                Log.d("SENDQ1 onFailure",  e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("SENDQ1 onResponse", myResponse);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean success;
                        dismissProgress();
                        try {
                            json = new JSONObject(myResponse);
                            success = json.getBoolean("success");
                            nRecords = json.getInt("nRows");
                            Log.d ("QLIST onResp Success:", Boolean.toString(success));
                            Log.d ("onResponse QID: ", Integer.toString(nRecords));
                            if (success) {
                                JSONArray problemList = json.getJSONArray("problems");
                                for (int index = 0; index < nRecords; index++) {
                                    JSONObject problem = problemList.getJSONObject(index);
                                    int problemNo = problem.getInt("poiProblemNo");
                                    String problemString = problem.getString("poiProblem");
                                    int severity = problem.getInt("poiSeverity");
                                    int bother = problem.getInt("poiBotherLevel");
                                    myPI.insertPIRecord(problemNo, problemString, severity, bother);
                                }
                            }
                            else {
                                Log.d("SENDQ POST ERROR: ", "ERROR");
                            }
                        } catch (JSONException e) {
                            Log.d("SENDQ onResp Excpn: ", e.getMessage());
                        }
                    }
                });
            }
        });
    }

}
