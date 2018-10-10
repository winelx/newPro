package measure.jjxx.com.baselibrary.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * description: 封装的实现mvp的Fragment，
 *
 * @author lx
 *         date: 2018/10/9 0009 下午 3:59
 */
public abstract class MvpFragment<M extends MvpModel, V extends BaseView> extends BaseFragment implements MvpView {
    public MvpPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initMvp();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initMvp() {
        Mvp mvp = Mvp.getInstance();
        mvp.registerView(this.getClass(), this);
        mPresenter = (MvpPresenter) mvp.getPresenter(Mvp.getGenericType(this, 0));
        mPresenter.initPresenter(getBaseView());
    }

    public abstract BaseView getBaseView();

    @Override
    public void onDestroy() {
        super.onDestroy();

        Mvp.getInstance().unRegister(this.getClass());
        mPresenter.destory();
    }
}
