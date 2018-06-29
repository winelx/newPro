package com.example.administrator.newsdf.zlaq.presenter;

import com.example.administrator.newsdf.zlaq.model.UnifedModel;
import com.example.administrator.newsdf.zlaq.model.modelImp.UnifedModelImp;
import com.example.administrator.newsdf.zlaq.view.UnifiedView;

/**
 * description: 控制层
 *
 * @author lx
 *         date: 2018/6/29 0029 下午 4:01
 *         update: 2018/6/29 0029
 *         version:
 */
public class UnifiedPresenter{

    private UnifedModel mUnifedModel;
    private UnifiedView mUnifiedView;

    //函数
    public UnifiedPresenter(UnifiedView UnifiedView) {
        super();
        this.mUnifiedView = UnifiedView;
        this.mUnifedModel = new UnifedModelImp();
    }

    //获取mode层的数据，并传递给view层
    public void getMode(String username, String password) {
        //显示进度
        //将数据传递到fragment
        mUnifedModel.login(username, password, new UnifedModel.OnClickListener() {
            @Override
            public void onComple() {
                //回调
                mUnifiedView.successful();
            }
        });
    }

    public void destory() {
        if (mUnifiedView != null) {
            mUnifiedView = null;
        }
    }


}
