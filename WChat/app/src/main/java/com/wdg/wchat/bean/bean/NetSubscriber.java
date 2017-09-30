package com.wdg.wchat.bean.bean;

import rx.Subscriber;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by ${wdgan} on 2017/9/29 0029.
 * 邮箱18149542718@163
 */

public class NetSubscriber<T> extends Subscriber<T>{

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(final Throwable e) {
        onCompleted();
    }

    @Override
    public void onNext(final T t) {
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
