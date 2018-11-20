package measure.jjxx.com.baselibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.math.BigDecimal;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description: 设置文字颜色
 */

public class TextUtils {
    /**
     * 设置有颜色文字
     */
    public static SpannableString setText(Context mContext, String str, int num) {
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FFFB0000"));
        spannableString.setSpan(colorSpan, num, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 保留两位小数
     *
     * @param str
     * @return
     */
    public static String bigdecimal(String str) {
        if (str.contains(".")) {
            BigDecimal bigDecimal = new BigDecimal(str + "00");
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } else {
            return str;
        }
    }
}
