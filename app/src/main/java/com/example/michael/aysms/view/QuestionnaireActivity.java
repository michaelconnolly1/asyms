package com.example.michael.aysms.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.AysmsDate;
import com.example.michael.aysms.Utils.DatePickerFragment;
import com.example.michael.aysms.model.FeelingSick;
import com.example.michael.aysms.model.FeelingSickEntity;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Michael Connolly on 15/08/2018.
 *
 *  Activity to handle Questionnaire selection page for patient
 */

public class QuestionnaireActivity extends AppCompatActivity {
    private App myApp;
    private FeelingSick myFSModel;
    private List<FeelingSickEntity> records;
    private LinearLayout.LayoutParams layoutParams;
    private LinearLayout ll;
    DialogFragment newFragment;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        myApp = (App)getApplicationContext();
        myFSModel = new FeelingSick(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(150, 20, 150, 20); // (left, top, right, bottom)
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        ll = (LinearLayout) findViewById(R.id.linearlayout);

        newFragment = DatePickerFragment.newInstance(myDateListener, this);
        newFragment.show(getFragmentManager(), "Date Picker");
    }

    private void addButton(int index, String recordDate) {
        Button dateButton = new Button(this);
        dateButton.setLayoutParams(layoutParams);
        dateButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button));
        dateButton.setPadding(50, 35, 50, 35);
        dateButton.setTextColor(ContextCompat.getColor(this, R.color.white));
        dateButton.setText(recordDate);
        dateButton.setId(index);
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Click", Integer.toString(v.getId()));
                Intent i = new Intent(QuestionnaireActivity.this, ViewQuestionnaireActivity.class);
                i.putExtra("DATE", records.get(v.getId()).getDateTime().getTime());
                startActivity(i);
            }
        });
        ll.addView(dateButton);
    }


    public DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String dayString = Integer.toString(day);
            if (dayString.length() == 1)
                dayString = '0' + dayString;
            String monthString = Integer.toString(month + 1);
            if (monthString.length() == 1)
                monthString = '0' + monthString;

            StringBuilder stringDate = new StringBuilder().append(dayString).append("/").append(monthString).append("/").append(year);
            Log.d("Date:", stringDate.toString());
            records = myFSModel.getFSRecords(new AysmsDate().convertStringToDate(stringDate.toString()));

            if (records.size() > 0) {

                for (int index = 0; index < records.size(); index++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(records.get(index).getDateTime());
                    int hours = cal.get(Calendar.HOUR_OF_DAY);
                    int minutes = cal.get(Calendar.MINUTE);
                    String mins = Integer.toString(minutes);
                    if (minutes < 10)
                        mins = "0" + mins;
                    addButton(index, hours + ":" + mins);
                }
            }
            else {
                reshowDatePicker();
            }
        }
    };

    private void reshowDatePicker() {
        myDialog = new Dialog(this, R.style.myDialogTheme);
        myDialog.setContentView(R.layout.layoutdialog);
        myDialog.setTitle("No questionnaires");
        TextView messageText = (TextView)myDialog.findViewById(R.id.messageText);
        messageText.setText("Do you wish to choose a different date?");
        myDialog.show();
    }

    public void yesNoClick(View view) {
        myDialog.dismiss();
        switch (view.getId()) {
            case R.id.noButton:
                Intent i = new Intent(QuestionnaireActivity.this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.yesButton:
                DialogFragment newFragment = DatePickerFragment.newInstance(myDateListener, this);
                newFragment.show(getFragmentManager(), "Date Picker");
                break;
        }
    }

}
