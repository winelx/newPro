package measure.jjxx.com.baselibrary.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * @author lx
 * @Created by: 2018/11/14 0014.
 * @description:
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
     * @param filePath 文件路径例如：E:\\imgData\\afr\\9211496189393485.jpg
     * @return    文件大小 Kb
     */
    public static long GetFileSize(String filePath){
        long fileSize=0l;
        FileChannel fc= null;
        try {
            File f= new File(filePath);
            if (f.exists() && f.isFile()){
                FileInputStream fis= new FileInputStream(f);
                fc= fis.getChannel();
                fileSize=fc.size()/1024;
                //logger.info(fileSize);
            }else{
                //logger.info("file doesn't exist or is not a file");
            }
        } catch (FileNotFoundException e) {
            //logger.error(e);
        } catch (IOException e) {
            //logger.error(e);
        } finally {
            if (null!=fc){
                try{
                    fc.close();
                }catch(IOException e){
                    //logger.error(e);
                }
            }
        }

        return fileSize;
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

}
