package com.littlesword.ozbargain.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kongw1 on 1/12/15.
 */
public class CommentView extends LinearLayout {
    private String submitted;
    private String content;
    private Context mContext;

    public CommentView(Context context, String submitted, String content) {
        super(context);
        this.submitted = submitted;
        this.content = content;
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        init();
    }

    private void init() {
        addView(authorView());
        addView(contentView());
    }

    private View contentView() {
        TextView s = new TextView(mContext);
        s.setTextSize(12);
        s.setText(content);
        return s;
    }

    private View authorView() {
        TextView s = new TextView(mContext);
        s.setTextSize(14);

        //color the author name
        String name = submitted.substring(0, submitted.indexOf(" "));
        String formatted = "<font color='#112288'>" + name + "</font>" + submitted.substring(submitted.indexOf(" "));
        s.setText(Html.fromHtml(formatted));
        return s;
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
