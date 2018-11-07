package measure.jjxx.com.baselibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018/11/5 0005.
 * 进行pdf的保存以及读取（另外还有pdfActivity）
 * 但是局限于pdf，还可以用户word xls等。单是由于是调用系统外部软件，所以存在局限，可能手机没有软件支持这些文件，
 */

public class PdfUtils {
    public static final String pathed = "file:///android_asset/pdfjs/web/viewer.html?file=";
    String paths;
    String pathname;

    public void getdata(final Context mContext, final String url) {
        paths = mContext.getExternalCacheDir().getPath().replace("cache", "PDF/");
        pathname = url.substring(url.lastIndexOf("/") + 1, url.length());
        final boolean status = fileIsExists(paths + pathname);
        if (status) {
            //存在
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                OpenFileUtils.getPdfFileIntent(pathed + paths + pathname);
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //判断当前系统是否高于或等于6.0
                    final String download = download(url);
                    if (download != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            try {
                                OpenFileUtils.getPdfFileIntent(pathname + download);
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * 判断路径下指定文件是否存在
     */
    public boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
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

    private String download(String path) {
        try {
            URL url = new URL(path);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
            File file = new File(paths);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            final String fileName = paths + pathname;
            File file1 = new File(fileName);
            if (file1.exists()) {
                file1.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(fileName);
            //写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完成后关闭流
            os.close();
            is.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
