package com.example.administrator.newsdf.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.DialogRecAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.TaskdetailsActivity;
import com.example.administrator.newsdf.callback.DetailsCallbackUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/12 0012.
 * 评论回复
 */

public class CameDialog {
    private static Dialog mCameraDialog;
    private static RecyclerView dialog_rec;
    public static DialogRecAdapter Dialogadapter;
    public static ArrayList<String> path  = new ArrayList<>();
    private static final int IMAGE_PICKER = 101;

    public static void setDialog(final String wtMainid, final Activity activity) {
        mCameraDialog = new Dialog(activity, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(activity).inflate(
                R.layout.dialog_custom, null);
        //初始化视图
        final Button send = (Button) root.findViewById(R.id.par_button);
        final EditText editext = (EditText) root.findViewById(R.id.par_editext);
        final ImageView imageView = (ImageView) root.findViewById(R.id.par_image);
        final TextView tvNetSpeed=root.findViewById(R.id.tvNetSpeed);
        dialog_rec = root.findViewById(R.id.dialog_rec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dialog_rec.setLayoutManager(linearLayoutManager);
        Dialogadapter = new DialogRecAdapter(activity, path, true);
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
                    ArrayList<File> files = new ArrayList<>();
                    for (int i = 0; i < path.size(); i++) {
                        files.add(new File(path.get(i)));
                    }
                    OkGo.<String>post(Requests.SAVECOMMENT)
                            .params("taskId", wtMainid)
                            .addFileParams("files", files)
                            .params("content", str)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    LogUtil.i("CameDialog",s);
                                    path.clear();
                                    DetailsCallbackUtils.dohomeCallBackMethod();
                                    mCameraDialog.dismiss();
                                }
                                //进度条
                                @Override
                                public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                    super.upProgress(currentSize, totalSize, progress, networkSpeed);
                                    tvNetSpeed.setText("已上传" + currentSize / 1024 / 1024 + "MB, 共" + totalSize / 1024 / 1024 + "MB;");
                                }
                            });
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
