package com.example.zcjlmodule.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.bean.MessageItem;
import com.example.zcmodule.R;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.frame.BaseFragment;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * description: 征拆首页的消息界面
 *
 * @author lx
 *         date: 2018/10/10 0010 下午 2:54
 *         update: 2018/10/10 0010
 *         version:
 */
public class MessageFragmentZc extends BaseFragment {
    private View rootView;
    private Context mContext;
    private RecyclerView mRecyclerview;
    private List<MessageItem> mData;
    private MessageAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //如果view为空就加载界面，否则就不加载，避免切换界面重新加载界面,减少界面的绘制，降低内存消耗
        if (rootView == null) {
            mContext = getActivity();
            mData = new ArrayList<>();
            rootView = inflater.inflate(R.layout.fragment_message_zc, null);
            TextView toolbarTitle = rootView.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("消息");
            mData.add(new MessageItem(R.mipmap.zc_fragment_message_notice, "公告通知", "这是第一条消息这是第一条消息这是第一条消息这是第一条消息这是第一条消息", "2018-02-12", 52));
            mData.add(new MessageItem(R.mipmap.zc_fragment_message_pendingtask, "代办任务", "这是第一条消息这是第一条消息这是第一条消息这是第一条消息这是第一条消息", "2018-02-12", 52));
            mData.add(new MessageItem(R.mipmap.zc_fragment_message_dothetask, "已办任务", "这是第一条消息这是第一条消息这是第一条消息这是第一条消息这是第一条消息", "2018-02-12", 52));
            mData.add(new MessageItem(R.mipmap.zc_fragment_message_myinitiation, "我的发起", "这是第一条消息这是第一条消息这是第一条消息这是第一条消息这是第一条消息", "2018-02-12", 52));
            mRecyclerview = rootView.findViewById(R.id.message_fragment_recyclerview);
            mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
            /**
             *             自定义分割线
             DividerItemDecoration divider = new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
             divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.custom_divider));
             mRecyclerview.addItemDecoration(divider);
             */
            mRecyclerview.setAdapter(mAdapter = new MessageAdapter(R.layout.fragment_message_item_zc, mData));
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (position) {
                        case 0:
                            ToastUtlis.getInstance().showShortToast(mData.get(position).getTitle());
                            break;
                        case 1:
                            ToastUtlis.getInstance().showShortToast(mData.get(position).getTitle());
                            break;
                        case 2:
                            ToastUtlis.getInstance().showShortToast(mData.get(position).getTitle());
                            break;
                        case 3:
                            ToastUtlis.getInstance().showShortToast(mData.get(position).getTitle());
                            break;
                        default:
                            break;
                    }
                }
            });

            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }
    //recyclerview适配器
    public class MessageAdapter extends BaseQuickAdapter<MessageItem, BaseViewHolder> {
        public MessageAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, MessageItem item) {
            helper.setImageResource(R.id.fragment_message_item_icon, item.getIcon());
            helper.setText(R.id.fragment_message_item_title, item.getTitle());
            helper.setText(R.id.fragment_message_item_content, item.getMessagecontent());
            helper.setText(R.id.fragment_message_item_messagenumner, item.getMessagenumber().toString());
            helper.setText(R.id.fragment_message_item_time, item.getData());

        }
    }

}
