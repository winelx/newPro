package com.example.baselibrary.ui.fragment;


import android.content.Context;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.request.RequestOptions;
import com.example.baselibrary.R;
import com.example.baselibrary.ui.activity.SignatureViewActivity;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.screen.ScreenUtil;
import com.lzy.okgo.OkGo;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @author lx
 * @data :2019/4/17 0017
 * @描述 : 签名预览界面
 * @see
 */
public class AutographPreview extends Fragment {
    private View view;
    private Button btButton;
    private Context mContext;
    private String paths;
    private ImageView image;
    private SignatureViewActivity activity;
    private RequestOptions options;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_autograph_preview, container, false);
        mContext = getActivity();
        options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .dontAnimate()
                .fitCenter()
                .override(ScreenUtil.getScreenHeight(mContext) / 2, ScreenUtil.getScreenWidth(mContext) / 2)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.noautigraph);
        activity = (SignatureViewActivity) mContext;
        btButton = view.findViewById(R.id.bt_button);
        image = view.findViewById(R.id.image);
        //设置签名
        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setItem(1);
                activity.sethorizontal();
            }
        });
        view.findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backactivity();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        request();
    }

    public void request() {
        NetWork.getHttp(Api.SIGNATURE, null, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        btButton.setText("修改签名");
                        String path = jsonObject.getString("data");
                        paths = Requests.networks + path;
                    } else {
                        paths = "";
                        btButton.setText("录入签名");
                    }
                    glide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                btButton.setText("录入签名");
                paths = "";
                glide();
            }
        });
    }

    public void glide() {
        Glide.with(mContext)
                .load(paths)
                .apply(options)
                .into(image);
    }
}
