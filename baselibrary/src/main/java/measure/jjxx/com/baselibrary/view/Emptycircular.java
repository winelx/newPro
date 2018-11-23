package measure.jjxx.com.baselibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:
 */

public class Emptycircular extends View {
    Paint paint;
    Context context;

    //构造方法
    public Emptycircular(Context context) {
        super(context);
    }

    public Emptycircular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Emptycircular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Emptycircular(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    /*绘图*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //得到屏幕宽高
        int width = getWidth();
        int radius = width - 450 / 2;
        int height = getHeight();
// 创建画笔
        Paint paint1 = new Paint();
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();
// 消除锯齿
        paint1.setAntiAlias(true);
        paint2.setAntiAlias(true);
        paint3.setAntiAlias(true);
        //画笔颜色
        paint1.setColor(Color.RED);
        paint2.setColor(Color.WHITE);
        paint3.setColor(Color.BLUE);
// 画圆。确定位置
// canvas.drawRect(100,100,width/2,height/2,paint1);
// canvas.drawCircle(100,100,100,paint1);
// canvas.drawCircle(250,250,200,paint2);
// canvas.drawCircle(500,500,300,paint3);
        //设置圆环形状和大小
        RectF oval = new RectF(width - radius, width - radius, width + radius, width + radius);
        paint1.setStrokeWidth(450);
        canvas.drawArc(oval, -90, 90, false, paint1);

        canvas.drawCircle(width / 2, height / 2, 450, paint1);
        canvas.drawCircle(width / 2, height / 2, 300, paint2);
        canvas.drawCircle(width / 2, height / 2, 200, paint3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
