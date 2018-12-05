package measure.jjxx.com.baselibrary.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import android.widget.Toast;


import com.github.barteksc.pdfviewer.PDFView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;

import measure.jjxx.com.baselibrary.R;
import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.interfaces.PermissionListener;

import measure.jjxx.com.baselibrary.utils.FileUtils;

import measure.jjxx.com.baselibrary.view.top_snackbar.BaseTransientBottomBar;
import measure.jjxx.com.baselibrary.view.top_snackbar.TopSnackBar;
import okhttp3.Call;
import okhttp3.Response;

/**
 * description: pdf查看界面
 *
 * @author lx
 *         date: 2018/11/13 0013 上午 10:46
 */
public class PdfActivity extends BaseActivity {
    private PDFView pdfView;
    private String paths;
    private Context mContext;
    private String pathname;
    private String url = "";
    private OutputStream os;
    private InputStream is;
    private SwipeRefreshLayout swiperelayout;
    private File files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        mContext = this;
        Intent intent = getIntent();
        url = intent.getStringExtra("http");
        pathname = url.substring(url.lastIndexOf("/") + 1, url.length());
        paths = getExternalCacheDir().getPath().replace("cache", "PDF/");
        pdfView = (PDFView) findViewById(R.id.pdfView);
        swiperelayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        files = new File(paths + pathname);
        permisssion();
        //设置刷新监听器
        swiperelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                permisssion();
            }
        });
        findViewById(R.id.base_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getdata() {
        //判断路径下下你文件是否存在
        boolean status = FileUtils.fileIsExists(files);
        if (status) {
            //存在
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                pdfView.fromFile(files)
                        .defaultPage(1)
                        .enableSwipe(true)
                        .load();
            }
        } else {
            OkGo.get(url)
                    .execute(new FileCallback(paths, pathname) {
                        @Override
                        public void onSuccess(File file, Call call, Response response) {
                            swiperelayout.setRefreshing(false);//取消刷新
                            pdfView.fromFile(file)
                                    .defaultPage(1)
                                    .enableSwipe(true)
                                    .load();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            swiperelayout.setRefreshing(false);//取消刷新
                            TopSnackBar.make(pdfView, "pdf下载失败", BaseTransientBottomBar.LENGTH_SHORT).show();
                        }

                        @Override
                        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                            Log.i("down", currentSize + "---" + totalSize + "---" + progress + "---" + networkSpeed);
                        }
                    });

        }
    }

    public void permisssion() {
        requestRunPermisssion(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                getdata();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(mContext, "被拒绝的权限：" + permission, Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}