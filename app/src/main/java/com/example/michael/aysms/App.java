package com.example.michael.aysms;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.DaoMaster;
import com.example.michael.aysms.model.DaoSession;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.greendao.database.Database;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Michael on 28/07/2018.
 */

public class App extends Application {
    public static final boolean ENCRYPTED = false;
    private static final String KEYSTORE = "AYSMSApp";
    private static final String LOGGEDIN = "LoggedIn";
    private static final String TOKEN = "token";
    private static final String ROLEID = "roleID";
    private static final String PASSCODE = "Passcode";
    private static final String PASSCODESET = "PasscodeSet";
    private static final String PASSCODEENTERED = "PasscodeEntered";
    private static final String ANSWER1 = "Answer1";
    private static final String ANSWER2 = "Answer2";
    private static final String ANSWER3 = "Answer3";
    private static final String LATESTQID = "LATESTQID";

    private DaoSession daoSession;
    private SharedPreferences sharedPref;
    private Date startDate;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "aysms-app-db-encrypted" : "chemo-app-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        sharedPref = getApplicationContext().getSharedPreferences(KEYSTORE, Context.MODE_PRIVATE);
        setPasscodeEntered(false);
        clearQuestionnairePages();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("firebase", "Token: " + refreshedToken);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public boolean getSharedPreferencesBoolean(String field, boolean defaultValue) {
        return sharedPref.getBoolean(field, defaultValue);
    }

    public void setSharedPreferencesBoolean(String field, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(field, value);
        editor.commit();
    }

    public int getSharedPreferencesInt(String field, int defaultValue) {
        return sharedPref.getInt(field, defaultValue);
    }

    public void setSharedPreferencesInt(String field, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(field, value);
        editor.commit();
    }

    public String getSharedPreferencesString(String field, String defaultValue) {
        return sharedPref.getString(field, defaultValue);
    }

    public void setSharedPreferencesString(String field, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(field, value);
        editor.commit();
    }

    public boolean getPasscodeEntered() { return getSharedPreferencesBoolean(PASSCODEENTERED, false); }

    public void setPasscodeEntered(boolean value) { setSharedPreferencesBoolean(PASSCODEENTERED, value);}

    public void startQuestionnaire() {
        Calendar calendar = Calendar.getInstance();
        startDate = calendar.getTime();
        clearQuestionnairePages();
    }

    public Date getQuestionnaireStartDate() {
        return startDate;
    }

    private void clearQuestionnairePages() {
        setSharedPreferencesBoolean(Constants.FEELINGSICK, false);
        setSharedPreferencesBoolean(Constants.BEINGSICK, false);
        setSharedPreferencesBoolean(Constants.DIARRHOEA, false);
        setSharedPreferencesBoolean(Constants.CONSTIPATION, false);
        setSharedPreferencesBoolean(Constants.EATINGANDDRINKING, false);
        setSharedPreferencesBoolean(Constants.EATINGANDDRINKINGB, false);
        setSharedPreferencesBoolean(Constants.SIGNSOFINFECTION, false);
        setSharedPreferencesBoolean(Constants.CLEXANE, false);
        setSharedPreferencesBoolean(Constants.ACTIVITYLEVELS, false);
        setSharedPreferencesBoolean(Constants.TIREDNESS, false);
        setSharedPreferencesBoolean(Constants.PAIN, false);
        setSharedPreferencesBoolean(Constants.BODYSELECT, false);
        setSharedPreferencesBoolean(Constants.HOWDOYOUFEEL, false);
        setSharedPreferencesBoolean(Constants.OTHERPROBS, false);
    }

    public boolean getFeelingSick() {
        return getSharedPreferencesBoolean(Constants.FEELINGSICK, false);
    }

    public boolean getBeingSick() {
        return getSharedPreferencesBoolean(Constants.BEINGSICK, false);
    }

    public boolean getDiarrhoea() {
        return getSharedPreferencesBoolean(Constants.DIARRHOEA, false);
    }

    public boolean getConstipation() {
        return getSharedPreferencesBoolean(Constants.CONSTIPATION, false);
    }

    public boolean getEatingAndDrinking() {
        return getSharedPreferencesBoolean(Constants.EATINGANDDRINKING, false);
    }

    public boolean getEatingAndDrinkingB() {
        return getSharedPreferencesBoolean(Constants.EATINGANDDRINKINGB, false);
    }

    public boolean getSignsOfInfection() {
        return getSharedPreferencesBoolean(Constants.SIGNSOFINFECTION, false);
    }

    public boolean getClexane() {
        return getSharedPreferencesBoolean(Constants.CLEXANE, false);
    }

    public boolean getActivityLevels() {
        return getSharedPreferencesBoolean(Constants.ACTIVITYLEVELS, false);
    }

    public boolean getTiredness() {
        return getSharedPreferencesBoolean(Constants.TIREDNESS, false);
    }

    public boolean getPain() {
        return getSharedPreferencesBoolean(Constants.PAIN, false);
    }

    public boolean getHowDoYouFeel() {
        return getSharedPreferencesBoolean(Constants.HOWDOYOUFEEL, false);
    }

    public boolean getOtherProbs() {
        return getSharedPreferencesBoolean(Constants.OTHERPROBS, false);
    }

    public boolean getBodySelect() {
        return getSharedPreferencesBoolean(Constants.BODYSELECT, false);
    }

    public boolean isLoggedIn() {
        return getSharedPreferencesBoolean(LOGGEDIN, false);
    }

    public void setLoggedIn() {
        setSharedPreferencesBoolean(LOGGEDIN, true);
    }

    public boolean getLoggedIn() {
        return getSharedPreferencesBoolean(LOGGEDIN, false);
    }

    public void setLoggedOut() {
        setSharedPreferencesBoolean(LOGGEDIN, false);
    }

    public String getToken() {
        return getSharedPreferencesString(TOKEN, "");
    }

    public void setToken(String token) {
        setSharedPreferencesString(TOKEN, token);
    }

    public String getPasscode() {
        return getSharedPreferencesString(PASSCODE, "");
    }

    public void setPasscode(String passcode) {
        setSharedPreferencesString(PASSCODE, passcode);
    }

    public boolean getPasscodeSet() { return getSharedPreferencesBoolean(PASSCODESET, false); }

    public void setPasscodeSet(boolean value) { setSharedPreferencesBoolean(PASSCODESET, value);}

    public String getAnswer1() {
        return getSharedPreferencesString(ANSWER1, "");
    }

    public void setAnswer1(String answer1) {
        setSharedPreferencesString(ANSWER1, answer1);
    }

    public String getAnswer2() {
        return getSharedPreferencesString(ANSWER2, "");
    }

    public void setAnswer2(String answer2) {
        setSharedPreferencesString(ANSWER2, answer2);
    }

    public String getAnswer3() {
        return getSharedPreferencesString(ANSWER3, "");
    }

    public void setAnswer3(String answer3) {
        setSharedPreferencesString(ANSWER3, answer3);
    }

    public void setRoleID(int roleID) {setSharedPreferencesInt(ROLEID, roleID);}

    public int getRoleID() { return getSharedPreferencesInt(ROLEID, 0); }

    public void setLatestQuestionnaireID(int ID) { setSharedPreferencesInt("LATESTQID", ID); }

    public int getLatestQuestionnaireID() { return getSharedPreferencesInt("LATESTQID", 0); }
}