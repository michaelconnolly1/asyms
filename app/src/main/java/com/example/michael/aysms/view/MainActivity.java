package com.example.michael.aysms.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.Utils.QuestionnaireData;
import com.example.michael.aysms.model.Questionnaire;
import com.example.michael.aysms.model.QuestionnaireState;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Michael Connolly on 15/07/2018.
 *
 *  Activity to handle Main activity page for Patients
 */

public class MainActivity extends AppCompatActivity {
    private Toolbar mTopToolbar;
    private App  myApp;
    private QuestionnaireData data;
    private Dialog myDialog;
    public String postUrl= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/sendquestionnaire_ws.php";
    private ProgressDialog myProgress;
    private Questionnaire questionnaireEntity;
    private QuestionnaireState questionnaire;
    private boolean networkError;
    private int nRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        myApp = (App)getApplicationContext();

        Intent intent = getIntent();
        boolean finished = intent.getBooleanExtra("FINISHED", false);
        boolean finishedError = intent.getBooleanExtra("FINISHEDERROR", false);
        boolean settingsChanged = intent.getBooleanExtra("CHANGESETTINGS", false);

        if (finished)
            Toast.makeText(this, "Thank-you for completing the questionnaire", Toast.LENGTH_LONG).show();
        else if (finishedError) {
                Toast.makeText(this, "A network error occurred - the questionnaire will be sent later", Toast.LENGTH_LONG).show();
            }
        else if (settingsChanged) {
                Toast.makeText(this, "Settings updated", Toast.LENGTH_LONG).show();
        }

        if (myApp.getRoleID() == Constants.ROLEID_PATIENT)
            checkforQuestionnairesToSend();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ChangeSettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_logout) {
            myApp.setLoggedOut();
            startActivity(new Intent(this, Login.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void runQuestionnaire(View view) {
        // before starting the questionnaire, clear the flags for retaining local data when moving between pages
        myApp.startQuestionnaire();
        Intent i = new Intent(MainActivity.this, FeelingSickActivity.class);
        startActivity(i);
    }

    public void runPastQuestionnaire(View view) {
        // before starting the questionnaire, clear the flags for retaining local data when moving between pages
        myApp.startQuestionnaire();
        Intent i = new Intent(MainActivity.this, QuestionnaireActivity.class);
        startActivity(i);
    }

    public void runSelfCareAdvice(View view) {
        Intent i = new Intent(MainActivity.this, SelfCareAdviceActivity.class);
        startActivity(i);
    }

    public void runMessageActivity(View view) {
        Intent i = new Intent(MainActivity.this, MessageActivity.class);
        startActivity(i);
    }

    public void continueClick(View view) {
        myDialog.dismiss();
    }

    private void checkforQuestionnairesToSend() {
        List<Questionnaire> qList;
        questionnaire = new QuestionnaireState(myApp.getDaoSession());
        qList = questionnaire.getQuestionaireRecordsForQuestionnaireID();
        Log.d("Main", "Number of questionnaires " + qList.size());
        if (qList.size() > 0) {
            showProgress();
            networkError = false;
            nRecords = qList.size() - 1;
            Log.d("Main", "Need to send records to server " + qList.size());
            for (int index = 0; index < qList.size() && (!networkError); index++) {
                Log.d("Main", "Sending record " + index + " to server ");
                JSONObject postBody = new JSONObject();
                questionnaireEntity = qList.get(index);
                data = new QuestionnaireData(myApp.getDaoSession(), qList.get(index).getDateTime());
                try {
                    postBody = data.getQuestionnaireData(myApp.getToken());
                } catch (JSONException e) {
                    Log.d("SENDQ JSONException: ", e.getMessage());
                }

                try {
                    postRequest(postUrl, postBody, this, index);
                } catch (IOException e) {
                    Log.d("SENDQ IOException: ", e.getMessage());
                }
            }

        }
    }

    private void postRequest(String postUrl, JSONObject postBody, final Activity activity, int index) throws IOException {
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
                networkError = true;
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
                        int questionnaireID;
                        boolean success;
                        // only dismiss progress bar at the end of all records
                        if (index == nRecords)
                            dismissProgress();
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            success = json.getBoolean("success");
                            questionnaireID = json.getInt("questionnaireID");
                            Log.d ("Main onResp Success:", Boolean.toString(success));
                            Log.d ("onResponse QID: ", Integer.toString(questionnaireID));
                            if (success) {

                                questionnaireEntity.setQuestionnaireID(questionnaireID);
                                questionnaire.updateQuestionnaire(questionnaireEntity);
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


}
