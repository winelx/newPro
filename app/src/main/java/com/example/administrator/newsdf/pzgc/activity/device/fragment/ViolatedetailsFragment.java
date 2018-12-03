package com.example.administrator.newsdf.pzgc.activity.device.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.DeviceViolatestandardActivity;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.callback.CheckCallback3;
import com.example.administrator.newsdf.pzgc.callback.ViolateCallbackUtils;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:特种设备违反标准-具体项
 * @Activity：DeviceViolatestandardActivity
 */

public class ViolatedetailsFragment extends Fragment implements CheckCallback3 {
    private View view;
    private Context mContext;
    private ListView organListItem;
    private SettingAdapter mAdapter;
    private ArrayList<String> list;
    private DeviceUtils deviceUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_organizationa, null);
            TextView com_title = view.findViewById(R.id.com_title);
            organListItem = view.findViewById(R.id.organ_list_item);
            deviceUtils = new DeviceUtils();
            list = new ArrayList<>();
            com_title.setText("违反标准");
            ViolateCallbackUtils.setCallBack(this);
            mContext = getActivity();
            view.findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceViolatestandardActivity activity = (DeviceViolatestandardActivity) mContext;
                    activity.showView();
                }
            });
            mAdapter = new SettingAdapter<String>(R.layout.check_standard_content, list) {
                @Override
                public void bindView(ViewHolder holder, String obj) {

                }
            };
            organListItem.setAdapter(mAdapter);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    /**
     * @description: 更新数据
     * @author lx
     * @date: 2018/12/3 0003 下午 3:06
     */
    @Override
    public void update(String id) {
        ToastUtils.showLongToast(id);
        deviceUtils.violateelist(id, new DeviceUtils.ViolickLitenerlist() {
            @Override
            public void onsuccess(ArrayList<String> data) {
                mAdapter.getData(data);
            }
        });
    }
}
