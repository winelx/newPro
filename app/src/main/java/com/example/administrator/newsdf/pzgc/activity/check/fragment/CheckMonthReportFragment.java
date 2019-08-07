package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.CheckQuarteradapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportOrgDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.CheckQuarterBean;
import com.example.administrator.newsdf.pzgc.callback.CheckCallBackUTils2;
import com.example.administrator.newsdf.pzgc.callback.CheckCallback2;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.view.BaseDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description:
 *
 * @author lx
 * date: 2018/8/14 0014 下午 2:48
 * update: 2018/8/14 0014
 * version:季度检查
 */
public class CheckMonthReportFragment extends Fragment implements CheckCallback2 {
    private View view;
    private Context mContext;
    private RecyclerView categoryList;
    private LinearLayout nullposion;
    private CheckQuarteradapter mAdapter;
    private NumberPicker yearPicker, monthPicker;
    private ArrayList<CheckQuarterBean> mData;
    private RelativeLayout linearDataTime, addcheck;
    private String orgId, mqnum;
    private PopupWindow mPopupWindow;
    private BaseDialog dialog;
    private LinearLayout checkQueater;
    private TextView dataTime, addcheckContent;
    /**
     * 是否加入检查（1否，2是）
     */
    private int checkstatus = 2;
    int quarter, years;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_checkquarter, container, false);
        mData = new ArrayList<>();
        dialog = new BaseDialog();
        CheckCallBackUTils2.setCallBack(this);
        mContext = CheckReportActivity.getInstance();
        orgId = SPUtils.getString(mContext, "orgId", "");
        categoryList = view.findViewById(R.id.category_list);
        checkQueater = view.findViewById(R.id.check_queater);
        addcheckContent = view.findViewById(R.id.addcheck_content);
        addcheck = view.findViewById(R.id.addcheck);
        linearDataTime = view.findViewById(R.id.linear_data_time);
        dataTime = view.findViewById(R.id.linear_data);
        SimpleDateFormat df = new SimpleDateFormat("yyyy年");
        String date = df.format(new Date());
        quarter = Utils.getquarter();
        categoryList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        categoryList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new CheckQuarteradapter(mContext, mData);
        categoryList.setAdapter(mAdapter);
        mqnum = Utils.getquarter() + "";
        years = Integer.parseInt(Dates.getYear());
//        mAdapter.setOnItemClickListener(new CheckQuarteradapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(mContext, CheckReportOrgDetailsActivity.class);
//                intent.putExtra("name", mData.get(position).getOrgname());
//                intent.putExtra("id", mData.get(position).getId());
//                intent.putExtra("year", years);
//                intent.putExtra("mqnum", mqnum);
//                intent.putExtra("type", "Q");
//                startActivity(intent);
//            }
//        });
        //是否加入检查
        addcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkpop();
            }
        });
        //选择季度
        linearDataTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meunPop();
            }
        });
        quarter = quarter - 1;
        if (quarter == 1) {
            //如果当前是第一季度，name显示去年的第四季度，
            //年份减-
            //显示的季度为第四季度，年度为减- 的年度
            years = years - 1;
            dataTime.setText(years + Utils.getquarters(3));
            //请求的数据为处理后的
            getdate(years + "", 4 + "");
        } else {
            dataTime.setText(date + Utils.getquarters(quarter));
            getdate(years + "", quarter + "");
        }
        return view;
    }
    /**
     * 选择季度
     */
    private void meunPop() {
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
     * 是否加入检查选择器
     */
    private void checkpop() {
        dialog.getadio(mContext, new Onclicklitener() {
            @Override
            public void confirm(String string) {
                if ("是".equals(string)) {
                    checkstatus = 2;
                } else {
                    checkstatus = 1;
                }
                //确认
                addcheckContent.setText("是否列入奖罚(" + string + ")");
                getdate(years + "", quarter + "");
            }

            @Override
            public void cancel(String string) {
                //取消
            }
        });
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
                        years = Integer.parseInt(Utils.year[yearPicker.getValue()]);
                        //获取季度
                        int month = monthPicker.getValue();
                        String monthdata = Utils.quarter[month];
                        dataTime.setText(years + monthdata);
                        quarter = Integer.parseInt(month + 1 + "");
                        getdate(years + "", quarter + "");
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
        TextView pop_vaulee = contentView.findViewById(R.id.pop_vaulee);
        pop_vaulee.setText("季度");
        TextView titledata = contentView.findViewById(R.id.titledata);
        titledata.setText("选择季度");
        contentView.findViewById(R.id.pop_dismiss).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_determine).setOnClickListener(menuItemOnClickListener);
        yearPicker = contentView.findViewById(R.id.years);
        Utils.setPicker(yearPicker, Utils.year, Utils.titleyear());
        monthPicker = contentView.findViewById(R.id.month);
        Utils.setPicker(monthPicker, Utils.quarter, Utils.getquarter() - 1);
        return contentView;
    }

    @Override
    public void update(String id) {
        orgId = id;
        getdate(years + "", quarter + "");
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

    public void getdate(String year, String quear) {
        OkGo.post(Requests.getOrgRanking)
                //组织Id
                .params("orgId", orgId)
                //查询年费
                .params("year", year)
                .params("isCheck", checkstatus)
                //查询季度
                .params("mqnum", quear)
                //查询类型：季度
                .params("selectType", "Q")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.d("ss", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                mData.clear();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String id;
                                        try {
                                            id = jsonObject1.getString("id");
                                        } catch (Exception e) {
                                            id = "";
                                        }
                                        String name;
                                        try {
                                            name = jsonObject1.getString("name");
                                        } catch (Exception e) {
                                            name = "";
                                        }
                                        String parentid;
                                        try {
                                            parentid = jsonObject1.getString("parent_id");
                                        } catch (Exception e) {
                                            parentid = "";
                                        }
                                        String parent_name;
                                        try {
                                            parent_name = jsonObject1.getString("parent_name");
                                        } catch (Exception e) {
                                            parent_name = "";
                                        }
                                        String score;
                                        try {
                                            score = jsonObject1.getString("score");
                                        } catch (Exception e) {
                                            score = "";
                                        }
                                        String rankingSorce;
                                        try {
                                            rankingSorce = jsonObject1.getString("rankingSorce");
                                        } catch (Exception e) {
                                            rankingSorce = "";
                                        }
                                        mData.add(new CheckQuarterBean(id, parentid, name, parent_name, score, rankingSorce));
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
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void setOrgId(String id) {
        orgId = id;
        getdate(years + "", quarter + "");
    }
}