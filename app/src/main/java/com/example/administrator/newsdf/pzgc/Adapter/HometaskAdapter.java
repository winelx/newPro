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
    }

    /*今日任务*/
    private void bindToday(Todayviewholder holder, final int position) {
        holder.todaytotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.TODAYTASK, position);
            }
        });
    }

    /*上月整改单统计*/
    @SuppressLint("SetTextI18n")
    private void bindLast(Lastviewholder holder, final int position) {
        String string = "10";
        holder.problemNumber.setText(setText(mContext, "问题共计：" + string + "项", string.length()));
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

    /*消息通知*/
    class Addupviewholder extends RecyclerView.ViewHolder {

        private RelativeLayout histoytotal;

        Addupviewholder(View itemView) {
            super(itemView);
            histoytotal = itemView.findViewById(R.id.histoytotal);
        }
    }

    class Todayviewholder extends RecyclerView.ViewHolder {

        private RelativeLayout todaytotal;

        Todayviewholder(View itemView) {
            super(itemView);
            todaytotal = itemView.findViewById(R.id.todaytotal);
        }
    }

    class Lastviewholder extends RecyclerView.ViewHolder {

        private TextView problemNumber;
        private RelativeLayout lasttotal;

        Lastviewholder(View itemView) {
            super(itemView);
            lasttotal = itemView.findViewById(R.id.lasttotal);
            problemNumber = itemView.findViewById(R.id.problem_number);
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
