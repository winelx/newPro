package measure.jjxx.com.baselibrary.base;




/**
 * @author lx
 *         #Created by: 2018/9/17 0017.
 *         #description:
 */

public class BaseMvpActivity<T extends BasePresenters> extends BaseActivity implements BaseView {
    public T mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
//        mPresenter.mView = null;
    }
}
