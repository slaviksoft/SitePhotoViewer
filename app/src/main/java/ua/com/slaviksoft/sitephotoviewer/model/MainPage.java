package ua.com.slaviksoft.sitephotoviewer.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slavik on 19.08.2016.
 */
public class MainPage extends CustomPage {

    private List<PageItem> items = new ArrayList<>();

    @Override
    public void parse(Document document) {

        Elements elementsFigure = document.select("figure");
        for(Element elementFigure: elementsFigure) {


            Elements elementsRef = elementFigure.select("div[id^=news-id] > a");
            Elements elementsImg = elementsRef.first().select("img");

            String imgRef = elementsImg.first().attr("src");
            String pageRef = elementsRef.first().attr("href");

            // vid-1 is tiles view - that's we need
            pageRef = pageRef.replace("vid-3", "vid-1");

            Elements elementsTitleRef = elementFigure.select("figcaption > h3 > a");
            String title = elementsTitleRef.first().attr("title");

            PageItem item = new PageItem(title, title, pageRef, imgRef);
            items.add(item);

        }

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
}

//    <figure>
//        <div id='news-id-72593'>
//            <a href="http://myass.ru/72593-viola-bailey/vid-3.html" target="_blank">
//                <img src="http://myass.ru/i/201608/14/m/20160857b0c2b8aebb2.jpg" />
//            </a>
//        </div>
//        <figcaption>
//            <h3>
//                <a href="http://myass.ru/72593-viola-bailey/vid-2.html" title="Viola Bailey">Viola Bailey</a>
//            </h3>
//            <h5>�����, 07:01</h5>
//            <noindex>
//                <div class="vid">
//                    <span style="float:right">
//                        <a href="javascript:;" onclick="ratePM(1,72593)"><span id="rate2-layer-1-72593">+16</span></a> <a href="javascript:;" onclick="ratePM(2,72593)"><span id="rate2-layer-2-72593">-2</span></a>
//                    </span>
//                    <span style="float:left">
//                    <a href="http://myass.ru/72593-viola-bailey/vid-2.html">&equiv;</a> <a href="http://myass.ru/72593-viola-bailey/vid-3.html">&hArr;</a> <a href="http://myass.ru/72593-viola-bailey/vid-1.html">&#9641;</a>
//                    </span>
//                </div>
//            </noindex>
//        </figcaption>
//    </figure>
