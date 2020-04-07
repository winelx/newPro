package com.example.administrator.newsdf.pzgc.photopicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.pchoose.PhotoEnue;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.photopicker.fragment.ImagePagerFragment;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;


import static com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview.EXTRA_CURRENT_ITEM;
import static com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview.EXTRA_ORIGINAL_TITLE;
import static com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview.EXTRA_PHOTOS;
import static com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview.EXTRA_SHOW_DELETE;
import static com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview.EXTRA_SHOW_LABEL;
import static com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview.EXTRA_SHOW_UPLOADE;
import static com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview.KEY_SELECTED_PHOTOS;
import static com.example.administrator.newsdf.pzgc.utils.Dates.getDate;

/**
 * description:
 *
 * @author lx
 * date: 2018/3/2 0002 下午 2:41
 * update: 2018/3/2 0002
 * version:
 */
public class PhotoPagerActivity extends BaseActivity {
    private ImagePagerFragment pagerFragment;
    private String path;
    private ActionBar actionBar;
    private boolean showDelete;
    private boolean showLabel;
    private TextView picker_title;
    private LinearLayout upload;
    private List<Shop> listPath;
    private List<String> paths;
    private List<String> pathname = new ArrayList<>();
    private List<String> imagepath = new ArrayList<>();
    private String result;
    private HorizontalScrollView picker_horizon;
    private String Title;
    Bitmap bitmap;
    /**
     * 异步get,直接调用
     */
    private OkHttpClient client;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //保存数据
                    byte[] bytes = (byte[]) msg.obj;
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

        //当前位置
        int currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
        //图片集合
        paths = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        //是否显示删除按钮
        showDelete = getIntent().getBooleanExtra(EXTRA_SHOW_DELETE, true);
        //判断是标准还是图册进入
        showLabel = getIntent().getBooleanExtra(EXTRA_SHOW_LABEL, false);
        //是否显示下载按钮
        boolean showUploade = getIntent().getBooleanExtra(EXTRA_SHOW_UPLOADE, true);
        //图片路径
        imagepath = getIntent().getStringArrayListExtra(EXTRA_ORIGINAL_TITLE);
        if (pagerFragment == null) {
            pagerFragment =
                    (ImagePagerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPagerFragment);
        }
        //图片名称
        pathname = new ArrayList<>();
        //图片路径
        listPath = new ArrayList<>();
        //加载数据库数据
        Message message = new Message();
        message.what = 2;
        handler.sendMessage(message);
        pagerFragment.setPhotos(paths, currentItem);
        picker_title = (TextView) findViewById(R.id.picker_title);
        picker_horizon = (HorizontalScrollView) findViewById(R.id.picker_horizon);
        upload = (LinearLayout) findViewById(R.id.upload);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        if (!showUploade) {
            upload.setVisibility(View.GONE);
        } else {
            upload.setVisibility(View.VISIBLE);
        }
        if (imagepath.size() > 0) {
            picker_horizon.setVisibility(View.VISIBLE);
        } else {
            picker_horizon.setVisibility(View.GONE);
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
                if (imagepath.size() == 0) {
                } else {
                    Title = imagepath.get(position);
                    picker_title.setText(imagepath.get(position));
                }
                updateActionBarTitle();
            }
        });
        /**
         * 下载图片
         */
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = paths.get(pagerFragment.getViewPager().getCurrentItem());
                //根据'/'切割地址，
                String[] strs = path.split("/");
                //拿到图片名称
                result = strs[strs.length - 1].replace(".jpg", "");
                if (imagepath.size() > 0) {
                    if (showLabel) {
                        care();
                    } else {
                        standard();
                    }
                    //进行判断当前图片是否有下载过  用图片名进行对比
                    boolean setr = pathname.contains(result);
                    if (setr) {
                        //下载图片
                        ToastUtils.showShortToast("已下载该图片过");
                    } else {
                        if (path != null) {
                            Dates.getDialogs(PhotoPagerActivity.this, "保存图片中...");
                            asyncGet(path, result);
                        }
                    }
                } else {
                    asyncGet(path, result);
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

    //返回
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
            listPath = LoveDao.queryCart();
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

    private void asyncGet(String imageUrl, String imagename) {
        OkGo.<File>get(imageUrl)
                .tag("image")
                .execute(new FileCallback(App.getInstance().imagepath, imagename + ".jpg") {
                    @Override
                    public void onSuccess(final File file, Call call, Response response) {
                        //设置系统时间为文件名
                        Shop shop = new Shop();
                        shop.setType(Shop.TYPE_CART);
                        shop.setImage_url(file.getPath());
                        if (!showLabel) {
                            shop.setProject(PhotoEnue.STANDARD);
                        }
                        shop.setName(result);
                        shop.setContent(Title);
                        shop.setTimme(getDate());
                        LoveDao.insertLove(shop);
                        ToastUtils.showShortToast("已保存");

                        Dates.disDialog();

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

    public void care() {
        //加载数据库数据，方便在下载时进行数据对比，看是否已下载该图片
        pathname.clear();
        listPath = LoveDao.queryCart();
        for (int i = 0; i < listPath.size(); i++) {
            String standard = listPath.get(i).getProject();
            if (!"standard".equals(standard)) {
                pathname.add(listPath.get(i).getName());
            }
        }
    }

    public void standard() {
        //加载数据库数据，方便在下载时进行数据对比，看是否已下载该图片
        pathname.clear();
        listPath = LoveDao.queryCart();
        for (int i = 0; i < listPath.size(); i++) {
            String standard = listPath.get(i).getProject();
            if ("standard".equals(standard)) {
                pathname.add(listPath.get(i).getName());
            }
        }
    }
}
