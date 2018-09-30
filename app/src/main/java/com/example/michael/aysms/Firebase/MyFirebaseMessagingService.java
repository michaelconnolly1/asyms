package com.example.michael.aysms.Firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.example.michael.aysms.App;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.view.MainActivity;
import com.example.michael.aysms.view.MessageActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
 * Created by Michael Connolly on 12/03/2018.
 *
 * Firebase service to handle message when it is received
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("firebase", "From: " + remoteMessage.getFrom());
        Log.d("firebase", "Notification Message Body: " +
                remoteMessage.getNotification().getBody());

        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Intent intent = new Intent(this, MessageActivity.class);
            if (remoteMessage.getData().get("title") != null) {
                Log.d("sendNotification", remoteMessage.getData().get("title"));
                Log.d("sendNotification", remoteMessage.getData().get("body"));
                Log.d("sendNotification", remoteMessage.getData().get("userID"));
                intent.putExtra("title", remoteMessage.getData().get("title"));
                intent.putExtra("body", remoteMessage.getData().get("body"));
                intent.putExtra("userID", Integer.parseInt(remoteMessage.getData().get("userID")));
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    Log.d("sendNotification", "cancelled exception");
                }
            }
            else Log.d("sendNotification", "Title is null");
        }
    }

}
