package com.example.administrator.newsdf.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.example.administrator.newsdf.callback.BrightCallBackUtils;
import com.example.administrator.newsdf.callback.HideCallbackUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.CameDialog;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.camera.ToastUtils.showLongToast;


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
    String str = null;
    int isSmartProject = -1, bright;
    String taskId;
    CameDialog cameDialog;
    boolean isFavorite;

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
        //是否被提亮
        isSmartProject = mDatas.get(posotion).getIsSmartProject();
        //提亮等级
        final int SmartProjectType = mDatas.get(posotion).getSmartProjectType();
        TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
        //设置图标
        audio.markimage(SmartProjectType);
        holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
        holder.givealikeText.setTextColor(Color.parseColor("#FFEC8B"));
        holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
        //提亮逻辑
        //未提亮
        if (SmartProjectType == 0) {
            //如果提亮的等级为0，说明没有提亮
            if (mDatas.get(posotion).isSmartprojectMain1Up()) {
                //判断是否有集团提亮
                holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                holder.givealikeText.setTextColor(Color.parseColor("#808080"));
            } else if (mDatas.get(posotion).isSmartprojectMain2Up()) {
                //判断是否有分公司提亮
                holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                holder.givealikeText.setTextColor(Color.parseColor("#808080"));
            } else if (mDatas.get(posotion).isSmartprojectMain2Up()) {
                //判断是否有项目提亮
                holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                holder.givealikeText.setTextColor(Color.parseColor("#808080"));
            } else {
                //如果都有权限，不给提亮按钮
                holder.givealike.setVisibility(View.GONE);
            }
        } else {
            //如果被提亮了
            if (SmartProjectType == 3) {
                //如果等级为3项目提亮，判断是否有集团和分公司权限
                if (mDatas.get(posotion).isSmartprojectMain1Up() || mDatas.get(posotion).isSmartprojectMain2Up() || mDatas.get(posotion).isSmartprojectMain3Up()) {
                    //如果有其中一种权限，那也显示为未提亮权限
                    holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                    holder.givealikeText.setTextColor(Color.parseColor("#808080"));
                } else if (mDatas.get(posotion).isSmartprojectMain1Up()) {
                    //判断是否有本级提亮权限
                    holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
                    holder.givealikeText.setTextColor(Color.parseColor("#FFEC8B"));
                } else {
                    //都没有权限隐藏
                    holder.givealike.setVisibility(View.GONE);
                }
            } else if (SmartProjectType == 2) {
                //如果等级为2，为分公司权限 判断是否有分公司权限，
                if (mDatas.get(posotion).isSmartprojectMain1Up()) {
                    //如果有分公司权限，显示为未提亮状态
                    holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                    holder.givealikeText.setTextColor(Color.parseColor("#808080"));
                } else if (mDatas.get(posotion).isSmartprojectMain2Up() || mDatas.get(posotion).isSmartprojectMain2Up()) {
                    //判断是否有本级提亮权限
                    holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
                    holder.givealikeText.setTextColor(Color.parseColor("#FFEC8B"));
                } else {
                    //都没有权限隐藏
                    holder.givealike.setVisibility(View.GONE);
                }
            } else {

                //提亮权限为1.集团提亮
                if (mDatas.get(posotion).isSmartprojectMain1Up()) {
                    //如果有集团权限，显示为提亮状态
                    holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
                    holder.givealikeText.setTextColor(Color.parseColor("#FFEC8B"));
                } else {
                    //都没有权限隐藏
                    holder.givealike.setVisibility(View.GONE);
                }
            }
        }
        //收藏
        isFavorite = mDatas.get(posotion).getIsFavorite();
        if (isFavorite) {
            holder.collectionImage.setBackgroundResource(R.mipmap.collectionup);
            holder.collectionText.setTextColor(Color.parseColor("#FFEC8B"));
        } else {
            holder.collectionImage.setBackgroundResource(R.mipmap.collectiondown);
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
        /**
         * 收藏/取消收藏
         */
        holder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                Dates.getDialogs(audio, "请求数据中");
                boolean isFavorites = mDatas.get(posotion).getIsFavorite();
                if (isFavorites) {
                    //请求数据，返回状态
                    OkGo.<String>post(Requests.DELETECOLLECTION)
                            .params("taskId", audio.gettaskId())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        int ret = jsonObject.getInt("ret");
                                        if (ret == 0) {
                                            ToastUtils.showLongToast("取消收藏");
                                            holder.collectionImage.setBackgroundResource(R.mipmap.collectiondown);
                                            holder.collectionText.setTextColor(Color.parseColor("#808080"));
                                            mDatas.get(posotion).setIsFavorite(false);
                                            HideCallbackUtils.removeCallBackMethod();
                                            Dates.disDialog();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Dates.disDialog();
                } else {
//                    //请求数据，返回状态
                    ToastUtils.showLongToast(isFavorite + "");
                    OkGo.<String>post(Requests.SAVECOLLECTION)
                            .params("taskId", audio.gettaskId())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        int ret = jsonObject.getInt("ret");
                                        if (ret == 0) {
                                            ToastUtils.showLongToast("收藏");
                                            holder.collectionImage.setBackgroundResource(R.mipmap.collectionup);
                                            holder.collectionText.setTextColor(Color.parseColor("#FFEC8B"));
                                            mDatas.get(posotion).setIsFavorite(true);
                                            HideCallbackUtils.removeCallBackMethod();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                    Dates.disDialog();
                }
            }
        });
        //提亮
        holder.givealike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有权限提亮
                TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                taskId = audio.gettaskId();
                //是否提亮
                //提亮权限，1为集团，2为分公司，3项目
                int smartprojecttype = mDatas.get(posotion).getSmartProjectType();
                if (smartprojecttype == 1) {
                    //判断是否有权限，
                    if (mDatas.get(posotion).isSmartprojectMain1Up()) {
                        brightUp();
                    } else {
                        //如果没有权限，那么就无法看到提亮功能
                        ToastUtils.showLongToast("没有操作权限");
                    }
                } else if (smartprojecttype == 2) {
                    //判断是否有权限，
                    if (mDatas.get(posotion).isSmartprojectMain2Up()) {
                        brightUp();
                    } else {
                        //如果没有权限，那么就无法看到提亮功能
                        ToastUtils.showLongToast("没有操作权限");
                    }
                } else if (smartprojecttype == 3) {
                    //判断是否有权限，
                    if (mDatas.get(posotion).isSmartprojectMain3Up()) {
                        brightUp();
                    } else {
                        //如果没有权限，那么就无法看到提亮功能
                        ToastUtils.showLongToast("没有操作权限");
                    }
                } else {
                    brightUp();
                }
            }
        });
        holder.givealike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                taskId = audio.gettaskId();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("撤亮当前任务?")
                        .setCancelable(false)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                int smartprojecttype = mDatas.get(posotion).getSmartProjectType();
                                if (smartprojecttype == 1) {
                                    //判断是否有权限，
                                    if (mDatas.get(posotion).isSmartprojectMain1Down()) {
                                        brightDown();
                                    } else {
                                        //如果没有权限，那么就无法看到提亮功能
                                        ToastUtils.showLongToast("没有操作权限");
                                    }
                                } else if (smartprojecttype == 2) {
                                    //判断是否有权限，
                                    if (mDatas.get(posotion).isSmartprojectMain2Down() || mDatas.get(posotion).isSmartprojectMain1Down()) {
                                        brightDown();
                                    } else {
                                        //如果没有权限，那么就无法看到提亮功能
                                        ToastUtils.showLongToast("没有操作权限");
                                    }
                                } else if (smartprojecttype == 3) {
                                    //判断是否有权限，
                                    if (mDatas.get(posotion).isSmartprojectMain3Down() || mDatas.get(posotion).isSmartprojectMain2Down() || mDatas.get(posotion).isSmartprojectMain1Down()) {
                                        brightDown();
                                    } else {
                                        //如果没有权限，那么就无法看到提亮功能
                                        ToastUtils.showLongToast("没有操作权限");
                                    }
                                } else {
                                    ToastUtils.showLongToast("没有操作权限");
                                }

                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.show();

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
        private TextView audioName, audioContent, audioData, audioAddress, commentCount, givealikeText, collectionText;
        private RecyclerView audioRec;
        private LinearLayout audioDataComm, givealike;
        private ImageView givealikeImage, audioNotimage, collectionImage;
        private LinearLayout collection;

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
            //收藏
            collection = itemView.findViewById(R.id.collection);
            //收藏图标
            collectionImage = itemView.findViewById(R.id.collection_image);
            collectionText = itemView.findViewById(R.id.collection_text);
        }
    }

    public void getdata(ArrayList<Aduio_data> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void brightDown() {
        OkGo.<String>post(Requests.SartProjectdown)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                                TaskdetailsActivity activity = (TaskdetailsActivity) mContext;
                                activity.deleteTop();
                              BrightCallBackUtils.removeCallBackMethod();
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

    public void brightUp() {
        OkGo.<String>post(Requests.SartProjectup)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                showLongToast("提亮成功");
                                TaskdetailsActivity activity = (TaskdetailsActivity) mContext;
                                activity.deleteTop();
                                 BrightCallBackUtils.removeCallBackMethod();
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
