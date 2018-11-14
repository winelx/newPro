package com.example.zcjlmodule.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.MessageZcItem;
import com.example.zcjlmodule.ui.activity.HomeZcActivity;
import com.example.zcjlmodule.ui.activity.original.NewAddOriginalZcActivity;
import com.example.zcjlmodule.utils.Api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import measure.jjxx.com.baselibrary.base.BaseFragment;
import measure.jjxx.com.baselibrary.ui.activity.PdfActivity;
import measure.jjxx.com.baselibrary.utils.PopCameraUtils;
import measure.jjxx.com.baselibrary.utils.TakePictureManager;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import release.App;

/**
 * description: 征拆首页的消息界面
 * 使用recyclerview的展示控件，根据点击的position判断点击的项，
 * （承载界面HomeZcActivity）
 *
 * @author lx
 *         2018/10/10 0010 下午 2:54
 */
public class MessageFragmentZc extends BaseFragment {
    private View rootView;//界面控件
    private Context mContext;//上下文
    private RecyclerView mRecyclerview;//列表控件
    private List<MessageZcItem> mData;//数据
    private MessageAdapter mAdapter;//适配器
    private TakePictureManager takePictureManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //如果view为空就加载界面，否则就不加载，避免切换界面重新加载界面,减少界面的绘制，降低内存消耗
        if (rootView == null) {
            mContext = getActivity();
            mData = new ArrayList<>();

            //初始化数据
            setmData();
            rootView = inflater.inflate(R.layout.fragment_message_zc, null);
            TextView toolbarTitle = rootView.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("消息");
            mRecyclerview = rootView.findViewById(R.id.message_fragment_recyclerview);
            //设置控件显示样式
            mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
            /**
             *             自定义分割线
             DividerItemDecoration divider = new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
             divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.custom_divider));
             mRecyclerview.addItemDecoration(divider);
             */
            //调加适配器，初始化布局和数据
            mRecyclerview.setAdapter(mAdapter = new MessageAdapter(R.layout.fragment_message_item_zc, mData));
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (position) {
                        case 0:
                            PopCameraUtils popCameraUtils = new PopCameraUtils();
                            popCameraUtils.showPopwindow(HomeZcActivity.getInstance(), new PopCameraUtils.CameraCallback() {

                                @Override
                                public void onComple(String str) {
                                    switch (str) {
                                        case "相机":
                                            takePictureManager = new TakePictureManager(MessageFragmentZc.this);
                                            //开启裁剪 比例 1:3 宽高 350 350  (默认不裁剪)
                                            takePictureManager.setTailor(1, 3, 450, 450);
                                            //拍照方式
                                            takePictureManager.startTakeWayByCarema();
                                            //回调
                                            takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                                                //成功拿到图片,isTailor 是否裁剪？ ,outFile 拿到的文件 ,filePath拿到的URl
                                                @Override
                                                public void successful(boolean isTailor, File outFile, Uri filePath) {
                                                    ToastUtlis.getInstance().showShortToast(filePath + "");
                                                }

                                                //失败回调
                                                @Override
                                                public void failed(int errorCode, List<String> deniedPermissions) {
                                                    Log.e("==w", deniedPermissions.toString());
                                                }
                                            });
                                            break;
                                        case "相册":
                                            PhotoPicker.builder()
                                                    .setPhotoCount(9)
                                                    .setShowCamera(true)
                                                    .setShowGif(true)
                                                    .setPreviewEnabled(false)
                                                    .start((Activity) mContext, PhotoPicker.REQUEST_CODE);
                                            break;
                                        default:
                                            break;

                                    }
                                }
                            });
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
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
    public class MessageAdapter extends BaseQuickAdapter<MessageZcItem, BaseViewHolder> {
        public MessageAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MessageZcItem item) {
            //图标
            helper.setImageResource(R.id.fragment_message_item_icon, item.getIcon());
            //标题
            helper.setText(R.id.fragment_message_item_title, item.getTitle());
            //消息内容
            helper.setText(R.id.fragment_message_item_content, item.getMessagecontent());
            //消息数量
            helper.setText(R.id.fragment_message_item_messagenumner, item.getMessagenumber().toString());
            //时间
            helper.setText(R.id.fragment_message_item_time, item.getData());
        }
    }

    public void setmData() {
        mData.add(new MessageZcItem(R.mipmap.zc_fragment_message_notice, "公告通知", "这是第一条消息这是第一条消息这是第一条消息这是第一条消息这是第一条消息", "2018-02-12", 52));
        mData.add(new MessageZcItem(R.mipmap.zc_fragment_message_pendingtask, "代办任务", "这是第一条消息这是第一条消息这是第一条消息这是第一条消息这是第一条消息", "2018-02-12", 52));
        mData.add(new MessageZcItem(R.mipmap.zc_fragment_message_dothetask, "已办任务", "这是第一条消息这是第一条消息这是第一条消息这是第一条消息这是第一条消息", "2018-02-12", 52));
        mData.add(new MessageZcItem(R.mipmap.zc_fragment_message_myinitiation, "我的发起", "这是第一条消息这是第一条消息这是第一条消息这是第一条消息这是第一条消息", "2018-02-12", 52));
    }

    //相机的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
        } else {
            takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
        }
    }
}
