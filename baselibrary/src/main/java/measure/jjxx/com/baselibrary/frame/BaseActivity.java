package measure.jjxx.com.baselibrary.frame;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lx
 * @Created by: 2018/10/9 0009.
 * @description:基础的Baseactivity
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        //屏幕只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return super.onCreateView(name, context, attrs);

    }
}
