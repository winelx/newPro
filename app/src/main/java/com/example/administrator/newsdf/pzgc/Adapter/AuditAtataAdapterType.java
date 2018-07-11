package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.audit.AuditdetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.Aduio_data;
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

    //判断workfragment是否初始化，如果没有，提亮的时候就不用刷新界面
    public AuditAtataAdapterType(Context mContext, boolean status, int bright) {
        this.mContext = mContext;
        this.bright = bright;
        cameDialog = new CameDialogs();
        mDatas = new ArrayList<>();

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
        String url = mDatas.get(posotion).getReplyUserHeaderURL();
        Glide.with(mContext)
                .load(url)
                .into(holder.audio_acathor);
        holder.audio_name.setText(mDatas.get(posotion).getReplyUserName());
        holder.audio_data.setText(mDatas.get(posotion).getUpdateDate());
        holder.audio_content.setText(mDatas.get(posotion).getUploadContent());
        holder.audio_address.setText(mDatas.get(posotion).getUploadAddr());
        holder.details_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuditdetailsActivity auditdetailsActivity = (AuditdetailsActivity) mContext;
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
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        holder.details_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuditdetailsActivity audio = (AuditdetailsActivity) mContext;
                cameDialog.setDialog(audio.getId(), audio, "输入打回理由");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class Viewholder extends RecyclerView.ViewHolder {
        ImageView audio_acathor;
        TextView audio_name, audio_data, audio_content, audio_address;
        TextView details_rejected, details_audit;

        Viewholder(View itemView) {
            super(itemView);
            audio_acathor = itemView.findViewById(R.id.audit_acathor);
            audio_name = itemView.findViewById(R.id.audio_name);
            audio_data = itemView.findViewById(R.id.audio_data);
            audio_content = itemView.findViewById(R.id.audio_content);
            audio_address = itemView.findViewById(R.id.audio_address);
            details_audit = itemView.findViewById(R.id.details_audit);
            details_rejected = itemView.findViewById(R.id.details_rejected);
        }
    }

    public void getdata(ArrayList<Aduio_data> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


}
