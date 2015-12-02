package com.littlesword.ozbargain.model;

import java.io.Serializable;

/**
 * Created by kongw1 on 28/11/15.
 */
public class Comment implements Serializable{
    public String username;
    public String content = "";
    public String timestamp;
    public Comment comment;
}
