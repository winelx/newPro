package measure.jjxx.com.baselibrary.base;


/**
 * @author lx
 *         #Created by: 2018/9/17 0017.
 *         #description:
 */

public class BaseMvpFragment<T extends BasePresenters> extends BaseFragment implements BaseView {
    public T mPresenter;


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
        mPresenter.mView = null;
    }
}
