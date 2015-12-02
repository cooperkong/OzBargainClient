package com.littlesword.ozbargain.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kongw1 on 15/11/15.
 */
public class Bargain implements Serializable{
    public String nodeId;
    public String image;
    public String title;
    public String upVote;
    public String downVote;
    public String submittedOn;
    public String tag;
    public String coupon;
    public String originLink;
    public boolean isExpired;
    public boolean isOutofstock;
    public ArrayList<Comment> comments;
}
