package ua.com.slaviksoft.sitephotoviewer.controllers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Slavik on 01.09.2016.
 */
public class PinChecker {

    private static String PIN = "PIN";

    private final Context context;
    private final SharedPreferences preferences;

    public PinChecker(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("main", Context.MODE_PRIVATE);
    }

    public boolean isPinSet(){
        return !preferences.getString(PIN, "").equals("");
    }

    public boolean check(String pin){
        String storedPin = preferences.getString(PIN, "");
        return pin.equals(storedPin);
    }

    public void setPIN(String pin){
        preferences.edit().putString(PIN, pin).commit();
    }

}
