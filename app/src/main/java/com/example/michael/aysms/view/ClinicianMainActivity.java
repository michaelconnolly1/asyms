package com.example.michael.aysms.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;;
import com.example.michael.aysms.patients.PatientsAdapter;
import com.example.michael.aysms.model.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
 *  Activity to handle main Clinician page (show list of patients in recycler view)
 */

public class ClinicianMainActivity extends AppCompatActivity {
    private Toolbar mTopToolbar;
    private App  myApp;
    private String GETPATIENTS_URL= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/patients_ws.php";
    private List<Patient> patients;
    private RecyclerView mPatientRecycler;
    private PatientsAdapter mPatientAdapter;
    private ProgressDialog myProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinician_main);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        myApp = (App)getApplicationContext();
        patients = new ArrayList<Patient>();

        Intent intent = getIntent();
        boolean finishedError = intent.getBooleanExtra("FINISHEDERROR", false);
        boolean settingsChanged = intent.getBooleanExtra("CHANGESETTINGS", false);

        if (finishedError) {
            Toast.makeText(this, "A network error occurred - the questionnaire will be sent later", Toast.LENGTH_LONG).show();
        }
        else if (settingsChanged) {
            Toast.makeText(this, "Settings updated", Toast.LENGTH_LONG).show();
        }

        getPatients();
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

    private void getPatients() {
        showProgress();
        JSONObject postBody = new JSONObject();
        try {
            postBody.put("token", myApp.getToken());
        } catch (JSONException e) {
            Log.d("getPatient JSONExcept: ", e.getMessage());
        }
        try {
            postRequest(GETPATIENTS_URL, postBody, this);
        } catch (IOException e) {
            Log.d("sendNote IOException: ", e.getMessage());
        }
    }

    private void postRequest(String postUrl, JSONObject postBody, final Activity activity) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(Constants.JSON, postBody.toString());
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        Toast.makeText(activity, "Network failure", Toast.LENGTH_SHORT).show();
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        boolean success;
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            Log.d("getPatients", json.toString());
                            success = json.getBoolean("success");
                            if (success) {
                                Log.d ("onResponse: ", "got patients");
                                setUpPatients(json);
                            }
                            else
                                Log.d ("onResponse: ", "Error getting patients");
                        } catch (JSONException e) {
                            Log.d("onResponse Exception: ", e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void setUpPatients(JSONObject json) {
        try {
            int nRows = json.getInt("nRows");
            JSONArray patientList = json.getJSONArray("patients");
            for (int index = 0; index < nRows; index++) {
                JSONObject patient = patientList.getJSONObject(index);
                int userID = patient.getInt("userID");
                String fName = patient.getString("FirstName");
                String lName = patient.getString("LastName");
                String fullName = fName + " " + lName;
                String CHINo = patient.getString("CHINo");
                String telNo = patient.getString("PhoneNo");
                int gender = patient.getInt("Gender");
                String genderString = (gender == 0) ? "Male" : "Female";
                String DOB = patient.getString("DOB");
                String email = patient.getString("userName");
                Patient pat = new Patient(userID, fullName, DOB, genderString, telNo, CHINo, email);
                Log.d("ADD", userID + " " + fullName + " " + DOB + " " + genderString + " " + telNo + " " + CHINo + " " + email);
                patients.add(pat);
            }
            displayMessages(patients);
        }
        catch  (JSONException e) {
            Log.d("setUpPatients Expn: ", e.getMessage());
        }
    }

    private void displayMessages(List<Patient> patients) {
        mPatientRecycler = (RecyclerView) findViewById(R.id.reyclerview_patient_list);
        mPatientAdapter = new PatientsAdapter(this, patients);
        mPatientRecycler.setAdapter(mPatientAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mPatientRecycler.setLayoutManager(llm);
    }

    public void choosePatient(int position) {
        Log.d("CHOSEN", Integer.toString(position));
        if (position < patients.size()) {
            Intent intent = new Intent(ClinicianMainActivity.this, ViewPatientActivity.class);
            intent.putExtra("userID", patients.get(position).getUserID());
            intent.putExtra("fullName", patients.get(position).getPatientName());
            intent.putExtra("CHINo", patients.get(position).getCHINo());
            intent.putExtra("telNo", patients.get(position).getPhoneNo());
            intent.putExtra("gender", patients.get(position).getGender());
            intent.putExtra("DOB", patients.get(position).getDOB());
            intent.putExtra("email", patients.get(position).getEmail());
            startActivity(intent);
        }
    }

    private void showProgress() {
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("AYsMS");
        myProgress.setMessage("Getting patient data ...");
        myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        myProgress.show();
    }

    private void dismissProgress() {
        myProgress.dismiss();
    }
}
