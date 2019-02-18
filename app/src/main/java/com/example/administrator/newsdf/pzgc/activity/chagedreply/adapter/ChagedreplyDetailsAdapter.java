package com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedreplyDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsRecord;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsContent;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsRecord;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsText;
import com.example.administrator.newsdf.pzgc.utils.Dates;

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
        Object object = list.get(position);
        holder.item_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(position, holder.notice_list_content.getText().toString());
            }
        });
    }

    /*记录项*/
    private void bindTypeRecord(TypeRecord holder, int position) {
        Object object = list.get(position);
    }

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

    @SuppressLint("SetTextI18n")
    private void bindContent(TypeContent holder, int position) {
        holder.content.setText(
                "下发组织：" + "" + "\n"
                        + "下发人：" + "\n" + ""
                        + "下发日期：" + "\n" + ""
                        + "整改组织：" + "\n" + ""
                        + "整改负责人：" + "\n" + ""
                        + "回复日期："


        );
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
        } else if (list.get(position) instanceof NoticeItemDetailsRecord) {
            //单据检查项
            return TYEP_ITEM;
        } else if (list.get(position) instanceof ReplyDetailsRecord) {
            //操作记录
            return TYEP_RECORD;
        } else {
            return super.getItemViewType(position);
        }
    }

    /*单据内容*/
    class TypeContent extends RecyclerView.ViewHolder {
        TextView content;

        TypeContent(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
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
        TypeRecord(View itemView) {
            super(itemView);
        }
    }

    /*单据问题项*/
    class TypeProblem extends RecyclerView.ViewHolder {
        private LinearLayout item_problem;
        private TextView notice_list_content;

        TypeProblem(View itemView) {
            super(itemView);
            item_problem = itemView.findViewById(R.id.item_problem);
            notice_list_content = itemView.findViewById(R.id.notice_list_content);
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
        void onClick(int position, String string);
    }

    private onclicktener onclicktener;

    public void setOnclicktener(onclicktener onclicktener) {
        this.onclicktener = onclicktener;
    }
}
