package com.example.vortexcar;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Helper {

    public static String convertDateFormat(String inputDateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(inputDateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e("error covert date",e.getMessage());
            return "2020-1-1";
        }
    }
}
