package com.example.administrator.newsdf.pzgc.special.programme.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.programme.adapter.ProgrammeAuditorAdapter;
import com.example.administrator.newsdf.pzgc.utils.DividerItemDecoration;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;

import java.util.ArrayList;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


/**
 * @Author lx
 * @创建时间 2019/8/2 0002 15:15
 * @说明 赛选审核人
 **/

public class ProgrammeAuditorActivity extends BaseActivity implements View.OnClickListener {
    private EditText searchEditext;
    private Context mContext;
    private RecyclerView recycler_list;
    private ProgrammeAuditorAdapter mAdapter;
    private EmptyUtils emptyUtils;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        mContext = this;
        emptyUtils = new EmptyUtils(this);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        findViewById(R.id.com_back).setOnClickListener(this);
        searchEditext = findViewById(R.id.search_editext);
        recycler_list = findViewById(R.id.recycler_list);
        recycler_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProgrammeAuditorAdapter(R.layout.adapter_programmeauditor, list);
        mAdapter.setEmptyView(emptyUtils.init());
        mAdapter.openLoadAnimation();
        recycler_list.setAdapter(mAdapter);
        //editext回车键搜索
        searchEditext.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    try {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(ProgrammeAuditorActivity.this.getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {

                    }
                    String search = searchEditext.getText().toString();
                    if (search.length() != 0) {
                        Toast.makeText(mContext, search, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "输入框为空，请输入搜索内容！", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("sdsfs");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }
}
