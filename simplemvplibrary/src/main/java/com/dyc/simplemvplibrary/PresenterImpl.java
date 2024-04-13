package com.dyc.simplemvplibrary;

import java.lang.ref.WeakReference;

public abstract class PresenterImpl<M extends Model, V extends View> implements Presenter<M, V> {
    /**
     * 使用弱引用来防止内存泄漏
     */
    private WeakReference<V> wrf;
    private M model;

    @Override
    public void registerModel(M model) {
        this.model = model;
    }

    @Override
    public void registerView(V view) {
        wrf = new WeakReference<V>(view);
    }

    /**
     * 注意要判断 wrf.get()后的值是否为空，这点交给调用者解决
     *
     * @return
     */
    @Override
    public V getView() {
        return wrf == null ? null : wrf.get();
    }

    public M getModel() {
        return model;
    }

//    /**
//     * 强制调用者抓取view空的异常，太麻烦，暂时不用
//     * @return
//     * @throws RuntimeException
//     */
//    @Override
//    public V getView() throws RuntimeException{
//        if (wrf == null) {
//            throw new RuntimeException("View is not registered or destroyed");
//        } else {
//            if (wrf.get() == null) {
//                throw new RuntimeException("View is not registered or destroyed");
//            } else {
//                return wrf.get();
//            }
//        }
//    }


    /**
     * 在Activity或Fragment卸载时调用View结束的方法
     */
    @Override
    public void destroy() {
        if (wrf != null) {
            wrf.clear();
            wrf = null;
        }
        onViewDestroy();
    }

    protected abstract void onViewDestroy();
}
