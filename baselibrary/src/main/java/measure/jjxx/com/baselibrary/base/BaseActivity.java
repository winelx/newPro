package measure.jjxx.com.baselibrary.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lx
 *         #Created by: 2018/9/17 0017.
 *         #description:
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        //屏幕竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return super.onCreateView(name, context, attrs);
    }
}
