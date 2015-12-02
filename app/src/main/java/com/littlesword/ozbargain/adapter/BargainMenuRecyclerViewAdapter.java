package com.littlesword.ozbargain.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.util.CommonUtil;
import com.littlesword.ozbargain.view.CategoryFragment;
import com.littlesword.ozbargain.view.onBargainItemClicklistener;

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
    private onBargainItemClicklistener listener;

    public BargainMenuRecyclerViewAdapter(ArrayList<Bargain> bargains, CategoryFragment listener){
        this.mBargains = bargains;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bargain_menu_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(
                l -> listener.onBargainClicked(mBargains.get(position))
        );
        holder.mImage.setImageURI(Uri.parse(mBargains.get(position).image));
        String desc = mBargains.get(position).title;
        holder.mDesc.setText(Html.fromHtml( CommonUtil.applyColorToString(desc)));
        holder.mExp.setText(expireoutofstockText(mBargains.get(position), holder.mExp));
        holder.mDownVote.setText(mBargains.get(position).downVote + " -");
        holder.mUpVote.setText(mBargains.get(position).upVote + " +");
        holder.mTag.setText(mBargains.get(position).tag);
        holder.mSubmitted.setText(mBargains.get(position).submittedOn.replace("on","").replace("-",""));

    }

    private String expireoutofstockText(Bargain bargain, TextView mExp) {
        if(bargain.isExpired) {
            mExp.setVisibility(View.VISIBLE);
            return "EXPIRED";
        }
        else if(bargain.isOutofstock) {
            mExp.setVisibility(View.VISIBLE);
            return "OUT OF STOCK";
        }
        else {
            mExp.setVisibility(View.GONE);
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return mBargains.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.menu_item_description)
        TextView mDesc;
        @Bind(R.id.menu_item_expired)
        TextView mExp;
        @Bind(R.id.menu_item_image)
        SimpleDraweeView mImage;
        @Bind(R.id.menu_item_upvote)
        TextView mUpVote;
        @Bind(R.id.menu_item_downvote)
        TextView mDownVote;
        @Bind(R.id.menu_item_timestamp)
        TextView mSubmitted;
        @Bind(R.id.menu_item_tag)
        TextView mTag;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
