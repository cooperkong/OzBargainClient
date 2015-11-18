package com.littlesword.ozbargain.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongw1 on 15/11/15.
 */
public class BargainMenuRecyclerViewAdapter extends RecyclerView.Adapter<BargainMenuRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Bargain> mBargains;
    private Pattern pattern = Pattern.compile("\\$[0-9]+(.[0-9][0-9]?)?");

    public BargainMenuRecyclerViewAdapter(ArrayList<Bargain> bargains){
        this.mBargains = bargains;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bargain_menu_list_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImage.setImageURI(Uri.parse(mBargains.get(position).image));
        String desc = mBargains.get(position).descriptoin;
        Matcher matcher = pattern.matcher(mBargains.get(position).descriptoin);

        // Find all matches
        while (matcher.find()) {
            // Get the matching string
            String match = matcher.group();
            desc = desc.replace(match, "<font color=\"#aa3311\">" + match + "</font>");
        }
        holder.mDesc.setText(Html.fromHtml(desc));

        holder.mDownVote.setText(mBargains.get(position).downVote + " -");
        holder.mUpVote.setText(mBargains.get(position).upVote + " +");
        holder.mSubmitted.setText(mBargains.get(position).submittedOn.replace("on","").replace("-",""));
    }

    @Override
    public int getItemCount() {
        return mBargains.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.menu_item_description)
        TextView mDesc;
        @Bind(R.id.menu_item_image)
        SimpleDraweeView mImage;
        @Bind(R.id.menu_item_upvote)
        TextView mUpVote;
        @Bind(R.id.menu_item_downvote)
        TextView mDownVote;
        @Bind(R.id.menu_item_timestamp)
        TextView mSubmitted;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
