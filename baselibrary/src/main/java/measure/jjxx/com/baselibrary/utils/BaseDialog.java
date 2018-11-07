package measure.jjxx.com.baselibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import measure.jjxx.com.baselibrary.R;


/**
 * 加载时的等待dialog
 */
public class BaseDialog {
    public static Dialog dialog;

    public static void getDialog(Context mContext, String str,boolean status) {
            dialog = new Dialog(mContext, R.style.progress_dialog);
            dialog.setContentView(R.layout.base_waiting_dialog);
            dialog.setCanceledOnTouchOutside(status);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView text = (TextView) dialog.findViewById(R.id.id_tv_loadingmsg);
            text.setText(str);
            dialog.show();
    }
}
