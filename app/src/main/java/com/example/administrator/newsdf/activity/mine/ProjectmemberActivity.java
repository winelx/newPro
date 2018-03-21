package com.example.administrator.newsdf.activity.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.adapter.ProjectmemberAdapter;
import com.example.administrator.newsdf.adapter.SettingAdapter;
import com.example.administrator.newsdf.bean.Icon;
import com.example.administrator.newsdf.bean.Makeup;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionItem;
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
public class ProjectmemberActivity extends AppCompatActivity  {
    private ListView uslistView;
    private LinearLayout comback;
    private SettingAdapter mAdapter = null;
    private ArrayList<Icon> mData, searchData;
    private Context mContext;
    private CheckPermission checkPermission;
    private LinearLayout homeBackgroud;
    private TextView homeBackgroudText;
    private String orgid, treepath, treetitle, treeids;
    private EditText treeEditextSearch;
    private RecyclerView mRecycler;
    private ArrayList<Makeup> makeups;
    private ArrayList<Makeup> listada;
    private List<String> listPath;
    private List<String> listIds;
    private ProjectmemberAdapter memberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectmember);
        Dates.getDialog(ProjectmemberActivity.this, "请求数据中...");

        mData = new ArrayList<>();
        makeups = new ArrayList<>();
        searchData = new ArrayList<>();
        listIds = new ArrayList<>();
        listPath = new ArrayList<>();
        Intent intent = getIntent();
        orgid = intent.getStringExtra("id");
        treepath = intent.getStringExtra("path");
        treetitle = intent.getStringExtra("title");
        treeids = intent.getStringExtra("ids");
        mContext = ProjectmemberActivity.this;

        listPath = Dates.stringToList(treepath);
        listIds = Dates.stringToList(treeids);
        for (int i = 0; i < listPath.size(); i++) {
            makeups.add(new Makeup(listPath.get(i), listIds.get(i)));
        }
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CALL_PHONE, getString(R.string.permission_cus_item_phone), R.drawable.permission_ic_phone));
        HiPermission.create(ProjectmemberActivity.this)
                .permissions(permissonItems)
                .checkMutiPermission(null);

        treeEditextSearch = (EditText) findViewById(R.id.tree_editext_search);
        homeBackgroud = (LinearLayout) findViewById(R.id.mine_backgroud);
        homeBackgroudText = (TextView) findViewById(R.id.mine_backgroud_text);
        mRecycler = (RecyclerView) findViewById(tree_path);
        LinearLayout searchLinear = (LinearLayout) findViewById(R.id.search_linear);
        comback = (LinearLayout) findViewById(R.id.com_back);

        uslistView = (ListView) findViewById(R.id.us_listView);
        searchLinear.setVisibility(View.GONE);
        // 若要部分 SpannableString 可点击，需要如下设置
        okgo(orgid);
        mAdapter = new SettingAdapter<Icon>(mData, R.layout.setting_member_item) {
            @Override
            public void bindView(ViewHolder holder, final Icon obj) {
                //头像
                holder.setImages(R.id.circleImageView, obj.getImageUrl());
                //名字
                holder.setText(R.id.member_name, obj.getName());
                //手机号
                holder.setText(R.id.member_moblie, obj.getMoblie());
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
        //联系人列表
        uslistView.setAdapter(mAdapter);
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
        memberAdapter.getData(makeups);
        memberAdapter.setOnItemClickListener(new ProjectmemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                okgo(makeups.get(position).getId());
                listada = new ArrayList<Makeup>();
                for (int i = 0; i <= position; i++) {
                    listada.add(makeups.get(i));
                }
                memberAdapter.getData(listada);
//                ToastUtils.showShortToast(makeups.get(position).getName()+"&&"+makeups.get(position).getId());
            }
        });


        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        treeEditextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String string = treeEditextSearch.getText().toString();
                    searchData.clear();
                    for (int i = 0; i < mData.size(); i++) {
                        String name = mData.get(i).getName();
                        if (name.indexOf(string) != -1) {
                            searchData.add(mData.get(i));
                        }
                    }
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ProjectmemberActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    mAdapter.getData(searchData);
                }
                return false;
            }
        });

    }

    /**
     * 网络请求
     */

    void okgo(String id) {
        OkGo.post(Request.Members)
                .params("orgId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            mData.clear();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String str = jsonObject.getString("msg");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject content = jsonArray.getJSONObject(i);
                                    String id = content.getString("id");
                                    String userId = content.getString("userId");
                                    String name = content.getString("name");
                                    String moblie = content.getString("moblie");
                                    String imageUrl = content.getString("imageUrl");
                                    mData.add(new Icon(id, userId, name, moblie, imageUrl));
                                }
                                if (mData.size() != 0) {
                                    homeBackgroud.setVisibility(View.GONE);
                                    mAdapter.getData(mData);
                                } else {
                                    homeBackgroud.setVisibility(View.VISIBLE);
                                    homeBackgroudText.setText("数据加载失败，试试下拉刷新");
                                }
                                Dates.disDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


//    private String getTime() {
//        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
//    }

}