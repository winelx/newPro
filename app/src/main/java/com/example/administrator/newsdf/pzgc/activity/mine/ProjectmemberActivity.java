package com.example.administrator.newsdf.pzgc.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.pzgc.adapter.ProjectmemberAdapter;
import com.example.administrator.newsdf.pzgc.adapter.SettingAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Icon;
import com.example.administrator.newsdf.pzgc.bean.Makeup;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.tree_path;


/**
 * description: 项目成员
 *
 * @author lx
 *         date: 2018/3/21 0021 下午 4:28
 *         update: 2018/3/21 0021
 *         version:
 */
public class ProjectmemberActivity extends BaseActivity {
    private ListView uslistView;
    private LinearLayout comback;
    private SettingAdapter<Icon> mAdapter = null;
    private ArrayList<Icon> mData, searchData;
    private Context mContext;
    private LinearLayout homeBackgroud;
    private TextView homeBackgroudText, mb_title;
    private String orgid, treepath, treetitle, treeids;
    private EditText treeEditextSearch;
    private RecyclerView mRecycler;
    private ArrayList<Makeup> makeups;
    private ArrayList<Makeup> listada;
    private List<String> listPath;
    private List<String> listIds;
    private ProjectmemberAdapter memberAdapter;
    private SmartRefreshLayout drawerLayout_smart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectmember);
        Dates.getDialog(ProjectmemberActivity.this, "请求数据中...");
        mContext = ProjectmemberActivity.this;
        //存储联系人信息
        mData = new ArrayList<>();
        //inteint传递过来的id，标题
        makeups = new ArrayList<>();
        //搜索结果
        searchData = new ArrayList<>();
        //intent的id
        listIds = new ArrayList<>();
        //intent传递的名称
        listPath = new ArrayList<>();
        Intent intent = getIntent();
        orgid = intent.getStringExtra("id");
        treepath = intent.getStringExtra("path");
        treetitle = intent.getStringExtra("title");
        treeids = intent.getStringExtra("ids");
        //将传递过来的String转成集合
        //名称集合
        listPath = Dates.stringToLists(treepath);
        //id集合
        listIds = Dates.stringToLists(treeids);
        //将id和名称存入一个集合
        for (int i = 0; i < listPath.size(); i++) {
            makeups.add(new Makeup(listPath.get(i), listIds.get(i)));
        }
        /**
         *  权限配置
         */
        //标题
        mb_title = (TextView) findViewById(R.id.mb_title);
        //搜索输入框
        treeEditextSearch = (EditText) findViewById(R.id.tree_editext_search);
        //数据为空的提示布局
        homeBackgroud = (LinearLayout) findViewById(R.id.mine_backgroud);
        //提示布局文字
        homeBackgroudText = (TextView) findViewById(R.id.mine_backgroud_text);
        //recycler，显示数据控件，用来显示节点层级的
        mRecycler = (RecyclerView) findViewById(tree_path);
        //下拉
        drawerLayout_smart= (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        drawerLayout_smart.setEnableRefresh(false);//禁止下拉
        drawerLayout_smart.setEnableLoadmore(false);//禁止上拉
//        drawerLayout_smart.setEnableOverScrollBounce(true);//仿ios越界
//        drawerLayout_smart.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        //返回键
        comback = (LinearLayout) findViewById(R.id.com_back);
        //设置标题
        mb_title.setText(treetitle);
        //显示联系人列表的控件
        uslistView = (ListView) findViewById(R.id.us_listView);
        // 若要部分 SpannableString 可点击，需要如下设置
        okgo(orgid);
        //listview的适配器
        mAdapter = new SettingAdapter<Icon>(mData, R.layout.setting_member_item) {
            @Override
            public void bindView(ViewHolder holder, final Icon obj) {
                //头像
                holder.setImages(R.id.memberImageView, obj.getImageUrl(), mContext);
                //名字
                holder.setText(R.id.member_name, obj.getName());
                //手机号
                holder.setText(R.id.member_moblie, obj.getUserId());
                holder.setOnClickListener(R.id.call_phone, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + obj.getMoblie()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        };

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycler.setLayoutManager(layoutManager);
        //设置增加或删除条目的动画
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        // 设置适配器
        memberAdapter = new ProjectmemberAdapter(this);
        mRecycler.setAdapter(memberAdapter);
        //更新数据
        memberAdapter.getData(makeups);
        //recycler列表界面item的点击事件
        memberAdapter.setOnItemClickListener(new ProjectmemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //将界面数据改为点击项的数据，拿到id请求数据
                okgo(makeups.get(position).getId());
                //更换节点后，标题也改变
                mb_title.setText(makeups.get(position).getName());
                //新建集合
                listada = new ArrayList<Makeup>();
                //获取到包括点击项的数据
                for (int i = 0; i <= position; i++) {
                    listada.add(makeups.get(i));
                }
                //并将数据重新更新到recycler，替换之前的数据
                memberAdapter.getData(listada);
            }
        });

        //返回键
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //editext回车键控制，改为搜索
        treeEditextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //获取editext的数据
                    String string = treeEditextSearch.getText().toString();
                    //情书集合
                    searchData.clear();
                    //便利数据拿到有关键只的数据并添加到新集合中
                    for (int i = 0; i < mData.size(); i++) {
                        String name = mData.get(i).getName();
                        if (name.contains(string)) {
                            searchData.add(mData.get(i));
                        }
                    }
                    //拿到数据后，隐藏键盘
                    try {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(ProjectmemberActivity.this.getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {

                    }

                    //更新数据
                    mAdapter.getData(searchData);
                }
                return false;
            }
        });
        //联系人列表
        uslistView.setAdapter(mAdapter);
    }

    /**
     * 网络请求
     * OkGo.post(Request.Members)
     */

    void okgo(final String id) {
        OkGo.post(Requests.Members)
                .params("orgId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("jsonArray", s);
                        if (s.contains("data")) {
                            mData.clear();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String str = jsonObject.getString("msg");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                LogUtil.i("jsonArray", jsonArray.length());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject content = jsonArray.getJSONObject(i);
                                    String id = content.getString("id");
                                    String userId;
                                    try {
                                        userId = content.getString("positionName");
                                    } catch (JSONException e) {
                                        userId = "";
                                    }
                                    String name = content.getString("name");
                                    String moblie = content.getString("moblie");
                                    String imageUrl;
                                    try {
                                        imageUrl = content.getString("imageUrl");
                                        imageUrl = Requests.networks + imageUrl;

                                    } catch (JSONException e) {
                                        imageUrl = "";
                                    }
                                    mData.add(new Icon(id, userId, name, moblie, imageUrl));
                                }
                                if (mData.size() != 0) {
                                    mAdapter.getData(mData);
                                    uslistView.setVisibility(View.VISIBLE);
                                    homeBackgroud.setVisibility(View.GONE);
                                } else {
                                    mAdapter.getData(mData);
                                    homeBackgroud.setVisibility(View.VISIBLE);
                                    homeBackgroudText.setText(R.string.text_nupoint);
                                }
                                Dates.disDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        } else {
                            mData.clear();
                            mAdapter.getData(mData);
                            Dates.disDialog();
                            homeBackgroud.setVisibility(View.VISIBLE);
                            homeBackgroudText.setText(R.string.text_nupoint);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                        homeBackgroudText.setText(R.string.text_okgo_error);
                        homeBackgroud.setVisibility(View.VISIBLE);
                    }
                });
    }

}