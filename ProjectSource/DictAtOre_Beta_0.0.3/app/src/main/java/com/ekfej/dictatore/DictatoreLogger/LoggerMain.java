package com.ekfej.dictatore.DictatoreLogger;

/**
 * Created by Buda Viktor on 2015.12.28..
 */

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoggerMain {


public void LogError (String fgvname, Exception hiba) {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    String date = dateFormat.format(cal.getTime());
    Log.d(date + " " + fgvname, hiba.getMessage());
}

}
