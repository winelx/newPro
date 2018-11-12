package measure.jjxx.com.baselibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @author lx
 * @Created by: 2018/11/12 0012.
 * @description:动态权限申请类
 */

public class PermissionUtils {
    /**
     * 检查是否拥有指定的所有权限
     */
    public boolean checkPermissionAllGranted(Context mContext, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
}
