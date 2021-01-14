package com.example.administrator.fengji.pzgc.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.bean.DailyrecordBean;
import com.example.administrator.fengji.pzgc.utils.SlantedTextView;

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
    private ArrayList<DailyrecordBean> mData;
    SpannableString sp;

    public DailyrecordAdapter(Context mContext, ArrayList<DailyrecordBean> mData) {
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
        //姓名
        holder.dailyName.setText("任务审核参与人:" + mData.get(position).getPersonName());
        //岗位
        holder.dailyJobs.setText("岗位:" + mData.get(position).getPosition());
        //所属标段
        holder.dailyBlock.setText("所属组织：" + mData.get(position).getOrgName());
        String str = "任务目标:" + mData.get(position).getAimsTask();
        holder.dailyTarget.setText(setText(str, 4, R.color.graytext, R.color.black));
        String str1 = "已审核:" + mData.get(position).getAuditCount();
        holder.dailyYaudit.setText(setText(str1, 3, R.color.graytext, R.color.finish_green));
        String str2 = "待审核:" + mData.get(position).getWaitTask();
        holder.dailyNaudit.setText(setText(str2, 3, R.color.graytext, R.color.yellow));
        String str4 = "超时审核:";
        holder.dailyTimeout.setText(setText(str4, 4, R.color.graytext, R.color.red));
        //角标
        holder.ItemMessage.setTextString(mData.get(position).getPercentage());
        if (mData.get(position).getPercentage().equals("100.00%") || mData.get(position).getPercentage().equals("100%")) {
            holder.ItemMessage.setSlantedBackgroundColor(R.color.finish_green);
        } else {
            holder.ItemMessage.setSlantedBackgroundColor(R.color.yellow);

        }
        return convertView;
    }

    static class ViewHolder {
        TextView dailyName, dailyJobs, dailyBlock, dailyTarget;
        TextView dailyYaudit, dailyNaudit, dailyTimeout;
        SlantedTextView ItemMessage;
    }

    private SpannableString setText(String str, int num, int color, int color2) {
        sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color)), 0,
                num,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color2)), num + 1,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    public void getData(ArrayList<DailyrecordBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
}
