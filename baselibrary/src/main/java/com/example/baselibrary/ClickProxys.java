package com.example.baselibrary;

import android.view.View;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/27 0027}
 * 描述 防止点击事件快速点击（有提示）
 *{@link }
 */
public class ClickProxys implements View.OnClickListener {
    long mLastClickTime = 0;
    long timeInterval;
    IAgain iAgain;
    private View.OnClickListener onClickListener;

    public ClickProxys(long timeInterval, View.OnClickListener onClickListener, IAgain iAgain) {
        this.timeInterval = timeInterval;
        this.onClickListener = onClickListener;
        this.iAgain=iAgain;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis()-mLastClickTime>timeInterval){
            onClickListener.onClick(v);
            mLastClickTime=System.currentTimeMillis();
        }else{
            iAgain.onAgain();
        }

    }

    public interface IAgain{
        void onAgain();
    }
    /*
    实例：
    *tv3.setOnClickListener(new ClickProxy(1000, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SecondActivity.class));

            }
        }, new ClickProxy.IAgain() {
            @Override
            public void onAgain() {
                Toast.makeText(MainActivity.this, "请勿重复点击", Toast.LENGTH_SHORT).show();

            }
        }));
    *
    * */
}
