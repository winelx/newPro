package com.example.administrator.newsdf.pzgc.activity.device.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.DeviceViolatestandardActivity;
import com.example.administrator.newsdf.pzgc.activity.device.ProblemItemActivity;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.callback.ViolateCallbackUtils;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:
 * @Activity：
 */

public class ProblemgradeFragment extends Fragment {
    private View view;
    private Context mContext;
    private DeviceUtils deviceUtils;
    private SettingAdapter mAdapter;
    private ListView organ_list_item;
    private ArrayList<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_organizationa, null);
            deviceUtils = new DeviceUtils();
            list = new ArrayList<>();
            TextView com_title = view.findViewById(R.id.com_title);
            organ_list_item = view.findViewById(R.id.organ_list_item);
            com_title.setText("隐患等级");
            mContext = getActivity();
            view.findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProblemItemActivity activity = (ProblemItemActivity) mContext;
                    activity.showView();
                }
            });
            mAdapter = new SettingAdapter<String>(R.layout.check_standard_content, list) {
                @Override
                public void bindView(ViewHolder holder, String obj) {

                }
            };
            organ_list_item.setAdapter(mAdapter);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        deviceUtils.problemgrade(new DeviceUtils.problemgradeLitenerlist() {
            @Override
            public void onsuccess(ArrayList<String> data) {
                list.clear();
                list.addAll(data);
                mAdapter.getData(data);
            }
        });
        return view;
    }
}
