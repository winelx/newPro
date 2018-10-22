package measure.jjxx.com.baselibrary.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import measure.jjxx.com.baselibrary.R;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class TextUtils {
    /**
     * 设置有颜色文字
     */
    public static SpannableString setText(Context mContext,String str, int num) {
        SpannableString sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.red)), num,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
