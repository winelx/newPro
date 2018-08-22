package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.MyNoticeDataBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 检查通知单全部模块通知列表
 *
 * @author lx
 *         date: 2018/8/8 0008 下午 4:03
 *         update: 2018/8/8 0008
 *         version:
 */

public class ChecknoticeMessagelistAllActivity extends AppCompatActivity implements View.OnClickListener {

    private SettingAdapter adapter;
    private ListView listView;
    ArrayList<MyNoticeDataBean> mData;
    private Context mContext;
    private TextView titleView;
    private ImageView checklistmeunimage;
    private PopupWindow mPopupWindow;
    private float resolution;
    private String id;
    private String status = "";
    private int page = 1;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage_all);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        resolution = ScreenUtil.getDensity(App.getInstance());
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        checklistmeunimage = (ImageView) findViewById(R.id.checklistmeunimage);
        checklistmeunimage.setBackgroundResource(R.mipmap.meun);
        checklistmeunimage.setVisibility(View.VISIBLE);
        findViewById(R.id.checklistmeun).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.maber_tree);
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(intent.getStringExtra("orgName"));
        mContext = ChecknoticeMessagelistAllActivity.this;
        mData = new ArrayList<>();
        adapter = new SettingAdapter<MyNoticeDataBean>(mData, R.layout.check_notice_all) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, MyNoticeDataBean obj) {
                holder.setText(R.id.management_block, "检测标段"+obj.getRectificationOrgName());
                holder.setText(R.id.management_title, "11111111111111");
                holder.setText(R.id.management_user, obj.getCheckPersonName() + "    " + obj.getUpdateDate());
                holder.setText(R.id.management_category, "所属类别:" + obj.getStandardDelName());
                holder.setText(R.id.management_org, "检查组织:" + obj.getCheckOrgName());
                holder.setText(mContext, R.id.management_number, "扣分:" + obj.getStandardDelScore(), 3, R.color.red);
                holder.setText(R.id.notice_user, "整改负责人:" + obj.getNoticeuser());
                holder.setText(R.id.notice_lasttime, "整改期限:" + obj.getNoticetime());
                String status = obj.getStatus();
                switch (status) {
                    case "0":
                        holder.setSlantedText(R.id.inface_item_message, "未下发", R.color.graytext);
                        break;
                    case "1":
                        holder.setSlantedText(R.id.inface_item_message, "未回复", R.color.Orange);
                        break;
                    case "2":
                        holder.setSlantedText(R.id.inface_item_message, "未验证", R.color.Orange);
                        break;
                    case "3":
                        holder.setSlantedText(R.id.inface_item_message, "打回", R.color.red);
                        break;
                    case "5":
                        holder.setSlantedText(R.id.inface_item_message, "完成", R.color.finish_green);
                        break;
                    default:
                        break;
                }
            }
        };
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nullposion));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 =new Intent(mContext, IssuedTaskDetailsActivity.class);
                intent1.putExtra("id",mData.get(position).getNoticeId());
                intent1.putExtra("isDeal",true);
                startActivity(intent1);
            }
        });
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checklistmeunimage.setOnClickListener(this);
        getdate();
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mData.clear();
                getdate();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getdate();
                //传入false表示加载失败
                refreshlayout.finishLoadmore(800);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistmeun:
                meun();
                break;
            default:
                break;
        }
    }

    private void meun() {
        //弹出框=
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                Dates.withFontSize(resolution) + 20, Dates.higtFontSize(resolution), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(checklistmeunimage);
        backgroundAlpha(0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    public View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.pop_checknoticeall;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                mData.clear();
                switch (v.getId()) {
                    case R.id.pop_All:
                        status = "";
                        break;
                    case R.id.pop_financial:
                        status = "1";
                        break;
                    case R.id.pop_manage:
                        status = "2";
                        break;
                    case R.id.pop_back:
                        status = "3";
                        break;
                    case R.id.pop_success:
                        status = "5";
                        break;
                    default:
                        break;
                }
                getdate();
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };

        contentView.findViewById(R.id.pop_All).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_back).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_success).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    public void getdate() {
        OkGo.post(Requests.GET_NOTICE_DATA_APP)
                .params("rectificationOrgid", id)
//通知单回复状态(0:未下发；1：未回复;2:未验证；3：打回；5:完成) (保存：0,提交 1)
//运动节点(0:未回复；1:回复未提交；2未验证；3:验证未提交；5完成)(保存：null,提交 0)
                .params("status", status)
                .params("page", page)
                .params("size", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        //标题
                                        String partDetails;
                                        try {
                                            partDetails = json.getString("partDetails");
                                        } catch (JSONException e) {
                                            partDetails = "";
                                        }
                                        //检查组织
                                        String checkOrgName = json.getString("checkOrgName");
                                        //责任人
                                        String sendPersonName = json.getString("checkPersonName");
                                        //所属标段
                                        String rectificationOrgName = json.getString("rectificationOrgName");
                                        //更新时间
                                        String updateDate;
                                        try {
                                            updateDate = json.getString("updateDate");
                                        } catch (JSONException e) {
                                            updateDate = json.getString("checkDate");
                                        }

                                        updateDate = updateDate.substring(0, 10);
                                        //
                                        String standardDelScore = json.getString("standardDelScore");
                                        //检查类别
                                        String standardDelName = json.getString("standardDelName");
                                        //id
                                        //状态
                                        String status = json.getString("status");
                                        String noticeld=json.getString("id");
                                        String motionNode;
                                        try {
                                            motionNode = json.getString("motionNode");
                                        } catch (JSONException e) {
                                            motionNode = "";
                                        }
                                        String verificationId;
                                        try {
                                            verificationId = json.getString("verificationId");
                                        }catch (JSONException e){
                                            verificationId="";
                                        }



                                        String checkPersonName = json.getString("rectificationPersonName");
                                        String rectificationDate = json.getString("rectificationDate");
                                        rectificationDate = rectificationDate.substring(0, 10);
                                        mData.add(new MyNoticeDataBean(partDetails, checkOrgName, sendPersonName,
                                                rectificationOrgName, updateDate, standardDelScore, standardDelName, noticeld, status, motionNode, checkPersonName, rectificationDate, "",verificationId, false));
                                    }
                                }
                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                            adapter.getData(mData);
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

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
