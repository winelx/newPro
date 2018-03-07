package com.example.administrator.newsdf.activity.work;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.photopicker.PhotoPreview;

import java.util.ArrayList;
import java.util.List;

public class PchooseActivity extends AppCompatActivity {
    private Context mContext;
    private TextView com_title;
    private List<Shop> listPath;
    ArrayList<String> drawable;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pchoose);
        mContext = PchooseActivity.this;

        listPath = new ArrayList<>();
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("图纸查看");

        image = (ImageView) findViewById(R.id.image);

        findViewById(R.id.pchoose_atlas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, PhotoListActivity.class));
            }
        });
        findViewById(R.id.pchoose_wbs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MmissPushActivity.class);
                intent.putExtra("data", "Photo");
                intent.putExtra("title", "图纸查看");
                startActivity(intent);
            }
        });

        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.uploading_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPath = LoveDao.queryCart();
                if (listPath.size() != 0) {
                    drawable = new ArrayList<>();
                    for (int i = 0; i < listPath.size(); i++) {
                        drawable.add(listPath.get(i).getImage_url());
                    }
                    PhotoPreview.builder().setPhotos(drawable).setCurrentItem(0).setShowDeleteButton(true).setShowUpLoadeButton(false)
                            .start((Activity) mContext);
                } else {
                    ToastUtils.showShortToast("没有下载图片");
                }
            }
        });
    }

    // byte[]转换成Bitmap
    public Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    // Bitmap转换成Drawable
    public Drawable bitmap2Drawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }
}
