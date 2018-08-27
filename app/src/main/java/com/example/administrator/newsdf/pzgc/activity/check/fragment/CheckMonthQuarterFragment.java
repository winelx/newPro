package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckQuarteradapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportActivity;
import com.example.administrator.newsdf.pzgc.bean.CheckQuarterBean;
import com.example.administrator.newsdf.pzgc.callback.CheckCallBackUTils1;
import com.example.administrator.newsdf.pzgc.callback.CheckCallback;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 检查月度报表
 *
 * @author lx
 *         date: 2018/8/14 0014 下午 2:48
 *         update: 2018/8/14 0014
 *         version:
 */
public class CheckMonthQuarterFragment extends Fragment {
    private View view;
    private Context mContext;
    private RecyclerView categoryList;
    private TextView dataTime, title;
    private LinearLayout nullposion;
    private CheckQuarteradapter mAdapter;
    private NumberPicker yearPicker, monthPicker;
    private ArrayList<CheckQuarterBean> mData;
    private LinearLayout linearDataTime;
    private String orgId, data;
    private PopupWindow mPopupWindow;
    private int dateMonth;
    private Date myDate = new Date();
    private LinearLayout checkQueater;
    private String yeare, mqnum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_checkquarter, container, false);
        mData = new ArrayList<>();
        mContext = CheckReportActivity.getInstance();
        dataTime = view.findViewById(R.id.linear_data);
        checkQueater = view.findViewById(R.id.check_queater);
        title = view.findViewById(R.id.title);
        categoryList = view.findViewById(R.id.category_list);
        linearDataTime = view.findViewById(R.id.linear_data_time);
        nullposion = view.findViewById(R.id.nullposion);
        data = Dates.getMonth();
        dateMonth = myDate.getMonth();
        linearDataTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeunPop();
            }
        });
        dataTime.setText(Dates.getMonth());
        categoryList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        categoryList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new CheckQuarteradapter(mContext, mData);
        categoryList.setAdapter(mAdapter);
        yeare = Dates.getYear();
        mqnum = Dates.stringToList(Dates.getMonth(), "-").get(1);
        getdate();
        return view;
    }

    /**
     * 时间选择器弹出框
     */
    private void MeunPop() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(dataTime);
        Utils.backgroundAlpha(0.5f, CheckReportActivity.getInstance());
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    /**
     * 设置pop的点击事件
     */
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.popwind_month;
        final View contentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_determine:
                        //获取年
                        yeare = Utils.year[yearPicker.getValue()];
                        //获取月
                        int month = monthPicker.getValue();
                        mqnum = Utils.month[month];
                        dataTime.setText(yeare + "-" + mqnum);
                        getdate();
                        break;
                    case R.id.pop_dismiss:
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.pop_dismiss).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_determine).setOnClickListener(menuItemOnClickListener);
        yearPicker = contentView.findViewById(R.id.years);
        //初始化年的选择器
        Utils.setPicker(yearPicker, Utils.year, Utils.titleyear());
        monthPicker = contentView.findViewById(R.id.month);
        //初始化月的选择器
        Utils.setPicker(monthPicker, Utils.month, dateMonth);
        return contentView;
    }


    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, CheckReportActivity.getInstance());
        }
    }

    public void getdate() {
        LogUtil.i("12", orgId);
        OkGo.post(Requests.getOrgRanking)
                //组织Id
                .params("orgId", orgId)
                //查询年份
                .params("year", yeare)
                //查询季度
                .params("mqnum", mqnum)
                //查询类型：季度
                .params("selectType", "M")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ToastUtils.showShortToast(s);
                        try {
                            mData.clear();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    String name = jsonObject1.getString("name");
                                    String parent_id = jsonObject1.getString("parent_id");
                                    String parent_name = jsonObject1.getString("parent_name");
                                    String score = jsonObject1.getString("score");
                                    mData.add(new CheckQuarterBean(id, parent_id, name, parent_name, score));
                                }
                            }
                            if (mData.size() > 0) {
                                checkQueater.setVisibility(View.GONE);
                                mAdapter.getData(mData);
                            } else {
                                mData.clear();
                                mAdapter.getData(mData);
                                checkQueater.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public void setOrgId(String id) {
        orgId = id;
        LogUtil.i("sss",orgId);
        getdate();
    }
}
