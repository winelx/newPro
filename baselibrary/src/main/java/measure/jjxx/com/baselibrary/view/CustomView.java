package measure.jjxx.com.baselibrary.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:
 */

public class CustomView extends View {

    private Paint mPaint;
    private float circle_one_center = 200f;//圆1圆心位置
    private float circle_one_radius = 30f;//圆1半径
    private float circle_two_center = 200f;//圆2圆心位置
    private float circle_two_radius = 40f;//圆2半径
    private float circle_one_changed_radius;//圆2随着两圆距离改变的半径
    private float maxDistance = 300f;//最大距离
    private float distance;//两圆实时距离
    private double offset_angle;//偏移角度
    private boolean isOutRange;//是否超出最大距离
    private boolean isDisappear;//是否消失
    private PointF[] circle_one_bayes_points = new PointF[2];
    private PointF[] circle_two_bayes_points = new PointF[2];
    private PointF control_point = new PointF();
    private PointF circle_one_center_point = new PointF(circle_one_center,circle_one_center);
    private PointF circle_two_center_point = new PointF(circle_two_center,circle_two_center);

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        distance = (float) Math.sqrt(Math.pow(circle_one_center_point.x - circle_two_center_point.x, 2)
                + Math.pow(circle_one_center_point.y - circle_two_center_point.y, 2));

        if (distance > maxDistance) {
            isOutRange = true;
        } else {
            circle_one_changed_radius = (1 - distance / maxDistance) * circle_one_radius;
        }

        offset_angle = 0;

        if (circle_one_center_point.x - circle_two_center_point.x != 0)
            offset_angle = (circle_one_center_point.y - circle_two_center_point.y)
                    / (circle_one_center_point.x - circle_two_center_point.x);

        circle_one_bayes_points = getBayesPoint(circle_one_center_point, circle_one_changed_radius, offset_angle);
        circle_two_bayes_points = getBayesPoint(circle_two_center_point, circle_two_radius, offset_angle);

        control_point = new PointF((circle_one_center_point.x + circle_two_center_point.x)/2,
                (circle_one_center_point.y + circle_two_center_point.y)/2);
        canvas.save();
        if (!isDisappear) {
            if (!isOutRange) {
                Path path = new Path();
                path.moveTo(circle_one_bayes_points[0].x, circle_one_bayes_points[0].y);
                path.quadTo(control_point.x, control_point.y,
                        circle_two_bayes_points[0].x, circle_two_bayes_points[0].y);
                path.lineTo(circle_two_bayes_points[1].x, circle_two_bayes_points[1].y);
                path.quadTo(control_point.x, control_point.y,
                        circle_one_bayes_points[1].x, circle_one_bayes_points[1].y);
                path.close();

                canvas.drawPath(path, mPaint);
                canvas.drawCircle(circle_one_center_point.x, circle_one_center_point.y, circle_one_changed_radius, mPaint);
            }
            canvas.drawCircle(circle_two_center_point.x, circle_two_center_point.y, circle_two_radius, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isOutRange = false;
                isDisappear = false;
                circle_one_center_point.set(event.getX(), event.getY());
                circle_two_center_point.set(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                circle_two_center_point.set(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isOutRange) {
                    distance = (float) Math.sqrt(Math.pow(circle_one_center_point.x - circle_two_center_point.x, 2)
                            + Math.pow(circle_one_center_point.y - circle_two_center_point.y, 2));
                    if (distance > maxDistance) {
                        isDisappear = true;
//                        invalidate();
                    } else {
                        circle_two_center_point.set(circle_one_center_point.x, circle_one_center_point.y);
                    }
                    invalidate();
                } else {
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float percent = animation.getAnimatedFraction();
                            circle_two_center_point.set(
                                    circle_one_center_point.x + (circle_two_center_point.x - circle_one_center_point.x) * (1 - percent),
                                    circle_one_center_point.y + (circle_two_center_point.y - circle_one_center_point.y) * (1 - percent)
                            );
                            invalidate();
                        }
                    });
                    valueAnimator.setInterpolator(new OvershootInterpolator(10));
                    valueAnimator.setDuration(500);
                    valueAnimator.start();
                }
                break;
                default:break;
        }
        return true;
    }

    public static PointF[] getBayesPoint(PointF pMiddle, float radius, Double lineK) {
        PointF[] points = new PointF[2];

        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);

        return points;
    }

}