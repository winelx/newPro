package com.example.administrator.fengji.pzgc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.activity.MainActivity;
import com.example.administrator.fengji.pzgc.activity.home.TaskdetailsActivity;
import com.example.administrator.fengji.pzgc.bean.AduioData;
import com.example.administrator.fengji.pzgc.callback.BrightCallBackUtils;
import com.example.administrator.fengji.pzgc.callback.BrightCallBackUtils1;
import com.example.administrator.fengji.pzgc.callback.BrightCallBackUtils2;
import com.example.administrator.fengji.pzgc.callback.HideCallbackUtils;
import com.example.administrator.fengji.pzgc.utils.CameDialog;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
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
 * date:2017/12/13 0013.
 * update: 2018/2/6 0006
 * version:
 */

public class RecycleAtataAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private boolean status;
    private ArrayList<AduioData> mDatas;
    String str = null;
    int isSmartProject = -1, bright;
    String taskId;
    CameDialog cameDialog;
    boolean isFavorite;
    //判断workfragment是否初始化，如果没有，提亮的时候就不用刷新界面
    boolean workbright = false;

    public RecycleAtataAdapterType(Context mContext, boolean status, int bright) {
        this.mContext = mContext;
        this.status = status;
        this.bright = bright;
        cameDialog = new CameDialog();
        mDatas = new ArrayList<>();
        MainActivity mian = MainActivity.getInstance();
        try {
            workbright = mian.wbright();
        } catch (NullPointerException e) {
            workbright = false;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.audio_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        bindContent((Viewholder) holder, position);
    }

    //内容主题
    private void bindContent(final Viewholder holder, final int posotion) {
        //判断是否有图片

        if (mDatas.get(posotion).getAttachments().size() > 0) {
            //有图片展示布局
            holder.audioRec.setVisibility(View.VISIBLE);
            holder.audioNotimage.setVisibility(View.GONE);
        } else {
            ToastUtils.showShortToast("");
            holder.audioRec.setVisibility(View.INVISIBLE);
            holder.audioNotimage.setVisibility(View.VISIBLE);
        }
        int size = mDatas.size() - 1;
        if (posotion == size) {
            holder.related.setVisibility(View.VISIBLE);
        } else {
            holder.related.setVisibility(View.GONE);
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
        //提亮等级
        final int SmartProjectType = mDatas.get(posotion).getSmartProjectType();
        final int IssmartProjectType = mDatas.get(posotion).getIsSmartProject();
        TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
        //设置图标
        audio.markimage(SmartProjectType);
        holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
        holder.givealikeText.setTextColor(Color.parseColor("#FFEC8B"));
        holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
        //提亮逻辑
        //未提亮
        if (IssmartProjectType == 0) {
            //如果提亮的等级为0，说明没有提亮
            if (mDatas.get(posotion).isSmartprojectMain1Up() || mDatas.get(posotion).isSmartprojectMain2Up() || mDatas.get(posotion).isSmartprojectMain3Up()) {
                //判断是否有集团提亮
                holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                holder.givealikeText.setTextColor(Color.parseColor("#808080"));
            } else {
                //如果都有没权限，不给提亮按钮
                holder.givealike.setVisibility(View.GONE);
            }
        } else {
            //如果被提亮了
            if (SmartProjectType == 3) {
                //如果等级为3项目提亮，判断是否有集团和分公司权限,如果没有，就判断本级
                if (mDatas.get(posotion).isSmartprojectMain1Up() || mDatas.get(posotion).isSmartprojectMain2Up()) {
                    //如果有其中一种权限，那也显示为未提亮权限
                    holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                    holder.givealikeText.setTextColor(Color.parseColor("#808080"));
                } else {
                    if (mDatas.get(posotion).isSmartprojectMain3Up()) {
                        //判断是否有本级提亮权限
                        holder.givealike.setVisibility(View.VISIBLE);
                        holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
                        holder.givealikeText.setTextColor(Color.parseColor("#FFEC8B"));
                    } else {
                        //都没有权限隐藏
                        holder.givealike.setVisibility(View.GONE);
                    }
                }
            } else if (SmartProjectType == 2) {
                //如果等级为2，为分公司权限 判断是否有分公司权限，
                if (mDatas.get(posotion).isSmartprojectMain1Up()) {
                    //如果有分公司权限，显示为未提亮状态
                    holder.givealikeImage.setBackgroundResource(R.mipmap.givealike);
                    holder.givealikeText.setTextColor(Color.parseColor("#808080"));
                } else {
                    if (mDatas.get(posotion).isSmartprojectMain2Up()) {
                        //判断是否有本级提亮权限
                        holder.givealikeImage.setBackgroundResource(R.mipmap.givealikenew);
                        holder.givealikeText.setTextColor(Color.parseColor("#FFEC8B"));
                    } else {
                        //都没有权限隐藏
                        holder.givealike.setVisibility(View.GONE);
                    }
                }
            } else if (SmartProjectType == 1) {
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
        DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider1.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        holder.audioRec.addItemDecoration(divider1);
        RectifierAdapter adapter = new RectifierAdapter(mContext, mDatas.get(posotion).getAttachments(), mDatas.get(posotion).getFilename());
        adapter.setHasStableIds(true);
        holder.audioRec.setAdapter(adapter);
        holder.audioDataComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                cameDialog.setDialog(audio.getId(), audio, "输入回复内容");
            }
        });
        /**
         * 收藏/取消收藏
         */holder.collection.setOnClickListener(new View.OnClickListener() {
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
                                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                                        if (ret == 0) {
                                            TaskdetailsActivity activity = (TaskdetailsActivity) mContext;
                                            activity.deleteTop();
                                            try {
                                                HideCallbackUtils.removeCallBackMethod();
                                                Dates.disDialog();
                                            } catch (NullPointerException e) {
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } else {
                    OkGo.<String>post(Requests.SAVECOLLECTION)
                            .params("taskId", audio.gettaskId())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        int ret = jsonObject.getInt("ret");
                                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                                        if (ret == 0) {
                                            TaskdetailsActivity activity = (TaskdetailsActivity) mContext;
                                            activity.deleteTop();
                                            try {
                                                HideCallbackUtils.removeCallBackMethod();
                                            } catch (Exception e) {
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    try {
                        Dates.disDialog();
                    } catch (NullPointerException e) {
                    }
                }
            }
        });
        isFavorite = mDatas.get(posotion).getIsFavorite();
        //提亮
        holder.givealike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有权限提亮
                TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                taskId = audio.gettaskId();
                //是否提亮
                //提亮权限，1为集团，2为分公司，3项目
                isSmartProject = mDatas.get(posotion).getIsSmartProject();
                //判断是否提亮，
                if (IssmartProjectType == 0) {
                    //没有提亮
                    if (mDatas.get(posotion).isSmartprojectMain1Up() || mDatas.get(posotion).isSmartprojectMain2Up() || mDatas.get(posotion).isSmartprojectMain3Up()) {
                        //判断是否提亮
                        brightUp();
                    } else {
                        //如果没有权限，那么就无法看到提亮功能
                        ToastUtils.showLongToast("您暂时没有提亮权限哦");
                    }
                } else {
                    //判断当前提亮登录
                    switch (SmartProjectType) {
                        //如果为集团
                        case 1:
                            if (mDatas.get(posotion).isSmartprojectMain1Up()) {
                                //判断是否提亮
                                ToastUtils.showLongToast("您暂时没有提亮权限哦!~");
                            }
                            break;
                        //如果为分公司
                        case 2:
                            if (mDatas.get(posotion).isSmartprojectMain1Up()) {
                                //判断是否提亮
                                brightUp();
                            } else {
                                //如果没有权限，那么就无法看到提亮功能
                                ToastUtils.showLongToast("您暂时没有提亮权限哦");
                            }
                            break;
                        case 3:
                            //项目
                            if (mDatas.get(posotion).isSmartprojectMain1Up() || mDatas.get(posotion).isSmartprojectMain2Up()) {
                                //判断是否提亮
                                if (!isFavorite) {
                                    brightUp();
                                } else {
                                    ToastUtils.showLongToast("您暂时没有提亮权限哦!~");
                                }
                            } else {
                                //如果没有权限，那么就无法看到提亮功能
                                ToastUtils.showLongToast("您暂时没有提亮权限哦");
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        holder.givealike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int smartprojecttype = mDatas.get(posotion).getSmartProjectType();
                if (smartprojecttype > 0) {
                    TaskdetailsActivity audio = (TaskdetailsActivity) mContext;
                    taskId = audio.gettaskId();
                    if (smartprojecttype == 1) {
                        //判断是否有权限，
                        if (mDatas.get(posotion).isSmartprojectMain1Down()) {
                            brightDown();
                        } else {
                            //如果没有权限，那么就无法看到提亮功能
                            ToastUtils.showLongToast("您暂时没有撤亮权限哦");
                        }
                    } else if (smartprojecttype == 2) {
                        //判断是否有权限，
                        if (mDatas.get(posotion).isSmartprojectMain2Down() || mDatas.get(posotion).isSmartprojectMain1Down()) {
                            brightDown();
                        } else {
                            //如果没有权限，那么就无法看到提亮功能
                            ToastUtils.showLongToast("您暂时没有撤亮权限哦");
                        }
                    } else if (smartprojecttype == 3) {
                        //判断是否有权限，
                        if (mDatas.get(posotion).isSmartprojectMain3Down() || mDatas.get(posotion).isSmartprojectMain2Down() || mDatas.get(posotion).isSmartprojectMain1Down()) {
                            brightDown();
                        } else {
                            //如果没有权限，那么就无法看到提亮功能
                            ToastUtils.showLongToast("您暂时没有撤亮权限哦");
                        }
                    } else {
                        ToastUtils.showLongToast("必须提亮才能撤亮");
                    }
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
        private TextView audioName, audioContent, audioData, audioAddress, commentCount, givealikeText, collectionText;
        private RecyclerView audioRec;
        private LinearLayout audioDataComm, givealike;
        private ImageView givealikeImage, audioNotimage, collectionImage;
        private LinearLayout collection, related;

        Viewholder(View itemView) {
            super(itemView);
            related = itemView.findViewById(R.id.related);
            //头像
            audioAcathor = itemView.findViewById(R.id.audio_acathor);
            //文字内容
            audioName = itemView.findViewById(R.id.audio_name);
            audioContent = itemView.findViewById(R.id.audio_content);
            audioData = itemView.findViewById(R.id.audio_data);
            audioAddress = itemView.findViewById(R.id.audio_address);
            //图片
            audioRec = itemView.findViewById(R.id.audio_rec);
            //评论
            audioDataComm = itemView.findViewById(R.id.audio_data_comm);
            //评论数量
            commentCount = itemView.findViewById(R.id.audio_data_commom_count);
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

    public void getdata(ArrayList<AduioData> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    private void brightDown() {
        OkGo.<String>post(Requests.SartProjectdown)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            ToastUtils.showShortToast(jsonObject.getString("msg"));
                            if (ret == 0) {
                                TaskdetailsActivity activity = (TaskdetailsActivity) mContext;
                                activity.deleteTop();
                                //如果是刷新界面
                                try {
                                    BrightCallBackUtils.CallBackMethod();
                                    BrightCallBackUtils1.CallBackMethod();
                                    BrightCallBackUtils2.CallBackMethod();
                                } catch (NullPointerException e) {

                                }
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

    private void brightUp() {
        OkGo.<String>post(Requests.SartProjectup)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            ToastUtils.showShortToast(jsonObject.getString("msg"));
                            if (ret == 0) {
                                TaskdetailsActivity activity = (TaskdetailsActivity) mContext;
                                //判断是否是从点亮界面进入
                                //如果是刷新界面
                                try {
                                    BrightCallBackUtils.CallBackMethod();
                                    BrightCallBackUtils1.CallBackMethod();
                                    BrightCallBackUtils2.CallBackMethod();
                                } catch (NullPointerException e) {
                                }

                                activity.deleteTop();
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
