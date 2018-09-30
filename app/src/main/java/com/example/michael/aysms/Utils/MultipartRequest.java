package com.example.michael.aysms.Utils;

/**
 * Created by laptop on 10/09/2018.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.michael.aysms.R;
import com.example.michael.aysms.model.Message;

import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MultipartRequest {
    private Activity context;
    private MultipartBody.Builder multipartBody;
    private OkHttpClient okHttpClient;
    private Message message;

    public MultipartRequest(final Activity context, Message message) {
        this.context = context;
        this.message = message;
        this.multipartBody = new MultipartBody.Builder();
        this.multipartBody.setType(MultipartBody.FORM);
        this.okHttpClient = new OkHttpClient();
    }

    // Add String
    public void addString(String name, String value) {
        this.multipartBody.addFormDataPart(name, value);
    }

    // Add Image File
    public void addFile(String name, String filePath, String fileName, String ext) {
        String type = "image/" + ext;
        this.multipartBody.addFormDataPart(name, fileName, RequestBody.create(MediaType.parse(type), new File(filePath)));
    }


    public void execute(String url) {
        RequestBody requestBody = null;
        Request request = null;

        requestBody = this.multipartBody.build();
        request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MESSAGE FAIL", Long.toString(message.getMessageDetails().getMessageID()) + " " + message.getMessageDetails().getMessageBody());
                            message.MessageSuccess(message.getMessageDetails(), -1);
                            setWarning(View.VISIBLE, Long.toString(message.getMessageDetails().getMessageID()));
                            Toast.makeText(context, "Network failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Log.d("SUCCESS", myResponse);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            boolean success;
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                success = json.getBoolean("success");
                                if (success) {
                                    message.MessageSuccess(message.getMessageDetails(), 0);
                                    setWarning(View.GONE, Long.toString(message.getMessageDetails().getMessageID()));
                                //    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Log.d ("onResponse: ", "Error inserting message");
                            } catch (JSONException e) {
                                Log.d("onResponse Exception: ", e.getMessage());
                            }
                        }
                    });
                }
        });
    }

    private void setWarning(int visibility, String messageID) {
        RecyclerView mMessageRecycler = (RecyclerView) context.findViewById(R.id.reyclerview_message_list);
        View warning = mMessageRecycler.findViewWithTag((Object) messageID);
        if (warning != null) {
            warning.setVisibility(visibility);
        } else
            Log.d("FAIL1", "Didn't find  warning");
    }
}
