package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.baselibrary.bean.photoBean;

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
    private RectifierAdapter mAdapter;
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
        if (holder instanceof Typeproblem) {
            bindproblem((Typeproblem) holder, position);
        } else if (holder instanceof Typechaged) {
            bindchaged((Typechaged) holder, position);
        } else if (holder instanceof Typerecord) {
            bindrecord((Typerecord) holder, position);
        } else {

        }
    }

    //整改前
    @SuppressLint("SetTextI18n")
    private void bindproblem(Typeproblem holder, int position) {
        NoticeItemDetailsProblem problem = (NoticeItemDetailsProblem) list.get(position);
        String str;

        str = "整改扣总分分值:" + problem.getStandardDelScore() + "\n";

        holder.typeproblem.setText(
                "整改部位：" + problem.getRectificationPartName() + "\n"
                        + "整改期限：" + problem.getRectificationDate().substring(0, 10) + "\n"
                        + "违反标准：" + problem.getStandardDelName() + "\n"
                        + str
                        + "存在问题：" + problem.getRectificationReason() + "\n"
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
        ArrayList<String> photonames = new ArrayList<>();
        ArrayList<photoBean> AfterFileslist = problem.getAfterFileslist();
        for (int i = 0; i < AfterFileslist.size(); i++) {
            photolist.add(AfterFileslist.get(i).getPhotopath());
            photonames.add(AfterFileslist.get(i).getPhotoname());
        }
        adapter = new RectifierAdapter(mContext, photolist, photonames);
        holder.typerecycler.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void bindchaged(Typechaged holder, int position) {
        NoticeItemDetailsChaged chaged = (NoticeItemDetailsChaged) list.get(position);
        holder.itemStatusRecord.setVisibility(View.VISIBLE);
        String replydate;
        try {
            replydate = chaged.getReplyDate().substring(0, 10);
        } catch (Exception e) {
            replydate = "";
        }
        holder.typeproblem.setText(
                "回复时间：" + isnull(replydate) + "\n"
                        + "整改描述：" + isnull(chaged.getReplyDescription()) + "\n"
                        + "整改后附件："
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider1.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        holder.typerecycler.setLayoutManager(layoutManager);
        holder.typerecycler.addItemDecoration(divider1);
        ArrayList<String> photolist = new ArrayList<>();
        ArrayList<String> photonames = new ArrayList<>();
        ArrayList<photoBean> beforeFileslist = chaged.getBeforeFileslist();
        for (int i = 0; i < beforeFileslist.size(); i++) {
            photolist.add(beforeFileslist.get(i).getPhotopath());
            photonames.add(beforeFileslist.get(i).getPhotoname());
        }
        mAdapter = new RectifierAdapter(mContext, photolist, photonames);
        //是否使用缩略图
        mAdapter.iscompress(false);
        holder.typerecycler.setAdapter(mAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void bindrecord(Typerecord holder, int position) {
        NoticeItemDetailsRecord record = (NoticeItemDetailsRecord) list.get(position);
        if (!record.getDealOpinion().isEmpty()) {
            holder.dealOpinion.setText(record.getDealOpinion());
            holder.dealOpinion.setVisibility(View.VISIBLE);
        } else {
            holder.dealOpinion.setVisibility(View.GONE);
        }
        //操作内容
        if (record.getBeDealPerson() != null && !TextUtils.isEmpty(record.getBeDealPerson())) {
            holder.dealContent.setText(record.getDealContent() + "：" + record.getBeDealPerson());
        } else {
            holder.dealContent.setText(record.getDealContent());
        }
        String opinion = record.getDealContent();
        if ("已验证《验证不通过》".equals(opinion)) {
            holder.dealContent.setTextColor(Color.parseColor("#FE0000"));
        } else if ("已验证《验证通过》".equals(opinion)) {
            holder.dealContent.setTextColor(Color.parseColor("#28c26A"));
        } else {
            holder.dealContent.setTextColor(Color.parseColor("#000000"));
        }
        try {
            holder.datatime.setText(setTextColor(record.getDealDate().substring(0, 16) + "  ", record.getDealPerson()));
        } catch (Exception e) {

        }
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
        private TextView dealOpinion, dealContent;
        private TextView datatime;

        Typerecord(View itemView) {
            super(itemView);
            dealOpinion = itemView.findViewById(R.id.dealOpinion);
            dealContent = itemView.findViewById(R.id.dealContent);
            datatime = itemView.findViewById(R.id.datatime);
        }
    }

    /*空布局*/
    class TypeEmpty extends RecyclerView.ViewHolder {
        TypeEmpty(View itemView) {
            super(itemView);
        }
    }

    public void setNewData(ArrayList<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public String isnull(String string) {
        if (string == null) {
            return "";
        } else {
            return string;
        }
    }

    private SpannableString setTextColor(String str1, String str2) {
        String text = str1 + str2;
        int length1 = str1.length();
        int length2 = str2.length();
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), 0, length1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.colorAccent)), length1,
                length1 + length2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
