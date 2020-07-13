package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckDetailBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.ExternalApi;
import com.example.administrator.newsdf.pzgc.adapter.PhotoAdapter;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;
import com.example.baselibrary.adapter.multiitem.MultipleItemRvAdapter;
import com.example.baselibrary.bean.photoBean;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.rx.LiveDataBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 * 创建时间： 2020/7/6 0006 10:24
 *
 * @author winelx
 */
public class ExternalCheckDetailAdapter extends MultipleItemRvAdapter<Object, BaseViewHolder> {
    public static final int TYPE_RECORD = 1;
    public static final int TYPE_OPTION = 2;
    public static final int TYPE_TAB = 3;
    private List<Object> mData;
    private String level;

    public ExternalCheckDetailAdapter(@Nullable List<Object> data) {
        super(data);
        this.mData = data;
        finishInitialize();
    }

    @Override
    protected int getViewType(Object bean) {
        if (bean instanceof CheckDetailBean.Project) {
            return TYPE_RECORD;
        } else if (bean instanceof CheckDetailBean.Company) {
            return TYPE_OPTION;
        } else if (bean instanceof CheckDetailBean.Group) {
            return TYPE_TAB;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new DetailProjectAdapter());
        mProviderDelegate.registerProvider(new DetailCompanyAdapter());
        mProviderDelegate.registerProvider(new DetailGroupAdapter());
    }

    /**
     * 说明：标段
     * 创建时间： 2020/7/6 0006 11:23
     *
     * @author winelx
     */
    public class DetailProjectAdapter extends BaseItemProvider<CheckDetailBean.Project, BaseViewHolder> {
        @Override
        public int viewType() {
            return TYPE_RECORD;
        }

        @Override
        public int layout() {
            return R.layout.adapter_item_external_checkdetail;
        }

        @Override
        public void convert(BaseViewHolder helper, CheckDetailBean.Project data, int position) {
            helper.setText(R.id.item_title, "项目质安部(C)");
            helper.setText(R.id.standardscore, Utils.isNull(data.getbStandardScore()));
            helper.setText(R.id.checkstandard, Utils.isNull(data.getbCheckStandard()));
            helper.setText(R.id.score, Utils.isNull(data.getbScore()));
            helper.setText(R.id.position, Utils.isNull(data.getbPosition()));
            helper.setText(R.id.description, Utils.isNull(data.getbDescription()));
            helper.setText(R.id.generate, Utils.isNull(data.getbGenerate()));
            TextView open_lin = helper.getView(R.id.open_lin);
            ImageView open_img = helper.getView(R.id.open_img);
            LinearLayout content_lin = helper.getView(R.id.content_lin);
            open_lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("展开".equals(open_lin.getText().toString())) {
                        open_lin.setText("收起");
                        open_img.setBackgroundResource(R.mipmap.top_blue_icon);
                        content_lin.setVisibility(View.VISIBLE);
                        LiveDataBus.get().with("recycler").setValue(helper.getAdapterPosition() + "");
                    } else {
                        open_lin.setText("展开");
                        open_img.setBackgroundResource(R.mipmap.bottom_blue_icon);
                        content_lin.setVisibility(View.GONE);

                    }
                }
            });
            if ("1".equals(level)) {
                open_lin.setText("展开");
                open_img.setBackgroundResource(R.mipmap.bottom_blue_icon);
                content_lin.setVisibility(View.GONE);
            }
            RecyclerView photo_rec = helper.getView(R.id.photo_rec);
            photo_rec.setLayoutManager(new GridLayoutManager(mContext, 4));
            ArrayList<String> photoPaths = new ArrayList<>();
            //标段
            if (data.getBFileList() != null) {
                for (int i = 0; i < data.getBFileList().size(); i++) {
                    CheckDetailBean.Project.BFileListBean beans = data.getBFileList().get(i);
                    photoPaths.add(Requests.networks + "/" + beans.getFilepath());
                }
            }
            if (photoPaths.size() > 0) {
                photo_rec.setVisibility(View.VISIBLE);
            } else {
                photo_rec.setVisibility(View.GONE);
            }
            PhotoAdapter adapter = new PhotoAdapter(mContext, photoPaths, "other");
            adapter.addview(false);
            adapter.addimage(false);
            photo_rec.setAdapter(adapter);
        }

    }

    /**
     * 说明：分公司
     * 创建时间： 2020/7/6 0006 11:23
     *
     * @author winelx
     */
    public class DetailCompanyAdapter extends BaseItemProvider<CheckDetailBean.Company, BaseViewHolder> {

        @Override
        public int viewType() {
            return TYPE_OPTION;
        }

        @Override
        public int layout() {
            return R.layout.adapter_item_external_checkdetail;
        }

        @Override
        public void convert(BaseViewHolder helper, CheckDetailBean.Company data, int position) {
            helper.setText(R.id.item_title, "分公司质安部（B）");
            helper.setText(R.id.checkstandard, Utils.isNull(data.getfCheckStandard()));
            helper.setText(R.id.score, Utils.isNull(data.getfScore()));
            helper.setText(R.id.position, Utils.isNull(data.getfPosition()));
            helper.setText(R.id.description, Utils.isNull(data.getfDescription()));
            helper.setText(R.id.generate, Utils.isNull(data.getfGenerate()));
            TextView standardscore_text = helper.getView(R.id.standardscore_text);
            standardscore_text.setText("管理行为扣分");
            TextView standardscore = helper.getView(R.id.standardscore);
            standardscore.setText(Utils.isNull(data.getbCheckScore()));
            if (!TextUtils.isEmpty(data.getbCheckScore())) {
                int scor = Integer.parseInt(data.getbCheckScore());
                if (scor < 0) {
                    standardscore.setTextColor(Color.parseColor("#FE0000"));
                } else {
                    standardscore.setTextColor(Color.parseColor("#000000"));
                }
            }
            TextView open_lin = helper.getView(R.id.open_lin);
            ImageView open_img = helper.getView(R.id.open_img);
            LinearLayout content_lin = helper.getView(R.id.content_lin);
            open_lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("展开".equals(open_lin.getText().toString())) {
                        open_lin.setText("收起");
                        open_img.setBackgroundResource(R.mipmap.top_blue_icon);
                        content_lin.setVisibility(View.VISIBLE);
                        LiveDataBus.get().with("recycler").setValue(helper.getAdapterPosition() + "");
                    } else {
                        open_lin.setText("展开");
                        open_img.setBackgroundResource(R.mipmap.bottom_blue_icon);
                        content_lin.setVisibility(View.GONE);
                    }
                }
            });
            if ("2".equals(level) || "1".equals(level)) {
                open_lin.setText("展开");
                open_img.setBackgroundResource(R.mipmap.bottom_blue_icon);
                content_lin.setVisibility(View.GONE);
            }
            RecyclerView photo_rec = helper.getView(R.id.photo_rec);
            photo_rec.setLayoutManager(new GridLayoutManager(mContext, 4));
            ArrayList<String> photoPaths = new ArrayList<>();
            //分公司
            if (data.getFFileList() != null) {
                for (int i = 0; i < data.getFFileList().size(); i++) {
                    CheckDetailBean.Company.FFileListBean beans = data.getFFileList().get(i);
                    photoPaths.add(Requests.networks + "/" + beans.getFilepath());
                }
            }
            if (photoPaths.size() > 0) {
                photo_rec.setVisibility(View.VISIBLE);
            } else {
                photo_rec.setVisibility(View.GONE);
            }
            PhotoAdapter adapter = new PhotoAdapter(mContext, photoPaths, "other");
            adapter.addview(false);
            adapter.addimage(false);
            photo_rec.setAdapter(adapter);
        }

    }

    /**
     * 说明：集团
     * 创建时间： 2020/7/6 0006 11:23
     *
     * @author winelx
     */
    public class DetailGroupAdapter extends BaseItemProvider<CheckDetailBean.Group, BaseViewHolder> {
        @Override
        public int viewType() {
            return TYPE_TAB;
        }

        @Override
        public int layout() {
            return R.layout.adapter_item_external_checkdetail;
        }

        @Override
        public void convert(BaseViewHolder helper, CheckDetailBean.Group data, int position) {
            helper.setText(R.id.item_title, "集团公司质安部（A）");
            helper.setText(R.id.score, Utils.isNull(data.getjScore()));
            helper.setText(R.id.position, Utils.isNull(data.getjPosition()));
            helper.setText(R.id.description, Utils.isNull(data.getjDescription()));
            helper.setText(R.id.generate, Utils.isNull(data.getjGenerate()));
            TextView standardscore_text = helper.getView(R.id.standardscore_text);
            standardscore_text.setText("管理行为扣分");
            TextView standardscore = helper.getView(R.id.standardscore);
            standardscore.setText(Utils.isNull(data.getfCheckScore()));
            if (!TextUtils.isEmpty(data.getfCheckScore())){
                int scor = Integer.parseInt(data.getfCheckScore());
                if (scor < 0) {
                    standardscore.setTextColor(Color.parseColor("#FE0000"));
                } else {
                    standardscore.setTextColor(Color.parseColor("#000000"));
                }
            }
            TextView open_lin = helper.getView(R.id.open_lin);
            ImageView open_img = helper.getView(R.id.open_img);
            LinearLayout content_lin = helper.getView(R.id.content_lin);
            open_lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("展开".equals(open_lin.getText().toString())) {
                        open_lin.setText("收起");
                        open_img.setBackgroundResource(R.mipmap.top_blue_icon);
                        content_lin.setVisibility(View.VISIBLE);
                        LiveDataBus.get().with("recycler").setValue(helper.getAdapterPosition() + "");
                    } else {
                        open_lin.setText("展开");
                        open_img.setBackgroundResource(R.mipmap.bottom_blue_icon);
                        content_lin.setVisibility(View.GONE);
                    }
                }
            });
            RecyclerView photo_rec = helper.getView(R.id.photo_rec);
            photo_rec.setLayoutManager(new GridLayoutManager(mContext, 4));
            ArrayList<String> photoPaths = new ArrayList<>();
            //集团
            if (data.getJFileList() != null) {
                for (int i = 0; i < data.getJFileList().size(); i++) {
                    CheckDetailBean.Group.JFileListBean beans = data.getJFileList().get(i);
                    photoPaths.add(Requests.networks + "/" + beans.getFilepath());
                }
            }
            if (photoPaths.size() > 0) {
                photo_rec.setVisibility(View.VISIBLE);
            } else {
                photo_rec.setVisibility(View.GONE);
            }
            PhotoAdapter adapter = new PhotoAdapter(mContext, photoPaths, "other");
            adapter.addview(false);
            adapter.addimage(false);
            photo_rec.setAdapter(adapter);
        }
    }

    public void getlevel(String string) {
        this.level = string;
    }
}
