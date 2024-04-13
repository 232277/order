package com.dyc.simplemvplibrary;

import android.os.Bundle;

public abstract class BaseMvpActivity<M extends Model, V extends View, P extends PresenterImpl> extends BaseActivity implements BaseMVP<M,V,P>, View {
    private P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter
        presenter = initP();
        if (presenter != null) {
            //将Model层注册到Presenter中
            presenter.registerModel(initM());
            //将View层注册到Presenter中
            presenter.registerView(this);
            presenter.afterInit();
        }
        doAfterInitView(getContentView(this));
    }

    //目前没啥用
    @Override
    public V initV() {
        return (V)this;
    }

    public P getPresenter(){
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            //Activity销毁时的调用，让具体实现BasePresenter中onViewDestroy()方法做出决定
            presenter.destroy();
        }
    }

}
