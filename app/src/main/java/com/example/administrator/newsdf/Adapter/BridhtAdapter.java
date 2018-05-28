package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.TaskdetailsActivity;
import com.example.administrator.newsdf.activity.work.BrightspotActivity;
import com.example.administrator.newsdf.bean.BrightBean;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LogUtil;
import com.example.administrator.newsdf.view.MultiImageView;

import java.util.ArrayList;
import java.util.List;


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

    private void bindContent(Viewholder holder, final int position) {
        if (mData.get(position).getPos() == 1) {
            holder.brightFrMark.setBackgroundResource(R.mipmap.marktwo);
        } else if (mData.get(position).getPos() == 2) {
            holder.brightFrMark.setBackgroundResource(R.mipmap.markone);
        } else {
            holder.brightFrMark.setBackgroundResource(R.mipmap.markthree);
        }
        holder.hrightItemViewgroup.setMaxChildCount(5);
        holder.hrightItemViewgroup.setMoreImgBg(R.mipmap.ic_launcher);
        try {
            List<String> impath = mData.get(position).getList();
            String[] urls = Dates.listToString(impath).split("，");
            holder.hrightItemViewgroup.setImgs(urls, 5);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//如果只包含一张图片     //multiImageView.setSingleImg("https://img4.duitang.com/uploads/item/201502/11/20150211005650_AEyUX.jpeg",400,300);
        int length = mData.get(position).getLeadername().length();
        String name = mData.get(position).getLeadername();
        String content = mData.get(position).getTaskName();
        content = name + " 回复: " + content;
        SpannableString sp1 = new SpannableString(content);
        sp1.setSpan(new ForegroundColorSpan(BrightspotActivity.getInstance().getResources()
                        .getColor(R.color.brighr_people)), 0,
                length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(BrightspotActivity.getInstance().getResources()
                        .getColor(R.color.persomal_text)), length + 1,
                length + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.content.setText(sp1);
        holder.brightItemBlock.setText(mData.get(position).getOrgName());
        holder.brightItemTime.setText(mData.get(position).getUpdateDate());
        holder.hright_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("TaskId", mData.get(position).getTaskId());
                Intent intent = new Intent(mContext, TaskdetailsActivity.class);
                intent.putExtra("TaskId", mData.get(position).getTaskId());
                intent.putExtra("status", "false");
                mContext.startActivity(intent);
            }
        });
        holder.hrightItemViewgroup.setClickCallback(new MultiImageView.ClickCallback() {
            @Override
            public void callback(int index, String[] str) {
                LogUtil.i("TaskId", mData.get(position).getTaskId());
                Intent intent = new Intent(mContext, TaskdetailsActivity.class);
                intent.putExtra("TaskId", mData.get(position).getOrgId());
                intent.putExtra("status", "false");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        MultiImageView hrightItemViewgroup;
        TextView content, brightItemBlock, brightItemTime;
        RelativeLayout hright_item;
        private ImageView brightFrMark;

        public Viewholder(View itemView) {
            super(itemView);
            hrightItemViewgroup = itemView.findViewById(R.id.hright_item_viewgroup);
            content = itemView.findViewById(R.id.content);
            brightItemBlock = itemView.findViewById(R.id.bright_item_block);
            brightItemTime = itemView.findViewById(R.id.bright_item_time);
            hright_item = itemView.findViewById(R.id.hright_item);
            brightFrMark = itemView.findViewById(R.id.bright_fr_mark);
        }
    }

    public void getData(ArrayList<BrightBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }
}
