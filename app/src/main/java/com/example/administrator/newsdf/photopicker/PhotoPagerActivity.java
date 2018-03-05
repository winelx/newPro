package com.example.administrator.newsdf.photopicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.photopicker.fragment.ImagePagerFragment;
import com.example.administrator.newsdf.utils.Dates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.administrator.newsdf.photopicker.PhotoPicker.KEY_SELECTED_PHOTOS;
import static com.example.administrator.newsdf.photopicker.PhotoPreview.EXTRA_CURRENT_ITEM;
import static com.example.administrator.newsdf.photopicker.PhotoPreview.EXTRA_PHOTOS;
import static com.example.administrator.newsdf.photopicker.PhotoPreview.EXTRA_SHOW_DELETE;


/**
 * description:
 *
 * @author lx
 *         date: 2018/3/2 0002 下午 2:41
 *         update: 2018/3/2 0002
 *         version:
 */
public class PhotoPagerActivity extends AppCompatActivity {
    String str = "/storage/emulated/0/Android/data/com.example.administrator.newsdf/";
    private ImagePagerFragment pagerFragment;
    private String path;
    private ActionBar actionBar;
    private boolean showDelete;
    private LinearLayout upload;
    private List<Shop> listPath;
    private List<String> paths;
    private List<String> pathname;
    private String result;
    /**
     * 异步get,直接调用
     */
    private OkHttpClient client;
    byte[] bytes;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //保存数据
                    byte[] bytes = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Dates.downloadPhoto(PhotoPagerActivity.this, bitmap, result);
                    break;
                case 2:
                    //加载数据库数据，方便在下载时进行数据对比，看是否已下载该图片
                    listPath = LoveDao.queryCart();
                    for (int i = 0; i < listPath.size(); i++) {
                        pathname.add(listPath.get(i).getName());
                    }
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.__picker_activity_photo_pager);

        int currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
        paths = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        showDelete = getIntent().getBooleanExtra(EXTRA_SHOW_DELETE, true);
        if (pagerFragment == null) {
            pagerFragment =
                    (ImagePagerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPagerFragment);
        }
        pathname = new ArrayList<>();
        listPath = new ArrayList<>();
        //加载数据库数据
        Message message = new Message();
        message.what = 2;
        handler.sendMessage(message);
        pagerFragment.setPhotos(paths, currentItem);
        upload = (LinearLayout) findViewById(R.id.upload);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        if (showDelete) {
            upload.setVisibility(View.GONE);
        } else {
            upload.setVisibility(View.VISIBLE);

        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            updateActionBarTitle();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                actionBar.setElevation(25);
            }
        }

        pagerFragment.getViewPager().addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateActionBarTitle();
            }
        });
        /**
         * 下载图片
         */
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                care();
                path = paths.get(pagerFragment.getViewPager().getCurrentItem());
                //根据'/'切割地址，
                String[] strs = path.split("/");
                //拿到图片名称
                result = strs[strs.length - 1].replace(".jpg", "");
                Log.i("result", result);
                //进行判断当前图片是否有下载过  用图片名进行对比
                boolean setr = pathname.contains(result);
                if (setr) {
                    //下载图片
                    ToastUtils.showShortToast("已下载该图片过");
                } else {
                    asyncGet(path);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showDelete) {
            getMenuInflater().inflate(R.menu.__picker_menu_preview, menu);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(KEY_SELECTED_PHOTOS, pagerFragment.getPaths());
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (item.getItemId() == R.id.delete) {
            Dates.getDialogs(PhotoPagerActivity.this, "删除数据");
            final int index = pagerFragment.getCurrentItem();
            if (pagerFragment.getPaths().size() >= 1) {
                pagerFragment.getPaths().remove(index);
                pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();

                LoveDao.deleteLove(listPath.get(index).getId());
                Dates.deleteFile(listPath.get(index).getImage_url());
            }
            Dates.disDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateActionBarTitle() {
        if (actionBar != null) {
            actionBar.setTitle(
                    getString(R.string.__picker_image_index, pagerFragment.getViewPager().getCurrentItem() + 1,
                            pagerFragment.getPaths().size()));
        }
    }

    private void asyncGet(String IMAGE_URL) {
        client = new OkHttpClient();
        final Request request = new Request.Builder().get()
                .url(IMAGE_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //下载失败
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //下载成功 在handler中保存数据
                Message message = new Message();
                message.what = 1;
                message.obj = response.body().bytes();
                handler.sendMessage(message);
                //......
            }
        });
    }

    public void care() {
        //加载数据库数据，方便在下载时进行数据对比，看是否已下载该图片
        pathname.clear();
        listPath = LoveDao.queryCart();
        Log.i("listPath", listPath.size() + "");
        for (int i = 0; i < listPath.size(); i++) {
            pathname.add(listPath.get(i).getName());
        }
    }

}
