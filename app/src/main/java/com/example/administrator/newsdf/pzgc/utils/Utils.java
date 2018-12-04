package com.example.administrator.newsdf.pzgc.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.administrator.newsdf.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    public final static String DATA = "data";
    public final static String CoorType_GCJ02 = "gcj02";
    public final static String CoorType_BD09LL = "bd09ll";
    public final static String CoorType_BD09MC = "bd09";
    /***
     *61 ： GPS定位结果，GPS定位成功。
     *62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
     *63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
     *65 ： 定位缓存的结果。
     *66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
     *67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
     *68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
     *161： 网络定位结果，网络定位定位成功。
     *162： 请求串密文解析失败。
     *167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
     *502： key参数错误，请按照说明文档重新申请KEY。
     *505： key不存在或者非法，请按照说明文档重新申请KEY。
     *601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
     *602： key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
     *501～700：key验证失败，请按照说明文档重新申请KEY。
     */


    public static float[] EARTH_WEIGHT = {0.1f, 0.2f, 0.4f, 0.6f, 0.8f}; // 推算计算权重_地球
    //public static float[] MOON_WEIGHT = {0.0167f,0.033f,0.067f,0.1f,0.133f};
    //public static float[] MARS_WEIGHT = {0.034f,0.068f,0.152f,0.228f,0.304f};

    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void showLog(String key, String Value) {
        Log.i(key, Value);
    }


    //界面亮度
    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }


    public static String[] year = new String[]{"2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};

    public static String[] month = new String[]{
            "01", "02", "03",
            "04", "05", "06",
            "07", "08", "09",
            "10", "11", "12"};

    public static String[] quarter = new String[]{
            "一季度", "二季度", "三季度",
            "四季度",};


    public static String[] day = new String[]{
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30", "31"};
    public static String[] daytwo = new String[]{
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28"};
    public static String[] daytwos = new String[]{
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29"};
    public static String[] dayth = new String[]{
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30"};

    public static List<String> getyear() {
        ArrayList<String> year = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int teger = 2012 + i * 4;
            year.add(teger + "");
        }
        return year;
    }


    //选择器抽取方法
    public static void setPicker(NumberPicker View, String[] str, int time) {
        //设置需要显示的数组
        View.setDisplayedValues(str);
        View.setWrapSelectorWheel(true);
        //这两行不能缺少,不然只能显示第一个，关联到format方法
        View.setMinValue(0);
        View.setMaxValue(str.length - 1);
        //中间不可点击
        View.setValue(time);
        View.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
    }

    //获取当前 年与日
    public static String titleDay() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        return date;
    }

    //获取当前 年与日
    public static String titleMonth() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        return date;
    }

    public static int titleyear() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        int datayear = 0;
        for (int i = 0; i < year.length; i++) {
            String str = year[i];
            if (str.equals(date)) {
                datayear = i;
            }
        }
        return datayear;
    }

    public static int getquarter() {
        int quarter = 0;
        Date myDate = new Date();
        int dateMonth = myDate.getMonth();
        dateMonth = dateMonth + 1;
        if (dateMonth == 1 || dateMonth == 2 || dateMonth == 3) {
            quarter = 1;
        } else if (dateMonth == 4 || dateMonth == 5 || dateMonth == 6) {
            quarter = 2;
        } else if (dateMonth == 7 || dateMonth == 8 || dateMonth == 9) {
            quarter = 3;
        } else if (dateMonth == 10 || dateMonth == 11 || dateMonth == 12) {
            quarter = 4;
        }
        return quarter;
    }
    /**
     * 给tablayout添加分割线，分割线保持上下内边距一定距离
     *
     * @param mTbTitle 传递TabLayout，
     * @param context  上下文
     */
    public static void addtabpaddingdivider(TabLayout mTbTitle, Context context) {
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
                R.drawable.layout_divider_verticals));

    }

    /**
     * 界面控件的margin的设置
     * 这里的值全都是px，对比正常dp，需要乘2
     * @param v 控件
     * @param l 左
     * @param t 上
     * @param r 右
     * @param b 下
     */
    public  void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


}
