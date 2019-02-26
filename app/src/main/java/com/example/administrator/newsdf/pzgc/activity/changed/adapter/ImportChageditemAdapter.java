package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedImportitemActivity;
import com.example.administrator.newsdf.pzgc.bean.ChagedImportitem;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.util.List;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：导入问题项
 * {@link ChagedImportitemActivity}
 */
public class ImportChageditemAdapter extends BaseQuickAdapter<ChagedImportitem, BaseViewHolder> {
    public ImportChageditemAdapter(int layoutResId, @Nullable List<ChagedImportitem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChagedImportitem item) {
        helper.setText(R.id.import_title, item.getTitlle());
        helper.setText(R.id.import_content, item.getContent());
        helper.setText(R.id.import_checkpeople, "检查人：" + item.getRealname());
        helper.setText(R.id.import_checkdata, "检查日期：" + item.getCheckDate().substring(0, 10));
        helper.setText(R.id.import_checkorg, "检查组织：" + item.getCheckOrgName());
        helper.setText(R.id.import_checkscore, Dates.setText(mContext, "总分:" + item.getScord(), 3, R.color.red));
        int iwork = item.getIwork();
        //是否内业检查，1不是2是
        if (iwork == 1) {
            helper.setVisible(R.id.import_status, false);
        }else {
            helper.setText(R.id.import_status, "内业");

        }
    }
}
