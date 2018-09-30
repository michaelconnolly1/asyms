package com.example.michael.aysms.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;

/**
 * Created by Michael Connolly on 15/09/2018.
 *
 *  Activity to handle ViewPatient page to display patient details for clinician
 */

public class ViewPatientActivity extends AppCompatActivity {
    String fullName, CHINo, telNo, gender, DOB, email;
    int userID;
    private App myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        myApp = (App)getApplicationContext();

        Intent mIntent = getIntent();
        if (mIntent != null) {
            userID = mIntent.getIntExtra("userID", 0);
            fullName = mIntent.getStringExtra("fullName");
            CHINo = mIntent.getStringExtra("CHINo");
            telNo = mIntent.getStringExtra("telNo");
            gender = mIntent.getStringExtra("gender");
            DOB = mIntent.getStringExtra("DOB");
            email = mIntent.getStringExtra("email");

            Log.d("VIEWPATIENT", userID + " " + fullName + " " + DOB + " " + gender + " " + telNo + " " + CHINo + " " + email);
        }

        displayPatientDetails(fullName, DOB, gender, telNo, CHINo, email);
    }

    private void displayPatientDetails(String fullName, String DOB, String gender, String telNo, String CHINo, String email) {
        TextView fullNameTV, CHINoTV, telNoTV, genderTV, DOBTV, emailTV;

        fullNameTV = (TextView)findViewById(R.id.my_name);
        CHINoTV = (TextView)findViewById(R.id.my_chi);
        telNoTV = (TextView)findViewById(R.id.my_phone);
        genderTV = (TextView)findViewById(R.id.my_gender);
        DOBTV = (TextView)findViewById(R.id.my_dob);
        emailTV = (TextView)findViewById(R.id.my_email);

        fullNameTV.setText(fullName);
        CHINoTV.setText(CHINo);
        telNoTV.setText(telNo);
        genderTV.setText(gender);
        DOBTV.setText(DOB);
        emailTV.setText(email);
    }

    public void viewQuestionnaires(View view)  {
        Intent intent = new Intent(ViewPatientActivity.this, QuestionnaireListActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    public void viewMessages(View view)  {
        Intent intent = new Intent(ViewPatientActivity.this, MessageActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}
