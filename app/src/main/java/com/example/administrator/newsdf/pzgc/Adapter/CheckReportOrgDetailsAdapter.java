package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.OrgDetailsFBean;
import com.example.administrator.newsdf.pzgc.bean.OrgDetailsTBean;

import java.util.ArrayList;

/**
 * @author lx
 * @date 2018/8/28 0028
 * 检查统计报表每个标段的得分扣分界面适配器
 */

public class CheckReportOrgDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;
    private ArrayList<Object> mData;
    private Context mContext;

    public CheckReportOrgDetailsAdapter(ArrayList<Object> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TOP:
                return new CheckReportOrgDetailsAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_checkreport_item, parent, false));
            case TYPE_CONTENT:
                return new CheckReportOrgDetailsAdapter.ViewHolder2(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_checkreport_items, parent, false));
            default:
                return null;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof OrgDetailsFBean) {
            return TYPE_TOP;
        } else if (mData.get(position) instanceof OrgDetailsTBean) {
            return TYPE_CONTENT;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Object obj = mData.get(position);
        if (holder instanceof ViewHolder) {
            /**
             * check_date : 2018-08-23 20:08:52
             * check_org_name : 测试公司
             * check_person_name : 测试分公司质安负责人001
             * id : 01ee44b85d0a4567884158ad472514e2
             * part_details : 安卓
             * rectification_date : 2018-08-04 00:00:00
             * rectification_org_name : 测试9标
             * rectification_person_name : 九标项目经理001
             * rectification_reason : 嗷呜good
             * standard_del_name : 对危险性较大的高边坡、高墩、大跨桥梁和地质复杂隧道，未进行风险评估
             * standard_del_score : 10.0
             * wbs_name : 桐木冲1号大桥桥梁总体
             */
            OrgDetailsFBean Tbean= (OrgDetailsFBean) obj;
            ((ViewHolder) holder).title.setText(Tbean.getStandardtypeName());
            ((ViewHolder) holder).wbspath.setText(Tbean.getWbsName());
            ((ViewHolder) holder).score.setText("-"+Tbean.getStandardDelScore().replace(".0","")+"分");
           String str= Tbean.getCheckDate().substring(0,10);
            ((ViewHolder) holder).checkuser.setText("下发人："+Tbean.getRectificationPersonName()+" "+str);
            ((ViewHolder) holder).orgname.setText("检查组织："+Tbean.getCheckOrgName());
            ((ViewHolder) holder).user.setText("整改负责人："+Tbean.getCheckPersonName());
            ((ViewHolder) holder).checkdata.setText("整改期限："+Tbean.getRectificationDate().substring(0,10));
            ((ViewHolder) holder).layout_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1
                    int position =   (holder).getLayoutPosition();
                    // 2
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        } else if (holder instanceof ViewHolder2){
            /**
             * check_date : 2018-08-24
             * check_org_name : 测试公司
             * check_plan_name : pc端新增检查计划，pc端完成
             * check_user_name : 测试分公司质安负责人001
             * id : d4e2e65d640b465eb304480c1c1c69d3
             * part_details : 部位详情
             * score : 84.0
             * wbs_name : 0#台左幅0-0桩基混凝土浇筑
             * wbs_type_name : 桥梁工程>钻孔灌注桩
             */
            OrgDetailsTBean Fbean= (OrgDetailsTBean) obj;
            ((ViewHolder2) holder).title.setText(Fbean.getCheck_plan_name());
            ((ViewHolder2) holder).wbspath.setText(Fbean.getWbs_name());
            ((ViewHolder2) holder).Score.setText("+"+Fbean.getScore().replace(".0","")+"分");
            ((ViewHolder2) holder).checkuser.setText("检查人："+Fbean.getCheck_user_name()+"  "+Fbean.getCheck_date());
            ((ViewHolder2) holder).orgname.setText("检查组织："+Fbean.getCheck_org_name());
            ((ViewHolder2) holder).layout_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1
                    int position =   (holder).getLayoutPosition();
                    // 2
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    //    OrgDetailsFBean
    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, wbspath, checkuser, orgname, user, checkdata,score;
        private LinearLayout layout_linear;
        public ViewHolder(View itemView) {
            super(itemView);
            layout_linear=itemView.findViewById(R.id.layout_linear);
            title = itemView.findViewById(R.id.check_report_item_title);
            wbspath = itemView.findViewById(R.id.check_report_item_wbs);
            checkuser = itemView.findViewById(R.id.check_report_item_checkuser);
            orgname = itemView.findViewById(R.id.check_report_item_org);
            user = itemView.findViewById(R.id.check_report_item_user);
            checkdata = itemView.findViewById(R.id.check_report_item_checkdata);
            score = itemView.findViewById(R.id.check_report_item_score);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView title, wbspath, checkuser, orgname,Score;
        private LinearLayout layout_linear;
        public ViewHolder2(View itemView) {
            super(itemView);
            layout_linear=itemView.findViewById(R.id.layout_linear);
            title = itemView.findViewById(R.id.check_t_titele);
            wbspath = itemView.findViewById(R.id.check_t_wbspath);
            checkuser = itemView.findViewById(R.id.check_t_user);
            orgname = itemView.findViewById(R.id.check_t_orgname);
            Score = itemView.findViewById(R.id.check_t_score);
        }
    }

    public void getmData(ArrayList<Object> list) {
        this.mData = list;
        notifyDataSetChanged();
    }
    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
