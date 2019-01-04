package measure.jjxx.com.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.Arrays;
import java.util.List;

import measure.jjxx.com.baselibrary.R;


/**
 * @内容: 一些单独的方法
 * @author lx
 * @date: 2018/10/26 0026.
*/
public class BaseUtils {
    /**
     * @内容:     //隐藏软键盘
     * @author lx
     * @date: 2018/12/25 0025 上午 9:18
    */
    public static void activity(Activity activity) {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (activity.getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * @内容:     //显示软键盘
     * @author lx
     * @date: 2018/12/25 0025 上午 9:18
    */
    public static void showkeyboard(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }


    /**
     * @内容:     //隐藏软键盘
     * @author lx
     * @date: 2018/12/25 0025 上午 9:18
    */
    public static void hidekeyboard(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * string转集合
     */
    public static List<String> stringToList(String strs) {
        if (strs == "" && strs.isEmpty()) {
        } else {
            String str[] = strs.split(",");
            return Arrays.asList(str);
        }
        return null;
    }

    /**
     * 集合转string
     */
    public static String listToStrings(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }
        /**
         * @内容:     //身份证格式验证
         * @author lx
         * @date: 2018/12/25 0025 上午 9:12
        */
    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾
        boolean matches = IDNumber.matches(regularExpression);
        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }

    /**
     * 界面控件的margin的设置
     *
     * @param v 控件
     * @param l 左
     * @param t 上
     * @param r 右
     * @param b 下
     */
    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 给tablayout添加分割线（没有添加padding,）
     *
     * @param view    传递TabLayout
     * @param context 上下文
     */
    public void addtabdivider(TabLayout view, Context context) {
        LinearLayout linearLayout = (LinearLayout) view.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(context, R.drawable.layout_divider_vertical));

    }

    /**
     * 给tablayout添加分割线，分割线保持上下内边距一定距离
     *
     * @param mTbTitle 传递TabLayout，
     * @param context  上下文
     */
    public void addtabpaddingdivider(TabLayout mTbTitle, Context context) {
        LinearLayout linearLayout = (LinearLayout) mTbTitle.getChildAt(0);
        /**
         *
         *设置在该布局中的项目之间应如何显示分隔符
         *@ PARAM SealDe除除器：{Link Lang-SuffiSealErrE}St}中的一个或多个，
         *{Link LySuffSeuleReMe}}，或{Link Lang-SuffySealErthEnth}
         *显示分隔符，或{Link Lang-SuffiSealErnNOn}以显示无分隔符。
         */
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        /**
         *在分隔符两端显示设置填充。对于垂直布局，应用填充。
         *分隔符的左和右端。对于水平布局，填充被应用到顶部和
         *分压器的底端。
         *
         * @param padding 将应用于每个端的像素填充值
         *
         * @see #setShowDividers(int)
         * @see #setDividerDrawable(Drawable)
         * @see #getDividerPadding()
         */
        linearLayout.setDividerPadding(35);
        /**
         * 设置一个可作为项目之间的分隔符的可绘制性。
         *
         * @param divider 可以分割每个项目的可绘制的。
         *
         * @see #setShowDividers(int)
         *
         * @attr ref android.R.styleable#LinearLayout_divider
         */
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(context,
                R.drawable.layout_divider_vertical));

    }

}
