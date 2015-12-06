package com.littlesword.ozbargain.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
    @Bind(R.id.bargain_detail_description)
    WebView mDescription;
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
                    coupon.setCompoundDrawablesWithIntrinsicBounds(CommonUtil.getTintedIcon(getResources()), null, null, null);
                }else{
                    coupon.setVisibility(View.GONE);
                }
                mDescription.setBackgroundColor(Color.TRANSPARENT);
                mDescription.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                mDescription.loadDataWithBaseURL(null, "<html><body>"+mBargain.description+"</body></html>", "text/html", "utf-8", null);
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
