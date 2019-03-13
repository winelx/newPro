package com.example.administrator.newsdf.pzgc.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.example.administrator.newsdf.pzgc.activity.home.HometaskActivity;
import com.example.administrator.newsdf.pzgc.bean.LastmonthBean;
import com.example.administrator.newsdf.pzgc.bean.TodayBean;
import com.example.administrator.newsdf.pzgc.bean.TotalBean;
import com.example.administrator.newsdf.pzgc.callback.Onclicktener;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.bean.bean;

import java.util.ArrayList;


/**
 * @author lx
 * @data:2019/3/6 0006
 * @Notes: 首页任务数量展示列表界面适配器
 * @see HometaskActivity }
 */
public class HometaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> list;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;

    private Onclicktener onclicktener;
    private Context mContext;

    public HometaskAdapter(Context mContext, ArrayList<Object> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                return new Addupviewholder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_hometask_addup, parent, false));
            case TYPE_TWO:
                return new Todayviewholder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_hometask_todaytask, parent, false));
            case TYPE_THREE:
                return new Lastviewholder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_hometask_last, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Addupviewholder && list.size() != 0) {
            bindAddup((Addupviewholder) holder, position);
        } else if (holder instanceof Todayviewholder && list.size() != 0) {
            bindToday((Todayviewholder) holder, position);
        } else if (holder instanceof Lastviewholder && list.size() != 0) {
            bindLast((Lastviewholder) holder, position);
        }
    }

    /*累计任务总数*/
    private void bindAddup(Addupviewholder holder, final int position) {

        holder.histoytotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.ADDUPTask, position);
            }
        });
        TotalBean bean = (TotalBean) list.get(position);
        holder.total_name.setText(bean.getfOrgName());
        holder.total_numer.setText(bean.getFinishCount());
    }

    /*今日任务*/
    private void bindToday(Todayviewholder holder, final int position) {
        holder.todaytotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.TODAYTASK, position);
            }
        });
        TodayBean bean = (TodayBean) list.get(position);
        holder.total_name.setText(bean.getfOrgName());
        holder.total_numer.setText(bean.getFinishCount());
    }

    /*上月整改单统计*/
    @SuppressLint("SetTextI18n")
    private void bindLast(Lastviewholder holder, final int position) {
        LastmonthBean bean = (LastmonthBean) list.get(position);
        String string = bean.getNoticeCount() + "";
        //存在问题
        holder.problemNumber.setText(setText(mContext, "问题共计：" + string + "项", string.length()));
        //公司名称
        holder.problem_name.setText(bean.getName());
        //完成任务
        holder.problem_complete.setText("已完成：" + bean.getFinish());
        //超期完成
        holder.problem_overtime.setText("超期完成：" + bean.getYesOverdueFinish());
        //未整改数
        holder.problem_chaged.setText("未完成整改：" + bean.getNotFinish());
        //未整改
        holder.problem_nochaged.setText("未整改：" + bean.getNoFinish());
        holder.lasttotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.LASTMONTHTASK, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
            if (list.get(position) instanceof TotalBean) {
                return TYPE_ONE;
            } else if (list.get(position) instanceof TodayBean) {
                return TYPE_TWO;
            } else if (list.get(position) instanceof LastmonthBean) {
                return TYPE_THREE;
            } else {
                return super.getItemViewType(position);
            }

    }

    class Addupviewholder extends RecyclerView.ViewHolder {
        private RelativeLayout histoytotal;
        private TextView total_name, total_numer;

        Addupviewholder(View itemView) {
            super(itemView);
            histoytotal = itemView.findViewById(R.id.histoytotal);
            total_numer = itemView.findViewById(R.id.total_numer);
            total_name = itemView.findViewById(R.id.total_name);
        }
    }

    class Todayviewholder extends RecyclerView.ViewHolder {

        private RelativeLayout todaytotal;
        private TextView total_name, total_numer;

        Todayviewholder(View itemView) {
            super(itemView);
            todaytotal = itemView.findViewById(R.id.todaytotal);
            total_numer = itemView.findViewById(R.id.total_numer);
            total_name = itemView.findViewById(R.id.total_name);
        }
    }

    class Lastviewholder extends RecyclerView.ViewHolder {

        private TextView problemNumber, problem_name, problem_complete;
        private TextView problem_overtime, problem_chaged, problem_nochaged;
        private RelativeLayout lasttotal;

        Lastviewholder(View itemView) {
            super(itemView);
            lasttotal = itemView.findViewById(R.id.lasttotal);
            problemNumber = itemView.findViewById(R.id.problem_number);
            problem_name = itemView.findViewById(R.id.problem_name);
            problem_complete = itemView.findViewById(R.id.problem_complete);
            problem_overtime = itemView.findViewById(R.id.problem_overtime);
            problem_chaged = itemView.findViewById(R.id.problem_chaged);
            problem_nochaged = itemView.findViewById(R.id.problem_nochaged);
        }
    }

    class Emptyholder extends RecyclerView.ViewHolder {
        public Emptyholder(View itemView) {
            super(itemView);

        }
    }

    public void setOnclicktener(Onclicktener onclicktener) {
        this.onclicktener = onclicktener;
    }

    public void setNewData(ArrayList<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static SpannableString setText(Context mContext, String text, int lenght) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.red)), 5,
                lenght + 5,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
