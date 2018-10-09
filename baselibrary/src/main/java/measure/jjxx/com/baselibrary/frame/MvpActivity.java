package measure.jjxx.com.baselibrary.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

;import measure.jjxx.com.baselibrary.base.BaseView;


/**
 * description: 封装的实现mvp的activity，
 * @author lx
 * date: 2018/10/9 0009 下午 3:59
*/
public abstract class MvpActivity <P extends MvpPresenter> extends BaseActivity implements MvpView{
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initMvp();
    }

    public void initMvp(){
        Mvp mvp = Mvp.getInstance();
        mvp.registerView(this.getClass(), this);
        mPresenter = (P) mvp.getPresenter(Mvp.getGenericType(this, 0));
        mPresenter.initPresenter(getBaseView());
    }

    /**
     * 确定IView类型
     * @return
     */
    public abstract BaseView getBaseView();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Mvp.getInstance().unRegister(this.getClass());
        mPresenter.destory();
    }
}
