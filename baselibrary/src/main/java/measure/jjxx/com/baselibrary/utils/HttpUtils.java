package measure.jjxx.com.baselibrary.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/23 0023.
 * @description:
 */

public class HttpUtils {
    public interface OnClickListener {
        void onsuccess(String s);

        void onerror();
    }

    public static void agree(String httpurl, String orgId, Map<String, String> map, final OnClickListener onClickListener) {
        GetRequest request = OkGo.get(httpurl);
        request.params("orgId", orgId);
        if (map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                request.params(entry.getKey(), entry.getValue());
            }
        }
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                onClickListener.onsuccess(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }
        });
    }
}
