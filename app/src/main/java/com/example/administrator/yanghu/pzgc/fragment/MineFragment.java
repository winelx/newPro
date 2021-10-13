package com.example.administrator.yanghu.pzgc.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.yanghu.App;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.AddFrileUpdataActivity;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.administrator.yanghu.pzgc.activity.LoginActivity;
import com.example.administrator.yanghu.pzgc.activity.MainActivity;
import com.example.administrator.yanghu.pzgc.activity.mine.AboutmeActivity;
import com.example.administrator.yanghu.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.yanghu.pzgc.activity.mine.PasswordActvity;
import com.example.administrator.yanghu.pzgc.activity.mine.ProjectMembersTreeActivity;
import com.example.administrator.yanghu.pzgc.utils.AppUtils;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.yanghu.pzgc.utils.SPUtils;
import com.example.baselibrary.ui.activity.SignatureViewActivity;
import com.example.baselibrary.utils.screen.ScreenUtil;
import com.example.baselibrary.view.PermissionListener;
import com.example.baselibrary.zxing.android.CaptureActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.CookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;


/**
 * description: 个人界
 *
 * @author lx
 * date: 2018/3/22 0022 下午 4:18
 * update: 2018/3/22 0022
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CircleImageView mineAvatar;
    private Context mContext;
    private static final int PERMISSION_REQUESTCODE = 10086;
    private TextView mineOrganization, staffName;
    private String version;
    private TextView mineUploading;
    private AlertDialog dialog;
    private RequestOptions options;
    private PermissionListener mListener;
    private static final int REQUEST_CODE_SCAN = 0x0000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mine, null);
            //我的组织
            rootView.findViewById(R.id.organizationa).setOnClickListener(this);
            //扫二维码上传附件
            rootView.findViewById(R.id.scancode).setOnClickListener(this);
            //项目成员
            rootView.findViewById(R.id.projectmember).setOnClickListener(this);
            //修改密码
            rootView.findViewById(R.id.changepassword).setOnClickListener(this);
            //系统设置
            rootView.findViewById(R.id.mine_setting).setOnClickListener(this);
            //关于我们
            rootView.findViewById(R.id.about_us).setOnClickListener(this);
            //检查新版本
            rootView.findViewById(R.id.newversion).setOnClickListener(this);
            //清除缓存
            rootView.findViewById(R.id.mine_Thecache).setOnClickListener(this);
            //头像
            rootView.findViewById(R.id.mine_avatar).setOnClickListener(this);
            //所在组织
            rootView.findViewById(R.id.mine_organization).setOnClickListener(this);
            //名字
            rootView.findViewById(R.id.staffName).setOnClickListener(this);
            //退出
            rootView.findViewById(R.id.BackTo).setOnClickListener(this);
            //我的清明
            rootView.findViewById(R.id.mine_autograph).setOnClickListener(this);
            mineUploading = rootView.findViewById(R.id.mine_uploadings);
            mineAvatar = rootView.findViewById(R.id.mine_avatar);
            mineOrganization = rootView.findViewById(R.id.mine_organization);
            staffName = rootView.findViewById(R.id.staffName);
        }
        mContext = getActivity();
        version = AppUtils.getVersionName(getActivity());
        mineUploading.setText("(当前版本:" + version + ")");
        options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .dontAnimate()
                .fitCenter()
                .override(ScreenUtil.getScreenHeight(mContext) / 2, ScreenUtil.getScreenWidth(mContext) / 2)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(com.example.baselibrary.R.mipmap.nonews);
        ititData();
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
//        double totalSize = Dates.getDirSize(new File("/storage/emulated/0/Android/data/com.example.administrator.newsdf"));
//        totalSize = totalSize * 1024 * 1024 * 1024;
        return rootView;
    }

    //初始化数据
    private void ititData() {
        //登录人头像
        String url = Requests.networks + SPUtils.getString(mContext, "portrait", null);
        Glide.with(this).load(url)
                .thumbnail(Glide.with(this).load(R.mipmap.mine_avatar)).into(mineAvatar);
        //组织
        mineOrganization.setText(SPUtils.getString(mContext, "username", ""));
        //名字
        staffName.setText(SPUtils.getString(mContext, "staffName", ""));

    }


    @SuppressLint("HandlerLeak")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //切换组织
            case R.id.organizationa:
                Intent intent = new Intent(mContext, OrganizationaActivity.class);
                intent.putExtra("title", "切换组织");
                intent.putExtra("data", "Organi");
                startActivity(intent);
                break;
            case R.id.mine_avatar:
//                startActivity(new Intent(getActivity(), AuditActivity.class));
                //获取相机权限，定位权限，内存权限
                break;
            //项目成员
            case R.id.projectmember:
                startActivity(new Intent(getActivity(), ProjectMembersTreeActivity.class));
                break;
            //修改密码
            case R.id.changepassword:
                startActivity(new Intent(getActivity(), PasswordActvity.class));
                break;
            //系统设置
            case R.id.mine_setting:
//                startActivity(new Intent(getActivity(), SettingActivity.class));
                showqrcode();
                break;
            case R.id.scancode:
                requestRunPermisssion(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(mContext, CaptureActivity.class), REQUEST_CODE_SCAN);
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        for (String permission : deniedPermission) {
                            Toast.makeText(mContext, "被拒绝的权限：" +
                                    permission, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            //关于我们
            case R.id.about_us:
                startActivity(new Intent(getActivity(), AboutmeActivity.class));
                break;
            case R.id.BackTo:
                //清除fragmentmanager
                getActivity().getSupportFragmentManager().popBackStack();
                Okgo();
                JPushInterface.setAlias(mContext, "", new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                    }
                });
                //清除cooking
                HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
                CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
                cookieStore.removeCookie(httpUrl);
                startActivity(new Intent(mContext, LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.mine_Thecache:
                Dates.getDialog(getActivity(), "清理缓存...");
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        //删除本地pdf
                        String paths = mContext.getExternalCacheDir().getPath();
                        paths = paths.replace("cache", "MyDownLoad/");
                        //删除目录
                        Dates.clearFiles(paths);
                        //glide缓存
                        Glide.get(mContext).clearDiskCache();
                        ToastUtils.showShortToast("缓存清理成功成功");
                    }
                };
                break;
            //检查新版本
            case R.id.newversion:
                upload();
                break;
            case R.id.mine_autograph:
                startActivity(new Intent(mContext, SignatureViewActivity.class));
                break;
            default:
                break;


        }
    }

    /**
     * 退出登录
     */
    private void Okgo() {
        OkGo.getInstance().cancelAll();
        OkGo.post(Requests.BackTo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int code = jsonObject.getInt("ret");
                            if (code != 1) {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        //可能会切换组织，所以每次走start时重新请求数据
        mineOrganization.setText(SPUtils.getString(mContext, "username", ""));
        staffName.setText(SPUtils.getString(mContext, "staffName", ""));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 自动检测升级
     */
    void upload() {
        OkGo.<String>post(Requests.UpLoading)
                .params("type", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    JSONObject json = jsonObject.getJSONObject("data");
                                    //版本号
                                    String versions = json.getString("versio");
                                    String description = json.getString("description");
                                    //更新地址
                                    String filePath = json.getString("downloadUrl");
                                    int lenght = version.compareTo(versions);
                                    if (lenght < 0) {
                                        ToastUtils.showShortToast("有新版本需要更新");
                                        show(filePath, description);
                                    }
                                } else {
                                    ToastUtils.showLongToast("已是最新版本");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

    void show(final String path, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
        builder.setTitle("新版本提示").setMessage(message).setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(path);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        builder.show();
    }

    /**
     * 说明：显示二维码
     * 创建时间： 2020/7/16 0016 13:16
     *
     * @author winelx
     */
    @SuppressLint("ResourceType")
    public void showqrcode() {
        dialog = new AlertDialog.Builder(mContext).create();
        // 获取布局
        View view = View.inflate(mContext, R.layout.fragment_mine_qrcode, null);
        ImageView imgAndroid = view.findViewById(R.id.android);
        ImageView imgIos = view.findViewById(R.id.ios);
        TextView tabAndroid = view.findViewById(R.id.tab_android);
        TextView tabIos = view.findViewById(R.id.tab_ios);
        Glide.with(mContext)
                .load(SPUtils.getString(mContext, "iosimg", ""))
                .apply(options)
                .into(imgIos);
        Glide.with(mContext)
                .load(SPUtils.getString(mContext, "androidimg", ""))
                .apply(options)
                .into(imgAndroid);
        tabAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabAndroid.setBackgroundResource(R.color.colorAccent);
                tabIos.setBackgroundResource(R.color.gray);
                imgAndroid.setVisibility(View.VISIBLE);
                imgIos.setVisibility(View.GONE);
            }
        });
        tabIos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabAndroid.setBackgroundResource(R.color.gray);
                tabIos.setBackgroundResource(R.color.colorAccent);
                imgAndroid.setVisibility(View.GONE);
                imgIos.setVisibility(View.VISIBLE);

            }
        });
        view.findViewById(R.id.diss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //添加布局
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTCODE:
                if (grantResults.length > 0) {
                    //存放没授权的权限
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        //说明都授权了
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 权限申请
     *
     * @param permissions 权限集合
     * @param listener    回调
     */
    public void requestRunPermisssion(String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        //遍历申请权限
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(permission);
            }
        }
        //判断是否授权
        if (!permissionLists.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) mContext, permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUESTCODE);
        } else {
            //表示全都授权了
            mListener.onGranted();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && requestCode == 0) {
            if (data != null) {
                startActivity(new Intent(App.getInstance(), AddFrileUpdataActivity.class)
                        .putExtra("billId", data.getStringExtra("billId"))
                        .putExtra("relateFeild", data.getStringExtra("relateFeild"))
                        .putExtra("relateTable", data.getStringExtra("relateTable"))
                        .putExtra("url", data.getStringExtra("url"))
                        .putExtra("ty", data.getStringExtra("ty")));
            }
        }
    }
}

