package com.example.administrator.fengji.pzgc.activity.pchoose.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.fengji.GreenDao.LoveDao;
import com.example.administrator.fengji.GreenDao.Shop;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.pchoose.PhotoEnue;
import com.example.administrator.fengji.pzgc.activity.work.MmissPushActivity;
import com.example.administrator.fengji.pzgc.activity.pchoose.activity.PhotoListActivity;
import com.example.administrator.fengji.pzgc.activity.work.UploadPhotoActivity;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * description: 标准
 *
 * @author lx
 * date: 2018/5/16 0016 下午 3:45
 * update: 2018/5/16 0016
 * version:
 */
public class StandardFragment extends Fragment {
    private List<Shop> listPath;
    private List<Shop> pathname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standard, container, false);
        RelativeLayout standard_wbs = view.findViewById(R.id.standard_wbs);
        RelativeLayout standard_project = view.findViewById(R.id.standard_project);
        RelativeLayout uploading_photo = view.findViewById(R.id.uploading_photo);
        standard_wbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent standard = new Intent(getActivity(), PhotoListActivity.class);
                standard.putExtra("Type", PhotoEnue.STANDARD);
                standard.putExtra("title", "选择标准分类");
                startActivity(standard);
            }
        });
        standard_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MmissPushActivity.class);
                intent.putExtra("Type", PhotoEnue.STANDARD);
                startActivity(intent);

            }
        });
        uploading_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathname = new ArrayList<>();
                listPath = new ArrayList<Shop>();
                listPath = LoveDao.queryCart();
                for (Shop shop : listPath) {
                    String str = shop.getProject();
                    if ("standard".equals(str)) {
                        pathname.add(shop);
                    }
                }
                if (pathname.size() != 0) {
                    Intent standard = new Intent(getActivity(), UploadPhotoActivity.class);
                    standard.putExtra("Type", PhotoEnue.STANDARD);
                    startActivity(standard);
                } else {
                    ToastUtils.showShortToast("没有下载图片");
                }
            }
        });
        return view;
    }
}
