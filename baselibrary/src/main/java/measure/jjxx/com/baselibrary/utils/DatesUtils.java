package measure.jjxx.com.baselibrary.utils;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

public class DatesUtils {

    //根据bii返回当前分辨率下该设置宽度
    public static int withFontSize(float screenWidth) {
        // 240X320 屏幕
        if (screenWidth == 1.0) {
            return 280;
            // 320X480 屏幕
        } else if (screenWidth == 2.0) {
            return 280;
            // 480X800 或 480X854 屏幕
        } else if (screenWidth == 3.0) {
            return 330;
        } else {
            return 330;

        }
    }

    //根据bii返回当前分辨率下该设置高度
    public static int higtFontSize(float screenWidth) {
        // 240X320 屏幕
        if (screenWidth == 1.0) {
            return 430;
            // 320X480 屏幕
        } else if (screenWidth == 2.0) {
            return 460;
            // 480X800 或 480X854 屏幕
        } else if (screenWidth == 3.0) {
            return 610;
        } else {
            return 610;
        }
    }
}
