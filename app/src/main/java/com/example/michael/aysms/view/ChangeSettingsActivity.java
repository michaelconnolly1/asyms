package com.example.michael.aysms.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;

/**
 * Created by Michael Connolly on 25/08/2018.
 *
 *  Activity to handle Change Settings page
 */

public class ChangeSettingsActivity extends AppCompatActivity {
    private EditText passcode, passcode1, passcode2, answer1, answer2, answer3;
    private TextView settings_message;
    private String currentPasscode, currentAnswer1, currentAnswer2, currentAnswer3;
    private Button mSaveButton;
    App myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_settings);

        myApp = (App)getApplicationContext();
        currentPasscode = myApp.getPasscode();
        currentAnswer1 = myApp.getAnswer1();
        currentAnswer2 = myApp.getAnswer2();
        currentAnswer3 = myApp.getAnswer3();
        getPasscode(currentPasscode);

    }

    private void setUpFields(String currentAnswer1, String currentAnswer2, String currentAnswer3) {
        TextView change_passcodes, change_questions;
        passcode1 = (EditText)findViewById(R.id.passcode1);
        passcode2 = (EditText)findViewById(R.id.passcode2);
        change_passcodes = (TextView)findViewById(R.id.change_passcodes);
        change_questions = (TextView)findViewById(R.id.change_questions);
        answer1 = (EditText)findViewById(R.id.passcode_question1);
        answer2 = (EditText)findViewById(R.id.passcode_question2);
        answer3 = (EditText)findViewById(R.id.passcode_question3);
        answer1.setVisibility(View.VISIBLE);
        answer2.setVisibility(View.VISIBLE);
        answer3.setVisibility(View.VISIBLE);
        answer1.setText(currentAnswer1);
        answer2.setText(currentAnswer2);
        answer3.setText(currentAnswer3);
        passcode1.setVisibility(View.VISIBLE);
        passcode2.setVisibility(View.VISIBLE);
        change_passcodes.setVisibility(View.VISIBLE);
        change_questions.setVisibility(View.VISIBLE);
    }

    public void getPasscode(String currentPasscode) {
        passcode = (EditText)findViewById(R.id.passcode);
        settings_message = (TextView)findViewById(R.id.change_settings_message);
        mSaveButton = (Button) findViewById(R.id.saveButton);
        mSaveButton.setVisibility(View.GONE);
        passcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("OnTextChanged:", "Changing text to " + s.toString());
                if (currentPasscode.equals(s.toString())) {
                    setUpFields(currentAnswer1, currentAnswer2, currentAnswer3);
                    getSettings();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private boolean hasText(EditText field) {
        String text = field.getText().toString().trim();
        return (!(text.length() == 0));
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

    public void getSettings() {
        passcode.setVisibility(View.GONE);
        settings_message.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.VISIBLE);
        mSaveButton.setOnClickListener((View view) -> {
            boolean error = false;
            if (hasText(passcode1) || hasText(passcode2)) {
                error = true;
                if (passcodeIsNumber(passcode1) && passcodeIsNumber(passcode2)) {
                    if (!passcode1.getText().toString().equals(passcode2.getText().toString())) {
                        passcode1.setError("Passcodes not the same");
                        passcode1.requestFocus();
                    } else {
                        error = false;
                        myApp.setPasscodeSet(true);
                        myApp.setPasscode(passcode1.getText().toString());
                    }
                }
            }

            if (!error && hasText(answer1) && hasText(answer2) && hasText(answer3)) {
                // now set up the preferences and finish and go to main activity
                if (!currentAnswer1.equals(answer1.getText().toString()))
                    myApp.setAnswer1(answer1.getText().toString());
                if (!currentAnswer2.equals(answer2.getText().toString()))
                    myApp.setAnswer2(answer2.getText().toString());
                if (!currentAnswer3.equals(answer3.getText().toString()))
                    myApp.setAnswer3(answer3.getText().toString());

                finishActivity();
            }
            else {
                EditText errorField;
                if (!hasText(answer1))
                    errorField = answer1;
                else if (!hasText(answer2))
                    errorField = answer2;
                else
                    errorField = answer3;
                errorField.setError("Field cannot be blank");
                errorField.requestFocus();
            }
        });
    }

    private void finishActivity() {
        Intent intent = new Intent(ChangeSettingsActivity.this, MainActivity.class);
        intent.putExtra("CHANGESETTINGS", true);
        intent.putExtra("message", "Details updated");
        startActivity(intent);
        finish();
    }

}
