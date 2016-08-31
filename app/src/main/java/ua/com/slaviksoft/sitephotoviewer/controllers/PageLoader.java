package ua.com.slaviksoft.sitephotoviewer.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import ua.com.slaviksoft.sitephotoviewer.helpers.LoadProgress;
import ua.com.slaviksoft.sitephotoviewer.model.CustomPage;

/**
 * Created by Slavik on 19.08.2016.
 */
public class PageLoader<T> extends AsyncTask<T, Void, T>{

    private PageLoadeListener listener;
    //private ProgressDialog progressDialog;
    private LoadProgress progressDialog;
    private Context context;

    private void log(String text){
        Log.d("DEBUG", text);
    }

    public PageLoader(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new LoadProgress(context);
        progressDialog.start();
    }

    @Override
    protected T doInBackground(T... params) {

        T page = params[0];

        String url = ((CustomPage)page).getUrl();
        try {
            Document document = Jsoup.connect(url).timeout(20000).get();
            ((CustomPage)page).parse(document);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return page;
    }

    @Override
    protected void onPostExecute(T page) {
        super.onPostExecute(page);
        Log.d("DEBUG", "Loaded");
        progressDialog.stop();
        if (listener!=null) listener.onPageLoaded(((CustomPage)page));
    }

    public void setListener(PageLoadeListener listener) {
        this.listener = listener;
    }
}


