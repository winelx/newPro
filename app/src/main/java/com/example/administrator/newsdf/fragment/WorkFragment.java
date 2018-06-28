package com.example.administrator.newsdf.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.SettingAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.BrightspotActivity;
import com.example.administrator.newsdf.activity.work.NotuploadActivity;
import com.example.administrator.newsdf.activity.work.OrganiwbsActivity;
import com.example.administrator.newsdf.activity.work.PushCheckActivity;
import com.example.administrator.newsdf.activity.work.pchoose.PchooseActivity;
import com.example.administrator.newsdf.bean.Fr_work_pie;
import com.example.administrator.newsdf.bean.work_fr_bright_bean;
import com.example.administrator.newsdf.callback.BrightCallBack;
import com.example.administrator.newsdf.callback.BrightCallBackUtils;
import com.example.administrator.newsdf.fragment.bright.FragmentBrightAdapter;
import com.example.administrator.newsdf.fragment.bright.FragmentBrightProAdapter;
import com.example.administrator.newsdf.fragment.bright.FragmentBrightcomAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LogUtil;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.SPUtils;
import com.example.administrator.newsdf.view.PieChartBeans;
import com.example.administrator.newsdf.view.PieChartOne;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Response;


/**
 * @author ：lx
 *         时间：2017/11/23 0023:下午 15:37
 *         说明：
 */
public class WorkFragment extends Fragment implements BrightCallBack {
    private View rootView;
    private Context mContext;
    private Disposable mDisposable;
    private CardView mCardView;
    int status = 1;
    /**
     * 饼状图
     */
    private PieChartOne PieChartOne;
    /**
     * 饼状图数据
     */
    private List<PieChartBeans> mData;
    /**
     * 饼状图参数布局
     */
    private GridView fr_work_grid;
    private String name = "";
    private int num = 0, number = 0, staus = 1, time;
    private ViewPager bridhtgroupViewpager, bridhtcompanyViewpager, bridhtprojectViewpager;
    //集团亮点
    private ArrayList<work_fr_bright_bean> groupbridhtList;
    //分公司亮点
    private ArrayList<work_fr_bright_bean> compangbrightList;
    //项目亮点
    private ArrayList<work_fr_bright_bean> projectbrightList;
    //延迟执行时间
    private int datatime = 4000;
    //展示轮播图的三个布局
    private RelativeLayout bridhtCompany, bridhtGroup, bridhtProject;
    /**
     * 通用适配器
     */
    private SettingAdapter mAdapter;
    //饼状图分类名称集合
    private ArrayList<Fr_work_pie> workpie = new ArrayList<>();
    //界面文字
    private TextView frWorkDn, taskManagement, moreandmore, frWorkName, taskPush, photoManagement, uploade;
    //饼状图色码
    private String[] color = {"#2F4554", "#D48265", "#91C7AE", "#749F83", "#C23531", "#61A0A8", "#61a882", "#68a861", "#618ca8", "#2F4554", "#D48265", "#91C7AE", "#749F83", "#C23531", "#61A0A8", "#61a882", "#68a861", "#618ca8"};
    private FragmentBrightAdapter brightViewpagerAdapter;
    private FragmentBrightcomAdapter bridhtcompanyAdapter;
    private FragmentBrightProAdapter bridhtprojectAdapter;
    private TextView bridhtGroup_text, bridhtCompany_text, bridhtProject_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_work, null);
            //初始化饼状图集合
            mData = new ArrayList<>();
            compangbrightList = new ArrayList<>();
            groupbridhtList = new ArrayList<>();
            projectbrightList = new ArrayList<>();
            BrightCallBackUtils.setCallBack(this);
            //初始化控件Id
            findId();
            //设置当前时间的问候
            setTime();
            //获取到当前登录人的名字，并展示
            frWorkName.setText(SPUtils.getString(mContext, "staffName", null) + ",");
            //更多
            moreandmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, BrightspotActivity.class));
//                    startActivity(new Intent(mContext, UnifiedActivity.class));
                }
            });
            //任务管理
            taskManagement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrganiwbsActivity.class);
                    startActivity(intent);
                }
            });
            //任务下发
            taskPush.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PushCheckActivity.class);
                    startActivity(intent);

                }
            });
            //图册管理
            photoManagement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, PchooseActivity.class));

                }
            });
            //未上传界面
            uploade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NotuploadActivity.class);
                    startActivity(intent);
                }
            });
            //饼状图内容适配器
            mAdapter = new SettingAdapter<PieChartBeans>(mData, R.layout.fr_work_gr_item) {
                @Override
                public void bindView(ViewHolder holder, PieChartBeans obj) {
                    holder.setBackgroud(R.id.fr_worl_gr_img, obj.getColor());
                    holder.setText(R.id.fr_worl_gr_text, obj.getValuer());
                }
            };
        }
        //设置饼状图
        PieChartOne.setDate(mData);
        //饼状图分类gridview的适配器
        fr_work_grid.setAdapter(mAdapter);
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        //请求图片轮播数据
        brightViewpagerAdapter = new FragmentBrightAdapter(getChildFragmentManager(), groupbridhtList);
        bridhtgroupViewpager.setAdapter(brightViewpagerAdapter);

        bridhtcompanyAdapter = new FragmentBrightcomAdapter(getChildFragmentManager(), compangbrightList);
        bridhtcompanyViewpager.setAdapter(bridhtcompanyAdapter);

        bridhtprojectAdapter = new FragmentBrightProAdapter(getChildFragmentManager(), projectbrightList);
        bridhtprojectViewpager.setAdapter(bridhtprojectAdapter);
        //延时 毫秒
        return rootView;
    }

    private void setTime() {
        String data = Dates.getHH();
        int time = Integer.parseInt(data);
        if (time >= 6 && time < 12) {
            frWorkDn.setText("上午好 !");
        } else if (time >= 12 && time < 14) {
            frWorkDn.setText("中午好 !");
        } else if (time >= 14 && time < 19) {
            frWorkDn.setText("下午好 !");
        } else if (time >= 19 && time <= 23) {
            frWorkDn.setText("晚上好 !");
        } else {
            frWorkDn.setText("早上好 !");
        }
    }

    private void findId() {
        bridhtGroup_text = rootView.findViewById(R.id.bridhtGroup_text);
        bridhtCompany_text = rootView.findViewById(R.id.bridhtCompany_text);
        bridhtProject_text = rootView.findViewById(R.id.bridhtProject_text);
        mCardView = rootView.findViewById(R.id.brightspot);
        fr_work_grid = rootView.findViewById(R.id.fr_work_grid);
        PieChartOne = rootView.findViewById(R.id.piechartone);
        //任务管理
        taskManagement = rootView.findViewById(R.id.task_management);
        //更多
        moreandmore = rootView.findViewById(R.id.moreandmore);
        //当前时间（早上，中午，晚上)
        frWorkDn = rootView.findViewById(R.id.fr_work_dn);
        //责任人
        frWorkName = rootView.findViewById(R.id.fr_work_name);
        //任务下发
        taskPush = (TextView) rootView.findViewById(R.id.push);
        //图册管理
        photoManagement = (TextView) rootView.findViewById(R.id.pphotoadm);
        //离线图纸
        uploade = rootView.findViewById(R.id.uploade);
        //亮点工程
        //集团
        bridhtgroupViewpager = rootView.findViewById(R.id.bridhtGroup_viewpager);
        //分公司
        bridhtcompanyViewpager = rootView.findViewById(R.id.bridhtCompany_viewpager);
        //项目
        bridhtprojectViewpager = rootView.findViewById(R.id.bridhtProject_viewpager);
        bridhtCompany = rootView.findViewById(R.id.bridhtCompany);
        bridhtGroup = rootView.findViewById(R.id.bridhtGroup);
        bridhtProject = rootView.findViewById(R.id.bridhtProject);
    }

    @Override
    public void onStart() {
        super.onStart();
        startTime();
        Bright();
        //饼状图数据请求
//        if (status) {
//            Okgo();
//        } else {
//            Okgo1();
//            PieChartOne.setDate(mData);
//            status = true;
//        }
    }

    //走oncreate
    private void Okgo() {
        mData.clear();
        workpie.clear();
        OkGo.post(Requests.PieChart)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            num = 0;
                            number = 0;
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("data") != -1) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);
                                    num = json.getInt("num");
                                    //获得中的数据
                                    number = number + num;
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        name = "";
                                    }
                                    workpie.add(new Fr_work_pie(name, num, color[i]));
                                }
                                Float numbers = Float.valueOf(number);
                                for (int i = 0; i < workpie.size(); i++) {
                                    //将数据转换成float
                                    Float siz = Float.valueOf(workpie.get(i).getNum());
                                    Float sjie = siz / numbers * 100;
                                    BigDecimal b = new BigDecimal(sjie);
                                    float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                                    mData.add(new PieChartBeans(workpie.get(i).getName(), f1, workpie.get(i).getColor()));
                                }
                                mAdapter.getData(mData);
                            } else {
                                mData.add(new PieChartBeans("未开始开工", 100f, color[1]));
                                mAdapter.getData(mData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    //走onstart 的饼状图
    private void Okgo1() {
        mData.clear();
        workpie.clear();
        OkGo.post(Requests.PieChart)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            num = 0;
                            number = 0;
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("data") != -1) {
                                System.out.println("包含");
                                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);

                                    try {
                                        num = json.getInt("num");
                                        number = number + num;
                                    } catch (JSONException e) {
                                        number = 0;
                                    }
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        name = "";
                                    }
                                    workpie.add(new Fr_work_pie(name, num, color[i]));
                                }
                                Float numbers = Float.valueOf(number);
                                for (int i = 0; i < workpie.size(); i++) {
                                    Float siz = Float.valueOf(workpie.get(i).getNum());
                                    Float sjie = siz / numbers * 100;
                                    BigDecimal b = new BigDecimal(sjie);
                                    float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                                    mData.add(new PieChartBeans(workpie.get(i).getName(), f1, workpie.get(i).getColor()));
                                }
                                mAdapter.getData(mData);
                            } else {
                                System.out.println("不包含");
                                mData.add(new PieChartBeans("未开始开工", 100f, color[1]));
                                mAdapter.getData(mData);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        PieChartOne.requestLayout();
                        PieChartOne.setDate(mData);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        closeTimer();
    }

    //亮点工程
    private void Bright() {
        OkGo.<String>post(Requests.ListByType)
                .params("page", 1)
                .params("size", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("result", s);
                        //每次请求数据，都需要清除之前的，避免重复
                        compangbrightList.clear();
                        groupbridhtList.clear();
                        projectbrightList.clear();
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id;
                                    try {
                                        id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        id = "";
                                    }
                                    String orgId;
                                    try {
                                        orgId = json.getString("orgId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        orgId = "";
                                    }
                                    String orgName;
                                    try {
                                        orgName = json.getString("detectionName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        orgName = "";
                                    }
                                    String taskName;
                                    try {
                                        taskName = json.getString("taskName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        taskName = "";
                                    }
                                    String taskId;
                                    try {
                                        taskId = json.getString("taskId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        taskId = "";
                                    }
                                    String leadername;
                                    try {
                                        leadername = json.getString("leaderName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        leadername = "";
                                    }
                                    String leaderImg;
                                    try {
                                        leaderImg = json.getString("leaderImg");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        leaderImg = "";
                                    }
                                    ArrayList<String> ImagePaths = new ArrayList<String>();
                                    try {

                                        JSONArray filePaths = json.getJSONArray("filePaths");
                                        for (int j = 0; j < filePaths.length(); j++) {
                                            String path = Requests.networks + filePaths.get(j);
                                            ImagePaths.add(path);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    int type = json.getInt("type");
                                    //判断type。
                                    if (type == 1) {
                                        //集团的
                                        groupbridhtList.add(new work_fr_bright_bean(id, orgId, taskName, orgName, taskId, leadername, leaderImg, type, ImagePaths));
                                    } else if (type == 2) {
                                        //公司的
                                        compangbrightList.add(new work_fr_bright_bean(id, orgId, taskName, orgName, taskId, leadername, leaderImg, type, ImagePaths));
                                    } else {
                                        //项目的
                                        projectbrightList.add(new work_fr_bright_bean(id, orgId, taskName, orgName, taskId, leadername, leaderImg, type, ImagePaths));
                                    }
                                }

//                            //判断是否有数据，如果有就展示界面，没有就隐藏界面
                                if (groupbridhtList.size() != 0) {
                                    bridhtGroup.setVisibility(View.VISIBLE);
                                    brightViewpagerAdapter.getData(groupbridhtList);
                                    bridhtGroup_text.setVisibility(View.GONE);
                                    bridhtgroupViewpager.setVisibility(View.VISIBLE);
                                } else {
                                    bridhtgroupViewpager.setVisibility(View.GONE);
                                    bridhtGroup_text.setVisibility(View.VISIBLE);
                                }

                                if (compangbrightList.size() != 0) {
                                    bridhtcompanyAdapter.getData(compangbrightList);
                                    bridhtcompanyViewpager.setVisibility(View.VISIBLE);
                                    bridhtCompany_text.setVisibility(View.GONE);
                                } else {
                                    bridhtCompany_text.setVisibility(View.VISIBLE);
                                    bridhtcompanyViewpager.setVisibility(View.GONE);
                                }

                                if (projectbrightList.size() != 0) {
                                    bridhtprojectAdapter.getData(projectbrightList);
                                    bridhtprojectViewpager.setVisibility(View.VISIBLE);
                                    bridhtProject_text.setVisibility(View.GONE);
                                } else {
                                    bridhtProject_text.setVisibility(View.VISIBLE);
                                    bridhtprojectViewpager.setVisibility(View.GONE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //如果返回的数据为空，定时器不启动
                            closeTimer();
                        }
                    }

                });
    }

    /**
     * 启动定时器
     */
    public void startTime() {
        //总时间
        final int count_time = 1000000;
        Observable.interval(0, 5, TimeUnit.SECONDS)
                //设置总共发送的次数
                .take(10000000)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        //aLong从0开始
                        return count_time - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        if (status == 1) {
                            //获取当前所处viewpager的页数
                            time = bridhtgroupViewpager.getCurrentItem();
                            //判断集合的长度是否等于0，如果等于0就不处理。
                            if (groupbridhtList.size() != 0) {
                                //集合的长度不等于0，那就判断获取的页数是否登录当前集合长度，如果等于，那就回到第一页
                                if (time + 1 == groupbridhtList.size()) {
                                    bridhtgroupViewpager.setCurrentItem(0);
                                } else {
                                    //如果不等于集合长度，那就在原来的页数上加1，跳转到下一页
                                    bridhtgroupViewpager.setCurrentItem(time + 1);
                                }
                            }
                            //设置下次执行的viewpager
                            status = 2;
                        } else if (status == 2) {
                            time = bridhtcompanyViewpager.getCurrentItem();
                            if (compangbrightList.size() != 0) {
                                if (time + 1 == compangbrightList.size()) {
                                    bridhtcompanyViewpager.setCurrentItem(0);
                                } else {
                                    bridhtcompanyViewpager.setCurrentItem(time + 1);
                                }
                            }
                            status = 3;
                        } else if (status == 3) {
                            time = bridhtprojectViewpager.getCurrentItem();
                            if (projectbrightList.size() != 0) {
                                if (time + 1 == projectbrightList.size()) {
                                    bridhtprojectViewpager.setCurrentItem(0);
                                } else {
                                    bridhtprojectViewpager.setCurrentItem(time + 1);
                                }
                            }
                            status = 1;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        closeTimer();
                    }
                });
    }

    /**
     * 关闭定时器
     */
    public void closeTimer() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void bright() {
        Bright();
    }
}