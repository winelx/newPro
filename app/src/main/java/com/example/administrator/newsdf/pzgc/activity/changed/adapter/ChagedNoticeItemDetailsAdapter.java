package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.RectifierAdapter;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeItemDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsChaged;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsProblem;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsRecord;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：通知单详情查看某一项的详情适配器
 * {@link ChagedNoticeItemDetailsActivity }
 */
public class ChagedNoticeItemDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> list;
    private Context mContext;
    private RectifierAdapter adapter;
    private static final int TYPE_PROBLEM = 1;
    private static final int TYPE_CHAGED = 2;
    private static final int TYPE_RECORD = 3;
    private static final int TYPE_DEFAULT = 4;

    public ChagedNoticeItemDetailsAdapter(ArrayList<Object> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PROBLEM:
                /*整改前*/
                return new Typeproblem(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_item_typeproblem, parent, false));
            case TYPE_CHAGED:
                /*整改后*/
                return new Typechaged(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_item_typeproblem, parent, false));
            case TYPE_RECORD:
                /*操作记录*/
                return new Typerecord(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_item_typerecord, parent, false));
            case TYPE_DEFAULT:
                //没有数据
                return new TypeEmpty(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.recycler_null_layout, parent, false));
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = list.get(position);
        if (holder instanceof Typeproblem) {
            bindproblem((Typeproblem) holder, obj, position);
        } else if (holder instanceof Typechaged) {
            bindchaged((Typechaged) holder, obj, position);
        } else if (holder instanceof Typerecord) {
            bindrecord((Typerecord) holder, obj, position);
        }
    }

    //整改前
    @SuppressLint("SetTextI18n")
    private void bindproblem(Typeproblem holder, Object obj, int position) {
        NoticeItemDetailsProblem problem = (NoticeItemDetailsProblem) obj;
        holder.typeproblem.setText(
                "整改部位：" + "" + "\n"
                        + "整改期限：" + "\n" + ""
                        + "违反标准：" + "\n" + ""
                        + "存在问题：" + "\n" + ""
                        + "整改前附件:"

        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider1.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        holder.typerecycler.setLayoutManager(layoutManager);
        holder.typerecycler.addItemDecoration(divider1);
        ArrayList<String> photolist = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            photolist.add("data:image/jpeg;base64,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");
        }
        adapter = new RectifierAdapter(mContext, photolist, new ArrayList<String>());
        holder.typerecycler.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void bindchaged(Typechaged holder, Object obj, int position) {
        NoticeItemDetailsChaged chaged = (NoticeItemDetailsChaged) obj;
        holder.itemStatusRecord.setVisibility(View.VISIBLE);
        holder.typeproblem.setText(
                "回复时间：" + "" + "\n"
                        + "整改描述：" + "\n" + ""
                        + "整改前附件:"


        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider1.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        holder.typerecycler.setLayoutManager(layoutManager);
        holder.typerecycler.addItemDecoration(divider1);
        ArrayList<String> photolist = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            photolist.add("data:image/jpeg;base64,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");
        }
        adapter = new RectifierAdapter(mContext, photolist, new ArrayList<String>());
        holder.typerecycler.setAdapter(adapter);
    }

    private void bindrecord(Typerecord holder, Object obj, int position) {
        NoticeItemDetailsRecord record = (NoticeItemDetailsRecord) obj;

    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            //如果为null或者为0，长度为1，用来显示空白数据布局
            return 1;
        } else {
            //默认
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list == null || list.size() == 0) {
            /*如果list为空或者长度为0，那么显示空白数据界面*/
            return TYPE_DEFAULT;
        } else {
            //如果有数据根据泛型显示界面
            if (list.get(position) instanceof NoticeItemDetailsProblem) {
                //整改前
                return TYPE_PROBLEM;
            } else if (list.get(position) instanceof NoticeItemDetailsChaged) {
                //整改后
                return TYPE_CHAGED;
            } else if (list.get(position) instanceof NoticeItemDetailsRecord) {
                //操作记录
                return TYPE_RECORD;
            } else {
                return super.getItemViewType(position);
            }
        }

    }

    /*整改前*/
    class Typeproblem extends RecyclerView.ViewHolder {
        private TextView typeproblem;
        private RecyclerView typerecycler;

        public Typeproblem(View itemView) {
            super(itemView);
            typeproblem = (TextView) itemView.findViewById(R.id.typeproblem);
            typerecycler = (RecyclerView) itemView.findViewById(R.id.typerecycler);
        }
    }

    /*整改后*/
    class Typechaged extends RecyclerView.ViewHolder {
        private TextView typeproblem;
        private RecyclerView typerecycler;
        private LinearLayout itemStatusRecord;

        Typechaged(View itemView) {
            super(itemView);
            typeproblem = (TextView) itemView.findViewById(R.id.typeproblem);
            typerecycler = (RecyclerView) itemView.findViewById(R.id.typerecycler);
            itemStatusRecord = (LinearLayout) itemView.findViewById(R.id.item_status_record);
        }
    }

    /*操作记录*/
    class Typerecord extends RecyclerView.ViewHolder {
        Typerecord(View itemView) {
            super(itemView);
        }
    }

    /*空布局*/
    class TypeEmpty extends RecyclerView.ViewHolder {
         TypeEmpty(View itemView) {
            super(itemView);
        }
    }
}
