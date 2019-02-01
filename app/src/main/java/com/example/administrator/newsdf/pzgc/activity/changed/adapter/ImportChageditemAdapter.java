package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.ImportChageditemActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.util.List;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：导入问题项
 * {@link ImportChageditemActivity}
 */
public class ImportChageditemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImportChageditemAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.import_title, "2019年12月品质工程检查");
        helper.setText(R.id.import_content, "主要内容太");
        helper.setText(R.id.import_checkpeople, "检查人：测试人员");
        helper.setText(R.id.import_checkdata, "检查日期：2019年1月31日");
        helper.setText(R.id.import_checkorg, "检查组织：规则路桥集团有限公司");
        helper.setText(R.id.import_checkscore, Dates.setText(mContext, "总分:80", 2, R.color.red));
    }
}
