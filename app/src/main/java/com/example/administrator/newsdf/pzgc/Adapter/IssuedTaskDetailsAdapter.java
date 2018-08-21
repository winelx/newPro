package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsContent;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsTop;

import java.util.ArrayList;


public class IssuedTaskDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mData;
    private Context mContext;
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;

    public IssuedTaskDetailsAdapter(ArrayList<Object> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TOP:
                return new DetailsTop(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_details_top, parent, false));
            case TYPE_CONTENT:
                return new DetailsContent(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_detaiils_content, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = mData.get(position);
        if (holder instanceof IssuedTaskDetailsAdapter.DetailsTop) {
            CheckDetailsTop top = (CheckDetailsTop) obj;
            ((DetailsTop) holder).checkDetailsTitle.setText(top.getSsss());
        } else if (holder instanceof DetailsContent) {
            CheckDetailsContent Content = (CheckDetailsContent) obj;
            ((DetailsContent) holder).detailsResultData.setText(Content.getDescribe());
            ((DetailsContent) holder).detailsValidationData.setText(Content.getUsername());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((DetailsContent) holder).detailsResultRec.setLayoutManager(linearLayoutManager);
            RectifierAdapter adapter = new RectifierAdapter(mContext, Content.getImageList(), Content.getImageList());
            ((DetailsContent) holder).detailsResultRec.setAdapter(adapter);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof CheckDetailsTop) {
            return TYPE_TOP;
        } else if (mData.get(position) instanceof CheckDetailsContent) {
            return TYPE_CONTENT;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    private class DetailsTop extends RecyclerView.ViewHolder {
        TextView checkDetailsTitle, checkDetailsUserdata, checkDetailsBlock,
                checkDetailsStandard, checkDetailsWhy, checkDetailsOrg;
        RecyclerView checkDetailsRec;
        TextView checkDetailsRectificationUser, checkDetailsLasetime, checkDetailsStatus;

        DetailsTop(View itemView) {
            super(itemView);
            checkDetailsTitle = itemView.findViewById(R.id.check_details_title);
            checkDetailsUserdata = itemView.findViewById(R.id.check_details_userData);
            checkDetailsBlock = itemView.findViewById(R.id.check_details_block);
            checkDetailsStandard = itemView.findViewById(R.id.check_details_standard);
            checkDetailsWhy = itemView.findViewById(R.id.check_details_why);
            checkDetailsOrg = itemView.findViewById(R.id.check_details_org);
            checkDetailsRectificationUser = itemView.findViewById(R.id.check_details_rectification_user);
            checkDetailsLasetime = itemView.findViewById(R.id.check_details_lasetime);
            checkDetailsStatus = itemView.findViewById(R.id.check_details_status);
            checkDetailsRec = itemView.findViewById(R.id.check_details_rec);

        }
    }

    private class DetailsContent extends RecyclerView.ViewHolder {
        TextView detailsResultData, detailsResultUser, detailsResultResult, detailsResultDescribe;
        TextView detailsValidationData, detailsValidationUser, detailsValidationResult, detailsValidationDescribe;
        RecyclerView detailsValidationRec, detailsResultRec;

        DetailsContent(View itemView) {
            super(itemView);
            /*
              回复
             */
            //时间
            detailsResultData = itemView.findViewById(R.id.details_result_data);
            //用户
            detailsResultUser = itemView.findViewById(R.id.details_result_user);
            //整改结果
            detailsResultResult = itemView.findViewById(R.id.details_result_result);
            //描述
            detailsResultDescribe = itemView.findViewById(R.id.details_result_describe);
            //附件
            detailsResultRec = itemView.findViewById(R.id.details_result_rec);
            /*
              验证
             */
            //验证时间
            detailsValidationData = itemView.findViewById(R.id.details_validation_data);
            //验证用户
            detailsValidationUser = itemView.findViewById(R.id.details_validation_user);
            //验证结果
            detailsValidationResult = itemView.findViewById(R.id.details_validation_result);
            //验证描述
            detailsValidationDescribe = itemView.findViewById(R.id.details_validation_describe);
            //验证附件
            detailsValidationRec = itemView.findViewById(R.id.details_validation_rec);
        }
    }

    public void getData(ArrayList<Object> list) {
        this.mData = list;
        notifyDataSetChanged();
    }
}