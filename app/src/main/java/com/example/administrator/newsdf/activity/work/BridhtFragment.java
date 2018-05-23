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
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.utils.Dates.stampToDate;

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
        Bright();
        return view;
    }

    private void Bright() {

        OkGo.<String>post(Requests.ListByType)
                .params("type", pos+1)
                .params("page",1 )
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                //id
                                String id;
                                try {
                                    id = json.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    id = "";
                                }
                                //标段ID
                                String orgId;
                                try {
                                    orgId = json.getString("orgId");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    orgId = "";
                                }
                                //标段名称
                                String orgName;
                                try {
                                    orgName = json.getString("orgName");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    orgName = "";
                                }
                                //任务名称
                                String taskName;
                                try {
                                    taskName = json.getString("taskName");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    taskName = "";
                                }
                                //责任人
                                String leadername;
                                try {
                                    leadername = json.getString("leaderName");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    leadername = "";
                                }
                                //图片
                                String leaderImg;
                                try {
                                    leaderImg = json.getString("leaderImg");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    leaderImg = "";
                                }
                                String TaskId;
                                try {
                                    TaskId = json.getString("taskId");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    TaskId = "";
                                }
                                String updateDate;
                                try {
                                    updateDate = stampToDate(json.getString("updateDate"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    updateDate = Dates.stampToDate(json.getString("createDate"));
                                }
                                ArrayList<String> ImagePaths=new ArrayList<String>();
                                try{

                                    JSONArray filePaths =json.getJSONArray("filePaths");
                                    for (int j = 0; j < filePaths.length(); j++) {
                                        String path=Requests.networks+ filePaths.get(j);
                                        ImagePaths.add(path);
                                    }

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                                mData.add(new BrightBean(id, orgId, orgName, taskName, leadername, leaderImg, updateDate,TaskId,pos,ImagePaths));
                            }
                            mAdapter.getData(mData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
}
