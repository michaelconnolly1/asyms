package com.example.michael.aysms.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.Constants;

/**
 * Created by Michael Connolly on 1/09/2018.
 *
 *  Activity to handle Passcode page
 */

public class PasscodeActivity extends AppCompatActivity {
    private StringBuilder mPasscode;
    private String actualPasscode;
    private int mMaskNumber;
    private TextView mask1, mask2, mask3, mask4;
    private App myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        myApp = (App)getApplicationContext();
        actualPasscode = myApp.getPasscode();

        mPasscode = new StringBuilder();
        mMaskNumber = 1;
        mask1 = (TextView) findViewById(R.id.mask1);
        mask2 = (TextView) findViewById(R.id.mask2);
        mask3 = (TextView) findViewById(R.id.mask3);
        mask4 = (TextView) findViewById(R.id.mask4);
    }

    public void passcodeOnClick(View view) {
        Character code = '*';
        boolean clear = false;
        switch (view.getId()) {
            case R.id.button0:
                code = '0';
                break;
            case R.id.button1:
                code = '1';
                break;
            case R.id.button2:
                code = '2';
                break;
            case R.id.button3:
                code = '3';
                break;
            case R.id.button4:
                code = '4';
                break;
            case R.id.button5:
                code = '5';
                break;
            case R.id.button6:
                code = '6';
                break;
            case R.id.button7:
                code = '7';
                break;
            case R.id.button8:
                code = '8';
                break;
            case R.id.button9:
                code = '9';
                break;
            case R.id.buttonclr: {
                mPasscode.setLength(0);
                clearMask(mMaskNumber);
                mMaskNumber = 1;
                clear = true;
            }
        }

        if (!clear) {
            mPasscode.append(code);
            setMask(mMaskNumber);
            if (mMaskNumber == 4) {
                boolean match = checkPasscode();
                if (!match) {
                    mPasscode.setLength(0);
                    clearMask(mMaskNumber);
                    mMaskNumber = 1;
                }
            }
            else
                mMaskNumber++;
        }
    }

    private boolean checkPasscode() {
        boolean match = false;
        if (actualPasscode.equals(mPasscode.toString())) {
            myApp.setPasscodeEntered(true);
            if (myApp.getRoleID() == Constants.ROLEID_PATIENT) {
                Intent intent = new Intent(PasscodeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(PasscodeActivity.this, ClinicianMainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        return match;
    }

    private void clearMask(int mMasks) {
        for (int index = 1; index <= mMasks; index++) {
            if (index == 1)
                mask1.setText("");
            else if (index == 2)
                mask2.setText("");
            else if (index == 3)
                mask3.setText("");
            else
                mask4.setText("");
        }
    }

    private void setMask(int mMaskNumber) {
        if (mMaskNumber == 1)
            mask1.setText("*");
        else if (mMaskNumber == 2)
            mask2.setText("*");
        else if (mMaskNumber == 3)
            mask3.setText("*");
        else
            mask4.setText("*");
    }
}
