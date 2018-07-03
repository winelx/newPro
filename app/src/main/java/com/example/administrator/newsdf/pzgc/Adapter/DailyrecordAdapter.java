package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;

import java.util.ArrayList;

/**
 * description: 每日审核
 *
 * @author lx
 *         date: 2018/7/3 0003 下午 1:57
 *         update: 2018/7/3 0003
 *         version:
 */
public class DailyrecordAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mData;
    SpannableString sp;
    public DailyrecordAdapter(Context mContext, ArrayList<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.report_fr_item, parent, false);
            holder = new ViewHolder();
            holder.ItemMessage = convertView.findViewById(R.id.daily_message);
            holder.dailyName = convertView.findViewById(R.id.daily_name);
            holder.dailyJobs = convertView.findViewById(R.id.daily_jobs);
            holder.dailyBlock = convertView.findViewById(R.id.daily_block);
            holder.dailyTarget = convertView.findViewById(R.id.daily_target);
            holder.dailyYaudit = convertView.findViewById(R.id.daily_yaudit);
            holder.dailyNaudit = convertView.findViewById(R.id.daily_naudit);
            holder.dailyTimeout = convertView.findViewById(R.id.daily_timeout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String str = "任务目标:120000";
        holder.dailyTarget.setText(setText(str, 4, R.color.graytext,R.color.black));
        String str1 = "已审核:00000";
        holder.dailyYaudit.setText(setText(str1, 3,  R.color.graytext,R.color.finish_green));
        String str2 = "待审核:00000";
        holder.dailyNaudit.setText(setText(str2, 3, R.color.graytext, R.color.yellow));
        String str4 = "超时审核:00000";
        holder.dailyTimeout.setText(setText(str4,4, R.color.graytext, R.color.red));
        //角标
        holder.ItemMessage.setTextString("99%");
        holder.ItemMessage.setSlantedBackgroundColor(R.color.yellow);
        return convertView;
    }

    static class ViewHolder {
        TextView dailyName, dailyJobs, dailyBlock, dailyTarget;
        TextView dailyYaudit, dailyNaudit, dailyTimeout;
        SlantedTextView ItemMessage;
    }

    private SpannableString setText(String str, int num, int color,int color2) {
        sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color)), 0,
                num,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color2)), num+1,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

}
