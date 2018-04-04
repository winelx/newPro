package com.example.administrator.newsdf.photopicker.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.photopicker.utils.AndroidLifecycleUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * description:
 *
 * @author lx
 *         date: 2018/3/8 0008 下午 5:10
 *         update: 2018/3/8 0008
 *         version:
 */
public class PhotoPagerAdapter extends PagerAdapter {

    private List<String> paths = new ArrayList<>();
    private RequestManager mGlide;

    public PhotoPagerAdapter(RequestManager glide, List<String> paths) {
        this.paths = paths;
        this.mGlide = glide;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Context context = container.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.__picker_picker_item_pager, container, false);
        final ImageView imageView = itemView.findViewById(R.id.iv_pager);
        final String path = paths.get(position);
//        if (path.startsWith("http")==true) {
//            uri = Uri.parse(path);
//        } else {
//            uri = Uri.fromFile(new File(path));
//        }

        boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(context);
        if (canLoadImage) {
            final RequestOptions options = new RequestOptions();
            options.dontAnimate()
                    .dontTransform()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                    .error(R.drawable.image_error);
            mGlide.setDefaultRequestOptions(options).load(path)
                    .thumbnail(0.1f)
                    .into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击空白地方退出界面
//                if (context instanceof Activity) {
//                    if (!((Activity) context).isFinishing()) {
//                        ((Activity) context).onBackPressed();
//                    }
//                }
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mGlide.clear((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
