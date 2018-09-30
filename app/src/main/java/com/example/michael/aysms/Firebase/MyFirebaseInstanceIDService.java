package com.example.michael.aysms.Firebase;

import android.util.Log;

import com.example.michael.aysms.App;
import com.example.michael.aysms.Utils.Constants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

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
 * Created by Michael Connolly on 12/08/2018.
 *
 * Firebase token has changed - send new token to server
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public MyFirebaseInstanceIDService() {
    }
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("firebase", "Refreshed token: " + refreshedToken);

// We will Send this refreshedToken to our app server, so app
// server can save it and can later use it for sending notification to app.

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        String UPDATE_FCMTOKEN_URL = "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/update_fcmtoken_ws.php";
        App myApp = (App)getApplication();
        String token = myApp.getToken();
        JSONObject postBody = new JSONObject();
        try {
            postBody.put("token", token);
            postBody.put("fcmToken", refreshedToken);
        } catch (JSONException e) {
            Log.d("sendR JSONExcept: ", e.getMessage());
        }

        try {
            postRequest(UPDATE_FCMTOKEN_URL, postBody);
        } catch (IOException e) {
            Log.d("sendR IOException: ", e.getMessage());
        }
    }

    private void postRequest(String postUrl,JSONObject postBody) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(Constants.JSON, postBody.toString());
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            boolean success = json.getBoolean("success");
                            Log.d("onResponse : ", Boolean.toString(success));
                        } catch (JSONException e) {
                            Log.d("onResponse Exception: ", e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }
}