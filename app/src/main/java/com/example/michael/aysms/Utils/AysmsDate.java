package com.example.michael.aysms.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by laptop on 08/08/2018.
 */

public class AysmsDate {

    private Date date;

    public AysmsDate() {
        date = new Date();
    }

    public AysmsDate(Date date) {
        this.date = date;
    }

    public Date convertStringToDate(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = dateFormat.parse(stringDate);

        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    public Date convertStringToTime(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh;mm");
        try {
            date = dateFormat.parse(stringDate);

        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    public Date dateWithoutTime(Date date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateWithoutTime = new Date();
        try {
            dateWithoutTime = dateformat.parse(dateformat.format(date));
            Log.d("dateWithoutTime", dateWithoutTime.toString());
        }
        catch (ParseException e) {
            Log.d("dateWithoutTime", "date conversion error");
        }
        return dateWithoutTime;
    }

    public Date dateWithTime() {
        SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm");
        Date dateWithTime = date;
        try {
            dateWithTime = dateformat.parse(dateformat.format(date));
            Log.d("dateWithoutTime", dateWithTime.toString());
        }
        catch (ParseException e) {
            Log.d("dateWithoutTime", "date conversion error");
        }
        return dateWithTime;
    }

    public Date convertDBStringToDate(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(stringDate);

        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }
    public String convertDateToString(Date date) {
        SimpleDateFormat ddMMYYYYformat = new SimpleDateFormat("dd/MM/yyyy");
        return ddMMYYYYformat.format(date);
    }

    public Date setDateNoTime(int day, int month, int year) {
        Date newDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        newDate = cal.getTime();
        return newDate;
    }
}
