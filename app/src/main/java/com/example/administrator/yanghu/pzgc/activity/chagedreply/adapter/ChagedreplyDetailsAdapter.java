package com.example.administrator.yanghu.pzgc.activity.chagedreply.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.chagedreply.ChagedreplyDetailsActivity;
import com.example.administrator.yanghu.pzgc.bean.NoticeItemDetailsRecord;
import com.example.administrator.yanghu.pzgc.bean.ReplyDetailsContent;
import com.example.administrator.yanghu.pzgc.bean.ReplyDetailsRecord;
import com.example.administrator.yanghu.pzgc.bean.ReplyDetailsText;
import com.example.administrator.yanghu.pzgc.utils.Dates;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/16 0016}
 * 描述： 回复单详情
 * {@link  ChagedreplyDetailsActivity}
 */
public class ChagedreplyDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYEP_CONTENT = 1;
    private static final int TYEP_ITEM = 2;
    private static final int TYEP_TEXT = 3;
    private static final int TYEP_RECORD = 4;

    private ArrayList<Object> list;
    private Context mContext;


    public ChagedreplyDetailsAdapter(ArrayList<Object> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYEP_CONTENT:
                //内容
                return new TypeContent(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chagedreply_detailscontent, parent, false));
            case TYEP_ITEM:
                //问题项
                return new TypeProblem(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chagedreply_detailsproblem, parent, false));
            case TYEP_TEXT:
                //功能名称
                return new TypeText(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chagedreply_detailstext, parent, false));
            case TYEP_RECORD:
                //操作记录
                return new TypeRecord(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chagedreply_detailsrecord, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeContent) {
            bindContent((TypeContent) holder, position);
        } else if (holder instanceof TypeText) {
            bindText((TypeText) holder, position);
        } else if (holder instanceof TypeProblem) {
            bindTypeProblem((TypeProblem) holder, position);
        } else if (holder instanceof TypeRecord) {
            bindTypeRecord((TypeRecord) holder, position);
        }
    }

    /*问题项*/
    private void bindTypeProblem(final TypeProblem holder, final int position) {
        final ReplyDetailsRecord item = (ReplyDetailsRecord) list.get(position);
        holder.noticeListContent.setText(item.getStandardDelName());
        String string = item.getIsOverdue() + "";
        //是否超时
        if ("1".equals(string)) {
            holder.overtime.setVisibility(View.GONE);
        } else if ("2".equals(string)) {
            holder.overtime.setBackgroundResource(R.mipmap.overtime);
        } else if ("3".equals(string)) {
            holder.overtime.setBackgroundResource(R.mipmap.noovertime);
        }
        //是否完成
        int isVerify = item.getIsVerify();
        if (isVerify == 1) {
            //未完成
            holder.complete.setBackgroundResource(R.mipmap.chagednocomplete);
        } else {
            holder.complete.setBackgroundResource(R.mipmap.chagedcomplete);
        }
        holder.itemProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicktener.onClick(position, item.getIsReply(), item.getId());
            }
        });

    }

    /*记录项*/
    @SuppressLint("SetTextI18n")
    private void bindTypeRecord(TypeRecord holder, int position) {
        NoticeItemDetailsRecord record = (NoticeItemDetailsRecord) list.get(position);
        if (!record.getDealOpinion().isEmpty()) {
            holder.dealOpinion.setText(record.getDealOpinion());
            holder.dealOpinion.setVisibility(View.VISIBLE);
        }else {
            holder.dealOpinion.setVisibility(View.GONE);
        }
        //操作内容
        if (record.getBeDealPerson()!=null&&!TextUtils.isEmpty(record.getBeDealPerson())){
            holder.dealContent.setText(record.getDealContent()+"："+record.getBeDealPerson());
        }else {
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
            holder.dealperson.setText(setTextColor(record.getDealDate().substring(0, 16) + "  ", record.getDealPerson()));
        } catch (Exception e) {

        }

    }

    /**
     * 标题文字项
     *
     * @param holder
     * @param position
     */
    @SuppressLint("ResourceAsColor")
    private void bindText(TypeText holder, int position) {
        Object obj = list.get(position);
        ReplyDetailsText text = (ReplyDetailsText) obj;
        if ("操作记录".equals(text.getString())) {
            holder.waitforname.setVisibility(View.VISIBLE);
            holder.waitforname.setText(Dates.setText(mContext, "待   测试人员  处理", 4, 10, R.color.colorAccent));
        } else {
            holder.waitforname.setVisibility(View.GONE);
        }
        holder.popTaskItem.setText(text.getString());
        holder.popTaskItem.setTextColor(Color.parseColor("#888888"));
        holder.popTaskItem.setBackgroundColor(Color.parseColor("#e0e0e0"));
    }

    /*单据主要内容*/
    @SuppressLint("SetTextI18n")
    private void bindContent(TypeContent holder, int position) {
        ReplyDetailsContent content = (ReplyDetailsContent) list.get(position);
        String reply;
        try {
            reply = content.getReplyDate().substring(0, 10);
        } catch (Exception e) {
            reply = "";
        }

        holder.content.setText(
                "下发组织：" + content.getSorgName() + "\n"
                        + "下发人：" + content.getSendUserName() + "\n"
                        + "下发日期：" + content.getSendDate() + "\n"
                        + "整改组织：" + content.getRorgName() + "\n"
                        + "整改负责人：" + content.getRuserName() + "\n"
                        + "回复日期：" + reply
        );
        holder.number.setText("编号：" + content.getCode());
        holder.noticeCode.setText("关联整改通知单：" + content.getNoticeCode());
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof ReplyDetailsContent) {
            //单据内容
            return TYEP_CONTENT;
        } else if (list.get(position) instanceof ReplyDetailsText) {
            //功能模块名称
            return TYEP_TEXT;
        } else if (list.get(position) instanceof ReplyDetailsRecord) {
            //问题项
            return TYEP_ITEM;
        } else if (list.get(position) instanceof NoticeItemDetailsRecord) {
            //操作记录
            return TYEP_RECORD;
        } else {
            return super.getItemViewType(position);
        }
    }

    /*单据内容*/
    class TypeContent extends RecyclerView.ViewHolder {
        TextView content, number, noticeCode;

        TypeContent(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            number = itemView.findViewById(R.id.number);
            noticeCode = itemView.findViewById(R.id.noticeCode);
        }
    }

    /*名称*/
    class TypeText extends RecyclerView.ViewHolder {
        TextView popTaskItem;
        TextView waitforname;

        TypeText(View itemView) {
            super(itemView);
            popTaskItem = itemView.findViewById(R.id.menuname);
            waitforname = itemView.findViewById(R.id.waitforname);
        }
    }

    /*单据记录*/
    class TypeRecord extends RecyclerView.ViewHolder {
        TextView datatime, dealperson, dealContent, dealOpinion;

        TypeRecord(View itemView) {
            super(itemView);
            datatime = itemView.findViewById(R.id.datatime);
            dealperson = itemView.findViewById(R.id.dealperson);
            dealContent = itemView.findViewById(R.id.dealContent);
            dealOpinion = itemView.findViewById(R.id.dealOpinion);
        }
    }

    /*单据问题项*/
    class TypeProblem extends RecyclerView.ViewHolder {
        private TextView noticeListContent;
        private ImageView overtime, complete;
        private LinearLayout itemProblem;

        public TypeProblem(View itemView) {
            super(itemView);
            noticeListContent = itemView.findViewById(R.id.notice_list_content);
            itemProblem = itemView.findViewById(R.id.item_problem);
            overtime = itemView.findViewById(R.id.notice_list_status);
            complete = itemView.findViewById(R.id.complete);

        }
    }

    class Tyepawait extends RecyclerView.ViewHolder {
        public Tyepawait(View itemView) {
            super(itemView);
        }
    }

    public void setNewData(ArrayList<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface onclicktener {
        void onClick(int position, int isreply, String string);
    }

    private onclicktener onclicktener;

    public void setOnclicktener(onclicktener onclicktener) {
        this.onclicktener = onclicktener;
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
