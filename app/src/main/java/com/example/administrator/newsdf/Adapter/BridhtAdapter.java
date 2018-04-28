package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.BrightspotActivity;
import com.example.administrator.newsdf.bean.BrightBean;
import com.example.administrator.newsdf.view.MultiImageView;

import java.util.ArrayList;


/**
 * description:lian
 *
 * @author lx
 *         date: 2018/4/25 0025 下午 2:40
 *         update: 2018/4/25 0025
 *         version:
 */
public class BridhtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<BrightBean> mData;

    public BridhtAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.bright_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindContent((Viewholder) holder, position);
    }

    private void bindContent(Viewholder holder, int position) {
        holder.hrightItemViewgroup.setMaxChildCount(12);
        holder.hrightItemViewgroup.setMoreImgBg(R.mipmap.ic_launcher);
        String[] urls = mData.get(position).getList().split(",");
        holder.hrightItemViewgroup.setImgs(urls, 4);
//如果只包含一张图片     //multiImageView.setSingleImg("https://img4.duitang.com/uploads/item/201502/11/20150211005650_AEyUX.jpeg",400,300);
        int length = mData.get(position).getName().length();
        String name = mData.get(position).getName();
        String content = mData.get(position).getContent();
        content = name + " 回复 " + content;
        SpannableString sp1 = new SpannableString(content);
        sp1.setSpan(new ForegroundColorSpan(BrightspotActivity.getInstance().getResources()
                        .getColor(R.color.brighr_people)), 0,
                length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(BrightspotActivity.getInstance().getResources()
                        .getColor(R.color.persomal_text)), length + 1,
                length + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.content.setText(sp1);
        holder.brightItemBlock.setText(mData.get(position).getBlock());
        holder.brightItemTime.setText(mData.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        MultiImageView hrightItemViewgroup;
        TextView content, brightItemBlock, brightItemTime;

        public Viewholder(View itemView) {
            super(itemView);
            hrightItemViewgroup = itemView.findViewById(R.id.hright_item_viewgroup);
            content = itemView.findViewById(R.id.content);
            brightItemBlock = itemView.findViewById(R.id.bright_item_block);
            brightItemTime = itemView.findViewById(R.id.bright_item_time);
        }
    }

    public void getData(ArrayList<BrightBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }
}
