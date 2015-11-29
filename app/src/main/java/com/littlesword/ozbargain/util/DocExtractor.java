package com.littlesword.ozbargain.util;

import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.model.Comment;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
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
    private static final String IMG = "img";
    private static final String SRC = "src";
    private static final String CONTENT = "content";
    private static final String CLASS = "class";
    private static final String SPAN = "span";


//    custom classes
    private static final String ID = "id";
    private static final String NODE_ATTRIBUTE = "node node-ozbdeal node-teaser";
    private static final String CLASS_N_RIGHT = "n-right";
    private static final String CLASS_FOX_CONTAINER = "foxshot-container";
    private static final String TITLE = "title";
    private static final String SBUMITTED = "submitted";
    private static final String LINKS = "links";
    private static final String TAG = "fa-tag";
    private static final String CLASS_VOTEUP = "voteup";
    private static final String CLASS_VOTEDOWN = "votedown";
    private static final String HEADER = "header2nd";//sub header, eg, "Electronic..."

    //for comment
    private static final String COMMENT_LEVEL0 = "comment level0";
    private static final String COMMENT_WRAP = "comment wrap";
    private static final String COMMENT_LEVEL = "comment level";
    private static final String COMMENT_NRIGHT = "n-right";

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


    /**
     * Parse html list to object list
     * @param doc
     * @return
     */
    public static ArrayList<Bargain> getBargainItems(Document doc){
        ArrayList<Bargain> ret = new ArrayList<>();
        Element a = doc.getElementById(CONTENT);
        Elements v = a.getElementsByAttributeValueContaining(CLASS, NODE_ATTRIBUTE);
        for(Element node : v){
            Bargain b = new Bargain();
            Elements right = node.getElementsByAttributeValueContaining(CLASS, CLASS_N_RIGHT);
            b.nodeId = node.getElementsByTag(DIV).get(0).attr(ID).replace("node","node/");
            for(Element r : right) {
                Elements href = r.getElementsByAttributeValueContaining(CLASS, CLASS_FOX_CONTAINER);
                Elements img = href.get(0).getElementsByTag(IMG);
                b.image = img.attr(SRC);
                b.title = r.getElementsByClass(TITLE).get(0).getElementsByTag(LINK).get(0).text();
                b.tag = r.getElementsByClass(LINKS).get(0).getElementsByTag(LINK).get(0).text();
                b.submittedOn = r.getElementsByClass(SBUMITTED).get(0).textNodes().get(0).text();//index 2 is the actual timestamp
            }
            b.upVote = node.getElementsByAttributeValueContaining(CLASS, CLASS_VOTEUP).get(0).getElementsByTag(SPAN).get(0).getElementsByTag(SPAN).get(0).text();
            b.downVote = node.getElementsByAttributeValueContaining(CLASS, CLASS_VOTEDOWN).get(0).getElementsByTag(SPAN).get(0).getElementsByTag(SPAN).get(0).text();
            ret.add(b);

        }
        Collections.sort(ret,
                (lhs, rhs) -> {
                    return rhs.submittedOn.compareTo(lhs.submittedOn);
                }
        );
        return ret;
    }

    public static ArrayList<Comment> getComments(Document document){
        ArrayList<Comment> ret = new ArrayList<>();
        Elements commentLevel0 = document.getElementsByAttributeValue(CLASS, COMMENT_LEVEL0);
        for(Element element  : commentLevel0.get(0).getElementsByAttributeValue(CLASS, COMMENT_NRIGHT)){
            Comment comment = new Comment();
            Elements content = element.getElementsByAttributeValueContaining(CLASS, "content");
            Elements submitted = element.getElementsByAttributeValueContaining(CLASS, "submitted");
            for(int i=0; i < content.size(); i++){
                comment.content = content.get(i).text();
                comment.timestamp = submitted.get(i).text();
            }
            ret.add(comment);
        }

        return ret;
    }
}
