package measure.jjxx.com.baselibrary.utils;

import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

import measure.jjxx.com.baselibrary.view.top_snackbar.BaseTransientBottomBar;
import measure.jjxx.com.baselibrary.view.top_snackbar.TopSnackBar;

/**
 * @author lx
 * @Created by: 2018/11/14 0014.
 * @description: 文件操作类
 */

public class FileUtils {
    /**
     * 清除指定路径下文件
     *
     * @param filePath
     */
    public static void clearFiles(String filePath) {
        try {
            File scFileDir = new File(filePath);
            File TrxFiles[] = scFileDir.listFiles();
            for (File curFile : TrxFiles) {
                curFile.delete();
            }
        } catch (Exception e) {
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

    /**
     * 计算文件大小文件大小
     *
     * @param filePath 文件路径例如：E:\\imgData\\afr\\9211496189393485.jpg
     * @return 文件大小 Kb
     */
    public static long GetFileSize(String filePath) {
        long fileSize = 0l;
        FileChannel fc = null;
        try {
            File f = new File(filePath);
            if (f.exists() && f.isFile()) {
                FileInputStream fis = new FileInputStream(f);
                fc = fis.getChannel();
                fileSize = fc.size() / 1024;
                //logger.info(fileSize);
            } else {
                //logger.info("file doesn't exist or is not a file");
            }
        } catch (FileNotFoundException e) {
            //logger.error(e);
        } catch (IOException e) {
            //logger.error(e);
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    //logger.error(e);
                }
            }
        }

        return fileSize;
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
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }


    /**
     * 将byte转成 KB、MB、GB的方法
     *
     * @param size
     * @return
     */
    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
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

    /**
     * 文件下载
     *
     * @param path
     * @return
     */
    private String download(String path, String filepaths, String filename, View view) {
        int currentLen = 0;
        InputStream is = null;
        FileOutputStream os = null;
        try {
            URL url = new URL(path);
            //打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //文件大小
            int totleLen = conn.getContentLength();
            //网络连接状态码
            int state = conn.getResponseCode();
            if (state != 200) {
                TopSnackBar.make(view, "文件不存在", BaseTransientBottomBar.LENGTH_SHORT).show();
                return null;
            }
            //打开输入流
            is = conn.getInputStream();
            File file = new File(filepaths);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            final String fileName = filepaths + filename;
            File file1 = new File(fileName);
            if (file1.exists()) {
                file1.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            os = new FileOutputStream(fileName);
            //写数据
            int progress = 0;
            while ((len = is.read(bs)) != -1) {
                currentLen += len;
                os.write(bs, 0, len);
//                percent = Math.ceil(currentLen * 1.0 / totleLen * 10000);
//                Log.i("下载 进度:", percent / 100.0 + "%");
            }
            os.close();
            is.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
