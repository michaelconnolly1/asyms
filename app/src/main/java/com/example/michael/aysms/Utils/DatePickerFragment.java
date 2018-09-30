package com.example.michael.aysms.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.widget.DatePicker;
import com.example.michael.aysms.R;
import com.example.michael.aysms.view.MainActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment  {

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Activity activity;
    private Date mTag;


    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener onDateSetListener, Activity activity) {
        DatePickerFragment pickerFragment = new DatePickerFragment();
        pickerFragment.setOnDateSetListener(onDateSetListener);
        pickerFragment.setActivity(activity);
        return pickerFragment;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }


    private void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
        this.onDateSetListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        if (getArguments() != null) {
            mTag = (Date) getArguments().getSerializable("dateTag");
            Log.d("DatePickerFragment:", mTag.toString());
            c.setTime(mTag);
        }
        else
            Log.d("DatePickerFragment:", "Tag is NULL");
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Log.d("DatePickerFragment:", Integer.toString(day) + " " + Integer.toString(month) + " " + Integer.toString(year));
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.myDialogTheme, onDateSetListener, year, month, day);
        setCancelable(false);
        return dialog;
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("Cancel Clicked", "CANCELLED CLICKED");
        Intent i = new Intent(activity, MainActivity.class);
        startActivity(i);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        Log.d("DatePickerDialog:", "Called onDate Set" + day + " " + month + " " + year);
        //       mListener.onComplete(new StringBuilder().append(day).append("/").append(month+1).append("/").append(year).toString());
    }


}

