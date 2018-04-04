package com.example.administrator.newsdf.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.service.OgranCallbackUtils;
import com.example.administrator.newsdf.treeviews.SimpleTreeListViewAdapters;
import com.example.administrator.newsdf.treeviews.bean.OrgBeans;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
import com.example.administrator.newsdf.treeviews.utils.Nodes;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 切换组织界面
 */
public class OrganizationaActivity extends AppCompatActivity {
    private ListView mTree;
    private SimpleTreeListViewAdapters<OrgBeans> mAdapter;
    private List<OrgBeans> mDatas2;
    private List<OrgenBeans> mData;
    private Context mContext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationa);
        mContext=OrganizationaActivity.this;
        mData = new ArrayList<OrgenBeans>();
        mTree = (ListView) findViewById(R.id.organ_list_item);
        mDatas2 = new ArrayList<OrgBeans>();
        initDatas();
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDatas() {
        OkGo.<String>post(Request.Swatchmakeup)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                JSONObject json = jsonObject1.getJSONObject("organization");

                                String Id;
                                try {
                                    Id = json.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Id = "";
                                }
                                String parentId;
                                try {
                                    parentId = json.getString("parentId");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    parentId = "";
                                    mDatas2.add(new OrgBeans(1, 0, json.getString("name"), Id, parentId));
                                }
                                String name;
                                try {
                                    name = json.getString("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    name = "";
                                }
                                try {
                                    mAdapter = new SimpleTreeListViewAdapters<OrgBeans>(mTree, OrganizationaActivity.this,
                                            mDatas2, 0);
                                    mTree.setAdapter(mAdapter);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                mData.add(new OrgenBeans(Id, parentId, name));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    //添加数据
    public void getAdd(int position, Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                mAdapter.addExtraNode(position, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId());
            }
        }

    }

    //判断是否显示图标
    public boolean getmIcon(Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                return true;
            }
        }
        return false;
    }
        /**
     * 切换组织
     */
    public void member(final String orgid, final String name) {
        OkGo.post(Request.Swatch)
                .params("orgId", orgid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String msg = jsonObject.getString("msg");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                SPUtils.deleShare(mContext, "orgName");
                                SPUtils.deleShare(mContext, "orgId");
                                //所在组织ID
                                SPUtils.putString(mContext, "orgId", orgid);
                                //所在组织名称
                                SPUtils.putString(mContext, "username", name);
//                                //刷新全部和我的界面的数据
                                OgranCallbackUtils.removeCallBackMethod();
                                finish(); //此方法后才能返回主Activity
                            } else {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
