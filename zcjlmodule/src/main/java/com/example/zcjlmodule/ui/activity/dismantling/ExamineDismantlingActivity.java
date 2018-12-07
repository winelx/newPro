package com.example.zcjlmodule.ui.activity.dismantling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.ExamineDismantlingAdapter;
import com.example.zcjlmodule.utils.activity.ExamineDismantlingUtils;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.adapter.PhotoPreview;
import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.bean.ExamineBean;
import measure.jjxx.com.baselibrary.ui.activity.PdfActivity;
import measure.jjxx.com.baselibrary.utils.PhotoUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;


/**
 * description:查看征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 4:50
 *         update: 2018/10/19 0019
 *         version:
 *         跳转界面StandardDecomposeZcActivity
 */
public class ExamineDismantlingActivity extends BaseActivity {
    private ExamineDismantlingAdapter mAdapter;
    private ExamineDismantlingUtils dismantlingUtils;
    private ArrayList<ExamineBean> list;
    private RecyclerView recyclerView;
    private Context mContext;
    private int page = 1;
    private String provincename = "", cityname = "", countyName = "", townName = "", filename = "",
            remarks = "", releasor = "", filenumber = "", createDate = "", number = "", id;
    private TextView examineProvincename, examineCityname, examineCountyname, examineTownname,
            examineReleasor, examineFilenumber, examineCreatedate, examine_number;
    private TextView examineFilename, examineRemarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_examine_dismantling);
        init();
        intent();
        recyclerView = (RecyclerView) findViewById(R.id.examine_layout_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(mAdapter = new ExamineDismantlingAdapter(list, mContext));
        //请求图片
        dismantlingUtils.getData(id, new ExamineDismantlingUtils.onclick() {
            @Override
            public void onSuccess(ArrayList<ExamineBean> data) {
                list.addAll(data);
                //网络请求成功
                mAdapter.setNewData(list);
            }

            @Override
            public void onError() {
                //网络请求失败

            }
        });
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAdapter.setOnItemClickListener(new ExamineDismantlingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ExamineBean examineBean = list.get(position);
                String type = examineBean.getType();
                if ("pdf".equals(type)) {
                    Intent intent = new Intent(mContext, PdfActivity.class);
                    intent.putExtra("http", examineBean.getPath());
                    startActivity(intent);
                } else if ("doc".equals(type) || "docx".equals(type)) {
                    ToastUtlis.getInstance().showShortToast("请到pc端查看");
                } else if ("xlsx".equals(type) || "xls".equals(type)) {
                    ToastUtlis.getInstance().showShortToast("请到pc端查看");
                } else {
                    PhotoPreview.builder().setCurrentItem(position).setPhotos(PhotoUtils.getPhoto(list)).start((Activity) mContext);
                }
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        //当前界面的帮助类
        dismantlingUtils = new ExamineDismantlingUtils();
        //图片
        list = new ArrayList<>();
        //标题
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("查看征拆标准");
        //省份
        examineProvincename = (TextView) findViewById(R.id.examine_provincename);
        //城市
        examineCityname = (TextView) findViewById(R.id.examine_cityname);
        //区域
        examineCountyname = (TextView) findViewById(R.id.examine_countyname);
        //乡镇
        examineTownname = (TextView) findViewById(R.id.examine_townname);
        //文件编号
        examineFilenumber = (TextView) findViewById(R.id.examine_filenumber);
        //文件名称
        examineFilename = (TextView) findViewById(R.id.examine_filename);
        //备注
        examineRemarks = (TextView) findViewById(R.id.examine_remarks);
        //发布人
        examineReleasor = (TextView) findViewById(R.id.examine_releasor);
        //时间
        examineCreatedate = (TextView) findViewById(R.id.examine_createDate);
        //标准编号
        examine_number = (TextView) findViewById(R.id.examine_number);

    }

    /**
     * 传递参数
     */
    private void intent() {
        Intent intent = getIntent();
        try {
            //省份
            provincename = intent.getStringExtra("provincename");
            //城市
            cityname = intent.getStringExtra("cityname");
            //区域
            countyName = intent.getStringExtra("countyName");
            //乡镇
            townName = intent.getStringExtra("townName");
            //文件名称
            filename = intent.getStringExtra("filename");
            //文件编号
            filenumber = intent.getStringExtra("filenumber");
            //备注
            remarks = intent.getStringExtra("remarks");
            //发布人
            releasor = intent.getStringExtra("releasor");
            //时间
            createDate = intent.getStringExtra("createDate");
            //标准编号
            number = intent.getStringExtra("number");
            //id
            id = intent.getStringExtra("id");
        } catch (NullPointerException e) {
            id = "";
        }
        examineProvincename.setText(provincename);
        examineCityname.setText(cityname);
        examineCountyname.setText(countyName);
        examineTownname.setText(townName);
        examineFilename.setText(filename);
        examineRemarks.setText(remarks);
        examineReleasor.setText(releasor);
        examineFilenumber.setText(filenumber);
        examineCreatedate.setText(createDate);
        examine_number.setText(number);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dismantlingUtils != null) {
            dismantlingUtils = null;
        }
    }
}

