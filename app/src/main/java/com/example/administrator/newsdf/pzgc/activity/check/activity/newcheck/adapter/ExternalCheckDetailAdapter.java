package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.PhotoAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：外业检查:检查项详情
 * 创建时间： 2020/6/24 0024 13:10
 *
 * @author winelx
 */
public class ExternalCheckDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ExternalCheckDetailAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView open_lin = helper.getView(R.id.open_lin);
        ImageView open_img = helper.getView(R.id.open_img);
        LinearLayout content_lin = helper.getView(R.id.content_lin);
        open_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("展开".equals(open_lin.getText().toString())) {
                    open_lin.setText("收起");
                    open_img.setBackgroundResource(R.mipmap.top_blue_icon);
                    content_lin.setVisibility(View.VISIBLE);
                } else {
                    open_lin.setText("展开");
                    open_img.setBackgroundResource(R.mipmap.bottom_blue_icon);
                    content_lin.setVisibility(View.GONE);
                    LiveDataBus.get().with("recycler").setValue(helper.getAdapterPosition() + "");
                }
            }
        });
        RecyclerView photo_rec = helper.getView(R.id.photo_rec);
        photo_rec.setLayoutManager(new GridLayoutManager(mContext, 4));
        ArrayList<String> photoPaths = new ArrayList<>();
        photoPaths.add("http://img1.gtimg.com/rushidao/pics/hv1/20/108/1744/113431160.jpg");
        photoPaths.add("http://a4.att.hudong.com/47/66/01300000337727123266663353910.jpg");
        photoPaths.add("http://img1.gtimg.com/rushidao/pics/hv1/20/108/1744/113431160.jpg");
        photoPaths.add("http://a4.att.hudong.com/47/66/01300000337727123266663353910.jpg");
        photoPaths.add("http://img1.gtimg.com/rushidao/pics/hv1/20/108/1744/113431160.jpg");
        photoPaths.add("http://a4.att.hudong.com/47/66/01300000337727123266663353910.jpg");
        PhotoAdapter adapter = new PhotoAdapter(mContext, photoPaths, "other");
        adapter.addview(false);
        adapter.addimage(false);
        photo_rec.setAdapter(adapter);

    }
}
