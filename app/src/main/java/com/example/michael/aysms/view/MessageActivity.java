package com.example.michael.aysms.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michael.aysms.App;
import com.example.michael.aysms.messages.MessageListAdapter;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.Utils.MultipartRequest;
import com.example.michael.aysms.model.Message;
import com.example.michael.aysms.model.MessageEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Michael Connolly on 1/09/2018.
 *
 *  Activity to handle Messages page
 */

public class MessageActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private Message myMessageModel;
    private App myApp;
    private List<MessageEntity> messageList;
    private EditText mMessageEditText;
    private Button mMessageSendButton;
    private ImageButton mCameraButton;
    private String INSERTCLINICIANMESSAGE_URL= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/insertclinicianmessage_ws.php";
    private String INSERTMESSAGE_URL= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/insertmessage_ws.php";
    private String INSERTMESSAGEWITHFILE_URL= "https://devweb2017.cis.strath.ac.uk/~nfb14188/asyms/ws/insertmessagewithfile_ws.php";
    private static final int SELECT_PICTURE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String token;
    private String mFilePath;
    private Uri mSelectedImageUri;
    private int patientID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mFilePath = null;
        myApp = (App)getApplicationContext();
        token = myApp.getToken();
        myMessageModel = new Message(myApp.getDaoSession());

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            patientID = startingIntent.getIntExtra("userID", -1);
        }

        if (patientID != -1)
            messageList = myMessageModel.getAllMessages(patientID, Constants.VIEW_TYPE_MESSAGE_SENT);
        else
            messageList = myMessageModel.getAllMessages();

        setUpDisplay();
        displayMessages(messageList);

        if (startingIntent != null) {
            getIntent(startingIntent);
        }

        Log.d("MESSAGEACTIVITY", Integer.toString(patientID));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        MessageEntity newMessage;
        super.onNewIntent(intent);
        Log.d("onNewIntent", "on new intent called");
        newMessage = getIntent(intent);
    }

    private void setUpDisplay() {
        mMessageSendButton = (Button)findViewById(R.id.button_chatbox_send);
        mMessageEditText = (EditText)findViewById(R.id.edittext_chatbox);
        mCameraButton = (ImageButton)findViewById(R.id.camera);

        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mMessageSendButton.setEnabled(true);
                } else {
                    mMessageSendButton.setEnabled(false);
                }
            }
        });

        mMessageSendButton.setEnabled(false);
        mMessageSendButton.setOnClickListener((View v) -> {
                String userInput = mMessageEditText.getText().toString();
                if (userInput.length() > 0) {
                    sendUserMessage(userInput);
                    mMessageEditText.setText("");
                    }
            });

        if (myApp.getRoleID() == Constants.ROLEID_CLINICIAN) {
            mCameraButton.setVisibility(View.GONE);
        }
        else {
            mCameraButton.setOnClickListener((View v) -> {
                seekPermission();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            });
        }
    }

    public void seekPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MESSAGEACTIVITY ", "Now have access");
                }
                else {
                    Log.d("MESSAGEACTIVITY ", "Access denied");
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch(requestCode){
                case SELECT_PICTURE:
                    mSelectedImageUri = data.getData();
                    Log.d("IMAGE", mSelectedImageUri.toString());
                    if (mSelectedImageUri != null) {
                        mFilePath = getPathFromURI(mSelectedImageUri);
                        Log.d("IMAGE", mFilePath);
                    }
                    break;
            }
        }
    }

    public String getPathFromURI(Uri selectedImageUri) {
        String filePath = null;
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            String ext = android.webkit.MimeTypeMap.getFileExtensionFromUrl(filePath);
            File fileName = new File(filePath);
            String strFileName = fileName.getName();
            Log.d("IMAGE", filePath + " EXT: " + ext + " NAME: " + strFileName);

        }catch(Exception e)
        {}
        return filePath;
    }

    private void sendUserMessage(String userInput) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        Message newMessage = new Message(myApp.getDaoSession());
        String selectedURI = null;
        if (mSelectedImageUri != null)
            selectedURI = mSelectedImageUri.toString();
        newMessage.addMessage(userInput, Constants.VIEW_TYPE_MESSAGE_SENT, selectedURI);
        mSelectedImageUri = null;
        mMessageAdapter.addRow(newMessage.getMessageDetails());
        if (selectedURI == null)
            sendNote(token, userInput, 0, newMessage);
        else
            sendNoteAndImage(token, userInput, 0, newMessage);
    }

    private void displayMessages(List<MessageEntity> messageList) {
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMessageRecycler.setLayoutManager(llm);
        if (messageList.size() > 1)
            mMessageRecycler.smoothScrollToPosition(messageList.size() - 1);
    }

    public MessageEntity getIntent(Intent startingIntent) {
        MessageEntity newMessage = null;
        Log.d("MessageActivity", intentToString(startingIntent));
        if (startingIntent != null) {
            String patientIDString = Integer.toString(patientID);
            String body = startingIntent.getStringExtra("body"); // Retrieve the body
            String title = startingIntent.getStringExtra("title");
            if (body != null) {
                Log.d("MessageActivity", title + " - " + body + " " + patientIDString);
    //            displayMessage(title, body);
                newMessage = myMessageModel.createMessage(0, title, body, patientIDString, Constants.VIEW_TYPE_MESSAGE_RECEIVED);
                mMessageAdapter.addRow(newMessage);
                mMessageRecycler.smoothScrollToPosition(messageList.size() - 1);
            }
            else {
                Log.d("MessageActivity", "No body");
                if (title != null)
                    Log.d("MessageActivity", title);
                else
                    Log.d("MessageActivity", "No title");
            }
        }
        else
            Log.d("MessageActivity", "No intent");
        return newMessage;
    }

    private static String intentToString(Intent intent) {
        if (intent == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder("action: ")
                .append(intent.getAction())
                .append(" data: ")
                .append(intent.getDataString())
                .append(" extras: ")
                ;
        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet())
                stringBuilder.append(key).append("=").append(intent.getExtras().get(key)).append(" ");
        }

        return stringBuilder.toString();
    }

    public void displayMessage(String title, String message) {
        final Dialog myMessageDialog = new Dialog(this, R.style.myDialogTheme);
        myMessageDialog.setContentView(R.layout.notification_dialog);
        TextView advice = (TextView)myMessageDialog.findViewById(R.id.advice);
        Button myOKButton = (Button)myMessageDialog.findViewById(R.id.okButton);
        myMessageDialog.setTitle(title);
        Log.d("displayMessage", message);
        advice.setText(message);
        myOKButton.setOnClickListener((View view1) -> {
            myMessageDialog.dismiss();
        });
        myMessageDialog.show();
    }

    public void addMessage(MessageEntity message) {
        mMessageAdapter.addRow(message);
    }

    private void sendNote(String token, String messageBody, int mRiskFlag, Message message) {
        JSONObject postBody = new JSONObject();
        try {
            postBody.put("token", token);
            postBody.put("message", messageBody);
            postBody.put("riskFlag", mRiskFlag);
            if (myApp.getRoleID() == Constants.ROLEID_CLINICIAN) {
                postBody.put("patientID", patientID);
            }
        } catch (JSONException e) {
            Log.d("sendNote JSONExcept: ", e.getMessage());
        }

        try {
            if (myApp.getRoleID() == Constants.ROLEID_CLINICIAN)
                postRequest(INSERTCLINICIANMESSAGE_URL, postBody, this, message);
            else
                postRequest(INSERTMESSAGE_URL, postBody, this, message);
        } catch (IOException e) {
            Log.d("sendNote IOException: ", e.getMessage());
        }
    }

    private void postRequest(String postUrl,JSONObject postBody, final Activity activity, Message message) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(Constants.JSON, postBody.toString());
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        Log.d("POSTREQUEST", postBody.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MESSAGE FAIL", Long.toString(message.getMessageDetails().getMessageID()) + " " + message.getMessageDetails().getMessageBody());
                        message.MessageSuccess(message.getMessageDetails(), -1);
                        setWarning(View.VISIBLE, Long.toString(message.getMessageDetails().getMessageID()));
                        Toast.makeText(activity, "Network failure", Toast.LENGTH_SHORT).show();
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("ONRESPONSE", myResponse);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean success;
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            success = json.getBoolean("success");
                            if (success) {
                                message.MessageSuccess(message.getMessageDetails(), 0);
                                setWarning(View.GONE, Long.toString(message.getMessageDetails().getMessageID()));
                                mMessageRecycler.smoothScrollToPosition(messageList.size() - 1);
                                Log.d ("onResponse: ", "message inserted");
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

    private void sendNoteAndImage(String token, String messageBody, int mRiskFlag, Message message) {
        MultipartRequest multipartRequest;
        String response;

        Uri imageURI = Uri.parse(message.getMessageDetails().getFileName());
        String filePath = getPathFromURI(imageURI);
        String ext = android.webkit.MimeTypeMap.getFileExtensionFromUrl(filePath);
        File fileName = new File(filePath);
        String strFileName = fileName.getName();
        Log.d("MULTIPATH", imageURI.toString() + " PATH: " + filePath + " EXT: " + ext + " NAME: " + strFileName);

        multipartRequest = new MultipartRequest(this, message);
        multipartRequest.addString("token", token);
        multipartRequest.addString("message", messageBody);
        multipartRequest.addString("riskFlag", Integer.toString(mRiskFlag));
        multipartRequest.addFile("filename", filePath, strFileName, ext);
        multipartRequest.execute(INSERTMESSAGEWITHFILE_URL);
    }

    public void retry(View view) {
        Log.d("RETRY", view.getTag().toString());
        MessageEntity message = myMessageModel.getMessageByID(view.getTag().toString());
        if (message != null) {
            myMessageModel.setMessageDetails(message);
            String URIString = message.getFileName();
            if (URIString == null)
                sendNote(token, message.getMessageBody(), 0, myMessageModel);
            else
                sendNoteAndImage(token, message.getMessageBody(), 0, myMessageModel);
        }
    }

    private void setWarning(int visibility, String messageID) {
        View warning = mMessageRecycler.findViewWithTag((Object) messageID);
        if (warning != null) {
            warning.setVisibility(visibility);
        } else
            Log.d("FAIL", "Didn't find  warning");
    }
}
