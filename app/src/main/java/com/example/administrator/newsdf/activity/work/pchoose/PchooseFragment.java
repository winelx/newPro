package com.example.administrator.newsdf.activity.work.pchoose;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.MmissPushActivity;
import com.example.administrator.newsdf.activity.work.PhotoListActivity;
import com.example.administrator.newsdf.activity.work.UploadPhotoActivity;
import com.example.administrator.newsdf.camera.ToastUtils;

import java.util.ArrayList;
import java.util.List;




/**
 * description:  图册
 *
 * @author lx
 *         date: 2018/5/16 0016 下午 3:44
 *         update: 2018/5/16 0016
 *         version:
 */
public class PchooseFragment extends Fragment implements View.OnClickListener {
    private List<Shop> listPath;
    private List<Shop> listPaths;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pchoose, container, false);
        listPath = new ArrayList<>();
        listPaths = new ArrayList<>();
        //图册
        view.findViewById(R.id.pchoose_atlas).setOnClickListener(this);
        //分部分项
        view.findViewById(R.id.pchoose_wbs).setOnClickListener(this);
        //离线图纸
        view.findViewById(R.id.uploading_photo).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pchoose_wbs:
                Intent intent = new Intent(getActivity(), MmissPushActivity.class);
                intent.putExtra("data", "Photo");
                intent.putExtra("title", "图纸查看");
                startActivity(intent);
                break;
            case R.id.uploading_photo:
                //离线图纸
                listPath = LoveDao.queryCart();
                for (Shop shop : listPath) {
                    if ("standard".equals(shop.getProject())) {
                        listPaths.add(shop);
                    }
                }
                if (listPath.size() != 0) {
                    Intent standard = new Intent(getActivity(), UploadPhotoActivity.class);
                    standard.putExtra("status", "Photo");
                    startActivity(standard);
                } else {
                    ToastUtils.showShortToast("没有下载图片");
                }
                break;
            case R.id.pchoose_atlas:
                //
                Intent standard = new Intent(getActivity(), PhotoListActivity.class);
                standard.putExtra("status", "PhotoList");
                startActivity(standard);
                break;
            default:
                break;
        }
    }
}
