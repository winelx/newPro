package com.example.zcjlmodule.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.zcjlmodule.R;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.bean.ExamineBean;

/**
 * description:查看征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 4:50
 *         update: 2018/10/19 0019
 *         version:
 *         跳转界面StandardDecomposeZcActivity
 */
public class ExamineDismantlingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<ExamineBean> list;
    private Context mContext;

    public ExamineDismantlingAdapter(ArrayList<ExamineBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Viewholder vh = new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_examine_zc, parent, false));
        //将创建的View注册点击事件
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Viewholder && list.size() > 0) {
            bindGrid((Viewholder) holder, position);
        }
    }

    @SuppressLint("SetTextI18n")
    private void bindGrid(Viewholder holder, final int position) {
        int size = list.size();
        size = size - 4;
        if (position == 3) {
            holder.examine_transparents.setText("+" + size);
            holder.examine_transparents.setVisibility(View.VISIBLE);
        } else {
            holder.examine_transparents.setVisibility(View.GONE);
        }
        if (list.get(position).getType().equals("pdf")) {
            holder.examine_file.setVisibility(View.VISIBLE);
            holder.examine_image.setVisibility(View.GONE);
            holder.examine_filename_content.setText(list.get(position).getName());
            holder.examine_filename_icon.setText("P");

        } else if (list.get(position).getType().equals("doc") || list.get(position).getType().equals("docx")) {
            holder.examine_file.setVisibility(View.VISIBLE);
            holder.examine_image.setVisibility(View.GONE);
            holder.examine_filename_content.setText(list.get(position).getName());
            holder.examine_filename_icon.setText("W");
        } else if (list.get(position).getType().equals("xlsx") || list.get(position).getType().equals("xls")) {
            holder.examine_file.setVisibility(View.VISIBLE);
            holder.examine_image.setVisibility(View.GONE);
            holder.examine_filename_content.setText(list.get(position).getName());
            holder.examine_filename_icon.setText("X");
        } else {
            holder.examine_file.setVisibility(View.GONE);
            holder.examine_image.setVisibility(View.VISIBLE);
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
                    .error(measure.jjxx.com.baselibrary.R.mipmap.base_image_error)
                    .placeholder(measure.jjxx.com.baselibrary.R.mipmap.base_picker_ic_photo_black_48dp);
            Glide.with(mContext)
                    .load(list.get(position).getPath())
                    .apply(options)
                    .into(holder.examine_image);
        }
        holder.examine_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
        holder.examine_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (list.size() >= 4) {
            return 4;
        } else {
            return list.size();
        }

    }


    class Viewholder extends RecyclerView.ViewHolder {
        //图片
        private ImageView examine_image;
        //是否是文件
        private RelativeLayout examine_file;
        //icon
        private TextView examine_filename_icon;
        //文件名称
        private TextView examine_filename_content;
        //蒙层
        private TextView examine_transparents;

        public Viewholder(View itemView) {
            super(itemView);
            examine_file = itemView.findViewById(R.id.examine_file);
            examine_filename_icon = itemView.findViewById(R.id.examine_filename_icon);
            examine_filename_content = itemView.findViewById(R.id.examine_filename_content);
            examine_transparents = itemView.findViewById(R.id.examine_transparents);
            examine_image = itemView.findViewById(R.id.examine_image);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setNewData(ArrayList<ExamineBean> data) {
        this.list = data;
        notifyDataSetChanged();
    }


    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
