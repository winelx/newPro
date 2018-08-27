package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsContent;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsContents;
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
                return new DetailsContents(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_delaiils_contents, parent, false));
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
            String data = top.getSendDate();
            ((DetailsTop) holder).checkDetailsUserdata.setText(top.getSendPersonName() + "  " + data.substring(0, 10));
            ((DetailsTop) holder).checkDetailsStandard.setText("违反标准：" + top.getStandardDelName());
            ((DetailsTop) holder).checkDetailsWhy.setText("整改事由：" + top.getRectificationOrgName());


            ((DetailsTop) holder).checkDetailsOrg.setText("检查组织：" + top.getCheckOrgName());
            ((DetailsTop) holder).checkDetailsRectificationUser.setText(setText2("整改负责人：" + top.getRectificationPersonName(), 5, R.color.colorAccent));
            String sub = top.getRectificationDate();
            ((DetailsTop) holder).checkDetailsLasetime.setText("整改期限：" + sub.substring(0, 10));
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
            if (list.size() > 0) {
                ((DetailsTop) holder).notimgeview.setVisibility(View.GONE);
            } else {
                ((DetailsTop) holder).notimgeview.setVisibility(View.VISIBLE);
            }
            ArrayList<String> path = new ArrayList<>();
            ArrayList<String> title = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                path.add(list.get(i).getName());
            }
            RectifierAdapter adapter1 = new RectifierAdapter(mContext, path, title);
            ((DetailsTop) holder).checkDetailsRec.setAdapter(adapter1);
            ((DetailsTop) holder).checkDetailsRec.addItemDecoration(new DividerItemDecoration(mContext,
                    DividerItemDecoration.VERTICAL_LIST));
        } else if (holder instanceof DetailsContent) {
            CheckDetailsContent Content = (CheckDetailsContent) obj;
            ((DetailsContent) holder).detailsResultData.setText(Content.getReplyDate());
            //回复人replyDescription
            ((DetailsContent) holder).detailsResultUser.setText(setText2("回复人：" + Content.getReplyPersonName(), 3, R.color.colorAccent));
            ((DetailsContent) holder).detailsResultDescribe.setText("整改描述：" + Content.getReplyDescription());
            ((DetailsContent) holder).detailsResultRec.setLayoutManager(new GridLayoutManager(mContext, 4));
            ArrayList<Audio> list1 = new ArrayList<>();
            list1 = (Content).getImageList();
            if (list1.size() > 0) {
                ((DetailsContent) holder).content_top_image.setVisibility(View.GONE);
                ((DetailsContent) holder).detailsResultRec.setVisibility(View.VISIBLE);
            } else {
                ((DetailsContent) holder).content_top_image.setVisibility(View.VISIBLE);
                ((DetailsContent) holder).detailsResultRec.setVisibility(View.GONE);
            }
            ArrayList<String> path1 = new ArrayList<>();
            ArrayList<String> title1 = new ArrayList<>();
            RectifierAdapter adapter = new RectifierAdapter(mContext, path1, title1);
            for (int i = 0; i < list1.size(); i++) {
                path1.add(list1.get(i).getName());
            }
            ((DetailsContent) holder).detailsResultRec.setAdapter(adapter);
        } else if (holder instanceof DetailsContents) {
            CheckDetailsContents Contents = (CheckDetailsContents) obj;
            ((DetailsContents) holder).detailsValidationData.setText(Contents.getReplyDate());
            //回复人
            ((DetailsContents) holder).detailsValidationUser.setText(setText2("验证人：" + Contents.getReplyPersonName(), 3, R.color.colorAccent));
            ((DetailsContents) holder).detailsValidationDescribe.setText("验证描述：" + Contents.getReplyDescription());
            ((DetailsContents) holder).detailsValidationRec.setLayoutManager(new GridLayoutManager(mContext, 4));
            String status = Contents.getStsuts();
            switch (status) {
                case "1":
                    ((DetailsContents) holder).detailsValidationResult.setText(setText2("验证结果：通过", 4, R.color.finish_green));
                    break;
                case "2":
                    ((DetailsContents) holder).detailsValidationResult.setText(setText2("验证结果:打回", 4, R.color.red));
                    break;
                default:
                    break;
            }
            ArrayList<Audio> list1 = new ArrayList<>();
            list1 = (Contents).getImageList();
            if (list1.size() > 0) {
                ((DetailsContents) holder).content_result_image.setVisibility(View.GONE);
                ((DetailsContents) holder).content_result_image.setVisibility(View.VISIBLE);
            } else {
                ((DetailsContents) holder).content_result_image.setVisibility(View.GONE);
                ((DetailsContents) holder).content_result_image.setVisibility(View.VISIBLE);
            }
            ArrayList<String> path2 = new ArrayList<>();
            ArrayList<String> title2 = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                path2.add(list1.get(i).getName());
            }
            RectifierAdapter adapter2 = new RectifierAdapter(mContext, path2, title2);
            ((DetailsContents) holder).detailsValidationRec.setAdapter(adapter2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof CheckDetailsTop) {
            return TYPE_TOP;
        } else if (mData.get(position) instanceof CheckDetailsContent) {
            return TYPE_CONTENT;
        } else if (mData.get(position) instanceof CheckDetailsContents) {
            return TYPE_RESULT;
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
        ImageView notimgeview;

        DetailsTop(View itemView) {
            super(itemView);
            checkDetailsTitle = itemView.findViewById(R.id.check_details_title);
            checkDetailsUserdata = itemView.findViewById(R.id.check_details_userData);
            notimgeview = itemView.findViewById(R.id.notimgeview);

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
        RecyclerView detailsResultRec;
        ImageView content_top_image;

        DetailsContent(View itemView) {
            super(itemView);
            /*
              回复
             */

            //时间
            detailsResultData = itemView.findViewById(R.id.details_result_data);
            content_top_image = itemView.findViewById(R.id.content_top_image);
            //用户
            detailsResultUser = itemView.findViewById(R.id.details_result_user);
            //整改结果
            detailsResultResult = itemView.findViewById(R.id.details_result_result);
            //描述
            detailsResultDescribe = itemView.findViewById(R.id.details_result_describe);
            //附件
            detailsResultRec = itemView.findViewById(R.id.details_result_rec);

        }
    }

    private class DetailsContents extends RecyclerView.ViewHolder {
        LinearLayout details_validation;
        RecyclerView detailsValidationRec;
        TextView detailsValidationData, detailsValidationUser, detailsValidationResult, detailsValidationDescribe;
        ImageView content_result_image;

        public DetailsContents(View itemView) {
            super(itemView);
              /*
             * 验证
               */
            details_validation = itemView.findViewById(R.id.details_validation);
            content_result_image = itemView.findViewById(R.id.content_result_image);
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

    public SpannableString setText2(String text, int num, int color2) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), 0,
                num,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color2)), num + 1,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}