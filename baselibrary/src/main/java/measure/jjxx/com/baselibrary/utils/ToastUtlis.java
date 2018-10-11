package measure.jjxx.com.baselibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class ToastUtlis {
    private static Toast toast;
    private Context mContext;


    private ToastUtlis() {

    }

    private static class InstanceHolder {
        private static final ToastUtlis INSTANCE = new ToastUtlis();
    }

    public static ToastUtlis getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public Context getApplictionContext() {
        return mContext;
    }

    /**
     * 显示长时间的Toast
     *
     * @param message 消息
     */
    public void showLongToast(String message) {


        toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(message);
        toast.show();

    }

    public void showLongToastCenter(String message) {

        toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(message);
        toast.show();

    }


    /**
     * 显示短时间Toast
     *
     * @param message 消息
     */
    public void showShortToast(String message) {

        toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

    /**
     * 显示短时间Toast
     *
     * @param message 消息
     */
    public void showShortToastCenter(String message) {
        toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

}
