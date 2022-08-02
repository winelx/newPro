package com.example.administrator.yanghu.pzgc.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

/**
 * @author lx
 * 创建日期：2019/3/9 0009
 * 描述：消息通知
 * {@link NoticeAdapter}
 */
public class NoticedBeanItemProvider extends BaseItemProvider<NoticedBean, BaseViewHolder> {

    @Override
    public int viewType() {
        return NoticeAdapter.TYPE_ONE;
    }

    @Override
    public int layout() {
        return R.layout.adapter_item_notice;
    }

    @Override
    public void convert(BaseViewHolder holder, NoticedBean bean, int position) {
        //时间
        if (bean.getNoticeDate() != null) {
            holder.setText(R.id.noticed_data, bean.getNoticeDate().substring(0, 10));
        }
        //标题
        if (bean.getModelName() != null) {
            holder.setText(R.id.noticed_title, bean.getModelName());
        }
        //内容
        if (bean.getShowContent() != null) {
            holder.setText(R.id.noticed_content, bean.getShowContent());
        }
        TextView isread = holder.getView(R.id.isread);
        isread.setVisibility("1".equals(bean.getIsRead()) ? View.VISIBLE : View.GONE);
    }
}
