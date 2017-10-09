package com.wdg.wchat.mvp.contract;

import android.app.ProgressDialog;

import rx.Observable;

/**
 * Created by ${wdgan} on 2017/9/29 0029.
 * 邮箱18149542718@163
 */
public interface BaseContract {
    interface Model {
    }

    interface View {
        ProgressDialog showProgress();
        <T> Observable.Transformer<T, T> bindToLifecycle();
    }

    interface Presenter {
    }
}
