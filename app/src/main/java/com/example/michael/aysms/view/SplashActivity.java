package com.example.michael.aysms.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.michael.aysms.App;

public class SplashActivity extends AppCompatActivity {
    private App mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApp = (App)getApplicationContext();
        boolean loggedInPreviously = mApp.getLoggedIn();
        boolean setUpPasscodePreviously = mApp.getPasscodeSet();
        Log.d("SPLASH", loggedInPreviously + " " + setUpPasscodePreviously);

        if (!loggedInPreviously) {
            if (!setUpPasscodePreviously) {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(this, PasscodeActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            if (!setUpPasscodePreviously) {
                Intent intent = new Intent(this, SetSettingsActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, PasscodeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
