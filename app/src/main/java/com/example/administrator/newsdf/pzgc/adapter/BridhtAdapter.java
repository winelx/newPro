package com.example.administrator.newsdf.pzgc.adapter;

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
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.activity.home.TaskdetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.BrightBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.view.MultiImageView;

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
        holder.hrightItemViewgroup.setMoreImgBg(R.mipmap.image_loading);
        try {
            List<String> impath = mData.get(position).getList();
            String[] urls = Dates.listToString(impath).split("，");
            holder.hrightItemViewgroup.setImgs(urls, 5);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        int length = mData.get(position).getLeadername().length();
        String name = mData.get(position).getLeadername();
        String content = mData.get(position).getTaskName();
        content = name + " 回复: " + content;
        SpannableString sp1 = new SpannableString(content);
        sp1.setSpan(new ForegroundColorSpan(MainActivity.getInstance().getResources()
                        .getColor(R.color.brighr_people)), 0,
                length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(MainActivity.getInstance().getResources()
                        .getColor(R.color.persomal_text)), length + 1,
                length + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.content.setText(sp1);
        holder.brightItemBlock.setText(mData.get(position).getOrgName());
        holder.brightItemTime.setText(mData.get(position).getUpdateDate());
        holder.hrightItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(position);
            }
        });
        holder.hrightItemViewgroup.setClickCallback(new MultiImageView.ClickCallback() {
            @Override
            public void callback(int index, String[] str) {
                click(position);
            }
        });
    }
    //跳转界面
    public  void  click(int position){
        Intent intent = new Intent(mContext, TaskdetailsActivity.class);
        intent.putExtra("TaskId", mData.get(position).getTaskId());
        intent.putExtra("status", "false");
        intent.putExtra("bright", true);
        mContext.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        MultiImageView hrightItemViewgroup;
        TextView content, brightItemBlock, brightItemTime;
        RelativeLayout hrightItem;
        private ImageView brightFrMark;

        public Viewholder(View itemView) {
            super(itemView);
            hrightItemViewgroup = itemView.findViewById(R.id.hright_item_viewgroup);
            content = itemView.findViewById(R.id.content);
            brightItemBlock = itemView.findViewById(R.id.bright_item_block);
            brightItemTime = itemView.findViewById(R.id.bright_item_time);
            hrightItem = itemView.findViewById(R.id.hright_item);
            brightFrMark = itemView.findViewById(R.id.bright_fr_mark);
        }
        }

    public void getData(ArrayList<BrightBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

}
