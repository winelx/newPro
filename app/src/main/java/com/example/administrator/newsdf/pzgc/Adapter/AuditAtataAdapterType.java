package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.audit.AuditdetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.Aduio_data;
import com.example.administrator.newsdf.pzgc.callback.AuditDetailsrefreshCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.AuditrecordCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.CameDialogs;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description:任务详情回复内容适配器
 *
 * @author lx
 *         date:2017/12/13 0013.
 *         update: 2018/2/6 0006
 *         version:
 */

public class AuditAtataAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Aduio_data> mDatas;
    String str = null;
    int bright;
    CameDialogs cameDialog;
    AuditdetailsActivity auditdetailsActivity;

    //判断workfragment是否初始化，如果没有，提亮的时候就不用刷新界面
    public AuditAtataAdapterType(Context mContext, boolean status, int bright) {
        this.mContext = mContext;
        this.bright = bright;
        cameDialog = new CameDialogs();
        mDatas = new ArrayList<>();
        auditdetailsActivity = (AuditdetailsActivity) mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.audit_content_type, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        bindContent((Viewholder) holder, position);
    }

    //内容主题
    private void bindContent(final Viewholder holder, final int posotion) {
        //判断是否有图片
        if (mDatas.get(posotion).getAttachments().size() != 0) {
            //有图片展示布局
            holder.audioRec.setVisibility(View.VISIBLE);
        } else {
            holder.audioRec.setVisibility(View.INVISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.audioRec.setLayoutManager(linearLayoutManager);
        RectifierAdapter adapter = new RectifierAdapter(mContext, mDatas.get(posotion).getAttachments(), mDatas.get(posotion).getFilename());
        holder.audioRec.setAdapter(adapter);
        //头像图片
        String url = mDatas.get(posotion).getReplyUserHeaderURL();
        Glide.with(mContext)
                .load(url)
                .into(holder.audioAcathor);
        String status = auditdetailsActivity.getStatus();
        if (status.equals("1")) {
            holder.related.setVisibility(View.GONE);
        } else if (status.equals("2")) {
            holder.related.setVisibility(View.GONE);
        } else {
            holder.related.setVisibility(View.VISIBLE);
        }
        holder.audioName.setText(mDatas.get(posotion).getReplyUserName());
        holder.audioData.setText(mDatas.get(posotion).getUpdateDate());
        holder.audioContent.setText(mDatas.get(posotion).getUploadContent());
        holder.audioAddress.setText(mDatas.get(posotion).getUploadAddr());
        holder.detailsAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskId = auditdetailsActivity.getId();
                OkGo.get(Requests.AUDITTask)
                        .params("taskId", taskId)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    int ret = jsonObject.getInt("ret");
                                    if (ret == 0) {
                                        ToastUtils.showLongToast("通过");
                                        //更新列表界面数据状态
                                        AuditrecordCallbackUtils.updata();
                                        //更新详情界面
                                        AuditDetailsrefreshCallbackUtils.refreshs();
                                        holder.related.setVisibility(View.GONE);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        holder.detailsRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameDialog.setDialog(auditdetailsActivity.getId(), auditdetailsActivity, "输入打回理由", holder.related);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class Viewholder extends RecyclerView.ViewHolder {
        ImageView audioAcathor;
        TextView audioName, audioData, audioContent, audioAddress;
        TextView detailsRejected, detailsAudit;
        LinearLayout related;
        RecyclerView audioRec;

        Viewholder(View itemView) {
            super(itemView);
            audioAcathor = itemView.findViewById(R.id.audit_acathor);
            audioName = itemView.findViewById(R.id.audio_name);
            audioData = itemView.findViewById(R.id.audio_data);
            audioContent = itemView.findViewById(R.id.audio_content);
            audioAddress = itemView.findViewById(R.id.audio_address);
            detailsAudit = itemView.findViewById(R.id.details_audit);
            detailsRejected = itemView.findViewById(R.id.details_rejected);
            related = itemView.findViewById(R.id.related);
            audioRec = itemView.findViewById(R.id.audio_rec);
        }
    }

    public void getdata(ArrayList<Aduio_data> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


}
