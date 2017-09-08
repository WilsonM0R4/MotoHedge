package com.simercom.motohedge.modules.utils;

import android.app.Activity;
;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wmora on 25/05/17.
 */

public class Util {

    public static boolean stringValidation(String str){
        return (str != null && !str.isEmpty());
    }

    public static String formatDateForLocal(String date){
        if(date.contains("-")){
            date = date.replace("-", "/");
        }
        return date;
    }

    public static String formatDateForCloud(String date){
        if(date.contains("/")){
            date = date.replace("/", "-");
        }
        return date;
    }

    public static String getCurrentDate(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getCurrentUser(Activity context){
        return context.getSharedPreferences(Constants.PREFERENCES, 0).getString(Constants.USER, null);
    }
}
