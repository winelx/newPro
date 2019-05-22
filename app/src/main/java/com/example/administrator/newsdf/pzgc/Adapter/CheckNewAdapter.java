package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.chekitemList;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/6 0006.
 * 检查
 */

public class CheckNewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<chekitemList> imagePaths;

    public CheckNewAdapter(Context mContext, ArrayList<chekitemList> imagePaths) {
        this.mContext = mContext;
        this.imagePaths = imagePaths;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.check_new_grid_item, null);
            holder = new ViewHolder();
            holder.pop_tast_item = convertView.findViewById(R.id.text_item);
            holder.LabelView = convertView.findViewById(R.id.angleofthe);
            holder.chekItemRe = convertView.findViewById(R.id.chek_item_re);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.pop_tast_item.setText(imagePaths.get(position).getPos()+"");
        //是否有此项
        boolean noSuch = imagePaths.get(position).isNoSuch();
        boolean gray=imagePaths.get(position).isGray();
        //判断是否操作过
        if (gray){
            //操作过
            if (noSuch) {
                //无此项
                holder.chekItemRe.setBackgroundResource(R.color.finish_green);
                holder.LabelView.setBackgroundResource(R.mipmap.triangle_gray);
                holder.LabelView.setVisibility(View.VISIBLE);
            } else {
                //是否被扣分
                boolean penalty = imagePaths.get(position).isPenalty();
                if (penalty) {
                    holder.chekItemRe.setBackgroundResource(R.color.Orange);
                    //是否被下通知
                    boolean Generate = imagePaths.get(position).isGenerate();
                    if (Generate) {
                        holder.LabelView.setBackgroundResource(R.mipmap.triangle_red);
                        holder.LabelView.setVisibility(View.VISIBLE);
                    }else {
                        holder.LabelView.setVisibility(View.GONE);
                    }
                } else {
                    //是否被下通知
                    boolean Generate = imagePaths.get(position).isGenerate();
                    if (Generate) {
                        holder.LabelView.setBackgroundResource(R.mipmap.triangle_red);
                        holder.LabelView.setVisibility(View.VISIBLE);
                    }else {
                        holder.LabelView.setVisibility(View.GONE);
                    }
                    holder.chekItemRe.setBackgroundResource(R.color.finish_green);
                }
            }
        }else {
            //没有做过操作
            holder.LabelView.setVisibility(View.GONE);
            holder.chekItemRe.setBackgroundResource(R.color.gray);
            //是否被下通知
            boolean Generate = imagePaths.get(position).isGenerate();
            if (Generate) {
                holder.LabelView.setBackgroundResource(R.mipmap.triangle_red);
                holder.LabelView.setVisibility(View.VISIBLE);
            }else {
                holder.LabelView.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView pop_tast_item;
        ImageView LabelView;
        RelativeLayout chekItemRe;
    }

    public void getdate(ArrayList<chekitemList> imagePaths) {
        this.imagePaths = imagePaths;
        notifyDataSetChanged();
    }
}
