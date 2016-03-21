package com.littlesword.ozbargain.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.categorylist.CategoryListContract;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.util.CommonUtil;
import com.littlesword.ozbargain.categorylist.CategoryFragment;
import com.littlesword.ozbargain.mvp.view.onBargainItemClicklistener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongw1 on 15/11/15.
 */
public class BargainMenuRecyclerViewAdapter extends RecyclerView.Adapter<BargainMenuRecyclerViewAdapter.ViewHolder>{

    private final Context mContext;
    private final Drawable mShoppingIcon;
    private ArrayList<Bargain> mBargains;
    private CategoryListContract.Actions listener;

    public BargainMenuRecyclerViewAdapter(Context context, ArrayList<Bargain> bargains, CategoryListContract.Actions listener){
        this.mBargains = bargains;
        this.listener = listener;
        this.mContext = context;
        this.mShoppingIcon = CommonUtil.getTintedIcon(mContext.getResources());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bargain_menu_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.openBargain(mBargains.get(position));
                    }
                }
        );
        holder.mImage.setImageURI(Uri.parse(mBargains.get(position).image));
        String desc = mBargains.get(position).title;
        holder.mDesc.setText(Html.fromHtml( CommonUtil.applyColorToString(desc)));
        holder.mExp.setText(expireoutofstockText(mBargains.get(position), holder.mExp));
        holder.mDownVote.setText(mBargains.get(position).downVote + " -");
        holder.mUpVote.setText(mBargains.get(position).upVote + " +");
        holder.mTag.setText(mBargains.get(position).tag);
        holder.mSubmitted.setText(mBargains.get(position).submittedOn.replace("on","").replace("-",""));
        //coupon code
        if(mBargains.get(position).coupon != null) {
            holder.mCoupon.setVisibility(View.VISIBLE);
            holder.mCoupon.setCompoundDrawablesWithIntrinsicBounds(mShoppingIcon, null, null, null);

            holder.mCoupon.setText(mBargains.get(position).coupon);
        }
        else
            holder.mCoupon.setVisibility(View.GONE);
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
        @Bind(R.id.menu_item_coupon)
        TextView mCoupon;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
