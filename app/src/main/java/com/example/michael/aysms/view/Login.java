package com.example.michael.aysms.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Michael Connolly on 15/08/2018.
 *
 *  Activity to handle Login page
 */

public class Login extends AppCompatActivity {
    public String postUrl= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/login_ws.php";
    private ProgressDialog myProgress;
    private EditText myUserName, myPassword;
    private Button button;
    private App mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApp = (App)getApplicationContext();
        if (mApp.isLoggedIn()) {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
        }

        button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginDetails();
            }
        });
    }

    public void checkLoginDetails(){
        String myUserNameString, myPasswordString;
        myUserName = (EditText) findViewById(R.id.username);
        myPassword = (EditText) findViewById(R.id.password);
        myUserNameString = myUserName.getText().toString();
        myPasswordString = myPassword.getText().toString();

        showProgress();

        JSONObject postBody = new JSONObject();
        try {
            String fcmToken = FirebaseInstanceId.getInstance().getToken();
            postBody.put("userName", myUserNameString);
            postBody.put("password",  myPasswordString);
            postBody.put("fcmToken",  fcmToken);
            Log.d("Login Username ", myUserNameString);
            Log.d("Login Password ", myPasswordString);
            Log.d("Login FCMToken ", fcmToken);
        } catch (JSONException e) {
            Log.d("Login JSONException: ", e.getMessage());
        }
        try {
            postRequest(postUrl, postBody, this);
        } catch (IOException e) {
            Log.d("Login IOException: ", e.getMessage());
        }

    }

    private void postRequest(String postUrl, JSONObject postBody, final Activity activity) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Log.d("Login postRequest", postBody.toString());
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
                Log.d("Login onFailure",  e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("Login onResponse", myResponse);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String token;
                        boolean success;
                        dismissProgress();
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            success = json.getBoolean("success");
                            token = json.getString("token");
                            Log.d ("Login onResp Success:", Boolean.toString(success));
                            Log.d ("onResponse Token: ", token);
                            if (success) {

                                // set the user as logged in to prevent login screen appearing again
                                mApp.setLoggedIn();
                                mApp.setPasscodeEntered(true);
                                mApp.setToken(token);
                                int roleID = json.getInt("roleID");
                                mApp.setRoleID(roleID);

                                boolean setUpPasscodePreviously = mApp.getPasscodeSet();
                                if (setUpPasscodePreviously) {
                                    if (roleID == Constants.ROLEID_PATIENT) {
                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        startActivity(i);
                                    } else if (roleID == Constants.ROLEID_CLINICIAN) {
                                        Intent i = new Intent(Login.this, ClinicianMainActivity.class);
                                        startActivity(i);
                                    } else {
                                        setError("Invalid role");
                                        Log.d("Login ERROR: ", "ERROR");
                                    }
                                }
                                else {
                                    Intent i = new Intent(Login.this, SetSettingsActivity.class);
                                    startActivity(i);
                                }
                            }
                            else {
                                setError("Invalid Username/password");
                                Log.d("Login ERROR: ", "ERROR");
                            }
                        } catch (JSONException e) {
                            setError("Internal exception error");
                            Log.d("Login onResp Excpn: ", e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void showProgress() {
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("AYsMS");
        myProgress.setMessage("Checking login details ...");
        myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        myProgress.show();
    }

    private void dismissProgress() {
        myProgress.dismiss();
    }

    private void setError(String error) {
        myUserName.setError(error);
        myUserName.requestFocus();
    }
}
