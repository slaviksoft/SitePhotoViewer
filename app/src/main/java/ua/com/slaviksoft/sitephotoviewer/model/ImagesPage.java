package ua.com.slaviksoft.sitephotoviewer.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slavik on 20.08.2016.
 */
public class ImagesPage extends CustomPage {

    private List<PageItem> items = new ArrayList<>();
    private String header;

    @Override
    public void parse(Document document) {

        Elements elementsBox = document.select("div.box");
        for(Element elementBox: elementsBox) {

            String title = elementBox.text();

            Elements elementsImageRef = elementBox.select("a");
            for(Element elementImageRef: elementsImageRef) {
                String pageRef = elementImageRef.attr("href");

                Element elementImg = elementImageRef.select("img").first();
                String imgRef = elementImg.attr("src");

                PageItem item = new PageItem(header, title, pageRef, imgRef);
                items.add(item);

            }
        }

    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    public List<PageItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<PageItem> items){
        this.items = items;
    }

}
