package com.example.administrator.yanghu.pzgc.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.yanghu.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 作者：winelx
 * 时间：2017/11/27 0027:下午 14:59
 * 说明：帮助类
 */
public class Dates {
    public static Dialog progressDialog = null;
    private static ArrayList<String> JpMap = new ArrayList<>();

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static ArrayList<String> getsize() {
        return JpMap;
    }

    /**
     * 时间戳转时间
     */
    public static String stampToDate(String s) {
        String res;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = Long.valueOf(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }

        return res;
    }

    /**
     * 时间戳转时间
     */
    public static String stampToDates(String s) {
        String res;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long lt = Long.valueOf(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }


        return res;
    }

    public static String stampToDates(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        res = res.substring(0, res.length() - 3);
        return res;
    }

    public static String datato(String str) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date begin = dfs.parse(str);
        java.util.Date end = dfs.parse(getDate());
        //除以1000是为了转换成秒
        long between = (end.getTime() - begin.getTime()) / 1000;
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;
        if (day1 == 0) {
            if (hour1 == 0) {
                return "1小时";
            }
            return hour1 + "小时";
        }
        if (hour1 == 0) {
            return day1 + "天" + 1 + "小时";
        }
        return day1 + "天" + hour1 + "小时";
    }


    /**
     * 获取时间
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String getDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String getMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String getHH() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String getYear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 加载图片
     */
    public static void getImg(Context context, String imageUrl, ImageView view) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .error(R.mipmap.mine_avatar)
                .placeholder(R.mipmap.image_loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .into(view);
    }

    public static void setback(Context context, String imageUrl, ImageView view) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .error(R.mipmap.mine_avatar)
                .placeholder(R.mipmap.mine_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .into(view);
    }

    /**
     * 判断权限是否开启
     */
    private boolean checkWriteExternalPermission(Context context) {
        //你要判断的权限名字
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * 删除保存本地图片
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null) {
            for (File f : files) {
                // 判断是否为文件夹
                if (f.isDirectory()) {
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    // 判断是否存在
                    if (f.exists()) {
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        try {
            File file = new File(sPath);
            // 路径为文件且不为空则进行删除
            if (file.isFile() && file.exists()) {
                file.delete();
                flag = true;
            }
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 删除文件和目录
     */
    public static void clearFiles(String workspaceRootPath) {
        File file = new File(workspaceRootPath);
        if (file.exists()) {
            deleteFile(file);
        }
    }

    public static void deleteFile(File file) {
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } catch (Exception e) {

        }

    }

    /**
     * 判断路径下指定文件是否存在
     */
    public static boolean fileIsExists(File file) {
        try {
            // 总文件大小
            if (!file.exists()) {
                //不存在
                return false;
            }
        } catch (Exception e) {
            //
            return false;
        }
        //存在
        return true;
    }

    public static String downloadPath() {
        // 首先保存图片
        String strpath = "/storage/emulated/0/Android/data/com.example.administrator.yanghu/picker";
        File appDir = new File(strpath, "picker");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        return strpath;
    }


    public static String downloadPhoto(Bitmap bmp, String Title) {
        // 首先保存图片
        String strpath = "/storage/emulated/0/Android/data/com.example.administrator.yanghu/picker";
        File appDir = new File(strpath, "picker");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //文件夹和文件名
        File file = new File(appDir, Title);
        try {
            //使用输入流将数据写入本地，
            FileOutputStream fos = new FileOutputStream(file);
            //设置保存的 文件格式，是否压缩，输入流
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush(); //刷新文件流
            fos.close();//关闭输入流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回路径展示图片
        return appDir + "/" + Title;

    }

    public static String saveBitmap(Bitmap bitmap, String bitName) throws IOException {
        String strpaths = "/storage/emulated/0/Android/data/com.example.administrator.yanghu";
        File appDir = new File(strpaths, "picker");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String strpath = "/storage/emulated/0/Android/data/com.example.administrator.yanghu/picker";
        File file = new File(strpath + bitName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strpath + bitName;
    }


    public static Bitmap compressPixel(String filePath) {
        Bitmap bmp = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = 2;
        //inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];
        try {
            //load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
            if (bmp == null) {

                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(inputStream, null, options);
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        } finally {
            return bmp;
        }
    }


    /**
     * 获取应用的 SHA1 值， 可据此判断高德，百度地图 key 是否正确
     *
     * @param context 上下文
     * @return 应用的 SHA1 字符串, 比如： 53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */

    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte aPublicKey : publicKey) {
                String appendString = Integer.toHexString(0xFF & aPublicKey).toUpperCase();
                if (appendString.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 集合转string
     */
    public static String listToString(List<String> list) {
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
                result.append("，");
            }
            result.append(string);
        }
        return result.toString();
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
     * string转集合
     */
    public static List<String> stringToList(String strs) {
        if (strs == "" && strs.isEmpty()) {

        } else {
            String str[] = strs.split("，");
            return Arrays.asList(str);
        }
        return null;
    }

    public static List<String> stringToList(String strs, String type) {
        if (strs != null && !strs.isEmpty()) {
            String str[] = strs.split(type);
            List<String> lsit = Arrays.asList(str);
            List<String> data = new ArrayList<>(lsit);
            return data;
        }
        return null;
    }

    public static List<String> stringToLists(String strs) {
        if (strs == "" && strs.isEmpty()) {
        } else {
            String str[] = strs.split(",");
            return Arrays.asList(str);
        }
        return null;
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {
        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            try {
                InputMethodManager inputmanger = (InputMethodManager) activity
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
            } catch (Exception e) {

            }
        }
        return false;
    }

    /**
     * 定时自动取消的dialog
     *
     * @param activity
     * @param str
     */
    public static void getDialog(Activity activity, String str) {
        if (progressDialog == null) {
            progressDialog = new Dialog(activity, R.style.progress_dialog);
            progressDialog.setContentView(R.layout.waiting_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView text = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            text.setText(str);
            //点击外部取消
            progressDialog.setCanceledOnTouchOutside(false);
//            //物理返回键
//            progressDialog.setCancelable(false);
            progressDialog.show();
            new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    return false;
                    //表示延迟3秒发送任务
                }
            }).sendEmptyMessageDelayed(0, 2000);
        }
    }

    /**
     * 展示dailog
     */
    public static void getDialogs(Activity activity, String str) {
        if (progressDialog == null) {
            progressDialog = new Dialog(activity, R.style.progress_dialog);
            progressDialog.setContentView(R.layout.waiting_dialog);
            //点击外部取消
            progressDialog.setCanceledOnTouchOutside(false);
//            //物理返回键
//            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView text = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            text.setText(str);
            progressDialog.show();
        }
    }

    public static void disDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param filePath 文件绝对路径
     * @return
     */
    public static byte[] fileByte(String filePath) {
        ByteArrayOutputStream bos = null;
        BufferedInputStream in = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException("file not exists");
            }
            bos = new ByteArrayOutputStream((int) file.length());
            in = new BufferedInputStream(new FileInputStream(file));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void addPut() {
        JpMap.add("消息");
    }

    public static void WriteFile(String content) {
        FileOutputStream fop = null;
        File file = null;
        try {
            file = new File("ExternalStorage/Android/data/${packageName}/newfile.txt");
            fop = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentInBytes = content.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //效率高
    public static String readTxtFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    System.out.println(lineTxt);
                    return lineTxt;
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
        return null;
    }


    public static void createmkdir(String path, String mkdir) {
        File appDir = new File(path, mkdir);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
    }


    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;

            }
        } else {
            return 0.0;
        }
    }

    /**
     * 屏幕亮度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    //根据bii返回当前分辨率下该设置宽度
    public static int withFontSize(float screenWidth) {
        // 240X320 屏幕
        if (screenWidth == 1.0) {
            return 250;
            // 320X480 屏幕
        } else if (screenWidth == 2.0) {
            return 250;
            // 480X800 或 480X854 屏幕
        } else if (screenWidth == 3.0) {
            return 300;
        } else {
            return 300;

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

    //评论界面专用：根据bii返回当前分辨率下该设置高度
    public static int higtFontSizes(float screenWidth) {
        // 240X320 屏幕
        if (screenWidth == 1.0) {
            return 155;
            // 320X480 屏幕
        } else if (screenWidth == 2.0) {

            return 155;
            // 480X800 或 480X854 屏幕
        } else if (screenWidth == 3.0) {

            return 210;
        } else {
            return 210;
        }
    }

    /**
     * 设置有颜色文字
     */
    public static SpannableString setText(Context mContext, String text, int lenght, int color2) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), 0,
                lenght,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color2)), lenght,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    public static SpannableString setText(Context mContext, int lenght, String text, int color2) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color2)), lenght,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    public static SpannableString setText(Context mContext, String text, int color) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color)), 0,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    public static SpannableString setText(Context mContext, String text, int lenght, int lastlenght, int color2) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), 0,
                lenght,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color2)), lenght,
                lastlenght,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    //隐藏键盘
    public static void hintKeyBoard(Activity activity) {
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

    // 获得上个月月份年份
    public static String getYearMonth() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        String m;
        if (month < 10) {
            m = "0" + month;
        } else {
            m = month + "";
        }
        if ("00".equals(m)) {
            m = "12";
            String str = now.get(Calendar.YEAR) - 1 + "";
            return str + "-" + m;
        }else {
           return now.get(Calendar.YEAR)+"-" + m;
        }

    }

}