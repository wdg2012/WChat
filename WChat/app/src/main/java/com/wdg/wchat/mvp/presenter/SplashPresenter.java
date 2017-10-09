package com.wdg.wchat.mvp.presenter;

import android.content.Context;
import android.widget.Toast;

import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.mvp.contract.SplashContract;
import com.wdg.wchat.mvp.model.SplashModel;
import com.wdg.wchat.utils.SharedPreferencesUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${wdgan} on 2017/10/9 0009.
 * 邮箱18149542718@163
 */
public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mView;
    private SplashContract.Model mModel;
    private Context mContext;

    public SplashPresenter(final SplashContract.View view) {
        mView = view;
        mModel = new SplashModel();
        mContext = MyAPP.getApp();

    }

    /**
     * 本地存储了账号并且是登录状态，跳转到mainActivity,AlreadyLoginActivity
     * 本地没存储账号让其选择登录或注册
     */
    @Override
    public void init() {
        boolean existLogin = mModel.existLogin(mContext);
        if (existLogin) {
            boolean isLogin = (boolean) SharedPreferencesUtils.getParam(mContext,
                    "is_login", false);
            if (isLogin) {
                toMainActivity();
            } else {
                toAlreadyLoginActivity();
            }
        } else {
            showLoginAndRegisterLayout();
        }
    }

    /**
     * 延时2秒显示登录注册布局
     */
    @Override
    public void showLoginAndRegisterLayout() {
        Observable.interval(2, TimeUnit.SECONDS)
                .take(1).compose(mView.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mView.showLoginAndRegisterLayout();
                    }
                });
    }

    /**
     * 延时3秒跳转MainActivity
     */
    @Override
    public void toMainActivity() {
        Observable.interval(3, TimeUnit.SECONDS)
                .take(1).compose(mView.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mView.toMainActivity();
                    }
                });
    }

    /**
     * 延时3秒跳转AlreadyLoginActivity
     */
    @Override
    public void toAlreadyLoginActivity() {
        Observable.interval(3, TimeUnit.SECONDS)
                .take(1).compose(mView.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mView.toAlreadyLoginActivity();
                    }
                });
    }
}
