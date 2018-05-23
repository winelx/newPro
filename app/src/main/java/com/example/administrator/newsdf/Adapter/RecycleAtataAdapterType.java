package com.example.administrator.newsdf.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.administrator.newsdf.activity.home.TaskdetailsActivity;
import com.example.administrator.newsdf.bean.Aduio_data;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.CameDialog;
import com.example.administrator.newsdf.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
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

public class RecycleAtataAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private boolean status;
    private ArrayList<Aduio_data> mDatas;
    TaskdetailsActivity activity;
    String str = null;
    int isSmartProject, bright;
    String taskId;
    CameDialog cameDialog;

    public RecycleAtataAdapterType(Context mContext, boolean status, int bright) {
        this.mContext = mContext;
        this.status = status;
        this.bright = bright;
        cameDialog = new CameDialog();
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.audio_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindContent((Viewholder) holder, position);
    }

    //内容主题
    private void bindContent(final Viewholder holder, final int posotion) {
        //判断是否有图片
        if (mDatas.get(posotion).getAttachments().size() != 0) {
            //有图片展示布局
            holder.audioRec.setVisibility(View.VISIBLE);
            //隐藏没有图片的提示图
            holder.audioNotimage.setVisibility(View.GONE);
        } else {
            holder.audioRec.setVisibility(View.INVISIBLE);
            holder.audioNotimage.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext)
                .load(mDatas.get(posotion).getUserpath())
                .into(holder.audioAcathor);
        //上传人
        holder.audioName.setText(mDatas.get(posotion).getReplyUserName());
        //附加内容
        holder.audioContent.setText(mDatas.get(posotion).getUploadContent());
        //上传时间
        holder.audioData.setText(mDatas.get(posotion).getUpdateDate());
        //上传地点
        holder.audioAddress.setText(mDatas.get(posotion).getUploadAddr());
        //评论条数
        holder.commentCount.setText(mDatas.get(posotion).getCommentCount());
        //评论
        isSmartProject = mDatas.get(posotion).getIsSmartProject();
        //判断默认状态是提亮还是降亮
        if (isSmartProject == 0) {
            if (mDatas.get(posotion).isUp()) {
                holder.givealikeText.setText("提亮");
                holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
            }else {
                holder.givealike.setVisibility(View.GONE);
            }

        } else {
            //判断是否有权限降亮
            if (mDatas.get(posotion).isDowm()) {
                holder.givealikeText.setText("降亮");
                holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
            }else {
                holder.givealike.setVisibility(View.GONE);
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.audioRec.setLayoutManager(linearLayoutManager);
        RectifierAdapter adapter = new RectifierAdapter(mContext, mDatas.get(posotion).getAttachments(), mDatas.get(posotion).getFilename());
        holder.audioRec.setAdapter(adapter);
        holder.audioDataComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                cameDialog.setDialog(audio.getId(), audio);
            }
        });
        //提亮
        holder.givealike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有权限提亮
                if (mDatas.get(posotion).isUp()) {
                    TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                    taskId = audio.gettaskId();
                    if (isSmartProject == 0) {
                        if (mDatas.get(posotion).isUp()) {
                            OkGo.<String>post(Requests.SartProjectup)
                                    .params("taskId", taskId)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(s);
                                                ToastUtils.showLongToast(s);
                                                int ret = jsonObject.getInt("ret");
                                                if (ret == 0) {
                                                    ToastUtils.showLongToast("成功");
                                                    TaskdetailsActivity activity = (TaskdetailsActivity) mContext;
                                                    activity.deleteTop();
                                                    holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(Call call, Response response, Exception e) {
                                            super.onError(call, response, e);

                                        }
                                    });
                        }
                    }

                }
            }
        });
        holder.givealike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                taskId = audio.gettaskId();
                if (mDatas.get(posotion).isDowm()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("撤亮当前任务?")
                            .setCancelable(false)
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    OkGo.<String>post(Requests.SartProjectdown)
                                            .params("taskId", taskId)
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(String s, Call call, Response response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(s);
                                                        ToastUtils.showLongToast(s);
                                                        int ret = jsonObject.getInt("ret");
                                                        if (ret == 0) {
                                                            ToastUtils.showLongToast("成功");
                                                            isSmartProject = 1;
                                                            holder.givealikeText.setText("提亮");
                                                            holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onError(Call call, Response response, Exception e) {
                                                    super.onError(call, response, e);

                                                }
                                            });
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                } else {
                    ToastUtils.showLongToast("没有权限");
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class Viewholder extends RecyclerView.ViewHolder {
        private CircleImageView audioAcathor;
        private TextView audioName, audioContent, audioData, audioAddress, commentCount, givealikeText;
        private RecyclerView audioRec;
        private LinearLayout audioDataComm, givealike;
        private ImageView givealikeImage, audioNotimage;

        public Viewholder(View itemView) {
            super(itemView);
            //头像
            audioAcathor = (CircleImageView) itemView.findViewById(R.id.audio_acathor);
            //文字内容
            audioName = (TextView) itemView.findViewById(R.id.audio_name);
            audioContent = (TextView) itemView.findViewById(R.id.audio_content);
            audioData = (TextView) itemView.findViewById(R.id.audio_data);
            audioAddress = (TextView) itemView.findViewById(R.id.audio_address);
            //图片
            audioRec = (RecyclerView) itemView.findViewById(R.id.audio_rec);
            //评论
            audioDataComm = (LinearLayout) itemView.findViewById(R.id.audio_data_comm);
            //评论数量
            commentCount = (TextView) itemView.findViewById(R.id.audio_data_commom_count);
            //提亮
            givealike = itemView.findViewById(R.id.givealike);
            //提交图标
            givealikeImage = itemView.findViewById(R.id.givealike_image);
            //提亮文字
            givealikeText = itemView.findViewById(R.id.givealike_text);
            //没有图片
            audioNotimage = itemView.findViewById(R.id.audio_notimage);
        }
    }

    public void getdata(ArrayList<Aduio_data> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


}
