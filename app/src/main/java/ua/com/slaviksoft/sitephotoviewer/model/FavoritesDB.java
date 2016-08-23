package ua.com.slaviksoft.sitephotoviewer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Slavik on 22.08.2016.
 */
public class FavoritesDB extends SQLiteOpenHelper {

    private final static String TABLE_URLS = "urls";

    private final static String FIELD_HEADER = "header";
    private final static String FIELD_TITLE = "title";
    private final static String FIELD_URL = "url";
    private final static String FIELD_URL_IMG = "urlimg";

    public FavoritesDB(Context context) {
        super(context, "favorites", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_URLS+" ("
                + FIELD_URL+" text primary key,"
                + FIELD_TITLE+" text,"
                + FIELD_HEADER+" text,"
                + FIELD_URL_IMG+" urlimg text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void remove(PageItem item){
        if (!find(item)) return;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_URLS, FIELD_URL+"=?", new String[]{item.getUrl()});
    }

    public void add(PageItem item){

        if (find(item)) return;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_TITLE, item.getTitle());
        values.put(FIELD_HEADER, item.getHeader());
        values.put(FIELD_URL, item.getUrl());
        values.put(FIELD_URL_IMG, item.getUrlImg());

        db.insert(TABLE_URLS, null, values);
        db.close();

        Log.d("DEBUG", "Item added. "+item.getUrl());
    }

    public boolean find(PageItem item){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_URLS, new String[] {FIELD_TITLE, FIELD_URL, FIELD_URL_IMG }, FIELD_URL + "=?", new String[] { item.getUrl() }, null, null, null, null);
        boolean result = cursor.getCount() > 0;
        db.close();

        Log.d("DEBUG", "find = "+result+" for "+item.getUrl());
        return result;
    }

    public ArrayList<PageItem> query() {

        ArrayList<PageItem> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_URLS, new String[]{FIELD_HEADER, FIELD_TITLE, FIELD_URL, FIELD_URL_IMG}, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String header = cursor.getString(cursor.getColumnIndex(FIELD_HEADER));
                String title = cursor.getString(cursor.getColumnIndex(FIELD_TITLE));
                String url = cursor.getString(cursor.getColumnIndex(FIELD_URL));
                String urlImg = cursor.getString(cursor.getColumnIndex(FIELD_URL_IMG));

                PageItem item = new PageItem(header, header, url, urlImg);
                list.add(item);

            } while (cursor.moveToNext());

            db.close();

        }

        return list;
    }

}
