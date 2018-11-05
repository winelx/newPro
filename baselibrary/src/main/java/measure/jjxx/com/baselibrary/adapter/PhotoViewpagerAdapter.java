package measure.jjxx.com.baselibrary.adapter;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import measure.jjxx.com.baselibrary.R;
import measure.jjxx.com.baselibrary.bean.PhotoviewBean;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import measure.jjxx.com.baselibrary.view.TouchImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * @author lx
 * @Created by: 2018/10/17 0017.
 * @description:所有展示图片的viewpager
 */

public class PhotoViewpagerAdapter extends PagerAdapter {
    private List<PhotoviewBean> datas;
    private Context mContext;
    private boolean aBoolean;

    public PhotoViewpagerAdapter(List<PhotoviewBean> datas, boolean aBoolean, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
        this.aBoolean = aBoolean;

    }

    /**
     * 返回viewpager要显示几页
     */
    @Override
    public int getCount() {
        return datas.size();
    }

    /**
     * 该函数用来判断instantiateItem(ViewGroup, int)
     * 函数所返回来的Key与一个页面视图是否是代表的同一个视图(即它俩是否是对应的，对应的表示同一个View)
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        //几乎是固定的写法,
        return view == object;
    }

    /**
     * 返回要显示的view,即要显示的视图
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.base_item_viewpager, container, false);
        //在这里可以做相应的操作
        TextView textView = view.findViewById(R.id.viewpager_item);
        TextView updown = view.findViewById(R.id.base_photo_updown);
        if ("true".equals(datas.get(position).isStatus())) {
            updown.setVisibility(View.VISIBLE);
        } else if ("false".equals(datas.get(position).isStatus())) {
            updown.setVisibility(View.GONE);
        } else {
            updown.setVisibility(View.GONE);
        }
        if (aBoolean) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
        updown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkGo.get(datas.get(position).getImgpath())
                        .execute(new FileCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onSuccess(final File file, Call call, Response response) {
                                new Handler(new Handler.Callback() {
                                    @Override
                                    public boolean handleMessage(Message msg) {
                                        if (!Environment.getExternalStorageState().isEmpty()) {
                                            //创建保存目录
                                            String path = Environment.getExternalStorageDirectory()
                                                    + File.separator + "pictures" + File.separator + "pzgc";
                                            String data = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
                                            //创建文件和创建文件夹
                                            String imagepath = createImagePath(path, data);
                                            OutputStream out = null;
                                            try {
                                                out = new FileOutputStream(imagepath);
                                                byte[] bytes = getBytesByFile(file);
                                                out.write(bytes);
                                                out.close();
                                                ToastUtlis.getInstance().showShortToast("下载完成");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            ToastUtlis.getInstance().showShortToast("没有内存卡");
                                        }
                                        return false;
                                    }
                                    //表示延迟3秒发送任务
                                }).sendEmptyMessageDelayed(0, 0);

                            }
                        });
            }
        });
        TouchImageView imageView = view.findViewById(R.id.iv_pager);
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(R.mipmap.base_image_error)
                .placeholder(R.mipmap.base_picker_ic_photo_black_48dp);
        Glide.with(mContext)
                .load(datas.get(position).getImgpath())
                .apply(options)
                .into(imageView);
        //数据填充
        textView.setText(datas.get(position).getPathtext());
        //这一步很重要
        container.addView(view);
        return view;
    }

    /**
     * 销毁条目
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public String createImagePath(String path, String imageName) {
        File destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = null;
        if (!TextUtils.isEmpty(imageName)) {
            file = new File(path, imageName + ".jpeg");
        }
        return file.getAbsolutePath();
    }

    //将文件转换成Byte数组
    public static byte[] getBytesByFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
