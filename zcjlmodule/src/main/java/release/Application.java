package release;

import measure.jjxx.com.baselibrary.base.BaseApplication;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * @author lx
 * @Created by: 2018/10/9 0009.
 * @description:
 */

public class Application extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        ToastUtlis.getInstance().init(this);
    }
}
