package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.Aduio_comm;
import com.example.administrator.newsdf.bean.Aduio_content;
import com.example.administrator.newsdf.bean.Aduio_data;
import com.example.administrator.newsdf.utils.Dates;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/13 0013.
 * 任务详情
 */

public class AudioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_XBANNR = 0xff01;
    public static final int TYPE_GRID = 0xff02;
    public static final int TYPE_LIST = 0xff03;

    private ArrayList<Aduio_content> content;
    private ArrayList<Aduio_data> datas;
    private ArrayList<Aduio_comm> comms;

    private RecycleAtataAdapterType dataTypeAdapter;
    private RecycleCommAdapterType commlistBTypeAdapter;
    private Context mContext;
    private boolean status = false;
    int bright;

    //构造
    public AudioAdapter(Context mContext) {
        this.mContext = mContext;
        content = new ArrayList<>();
        datas = new ArrayList<>();
        comms = new ArrayList<>();
    }

    //type的类型分类
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_XBANNR:
                return new TypeBannerHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.handover_content, parent, false));
            case TYPE_GRID:
                return new TypeGridHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.handover_part_item, parent, false));
            case TYPE_LIST:
                return new TypeListHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.handover_list_item, parent, false));
            default:
                return null;
        }
    }

    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeBannerHolder && content.size() != 0) {
            bindBAnner((TypeBannerHolder) holder, position);
        } else if (holder instanceof TypeGridHolder && datas.size() != 0) {
            bindGrid((TypeGridHolder) holder);
        } else if (holder instanceof TypeListHolder && comms.size() != 0) {
            bindList((TypeListHolder) holder);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    //获取itemtype类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_XBANNR;
        } else if (position == 1) {
            return TYPE_GRID;
        } else if (position == 2) {
            return TYPE_LIST;
        } else {
            return TYPE_LIST;
        }
    }

    //控件数据处理
    private void bindBAnner(TypeBannerHolder holder, final int posotion) {
        if (content.size() != 0) {
            holder.linearLayout.setVisibility(View.VISIBLE);
            //标题
            if (content.get(posotion).getName().length() != 0) {
                holder.detailsTitle.setText(content.get(posotion).getName());
            } else {
                holder.detailsTitle.setText("主动上传任务");
            }
            //创建时间
            String data = null;
            try {
                data = Dates.datato(content.get(posotion).getCreateDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.detailsContent.setText(content.get(posotion).getContent());
            holder.detailsFixedData.setText(content.get(posotion).getBackdata());
            holder.details_checkStandard.setText(content.get(posotion).getCheckStandard());
            //转交人
            holder.detailsUser.setText(content.get(posotion).getLeaderName());
            if (content.get(posotion).getStatus().equals("0")) {
                holder.detailsData.setText(content.get(posotion).getCreateDate() + "  已推送：" + data);
                //状态
                holder.detailsBoolean.setText("未完成");
                //状态
                holder.detailsBoolean.setTextColor(mContext.getResources().getColor(R.color.Orange));

            } else {
                holder.detailsData.setText(content.get(posotion).getCreateDate());
                //状态
                holder.detailsBoolean.setText("已完成");
                //状态 finish_green
                holder.detailsBoolean.setTextColor(mContext.getResources().getColor(R.color.finish_green));

            }// 转交说明
            holder.handoverStatusDescription.setText(content.get(posotion).getCreateDate());
            holder.detailsFixedBoolean.setText("部位状态:");
        } else {
            holder.linearLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 提交数据
     *
     * @param holder
     */
    private void bindGrid(TypeGridHolder holder) {
        if (datas.size() != 0) {
            holder.setpin.setVisibility(View.VISIBLE);
            holder.dataRec.setLayoutManager(new LinearLayoutManager(holder.dataRec.getContext(), LinearLayoutManager.VERTICAL, false));
            dataTypeAdapter = new RecycleAtataAdapterType(mContext, status, bright);
            holder.dataRec.setAdapter(dataTypeAdapter);
            dataTypeAdapter.getdata(datas);
        } else {
            holder.setpin.setVisibility(View.GONE);
            holder.dataRec.setVisibility(View.GONE);
        }
    }

    /**
     * 评论
     *
     * @param holder
     */
    private void bindList(TypeListHolder holder) {
        if (comms.size() != 0) {
            holder.textView.setVisibility(View.VISIBLE);
            holder.ListRec.setLayoutManager(new GridLayoutManager(holder.
                    ListRec.getContext(), 1, GridLayoutManager.VERTICAL, false));
            commlistBTypeAdapter = new RecycleCommAdapterType(mContext);
            holder.ListRec.setAdapter(commlistBTypeAdapter);
            holder.ListRec.setNestedScrollingEnabled(false);
            commlistBTypeAdapter.getComm(comms);
        } else {
            holder.textView.setVisibility(View.GONE);
            holder.ListRec.setVisibility(View.GONE);
        }

    }

    //ViewHolder初始化控件
    public class TypeBannerHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView detailsTitle, detailsData, details_checkStandard,
                detailsUser, detailsBoolean,
                handoverStatusDescription, handoverHuifu,
                detailsContent, detailsFixedData, handoversText, detailsFixedBoolean;

        public TypeBannerHolder(View itemView) {
            super(itemView);
            detailsFixedBoolean = itemView.findViewById(R.id.details_fixed_boolean);
            details_checkStandard = itemView.findViewById(R.id.details_checkStandard);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            detailsTitle = itemView.findViewById(R.id.details_title);
            detailsData = itemView.findViewById(R.id.details_data);
            detailsFixedData = itemView.findViewById(R.id.details_end_data);
            detailsUser = itemView.findViewById(R.id.details_user);
            detailsBoolean = itemView.findViewById(R.id.details_boolean);
            handoverStatusDescription = itemView.findViewById(R.id.handover_status_description);
            detailsContent = itemView.findViewById(R.id.details_content);

//            handover_huifu = itemView.findViewById(R.id.handover_fhui);
        }
    }

    public class TypeGridHolder extends RecyclerView.ViewHolder {
        RecyclerView dataRec;
        TextView setpin;

        public TypeGridHolder(View itemView) {
            super(itemView);
            dataRec = (RecyclerView) itemView.findViewById(R.id.handover_part_item);
            setpin = itemView.findViewById(R.id.handovers_text);
        }
    }

    public class TypeListHolder extends RecyclerView.ViewHolder {
        RecyclerView ListRec;
        TextView textView;

        public TypeListHolder(View itemView) {
            super(itemView);
            ListRec = (RecyclerView) itemView.findViewById(R.id.handover_part_item);
            textView = itemView.findViewById(R.id.handover_text);
        }
    }

    /**
     * 数据源
     */
    public void setmBanner(ArrayList<Aduio_content> content, ArrayList<Aduio_data> datas, ArrayList<Aduio_comm> comms) {
        this.content = content;
        this.datas = datas;
        this.comms = comms;
        notifyDataSetChanged();
    }


}