package com.example.administrator.newsdf.pzgc.special.programme.fragment;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.FiletypeAdapter;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.special.programme.activity.ProgrammeApprovalActivity;
import com.example.administrator.newsdf.pzgc.special.programme.activity.ProgrammeDetailsActivity;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.DialogUtils;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 15:23
 * @说明 施工方案详情:内容
 **/

public class ProgrammeDetailsContentFragment extends LazyloadFragment implements View.OnClickListener {
    private Button approval;
    private TextView programmename, ascriptionorgname, ascriptionloedger, ascriptiontype;
    private TextView username, applydata, describe, tips;
    private RecyclerView proRecycler;
    private FiletypeAdapter filetypeAdapter;
    private ProgrammeDetailsActivity activity;
    private String isAssign;

    @Override
    protected int setContentView() {
        return R.layout.fragment_programmedetails_content;
    }

    @Override
    protected void init() {
        findid();
        activity = (ProgrammeDetailsActivity) getContext();
        approval = (Button) findViewById(R.id.approval);
        approval.setOnClickListener(this);

        LiveDataBus.get().with("prodetails_content", ProDetails.class)
                .observe(this, new Observer<ProDetails>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onChanged(@Nullable ProDetails proDetails) {
                        isAssign = proDetails.getIsAssign();
                        programmename.setText("专项施工方案名称：" + proDetails.getData().getSpecialItemDelName());
                        ascriptionorgname.setText("所属标段：" + proDetails.getData().getOrgName());
                        ascriptionloedger.setText("所属管理台账：" + proDetails.getData().getSpecialItemMainName());
                        ascriptiontype.setText("所属类型：" + proDetails.getData().getSpecialItemBaseName());
                        String CreatePerosn = proDetails.getData().getCreatePerson();
                        if (CreatePerosn == null) {
                            username.setText("编制人：");
                        } else {
                            username.setText("编制人：" + proDetails.getData().getCreatePerson());
                        }
                        if (TextUtils.isEmpty(proDetails.getData().getSubmitDate())) {
                            applydata.setText("申报日期：");
                        } else {
                            applydata.setText("申报日期：" + proDetails.getData().getSubmitDate().substring(0, 10));
                        }
                        if (TextUtils.isEmpty(proDetails.getData().getProjectDescription())) {
                            describe.setText("项目简述：" + proDetails.getData().getProjectDescription());
                        } else {
                            describe.setText("项目简述：" + proDetails.getData().getProjectDescription());
                        }
                        //判断是否有权限操作
                        int per = proDetails.getPermission();
                        if (per == 0 | per == 2) {
                            approval.setVisibility(View.GONE);
                        } else {
                            approval.setVisibility(View.VISIBLE);
                        }
                        //判断是否需要显示提示
                        if (TextUtils.isEmpty(proDetails.getMsg())) {
                            tips.setVisibility(View.GONE);
                        } else {
                            tips.setVisibility(View.VISIBLE);
                            tips.setText(proDetails.getMsg());
                        }
                        List<ProDetails.FileResultListBean> list = proDetails.getFileResultList();
                        ArrayList<FileTypeBean> filelist = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            filelist.add(new FileTypeBean(list.get(i).getFileName(), Requests.networks + list.get(i).getFileUrl(), ""));
                        }
                        filetypeAdapter.getData(filelist);
                    }
                });
    }

    private void findid() {
        //提示
        tips = (TextView) findViewById(R.id.tips);
        //方案名称
        programmename = (TextView) findViewById(R.id.programmename);
        //所属组织名称
        ascriptionorgname = (TextView) findViewById(R.id.ascriptionorgname);
        //所属台账
        ascriptionloedger = (TextView) findViewById(R.id.ascriptionloedger);
        //所属类型
        ascriptiontype = (TextView) findViewById(R.id.ascriptiontype);
        //编制人
        username = (TextView) findViewById(R.id.username);
        //申报日期
        applydata = (TextView) findViewById(R.id.applydata);
        //简述
        describe = (TextView) findViewById(R.id.describe);
        //附件
        proRecycler = (RecyclerView) findViewById(R.id.pro_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        proRecycler.setLayoutManager(linearLayoutManager);
        filetypeAdapter = new FiletypeAdapter(getContext(), new ArrayList<>());
        proRecycler.setAdapter(filetypeAdapter);
        filetypeAdapter.setitemOnClickListener(new FiletypeAdapter.ItemOnClickListener() {
            @Override
            public void onclick(FileTypeBean bean) {
                downloadcad(bean.getUrl(), bean.getName());

            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.approval:
                Intent intent = new Intent(getContext(), ProgrammeApprovalActivity.class);
                intent.putExtra("orgid", activity.getOrgid());
                intent.putExtra("id", activity.getId());
                intent.putExtra("isAssign", isAssign);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void downloadcad(String url, String name) {
        //创建下载路径
        String paths = createSDCardDir("GCGL");
        //文件绝对路径
        File file = new File(Environment.getExternalStorageDirectory() + "/GCGL/" + name);
        //判断路径下是否有文件存在
        boolean lean = Dates.fileIsExists(file);
        if (!lean) {
            //判断是否有权限
            activity.getJurisdiction(new ProgrammeDetailsActivity.JurisdictionCallback() {
                @Override
                public void success() {
                    //显示下载框
                    DialogUtils.download(getContext());
                    OkGo.get(url)
                            .execute(new FileCallback(paths, name) {
                                @Override
                                public void onSuccess(File file, Call call, Response response) {
                                    DialogUtils.dismiss();
                                    ToastUtils.showLongToast("下载成功,保存路径：" + file.getPath());
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    Toast.makeText(getContext(), "下载失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                    super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                                    Log.i("down", currentSize + "---" + totalSize + "---" + progress + "---" + networkSpeed);
                                    BigDecimal setScale = new BigDecimal(progress).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                                    double result3 = setScale.multiply(new BigDecimal(100)).doubleValue();
                                    final int bar = (int) result3;
                                    LogUtil.d("下载", bar + "");
                                    if (bar != 0) {
                                        DialogUtils.progressBar.setProgress(bar);
                                    }
                                }
                            });
                }

                @Override
                public void error() {

                }
            });

        } else {
            ToastUtils.showLongToast("文件已下载，保存路径为：" + file.getPath());
        }

    }

    public String createSDCardDir(String dir) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory();
            //得到一个路径，内容是sdcard的文件夹路径和名字
            String path = sdcardDir.getPath() + "/" + dir;
            File path1 = new File(path);
            if (!path1.exists()) {
                //若不存在，创建目录，可以在应用启动的时候创建
                path1.mkdirs();
            }
            return path;
        } else {
            return "";
        }
    }
}
