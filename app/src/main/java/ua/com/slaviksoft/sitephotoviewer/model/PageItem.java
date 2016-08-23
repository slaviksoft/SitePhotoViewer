package ua.com.slaviksoft.sitephotoviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Slavik on 17.08.2016.
 */
public class PageItem implements Parcelable{

    public static String EXTRA_NAME = "PAGE_ITEM";
    public static String EXTRA_LIST_NAME = "LIST_PAGE_ITEM";
    public static String EXTRA_LOAD_FROM_DB = "PAGE_ITEM_LOAD_FROM_DB";

    private String title;
    private String url;
    private String urlImg;
    private String header;

    protected PageItem(Parcel in) {
        title = in.readString();
        url = in.readString();
        urlImg = in.readString();
        header = in.readString();
    }

    public static final Creator<PageItem> CREATOR = new Creator<PageItem>() {
        @Override
        public PageItem createFromParcel(Parcel in) {
            return new PageItem(in);
        }

        @Override
        public PageItem[] newArray(int size) {
            return new PageItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getHeader() {
        return header;
    }

    public PageItem(String header, String title, String url, String urlImg){
        this.title = title;
        this.url = url;
        this.urlImg = urlImg;
        this.header = header;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(urlImg);
        dest.writeString(header);
    }
}
