package measure.jjxx.com.baselibrary.frame;

import android.content.Context;



import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import measure.jjxx.com.baselibrary.base.BaseView;



/**
 * description: 基础的presenter层,抽取公共方法
 * @author lx
 * date: 2018/10/9 0009 下午 4:01
*/
public class MvpPresenter <M extends MvpModel, V extends BaseView> {
    public Context mContext;
    public Reference<V> mViewRef;
    public M mModel;

    public void initPresenter(V view){
        mModel = (M) Mvp.getInstance().getModel(Mvp.getGenericType(this, 0));

        mViewRef = new WeakReference<V>(view);
        mContext = Mvp.getInstance().getApplictionContext();
    }

    public V getIView(){
        return mViewRef.get();
    }

    public void destory(){
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
