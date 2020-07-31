package com.example.administrator.newsdf.pzgc.activity.check.activity.record.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity.ExternalCheckDetailActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckType;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.bean.RecordDetailBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.utils.RecodModel;
import com.example.administrator.newsdf.pzgc.adapter.AddFileAdapter;
import com.example.administrator.newsdf.pzgc.adapter.FiletypeAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.bean.FileTypeBean;
import com.example.baselibrary.bean.bean;
import com.example.baselibrary.bean.photoBean;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.dialog.BaseDialogUtils;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.BaseDialog;
import com.example.baselibrary.view.PermissionListener;
import com.example.timepickter.TimePickerDialog;
import com.example.timepickter.data.Type;
import com.example.timepickter.listener.OnDateSetListener;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：新增进度检查单界面
 * 创建时间： 2020/7/30 0030 10:13
 *
 * @author winelx
 */
public class NewRecordCheckActiviy extends BaseActivity implements View.OnClickListener {
    private TextView title, projectName, checkData, check_import_user, check_import_unuser, com_button;
    private EditText check_department, check_duty, problem, explain;
    private TextView check_user, check_unuser, delete_unuser, delete_user, commit;
    private LinearLayout toolbar_menu;
    private ImageView arrow_right;
    private RecyclerView recyclerview;
    private ArrayList<String> userlist;
    private ArrayList<String> unuserlist;
    private ArrayList<FileTypeBean> fileTypelist;
    private ArrayList<String> deletelist;
    private String orgId, orgname;
    private AddFileAdapter addFileAdapter;
    private PopCameraUtils popCameraUtils;
    private TimePickerDialog mDialogYearMonthDay;
    private TakePictureManager takePictureManager;
    private RecodModel recodModel;
    private String id;
    boolean lean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recordcheck);
        recodModel = new RecodModel();
        timeselector();
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgid");
        orgname = intent.getStringExtra("orgname");
        id = intent.getStringExtra("id");
        popCameraUtils = new PopCameraUtils();
        takePictureManager = new TakePictureManager(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        title = findViewById(R.id.com_title);
        title.setText("新增记录");
        com_button = findViewById(R.id.com_button);
        com_button.setText("保存");
        toolbar_menu = findViewById(R.id.toolbar_menu);
        toolbar_menu.setOnClickListener(this);
        commit = findViewById(R.id.commit);
        commit.setOnClickListener(this);
        projectName = findViewById(R.id.project_name);
        delete_user = findViewById(R.id.delete_user);
        delete_user.setOnClickListener(this);
        delete_unuser = findViewById(R.id.delete_unuser);
        delete_unuser.setOnClickListener(this);
        checkData = findViewById(R.id.check_data);
        checkData.setOnClickListener(this);
        check_import_user = findViewById(R.id.check_import_user);
        check_import_user.setOnClickListener(this);
        check_import_unuser = findViewById(R.id.check_import_unuser);
        check_import_unuser.setOnClickListener(this);
        check_department = findViewById(R.id.check_department);
        check_duty = findViewById(R.id.check_duty);
        problem = findViewById(R.id.problem);
        explain = findViewById(R.id.explain);
        check_user = findViewById(R.id.check_user);
        check_unuser = findViewById(R.id.check_unuser);
        arrow_right = findViewById(R.id.arrow_right);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 4));
        addFileAdapter = new AddFileAdapter(mContext, new ArrayList<>());
        recyclerview.setAdapter(addFileAdapter);
        projectName.setText(orgname);
        setEnabled(true);
        userlist = new ArrayList<>();
        unuserlist = new ArrayList<>();
        fileTypelist = new ArrayList<>();
        deletelist = new ArrayList<>();
        check_user.setText(Dates.listToStrings(userlist));
        check_unuser.setText(Dates.listToStrings(unuserlist));
        addFileAdapter.setOnItemClickListener(new AddFileAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                permisssion();
            }

            @Override
            public void delete(int position) {
                deletelist.add(fileTypelist.get(position).getId());
                fileTypelist.remove(position);
                addFileAdapter.getData(fileTypelist);
            }
        });
        getData();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_menu) {
            if (com_button.getText().toString().equals("保存")) {
                BaseDialog.confirmdialog(mContext, "是否保存数据", "", new Onclicklitener() {
                    @Override
                    public void confirm(String string) {
                        savedata();
                    }

                    @Override
                    public void cancel(String string) {

                    }
                });
            } else {
                com_button.setText("保存");
                setEnabled(true);
                addFileAdapter.addview(true);
            }
        } else if (i == R.id.commit) {
            BaseDialog.confirmdialog(mContext, "是否提交数据", "", new Onclicklitener() {
                @Override
                public void confirm(String string) {
                    optionStatusByApp();
                }

                @Override
                public void cancel(String string) {

                }
            });
        } else if (i == R.id.check_import_user) {
            Intent intent = new Intent(mContext, SuperviseCheckRecordUerListActivity.class);
            intent.putExtra("type", true);
            intent.putExtra("content", check_user.getText().toString());
            intent.putExtra("orgId", orgId);
            startActivityForResult(intent, 1001);
        } else if (i == R.id.check_import_unuser) {
            Intent intent = new Intent(mContext, SuperviseCheckRecordUerListActivity.class);
            intent.putExtra("orgId", false);
            intent.putExtra("orgId", orgId);
            intent.putExtra("content", check_unuser.getText().toString());
            startActivityForResult(intent, 1002);
        } else if (i == R.id.com_back) {
            finish();
        } else if (i == R.id.delete_user) {
            check_user.setText("");
        } else if (i == R.id.delete_unuser) {
            check_unuser.setText("");
        } else if (i == R.id.check_data) {
            mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
        }
    }

    /**
     * 说明：界面状态
     * 创建时间： 2020/7/31 0031 11:36
     *
     * @author winelx
     */
    public void setEnabled(boolean lea) {
        projectName.setEnabled(lea);
        checkData.setEnabled(lea);
        commit.setEnabled(!lea);
        check_department.setEnabled(lea);
        check_duty.setEnabled(lea);
        problem.setEnabled(lea);
        explain.setEnabled(lea);
        check_import_user.setVisibility(lea ? View.VISIBLE : View.GONE);
        check_import_unuser.setVisibility(lea ? View.VISIBLE : View.GONE);
        arrow_right.setVisibility(lea ? View.VISIBLE : View.GONE);
        delete_user.setVisibility(lea ? View.VISIBLE : View.GONE);
        delete_unuser.setVisibility(lea ? View.VISIBLE : View.GONE);
        if (lea) {
            problem.setHint("请输入");
            explain.setHint("请输入");
            check_duty.setHint("请输入");
            check_department.setHint("请输入");

            checkData.setHint("请选择");
            commit.setBackgroundResource(R.color.gray);
        } else {
            problem.setHint("");
            explain.setHint("");
            check_duty.setHint("");
            check_department.setHint("");
            checkData.setHint("");
            commit.setBackgroundResource(R.color.colorAccent);
        }

    }


    /**
     * 说明：权限申请
     * 创建时间： 2020/3/6 0006 9:56
     * author  winelx
     */
    public void permisssion() {
        requestRunPermisssion(new String[]{Enum.CAMERA, Enum.FILEWRITE, Enum.FILEREAD}, new PermissionListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onGranted() {
                //打开选择图片的弹窗
                popCameraUtils.showPopwindow(NewRecordCheckActiviy.this, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void oncamera() {
                        //拍照方式
                        takePictureManager.startTakeWayByCarema();
                        //回调
                        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                            @Override
                            public void successful(boolean isTailor, final File outFile, Uri filePath) {
//                                    //压缩图片
                                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                                Tiny.getInstance().source(outFile).asFile().withOptions(options).compress(new FileCallback() {
                                    @Override
                                    public void callback(boolean isSuccess, String outfile) {
                                        fileTypelist.add(new FileTypeBean(outfile, "jpg"));
                                        addFileAdapter.getData(fileTypelist);
                                    }
                                });
                            }

                            //失败回调
                            @Override
                            public void failed(int errorCode, List<String> deniedPermissions) {
                            }
                        });
                    }

                    @Override
                    public void onalbum() {
                        //相册
                        Intent intent = new Intent(mContext, ImageGridActivity.class);
                        startActivityForResult(intent, Enum.IMAGE_PICKER);
                    }
                });
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    if (permission.equals(Enum.CAMERA)) {
                        BaseDialogUtils.openAppDetails(mContext, "上传图片需要相机,请到权限管理中心打开");
                    } else {
                        BaseDialogUtils.openAppDetails(mContext, "APP更新需要手机存储权限,请到权限管理中心打开");
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 101) {
            check_user.setText(data.getStringExtra("content"));
        } else if (requestCode == 1002 && resultCode == 101) {
            check_unuser.setText(data.getStringExtra("content"));
        } else if (requestCode == Enum.IMAGE_CREAMA) {
            //相机拍照回调
            try {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (requestCode == Enum.IMAGE_PICKER) {
            if (data != null) {
                //获取返回的图片路径集合
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    double mdouble = Dates.getDirSize(new File(images.get(i).path));
                    if (mdouble != 0.0) {
                        //实例化Tiny.FileCompressOptions，并设置quality的数值（quality改变图片压缩质量）
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        options.quality = 95;
                        Tiny.getInstance().source(images.get(i).path).asFile().withOptions(options).compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile) {
                                //新增数据
                                fileTypelist.add(new FileTypeBean(outfile, "jpg"));
                                //刷新数据，并指定刷新的位置
                                addFileAdapter.getData(fileTypelist);
                            }
                        });
                    } else {
                        ToastUtils.showLongToast("请检查上传的图片是否损坏");
                    }
                }
            }
        }
    }

    /**
     *
     *说明：保存数据
     *创建时间： 2020/7/31 0031 14:26
     *@author winelx
     */
    public void savedata() {
        lean = true;
        if (TextUtils.isEmpty(checkData.getText().toString())) {
            ToastUtils.showShortToast("检查日期不能为空");
            lean = false;
            return;
        }
        if (TextUtils.isEmpty(check_department.getText().toString())) {
            ToastUtils.showShortToast("检查部门不能为空");
            lean = false;
            return;
        }
        if (TextUtils.isEmpty(check_duty.getText().toString())) {
            ToastUtils.showShortToast("责任部门不能为空");
            lean = false;
            return;
        }
        if (TextUtils.isEmpty(check_user.getText().toString())) {
            ToastUtils.showShortToast("检查人员不能为空");
            lean = false;
            return;
        }
        if (TextUtils.isEmpty(check_unuser.getText().toString())) {
            ToastUtils.showShortToast("被检查人员不能为空");
            lean = false;
            return;
        }
        if (lean) {
            Map<String, String> map = new HashMap<>();
            if (id != null) {
                map.put("id", id);
            }
            map.put("orgId", orgId);
            map.put("orgName", projectName.getText().toString());
            map.put("checkDate", checkData.getText().toString());
            map.put("checkPart", check_department.getText().toString());
            map.put("responsibilityPart", check_duty.getText().toString());
            map.put("checkPersion", check_user.getText().toString());
            map.put("beCheckPersion", check_unuser.getText().toString());
            map.put("problem", problem.getText().toString());
            map.put("explanation", explain.getText().toString());
            map.put("deleteAttachmentIdStr", Dates.listToStrings(deletelist));
            ArrayList<File> files = new ArrayList<>();
            for (int i = 0; i < fileTypelist.size(); i++) {
                FileTypeBean bean = fileTypelist.get(i);
                if (bean.getId() == null) {
                    files.add(new File(bean.getFilepath()));
                }
            }
            Dates.getDialogs((Activity) mContext, "提交数据中...");
            recodModel.savespecial(map, files, new NetworkAdapter() {
                @Override
                public void onsuccess(Object object) {
                    super.onsuccess(object);
                    Dates.disDialog();
                    if (id == null) {
                        LiveDataBus.get().with("ex_list").setValue("刷新");
                    }
                    RecordDetailBean bean = (RecordDetailBean) object;
                    setContent(bean);
                }

                @Override
                public void onerror(String string) {
                    super.onerror(string);
                    Dates.disDialog();
                }
            });
        }

    }

    /**
     * 说明：获取界面数据
     * 创建时间： 2020/7/31 0031 13:54
     *
     * @author winelx
     */
    public void getData() {
        if (id != null) {
            recodModel.getspecialcheckrecordbyapp(id, new NetworkAdapter() {
                @Override
                public void onsuccess(Object object) {
                    super.onsuccess(object);
                    RecordDetailBean bean = (RecordDetailBean) object;
                    setContent(bean);
                }
            });
        }
    }

    /**
     * 说明：设置界面内容
     * 创建时间： 2020/7/31 0031 13:54
     *
     * @author winelx
     */
    public void setContent(RecordDetailBean bean) {
        com_button.setText("编辑");
        setEnabled(false);
        addFileAdapter.addview(false);
        //项目名称
        projectName.setText(Utils.isNull(bean.getOrgName()));
        //检查日期
        checkData.setText(Utils.isNull(bean.getCheckDate()));
        //检查部门
        check_department.setText(Utils.isNull(bean.getCheckPart()));
        //责任部门
        check_duty.setText(Utils.isNull(bean.getResponsibilityPart()));
        //检查人员
        check_user.setText(Utils.isNull(bean.getCheckPersion()));
        //被检查人员
        check_unuser.setText(Utils.isNull(bean.getBeCheckPersion()));
        //问题
        problem.setText(Utils.isNull(bean.getProblem()));
        //说明
        explain.setText(Utils.isNull(bean.getExplanation()));
        List<RecordDetailBean.AttachmentListBean> list = bean.getAttachmentList();
        fileTypelist.clear();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RecordDetailBean.AttachmentListBean beans = list.get(i);
                fileTypelist.add(new FileTypeBean(beans.getId(), beans.getFilename(), Requests.networks + beans.getFilepath(), beans.getFileext()));
            }
            addFileAdapter.getData(fileTypelist);
        }
    }

    /**
     * 说明：时间选择器初始化
     * 创建时间： 2020/7/1 0001 10:57
     *
     * @author winelx
     */
    public void timeselector() {
        Date now = new Date();
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextSize(15)
                .setCyclic(false)
//                .setMinMillseconds(now.getTime() - (24 * 60 * 60 * 1000) * 2)//最小时间
                .setCurrentMillseconds(now.getTime())
                .setWheelItemTextSelectorColor(getResources().getColor(com.example.baselibrary.R.color.colorAccent))
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        checkData.setText(Dates.stampToDates(millseconds));
                    }
                })//回调
                .build();
    }

    /**
     * 说明：提交
     * 创建时间： 2020/7/31 0031 14:25
     * @author winelx
     */
    public void optionStatusByApp() {
        recodModel.optionStatusByApp(id, new NetworkAdapter() {
            @Override
            public void onsuccess() {
                super.onsuccess();
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
                ToastUtils.showShortToast(string);
            }
        });


    }
}
