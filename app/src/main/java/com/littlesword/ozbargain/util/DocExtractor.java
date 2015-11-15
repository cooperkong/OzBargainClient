package com.littlesword.ozbargain.util;

import com.littlesword.ozbargain.model.Bargain;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by kongw1 on 14/11/15.
 */
public class DocExtractor {

    private static final String BODY = "body";
    private static final String LINK = "a";
    private static final String LIST = "ul";
    private static final String DIV = "div";
    private static final String CONTENT = "content";
    private static final String CLASS = "class";
    private static final String NODE_ATTRIBUTE = "node node-ozbdeal node-teaser";
    private static final String HEADER = "header2nd";//sub header, eg, "Electronic..."

    /**
     * return the list of categories.
     * @param doc
     * @return
     */
    public static Observable<String> getCategories(Document doc){
        List<String> ret = new ArrayList<>();
        Element headers = doc.getElementById(HEADER);
        Elements uls = headers.getElementsByTag(LIST);
        for(Element ui : uls.get(0).children())//assume ul tag only appear at index 0 in headers2nd body tag.
            ret.add(ui.getElementsByTag(LINK).text());
        return Observable.from(ret);

    }


    public static ArrayList<Bargain> getBargainItems(Document doc){
        ArrayList<Bargain> ret = new ArrayList<>();
        Element a = doc.getElementById(CONTENT);
        Elements v = a.getElementsByAttributeValueContaining(CLASS, NODE_ATTRIBUTE);
        for(Element node : v){
            Elements right = node.getElementsByAttributeValueContaining(CLASS, "n-right");
            for(Element r : right) {
                Elements href = r.getElementsByAttributeValueContaining(CLASS, "foxshot-container");
                Elements img = href.get(0).getElementsByTag("img");
                Bargain b = new Bargain();
                b.image = img.attr("src");
                ret.add(b);
            }
        }
        return ret;
    }
}
