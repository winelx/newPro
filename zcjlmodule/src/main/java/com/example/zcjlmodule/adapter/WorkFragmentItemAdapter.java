package com.example.zcjlmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.WorkItemZcBean;
import com.example.zcjlmodule.ui.activity.original.OriginalZcActivity;
import com.example.zcjlmodule.ui.activity.paydetail.PayDetailedlistZcActivity;
import com.example.zcjlmodule.ui.activity.dismantling.StandardDismantlingZcActivity;

import java.util.List;

import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description: 工作界面的功能的recyclerview，这个adapter是嵌套在WorkFragmentAdapter里面的
 */

public class WorkFragmentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<WorkItemZcBean> list;
    private Context mContext;

    public WorkFragmentItemAdapter(Context mContext, List<WorkItemZcBean> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_work_fragment_item_zc, parent, false);
        TypeHolder holder = new TypeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeHolder && list.size() > 0) {
            bindView((TypeHolder) holder, position);
        }
    }

    private void bindView(TypeHolder holder, final int position) {
        holder.iamge.setBackgroundResource(list.get(position).getIcon());
        holder.content.setText(list.get(position).getName());
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = list.get(position).getName();
                //根据点击的功能名称处理跳转时间
                switch (content) {
                    case "支付清册":
                        mContext.startActivity(new Intent(mContext,PayDetailedlistZcActivity.class));
                        break;
                    case "原始勘丈表":
                        mContext.startActivity(new Intent(mContext,OriginalZcActivity.class));
                        break;
                    case "征拆标准":
                        mContext.startActivity(new Intent(mContext,StandardDismantlingZcActivity.class));
                        break;
                    case "资金申请单":
                        ToastUtlis.getInstance().showShortToast("资金申请单");
                        break;
                    case "资金审批单":
                        ToastUtlis.getInstance().showShortToast("资金审批单");
                        break;
                    case "计量单据审核":
                        ToastUtlis.getInstance().showShortToast("计量单据审核");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TypeHolder extends RecyclerView.ViewHolder {
        ImageView iamge;
        TextView content;
        LinearLayout line;

        public TypeHolder(View itemView) {
            super(itemView);
            iamge = itemView.findViewById(R.id.work_rec_item_iamge);
            content = itemView.findViewById(R.id.work_rec_item_content);
            line = itemView.findViewById(R.id.work_rec_item_line);
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
}
