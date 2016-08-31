package ua.com.slaviksoft.sitephotoviewer.helpers;

import android.app.ProgressDialog;
import android.content.Context;

import ua.com.slaviksoft.sitephotoviewer.R;

/**
 * Created by Slavik on 31.08.2016.
 */
public class LoadProgress extends ProgressDialog{

    public LoadProgress(Context context) {
        super(context);

        this.setIndeterminate(true);
        this.setCancelable(true);
        this.setMessage(context.getString(R.string.message_downloading));
        this.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    public void start(){
        this.show();
    }

    public void stop(){
        this.dismiss();
    }

}
