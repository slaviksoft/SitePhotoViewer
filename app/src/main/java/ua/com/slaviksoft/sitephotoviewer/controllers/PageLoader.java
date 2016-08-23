package ua.com.slaviksoft.sitephotoviewer.controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import ua.com.slaviksoft.sitephotoviewer.model.CustomPage;

/**
 * Created by Slavik on 19.08.2016.
 */
public class PageLoader<T> extends AsyncTask<T, Void, T>{

    private PageLoadeListener listener;

    private void log(String text){
        Log.d("DEBUG", text);
    }

    @Override
    protected T doInBackground(T... params) {

        T page = params[0];

        String url = ((CustomPage)page).getUrl();
        try {
            Document document = Jsoup.connect(url).get();
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
        if (listener!=null) listener.onPageLoaded(((CustomPage)page));
    }

    public void setListener(PageLoadeListener listener) {
        this.listener = listener;
    }
}


