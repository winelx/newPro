package com.example.administrator.newsdf.pzgc.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.DialogRecAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.TaskdetailsActivity;
import com.example.administrator.newsdf.pzgc.callback.DetailsCallbackUtils;
import com.example.baselibrary.utils.Requests;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.post;

/**
 * Created by Administrator on 2018/4/12 0012.
 * 评论回复
 */

public class CameDialog {
    private Dialog mCameraDialog;
    private static RecyclerView dialog_rec;
    public static DialogRecAdapter Dialogadapter;
    public static ArrayList<String> path = new ArrayList<>();
    private static final int IMAGE_PICKER = 101;
    private ArrayList<File> files;

    public void setDialog(final String wtMainid, final Activity activity, String str) {
        mCameraDialog = new Dialog(activity, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(activity).inflate(
                R.layout.dialog_custom, null);
        //初始化视图
        final Button send = (Button) root.findViewById(R.id.par_button);
        final EditText editext = (EditText) root.findViewById(R.id.par_editext);
        final ImageView imageView = (ImageView) root.findViewById(R.id.par_image);
        dialog_rec = root.findViewById(R.id.dialog_rec);
        editext.setHint(str);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dialog_rec.setLayoutManager(linearLayoutManager);
        Dialogadapter = new DialogRecAdapter(activity, path);
        dialog_rec.setAdapter(Dialogadapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskdetailsActivity.getInstance(), ImageGridActivity.class);
                TaskdetailsActivity.getInstance().startActivityForResult(intent, IMAGE_PICKER);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editext.getText().toString();
                if (str == null || str.isEmpty()) {
                    ToastUtils.showShortToast("回复不能为空");
                } else {
                        Dates.getDialogs(activity, "上传数据中");
                        send.setClickable(false);
                        send.setTextColor(Color.parseColor("#F0F0F0"));
                        files = new ArrayList<>();
                        PostRequest mRequest = post(Requests.SAVECOMMENT)
                                //强制使用multipart/form-data 表单提交
                                .isMultipart(true)
                                .params("taskId", wtMainid)
                                .params("content", str);
                        if (path.size() != 0) {
                            for (int i = 0; i < path.size(); i++) {
                                files.add(new File(path.get(i)));
                            }
                            mRequest.addFileParams("files", files)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            Dates.disDialog();
                                            send.setClickable(true);
                                            send.setTextColor(Color.parseColor("#5096F8"));
                                            try {
                                                JSONObject jsonObject = new JSONObject(s);
                                                int ret = jsonObject.getInt("ret");
                                                //提示消息
                                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                                                if (ret == 0) {
                                                    path.clear();
                                                    DetailsCallbackUtils.dohomeCallBackMethod();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            mCameraDialog.dismiss();
                                            Dates.disDialog();
                                        }

                                        @Override
                                        public void onError(Call call, Response response, Exception e) {
                                            super.onError(call, response, e);
                                            Dates.disDialog();
                                            send.setClickable(true);
                                            send.setTextColor(Color.parseColor("#5096F8"));

                                        }
                                    });
                        } else {
                            mRequest.execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Dates.disDialog();
                                    send.setClickable(true);
                                    send.setTextColor(Color.parseColor("#5096F8"));
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        int ret = jsonObject.getInt("ret");
                                        //提示消息
                                        ToastUtils.showLongToast(jsonObject.getString("msg"));
                                        if (ret == 0) {
                                            path.clear();
                                            DetailsCallbackUtils.dohomeCallBackMethod();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    mCameraDialog.dismiss();
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    Dates.disDialog();
                                    send.setClickable(true);
                                    send.setTextColor(Color.parseColor("#5096F8"));
                                }
                            });
                        }

                }
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        // 添加动画
        dialogWindow.setWindowAnimations(R.style.DialogAnimation);
        // 获取对话框当前的参数值
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // 新位置X坐标
        lp.x = 0;
        //新位置Y坐标
        lp.y = 0;
        // 宽度
        lp.width = (int) activity.getResources().getDisplayMetrics().widthPixels;
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        // 透明度
        lp.alpha = 8f;
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
        activity.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    editext.requestFocus();
                    imm.showSoftInput(editext, 0);
                }
            }
        }, 0);
    }
}
