package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsContent;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsTop;
import com.example.administrator.newsdf.pzgc.utils.DividerItemDecoration;

import java.util.ArrayList;

/**
 * description: 通知单详情适配器
 *
 * @author lx
 *         date: 2018/8/22 0022 下午 5:26
 *         update: 2018/8/22 0022
 *         version:
 */

public class IssuedTaskDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mData;
    private Context mContext;
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_RESULT = 2;

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
            case TYPE_RESULT:
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
            ((DetailsTop) holder).checkDetailsTitle.setText(top.getWbspath());
            ((DetailsTop) holder).checkDetailsUserdata.setText(top.getSendPersonName() + "  " + top.getSendDate());
            ((DetailsTop) holder).checkDetailsBlock.setText("所属标段:" + top.getRectificationOrgName());
            ((DetailsTop) holder).checkDetailsStandard.setText("违反标准:" + top.getStandardDelName());
            if (top.getCheckplan().length() > 0) {
                ((DetailsTop) holder).checkDetailsWhy.setText("整改事由:" + top.getCheckplan());
            } else {
                ((DetailsTop) holder).checkDetailsWhy.setVisibility(View.GONE);
            }

            ((DetailsTop) holder).checkDetailsOrg.setText("检查组织:" + top.getCheckOrgName());
            ((DetailsTop) holder).checkDetailsRectificationUser.setText("整改负责人：" + top.getRectificationPersonName());
            ((DetailsTop) holder).checkDetailsLasetime.setText("整改期限：" + top.getRectificationDate());
//            通知单回复状态(0:未下发；1：未回复;2:未验证；3：打回；5:完成)
            String status = top.getStatus();
            switch (status) {
                case "0":
                    ((DetailsTop) holder).checkDetailsStatus.setText(setText("状态：未下发", R.color.graytext));
                    break;
                case "1":
                    ((DetailsTop) holder).checkDetailsStatus.setText(setText("状态：未回复", R.color.Orange));
                    break;
                case "2":
                    ((DetailsTop) holder).checkDetailsStatus.setText(setText("状态：未验证", R.color.Orange));
                    break;
                case "3":
                    ((DetailsTop) holder).checkDetailsStatus.setText(setText("状态：打回", R.color.red));
                    break;
                case "5":
                    ((DetailsTop) holder).checkDetailsStatus.setText(setText("状态：完成", R.color.finish_green));
                    break;
                default:
                    break;
            }
            ((DetailsTop) holder).checkDetailsRec.setLayoutManager(new GridLayoutManager(mContext, 4));
            ArrayList<Audio> list = new ArrayList<>();
            list = ((CheckDetailsTop) obj).getAttachmentList();
            ArrayList<String> path = new ArrayList<>();
            ArrayList<String> title = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                path.add(list.get(i).getName());
            }
            RectifierAdapter adapter = new RectifierAdapter(mContext, path, title);
            ((DetailsTop) holder).checkDetailsRec.setAdapter(adapter);
            ((DetailsTop) holder).checkDetailsRec.addItemDecoration(new DividerItemDecoration(mContext,
                    DividerItemDecoration.VERTICAL_LIST));
        } else if (holder instanceof DetailsContent) {
            CheckDetailsContent Content = (CheckDetailsContent) obj;
            ((DetailsContent) holder).detailsResultData.setText(Content.getReplyDate());
            //回复人
            ((DetailsContent) holder).detailsResultUser.setText("回复人：" + Content.getReplyPersonName());
            ((DetailsContent) holder).detailsResultDescribe.setText("整改事由：" + Content.getReplyDescription());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((DetailsContent) holder).detailsResultRec.setLayoutManager(linearLayoutManager);
            ArrayList<Audio> list = new ArrayList<>();
            list = (Content).getImageList();
            ArrayList<String> path = new ArrayList<>();
            ArrayList<String> title = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                path.add(list.get(i).getName());
            }
            RectifierAdapter adapter = new RectifierAdapter(mContext, path, title);
            ((DetailsContent) holder).detailsResultRec.setAdapter(adapter);
            String name = Content.getReplyPersonName2();
            if (name == null || !name.isEmpty()) {
                ((DetailsContent) holder).details_validation.setVisibility(View.GONE);
            } else {
                ((DetailsContent) holder).detailsValidationData.setText(Content.getReplyDescription());
                ((DetailsContent) holder).detailsValidationUser.setText("验证人:" + Content.getReplyPersonName2());
                ((DetailsContent) holder).detailsResultResult.setText("验证结果:" + Content.getReplyDescription2());
                ((DetailsContent) holder).detailsValidationDescribe.setText("验证描述:" + Content.getReplyDescription2());
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ((DetailsContent) holder).detailsResultRec.setLayoutManager(linearLayoutManager1);
                ArrayList<Audio> list1 = new ArrayList<>();
                ArrayList<String> path1 = new ArrayList<>();
                ArrayList<String> title1 = new ArrayList<>();
                list1 = (Content).getImageList2();
                for (int i = 0; i < list1.size(); i++) {
                    path.add(list1.get(i).getName());
                }
                RectifierAdapter adapter1 = new RectifierAdapter(mContext, path1, title1);
                ((DetailsContent) holder).detailsValidationRec.setAdapter(adapter1);
            }
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
        LinearLayout details_validation;

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
             * 验证
               */
            details_validation = itemView.findViewById(R.id.details_validation);
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

    /**
     * 设置有颜色文字
     */
    public SpannableString setText(String text, int color2) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), 0,
                3,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color2)), 3,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

}