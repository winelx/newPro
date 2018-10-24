package measure.jjxx.com.baselibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import measure.jjxx.com.baselibrary.R;

/**
 * Created by Administrator on 2018/10/24 0024.
 */

public class BaseDialog {
    public static Dialog dialog;

    /**
     * 定时自动取消的dialog
     *
     * @param str
     */
    public static void getDialog(Context mContext, String str,boolean status) {
            dialog = new Dialog(mContext, R.style.progress_dialog);
            dialog.setContentView(R.layout.base_waiting_dialog);
            dialog.setCanceledOnTouchOutside(status);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView text = (TextView) dialog.findViewById(R.id.id_tv_loadingmsg);
            text.setText(str);
            dialog.show();
//        new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                dialog.dismiss();
//                return false;
//                //表示延迟3秒发送任务
//            }
//        }).sendEmptyMessageDelayed(0, time);

    }
}
