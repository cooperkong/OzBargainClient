package com.littlesword.ozbargain.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongw1 on 15/11/15.
 */
public class BargainMenuRecyclerViewAdapter extends RecyclerView.Adapter<BargainMenuRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Bargain> mBargains;

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
        holder.mDesc.setText(mBargains.get(position).descriptoin);
        holder.mDownVote.setText(mBargains.get(position).downVote + " -");
        holder.mUpVote.setText(mBargains.get(position).upVote + " +");
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

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
