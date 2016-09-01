package ua.com.slaviksoft.sitephotoviewer.helpers;

import android.util.Log;

/**
 * Created by Slavik on 01.09.2016.
 */
public class Debuger {

    private static String TAG = "DEBUG";

    static public void log(String text){
        Log.d(TAG, text);
    }

   static public void log(Class<?> cls, String text){
        Log.d(TAG+"."+cls.getSimpleName(), text);
   }

   static public void log(Object obj, String text){
        Log.d(TAG+"."+obj.getClass().getSimpleName(), text);
   }

}
