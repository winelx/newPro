package com.example.zcjlmodule.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.callback.Callback;
import com.example.zcjlmodule.callback.ChangOrgCallBackUtils;
import com.example.zcjlmodule.ui.activity.HomeZcActivity;
import com.example.zcjlmodule.ui.activity.ModuleMainActivity;
import com.example.zcjlmodule.ui.activity.PasswordActivity;
import com.example.zcjlmodule.ui.activity.UserOrgZcActivity;
import com.example.zcjlmodule.utils.fragment.FragmentmineUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import measure.jjxx.com.baselibrary.base.BaseFragment;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;


/**
 * description: 征拆首页 我的 界面  （承载界面HomeZcActivity）
 *
 * @author lx
 *         date: 2018/10/10 0010 下午 3:01
 *         update: 2018/10/10 0010
 *         version:
 */
public class MineFragmentZc extends BaseFragment implements View.OnClickListener,Callback {
    private View rootView;
    private Context mContext;
    private CircleImageView mineZcAvatar;
    private TextView minename, ascriptionOrg;
    private FragmentmineUtils utils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //如果view为空就加载界面，否则就不加载，避免切换界面重新加载界面,减少界面的绘制，降低内存消耗
        if (rootView == null) {
            mContext = HomeZcActivity.getInstance();
            ChangOrgCallBackUtils.setCallBack(this);
            utils = new FragmentmineUtils();
            rootView = inflater.inflate(R.layout.fragment_mine_zc, null);
            //登录人头像
            mineZcAvatar = rootView.findViewById(R.id.mine_zc_avatar);
            //登录人名称
            minename = rootView.findViewById(R.id.mine_zc_ascription_name);
            //当前组织
            rootView.findViewById(R.id.mine_zc_ascription_org).setOnClickListener(this);
            //切换组织
            rootView.findViewById(R.id.mine_zc_organization).setOnClickListener(this);
            //修改 密码
            rootView.findViewById(R.id.mine_zc_password).setOnClickListener(this);
            //关于我们
            rootView.findViewById(R.id.mine_zc_aboutme).setOnClickListener(this);
            //更新
            rootView.findViewById(R.id.mine_zc_update).setOnClickListener(this);
            //退出
            rootView.findViewById(R.id.mine_zc_exit).setOnClickListener(this);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_zc_organization:
                //切换组织
               startActivity(new Intent(mContext, UserOrgZcActivity.class));
                break;
            case R.id.mine_zc_password:
                //修改密码
                startActivity(new Intent(mContext, PasswordActivity.class));
                break;
            case R.id.mine_zc_aboutme:
                //关于我们
                ToastUtlis.getInstance().showShortToast("关于我们");
                break;
            case R.id.mine_zc_update:
                //更新
                utils.findversion(new FragmentmineUtils.OnClickLister() {
                    @Override
                    public void onClickLister(int status) {

                    }
                });
                break;
            case R.id.mine_zc_exit:
                //退出
                utils.logout(new FragmentmineUtils.OnClickLister() {
                    @Override
                    public void onClickLister(int status) {
                        mContext.startActivity(new Intent(mContext, ModuleMainActivity.class));
                        getActivity().finish();
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void callback() {

    }
}
