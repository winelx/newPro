package com.example.administrator.newsdf.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.SettingAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.BrightspotActivity;
import com.example.administrator.newsdf.activity.work.NotuploadActivity;
import com.example.administrator.newsdf.activity.work.OrganiwbsActivity;
import com.example.administrator.newsdf.activity.work.PchooseActivity;
import com.example.administrator.newsdf.activity.work.PushCheckActivity;
import com.example.administrator.newsdf.bean.Fr_work_pie;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.SPUtils;
import com.example.administrator.newsdf.view.MultiImageView;
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

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.pphotoadm;


/**
 * @author ：lx
 *         时间：2017/11/23 0023:下午 15:37
 *         说明：
 */
public class WorkFragment extends Fragment {
    private View rootView;
    private Context mContext;
    /**
     * 饼状图
     */
    private PieChartOne PieChartOne;
    /**
     * 饼状图数据
     */
    private List<PieChartBeans> mData;
    /**
     * 饼状图参数
     */
    private GridView fr_work_grid;

    private String name = "";
    private int num = 0;
    private int number = 0;

    boolean status = false;
    /**
     * 通用适配器
     */
    private SettingAdapter mAdapter;
    private MultiImageView multiImageView;
    private ArrayList<Fr_work_pie> workpie = new ArrayList<>();
    private TextView frWorkDn, taskManagement, moreandmore, frWorkName, taskPush, photoManagement, uploade;

    private String[] color = {"#2F4554", "#D48265", "#91C7AE", "#749F83", "#C23531", "#61A0A8", "#61a882", "#68a861", "#618ca8", "#2F4554", "#D48265", "#91C7AE", "#749F83", "#C23531", "#61A0A8", "#61a882", "#68a861", "#618ca8"};

    String url1 = "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_01small_02_2018414191166C.jpg，" +
            "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg，" +
            "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg，" +
            "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg，" +
            "http://img1.gamersky.com/image2018/04/20180414_zl_91_4/gamersky_02small_04_20184141911D6A.jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_work, null);
            mData = new ArrayList<>();
            //初始化控件Id
            findId();
            //设置当前时间的问候
            setTime();
            //获取到当前登录人的名字，并展示
            frWorkName.setText(SPUtils.getString(mContext, "staffName", null) + ",");

            moreandmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, BrightspotActivity.class));
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
            //下载离线图纸
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
        fr_work_grid.setAdapter(mAdapter);
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        String[] urls = url1.split("，");
        multiImageView.setMaxChildCount(12);
        multiImageView.setMoreImgBg(R.mipmap.ic_launcher);
        multiImageView.setImgs(urls, 4);
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
        photoManagement = (TextView) rootView.findViewById(pphotoadm);
        //离线图纸
        uploade = rootView.findViewById(R.id.uploade);
        multiImageView = rootView.findViewById(R.id.multiImageView);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (status != false) {
            okgo1();
        } else {
            okgo();
            PieChartOne.setDate(mData);
            status = true;
        }
    }

    //走oncreate
    private void okgo() {
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

    //走onstart
    private void okgo1() {
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
}