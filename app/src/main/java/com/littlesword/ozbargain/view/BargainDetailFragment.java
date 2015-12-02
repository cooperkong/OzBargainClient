package com.littlesword.ozbargain.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.model.Comment;
import com.littlesword.ozbargain.util.CommonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongw1 on 25/11/15.
 */
public class BargainDetailFragment extends Fragment {
    private static final String BARGAIN_DETAIL_KEY = "bargain_details_key";

    @Bind(R.id.bargain_detail_title)
    TextView title;
    @Bind(R.id.bargain_detail_coupon)
    TextView coupon;
    @Bind(R.id.bargain_detail_image)
    SimpleDraweeView image;
    @Bind(R.id.comment_container)
    LinearLayout commentContainer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bargain_fragment, container, false);
        ButterKnife.bind(this, v);
        Bundle bundle = getArguments();
        if(bundle != null){
            Bargain mBargain = (Bargain) bundle.getSerializable(BARGAIN_DETAIL_KEY);
            if(mBargain != null) {
                title.setText(Html.fromHtml(CommonUtil.applyColorToString(mBargain.title)));
                image.setImageURI(Uri.parse(mBargain.image));
                for(Comment c : mBargain.comments){
                    commentContainer.addView(new CommentView(getContext(), c.timestamp, c.content));
                }
                if(mBargain.coupon != null){
                    coupon.setText(mBargain.coupon);
                    Drawable shoppingCart = getResources().getDrawable(R.mipmap.ic_shopping_cart_black_12dp);
                    Drawable wrapDrawable = DrawableCompat.wrap(shoppingCart);
                    DrawableCompat.setTint(wrapDrawable, Color.parseColor("#aaaaaa"));
                    coupon.setCompoundDrawablesWithIntrinsicBounds(wrapDrawable, null, null, null);
                }else{
                    coupon.setVisibility(View.GONE);
                }
            }
        }
        return v;
    }



    public static Fragment newInstance(Bargain bargain) {
        BargainDetailFragment f = new BargainDetailFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putSerializable(BARGAIN_DETAIL_KEY, bargain);
        f.setArguments(args);
        return f;
    }
}
