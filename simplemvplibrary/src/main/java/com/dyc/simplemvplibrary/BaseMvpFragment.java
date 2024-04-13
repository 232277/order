package com.dyc.simplemvplibrary;

import android.content.Context;

public abstract class BaseMvpFragment<M extends Model, V extends View, P extends PresenterImpl> extends BaseFragment implements BaseMVP<M, V, P>, View {
    private P presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = initP();
        if (presenter != null) {
            presenter.registerModel(initM());
            presenter.registerView(this);
        }
        doAfterInitView(getContentView(getActivity()));
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
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.destroy();
        }
    }
}
