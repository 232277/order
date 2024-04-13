package com.dyc.simplemvplibrary;

public interface Presenter<M extends Model, V extends View> {
    /**
     * 注册Model层
     *
     * @param model 数据
     */
    void registerModel(M model);

    /**
     * 注册View层
     *
     * @param view 视图
     */
    void registerView(V view);

    /**
     * 初始化后执行
     */
    void afterInit();

    /**
     * 获取View
     *
     * @return V
     */
    V getView();

    /**
     * 获取Model
     *
     * @return M
     */
    M getModel();

    /**
     * 销毁动作（如Activity、Fragment的卸载）
     */
    void destroy();
}
