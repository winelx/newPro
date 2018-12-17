package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsBean;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsResult;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsTop;
import com.example.administrator.newsdf.pzgc.bean.DeviceReflex;
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
    boolean leam = false;

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
        DeviceDetailsTop top = (DeviceDetailsTop) obj;
        //获取主体内容
        DeviceDetailsBean bean = top.getBean();
        //特种设备名称
        holder.detailsTopTitle.setText(bean.getTypeName());
        //编号
        holder.detailsTopNumber.setText(bean.getNumber());
        holder.detailsTopUsername.setText("巡检人：" + bean.getCheckUserName());
        holder.detailsTopData.setText("巡检日期：" + bean.getCheckDate());
        holder.detailsTopRectifyorg.setText(setText("整改组织：" + bean.getOrgName(), 5, R.color.Orange));
        holder.detailsTopBlameuser.setText(setText("整改负责人：" + bean.getPersonLiableName(), 6, R.color.finish_green));
        holder.detailsTopAddress.setText("使用地点：" + bean.getPlace());
        holder.detailsTopModel.setText("型号规格：" + bean.getTs());//型号规格
        holder.detailsTopRemarks.setText("备注：" + bean.getRemarks());
        holder.details_top_inspectorg.setText("巡检组织：" + bean.getCheckOrgName());
        //,0未下发1未回复2未验证3打回5完成
        int status = bean.getStatus();
        switch (status) {
            case 0:
                holder.detailsTopStatus.setText(setText("状态：未下发", 3, R.color.graytext));
                break;
            case 1:
                holder.detailsTopStatus.setText(setText("状态：未回复", 3, R.color.Orange));
                break;
            case 2:
                holder.detailsTopStatus.setText(setText("状态：未验证", 3, R.color.Orange));
                break;
            case 3:
                holder.detailsTopStatus.setText(setText("状态：打回", 3, R.color.red));
                break;
            case 5:
                holder.detailsTopStatus.setText(setText("状态：完成", 3, R.color.finish_green));
                break;
            default:
                break;
        }
        //违反标准的列表的显示样式
        holder.detailsTopRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        //设置分割线
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        //设置分割线样式
        divider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        //显示分割线
        holder.detailsTopRecyclerview.addItemDecoration(divider);
        ArrayList<DeviceTrem> list = new ArrayList<>();
        list.addAll(((DeviceDetailsTop) obj).getList());
        int problem = 0;
        for (int i = 0; i < list.size(); i++) {
            boolean replied = list.get(i).isReplied();
            if (replied) {
                problem++;
            }
        }
        if (problem == list.size()) {
            leam = true;
        } else {
            leam = false;
        }
        DeviceDetailsTermAdapter adapter = new DeviceDetailsTermAdapter(list, mContext);
        holder.detailsTopRecyclerview.setAdapter(adapter);
        holder.detailsTopDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickitemlitener.seedetails();
            }
        });
        adapter.setOnclickItemLitener(new onclickitemlitener() {
            @Override
            public void seedetails() {
                onclickitemlitener.seedetails();
            }
        });
    }

    //回复
    private void bindContet(DetailsReply holder, Object obj, int position) {
        DeviceReflex bean = (DeviceReflex) obj;
        //回复人
        holder.detailsReplyReplyname.setText(setText("回复人：" + bean.getRealname(), 4, R.color.blue));
        //时间
        holder.detailsReplyData.setText(bean.getOperTime().substring(0, 10));
    }

    //验证
    private void bindContets(Detailsproving holder, Object obj, int position) {
        DeviceDetailsResult bean = (DeviceDetailsResult) obj;

        //验证人
        holder.detailsProvingUusername.setText((setText("验证人：" + bean.getRealname(), 4, R.color.blue)));
        //时间
        holder.detailsProvingData.setText(bean.getOperTime().substring(0, 10));
        //验证结果
        int type = bean.getType();
        switch (type) {
            case 11:
                holder.detailsProvingResult.setText(setText("验证结果：项目经理验证打回", 5, R.color.red));
                holder.detailsProvingName.setText("项目经理验证");
                break;
            case 12:
                holder.detailsProvingResult.setText(setText("验证结果：项目经理验证通过", 5, R.color.finish_green));
                holder.detailsProvingName.setText("项目经理验证");
                break;
            case 22:
                holder.detailsProvingResult.setText(setText("验证结果：下发人验证打回", 5, R.color.red));
                holder.detailsProvingName.setText("验证结果");
                break;
            case 23:
                holder.detailsProvingResult.setText(setText("验证结果：下发人验证通过", 5, R.color.finish_green));
                holder.detailsProvingName.setText("验证结果");
                break;
            default:
                break;
        }
        holder.detailsProvingDescribe.setText(bean.getView());
        ArrayList<FileTypeBean> list = new ArrayList<>();
        list.addAll(bean.getFile());
        if (list.size() > 0) {
            holder.detailstext.setVisibility(View.VISIBLE);
            holder.detailsProvingRecycler.setVisibility(View.VISIBLE);
        } else {
            holder.detailstext.setVisibility(View.GONE);
            holder.detailsProvingRecycler.setVisibility(View.GONE);
        }
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
        } else if (mData.get(position) instanceof DeviceReflex) {
            return TYPE_CONTENT;
        } else if (mData.get(position) instanceof DeviceDetailsResult) {
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
        TextView details_top_inspectorg;//巡检组织
        LinearLayout detailsTopDetails;//查看详情
        RecyclerView detailsTopRecyclerview;//附件图片

        public DetailsTop(View itemView) {
            super(itemView);
            details_top_inspectorg = itemView.findViewById(R.id.details_top_inspectorg);
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
        TextView detailsReplyData;//

        public DetailsReply(View itemView) {
            super(itemView);
            detailsReplyReplyname = itemView.findViewById(R.id.details_reply_replyname);
            detailsReplyData = itemView.findViewById(R.id.details_reply_data);
        }
    }

    //验证
    class Detailsproving extends RecyclerView.ViewHolder {
        //验证人
        TextView detailsProvingUusername;
        //验证结果
        TextView detailsProvingResult;
        //验证描述
        TextView detailsProvingDescribe;
        //时间
        TextView detailsProvingData;
        TextView detailsProvingName;
        TextView detailstext;
        RecyclerView detailsProvingRecycler;

        public Detailsproving(View itemView) {
            super(itemView);
            detailstext = itemView.findViewById(R.id.detailstext);
            detailsProvingData = itemView.findViewById(R.id.details_proving_data);
            detailsProvingUusername = itemView.findViewById(R.id.details_proving_uusername);
            detailsProvingResult = itemView.findViewById(R.id.details_proving_result);
            detailsProvingDescribe = itemView.findViewById(R.id.details_proving_describe);
            detailsProvingRecycler = itemView.findViewById(R.id.details_proving_recycler);
            detailsProvingName = itemView.findViewById(R.id.details_proving_name);
        }
    }

    public void setNewData(ArrayList<Object> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    /**
     * 设置有颜色文字
     */
    public SpannableString setText(String text, int lenght, int color2) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), 0,
                3,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color2)), lenght,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    public interface onclickitemlitener {
        void seedetails();
    }

    private onclickitemlitener onclickitemlitener;

    public void setOnclickItemLitener(onclickitemlitener litener) {
        this.onclickitemlitener = litener;
    }

    public boolean getproblem() {
        return leam;
    }
}
