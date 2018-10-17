package measure.jjxx.com.baselibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author lx
 * @Created by: 2018/10/17 0017.
 * @description:解决viewpager 使用photoview异常的问题
 */

public class ViewPagerFixed extends android.support.v4.view.ViewPager {

    private static final String TAG = ViewPagerFixed.class.getName();

    public ViewPagerFixed(Context context) {
        super(context);
    }

    public ViewPagerFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
