package com.example.michael.aysms.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;

/**
 * Created by Michael Connolly on 15/08/2018.
 *
 *  Activity to handle Set Settings page toallow user to set PIN and memorable data
 */

public class SetSettingsActivity extends AppCompatActivity {
    private EditText passcode1, passcode2, answer1, answer2, answer3;
    private Button setUpPasscodeButton;
    private App myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_settings);

        myApp = (App)getApplicationContext();

        passcode1 = (EditText)findViewById(R.id.passcode1);
        passcode1.requestFocus();
        passcode2 = (EditText)findViewById(R.id.passcode2);
        answer1 = (EditText)findViewById(R.id.passcode_question1);
        answer2 = (EditText)findViewById(R.id.passcode_question2);
        answer3 = (EditText)findViewById(R.id.passcode_question3);

        setUpPasscodeButton = (Button)findViewById(R.id.setPasscodeButton);
        setUpPasscodeButton.setOnClickListener((View view) -> {
            boolean error = true;
            if (hasText(passcode1) && hasText(passcode2) && hasText(answer1) && hasText(answer2) && hasText(answer3))
                if (passcodeIsNumber(passcode1)) {
                    if (!passcode1.getText().toString().equals(passcode2.getText().toString())) {
                        passcode1.setError("Passcodes not the same");
                        passcode1.requestFocus();
                    }
                    else {
                        myApp.setPasscodeSet(true);
                        myApp.setPasscode(passcode1.getText().toString());
                        myApp.setAnswer1(answer1.getText().toString());
                        myApp.setAnswer2(answer2.getText().toString());
                        myApp.setAnswer3(answer3.getText().toString());
                        int roleID = myApp.getRoleID();
                        Intent intent = null;
                        if (roleID == Constants.ROLEID_PATIENT) {
                            intent = new Intent(SetSettingsActivity.this, MainActivity.class);
                        } else if (roleID == Constants.ROLEID_CLINICIAN) {
                            intent = new Intent(SetSettingsActivity.this, ClinicianMainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                }
        });
    }

    private boolean hasText(EditText field) {
        String text = field.getText().toString().trim();
        field.setError(null);
        if (text.length() == 0) {
            field.setError("required");
            field.requestFocus();
            return false;
        }
        else
            return true;
    }

    private boolean passcodeIsNumber(EditText passcode) {
        String text = passcode.getText().toString().trim();
        passcode.setError(null);
        if (text.length() != 4) {
            passcode.setError("Passcodes must be 4 digits");
            passcode.requestFocus();
            return false;
        }
        else {
            for (int index  = 0; index < text.length(); index++)  {
                if (!Character.isDigit(text.charAt(index))) {
                    passcode.setError("Passcodes must be 4 digits");
                    passcode.requestFocus();
                    return false;
                }
            }
            return true;
        }
    }
}
