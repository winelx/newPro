package com.example.baselibrary.utils.dialog;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;
import java.math.BigDecimal;

import okhttp3.Call;
import okhttp3.Response;

public class DownLogindUtils {

    /**
     * 更新app
     *
     * @param url
     * @param paths
     * @param callback
     */
    public static void down(String url, String paths, final DownCallback callback) {
        OkGo.get(url)
                .execute(new FileCallback(paths, "base.apk") {
                    @Override
                    public void onSuccess(final File file, Call call, Response response) {
                        callback.onsuccess(file.getPath());
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callback.onerror("下载失败");
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                        BigDecimal setScale = new BigDecimal(progress).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                        double result3 = setScale.multiply(new BigDecimal(100)).doubleValue();
                        final int bar = (int) result3;
                        callback.onprogress(bar);
                    }
                });
    }

    public interface DownCallback {
        void onsuccess(String path);

        void onerror(String str);

        void onprogress(int progress);
    }
}
