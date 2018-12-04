package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.administrator.newsdf.pzgc.activity.device.SeeDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsProving;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsReply;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsTop;
import com.example.administrator.newsdf.pzgc.bean.DeviceTrem;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/28 0028.
 * @description:
 */

public class DeviceDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_RESULT = 2;
    private Context mContext;
    private ArrayList<Object> mData;

    public DeviceDetailsAdapter(Context mContext, ArrayList<Object> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TOP:
                return new DetailsTop(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.devicedetails_top, parent, false));
            case TYPE_CONTENT:
                return new DetailsReply(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.devicedetails_reply, parent, false));
            case TYPE_RESULT:
                return new Detailsproving(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.devicedetails_proving, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = mData.get(position);
        if (holder instanceof DetailsTop) {
            bindTop((DetailsTop) holder, obj, position);
        } else if (holder instanceof DetailsReply) {
            bindContet((DetailsReply) holder, obj, position);
        } else if (holder instanceof Detailsproving) {
            bindContets((Detailsproving) holder, obj, position);
        }
    }

    //顶部
    private void bindTop(DetailsTop holder, Object obj, int position) {
//        CheckDetailsTop top = (CheckDetailsTop) obj;
        holder.detailsTopRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        holder.detailsTopRecyclerview.addItemDecoration(divider);
        ArrayList<DeviceTrem> list = new ArrayList<>();
        list.add(new DeviceTrem("册数数据", "false"));
        list.add(new DeviceTrem("册数数据", "true"));
        list.add(new DeviceTrem("册数数据", "false"));
        holder.detailsTopRecyclerview.setAdapter(new DeviceDetailsTermAdapter(list, mContext));
        holder.detailsTopDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SeeDetailsActivity.class));
            }
        });
    }

    //回复
    private void bindContet(DetailsReply holder, Object obj, int position) {

    }

    //验证
    private void bindContets(Detailsproving holder, Object obj, int position) {
        ArrayList<FileTypeBean> list = new ArrayList<>();
        list.add(new FileTypeBean("测试图片.pdf", "http://file06.16sucai.com/2016/0324/7b07c97b5e653c45c37499236848d519.pdf", "pdf"));
        list.add(new FileTypeBean("测试图片.pdf", "http://file06.16sucai.com/2016/0324/7b07c97b5e653c45c37499236848d519.pdf", "pdf"));
        list.add(new FileTypeBean("测试图片.jpg", "http://file06.16sucai.com/2016/0324/7b07c97b5e653c45c37499236848d519.jpg", "jpg"));
        list.add(new FileTypeBean("测试图片.doc", "http://file06.16sucai.com/2016/0324/7b07c97b5e653c45c37499236848d519.pdf", "doc"));
        list.add(new FileTypeBean("测试图片.xls", "http://file06.16sucai.com/2016/0324/7b07c97b5e653c45c37499236848d519.xls", "xls"));
        list.add(new FileTypeBean("测试图片.jpg", "http://img.zcool.cn/community/017c5d554909920000019ae9d202fe.jpg@1280w_1l_2o_100sh.jpg", "jpg"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.detailsProvingRecycler.setLayoutManager(linearLayoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        holder.detailsProvingRecycler.addItemDecoration(divider);
        FiletypeAdapter adapter2 = new FiletypeAdapter(mContext, list);
        holder.detailsProvingRecycler.setAdapter(adapter2);
    }

    //列表长度
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //展示类型对比
    @Override
    public int getItemViewType(int position) {

        if (mData.get(position) instanceof DeviceDetailsTop) {
            return TYPE_TOP;
        } else if (mData.get(position) instanceof DeviceDetailsReply) {
            return TYPE_CONTENT;
        } else if (mData.get(position) instanceof DeviceDetailsProving) {
            return TYPE_RESULT;
        } else {
            return super.getItemViewType(position);
        }
    }

    //返回ID
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    //头部
    class DetailsTop extends RecyclerView.ViewHolder {
        TextView detailsTopTitle;//标题
        TextView detailsTopNumber;//编号
        TextView detailsTopUsername;//巡检人
        TextView detailsTopData;//巡检时间
        TextView detailsTopRectifyorg;//整改组织
        TextView detailsTopBlameuser;//整改负责人
        TextView detailsTopAddress;//使用地点
        TextView detailsTopModel;//型号规格
        TextView detailsTopRemarks;//备注
        TextView detailsTopStatus;//状态
        LinearLayout detailsTopDetails;//查看详情
        RecyclerView detailsTopRecyclerview;//附件图片

        public DetailsTop(View itemView) {
            super(itemView);
            detailsTopTitle = itemView.findViewById(R.id.details_top_title);
            detailsTopNumber = itemView.findViewById(R.id.details_top_number);
            detailsTopUsername = itemView.findViewById(R.id.details_top_username);
            detailsTopData = itemView.findViewById(R.id.details_top_data);
            detailsTopRectifyorg = itemView.findViewById(R.id.details_top_rectifyorg);
            detailsTopBlameuser = itemView.findViewById(R.id.details_top_blameuser);
            detailsTopAddress = itemView.findViewById(R.id.details_top_address);
            detailsTopModel = itemView.findViewById(R.id.details_top_model);
            detailsTopRemarks = itemView.findViewById(R.id.details_top_remarks);
            detailsTopStatus = itemView.findViewById(R.id.details_top_status);
            detailsTopDetails = itemView.findViewById(R.id.details_top_details);
            detailsTopRecyclerview = itemView.findViewById(R.id.details_top_recyclerView);

        }
    }

    //回复
    class DetailsReply extends RecyclerView.ViewHolder {
        TextView detailsReplyReplyname;//回复人
        TextView detailsReplyStatus;//回复状态

        public DetailsReply(View itemView) {
            super(itemView);
            detailsReplyReplyname = itemView.findViewById(R.id.details_reply_replyname);
            detailsReplyStatus = itemView.findViewById(R.id.details_reply_status);

        }
    }

    //验证
    class Detailsproving extends RecyclerView.ViewHolder {
        TextView detailsProvingUusername;
        TextView detailsProvingResult;
        TextView details_proving_describe;
        RecyclerView detailsProvingRecycler;

        public Detailsproving(View itemView) {
            super(itemView);
            detailsProvingUusername = itemView.findViewById(R.id.details_proving_uusername);
            detailsProvingResult = itemView.findViewById(R.id.details_proving_result);
            details_proving_describe = itemView.findViewById(R.id.details_proving_describe);
            detailsProvingRecycler = itemView.findViewById(R.id.details_proving_recycler);
        }
    }
}
