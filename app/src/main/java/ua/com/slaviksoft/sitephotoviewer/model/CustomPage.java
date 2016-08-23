package ua.com.slaviksoft.sitephotoviewer.model;

import org.jsoup.nodes.Document;

/**
 * Created by Slavik on 19.08.2016.
 */
public abstract class CustomPage {

    protected String url;

    public abstract void parse(Document document);
    public abstract String getUrl();
    public abstract void setUrl(String url);


}
