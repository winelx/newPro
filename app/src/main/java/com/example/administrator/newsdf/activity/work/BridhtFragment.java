package com.example.administrator.newsdf.activity.work;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.Adapter.BridhtAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.BrightBean;

import java.util.ArrayList;

/**
 * description: 亮点展示界面
 *
 * @author lx
 *         date: 2018/4/25 0025 下午 2:32
 *         update: 2018/4/25 0025
 *         version:
 */
public class BridhtFragment extends Fragment {
    View view;
    private int pos = 0;
    private BridhtAdapter mAdapter;
    private RecyclerView brightspot_list;
    private ArrayList<BrightBean> mData = new ArrayList<>();
    public BridhtFragment(int pos) {
        this.pos = pos;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bright_list_view, container, false);
        brightspot_list = view.findViewById(R.id.brightspot_list);
        LinearLayoutManager linearLayout = new LinearLayoutManager(BrightspotActivity.getInstance());
        //添加Android自带的分割线
        brightspot_list.addItemDecoration(new DividerItemDecoration(BrightspotActivity.getInstance(), DividerItemDecoration.VERTICAL));
        brightspot_list.setLayoutManager(linearLayout);
        //设置Adapter
        mAdapter = new BridhtAdapter(BrightspotActivity.getInstance());
        brightspot_list.setAdapter(mAdapter);


        String url1 = "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_01small_02_2018414191166C.jpg," +
                "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg," +
                "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg," +
                "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg";
        String url2 = "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_01small_02_2018414191166C.jpg," +
                "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg," +
                "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg";
        String url3 = "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_01small_02_2018414191166C.jpg";
        String url4 = "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_01small_02_2018414191166C.jpg," +
                "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg," +
                "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg," +
                "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg";
        for (int i = 0; i <50 ; i++) {
            mData.add(new BrightBean("两个", "此自定义的TextView不仅可以处理属性此自定义的TextView不仅可以处理SpannableString属性", "三度一标", "2018-04-25 13:12", url1));
            mData.add(new BrightBean("两个", "此自定义的TextView不仅可以处理属性此自定义的TextView不仅可以处理SpannableString属性", "三度二标", "2018-05-25 13:12", url2));
            mData.add(new BrightBean("三个字", "此自定义的TextView不仅可以处理属性此自定义的TextView不仅可以处理SpannableString属性", "三度三标", "2018-06-25 13:12", url3));
            mData.add(new BrightBean("两个", "此自定义的TextView不仅可以处理属性此自定义的TextView不仅可以处理SpannableString属性", "三度四标", "2018-07-25 13:12", url4));
        }


        mAdapter.getData(mData);
        return view;
    }
}
