package com.wdg.wchat.mvp.presenter;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.bean.dto.RegisterDto;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.mvp.contract.VerCodeContract;
import com.wdg.wchat.mvp.model.VerCodeModel;
import com.wdg.wchat.utils.SharedPreferencesUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public class VerCodePresenter implements VerCodeContract.Presenter {
    private VerCodeContract.View mView;
    private VerCodeContract.Model mModel;
    private MyAPP mContext;
    private boolean isNoGetSms = false;
    private EditText mEtVerCode;
    private TextView mTvVerCodeInfo;

    public VerCodePresenter(final VerCodeContract.View view) {
        mView = view;
        mModel = new VerCodeModel();
        mContext = MyAPP.getApp();
        mEtVerCode = mView.getVerCodeView();
        mTvVerCodeInfo = mView.getCountDownView();
    }

    /**
     * 注册
     *
     * @param bean
     */
    @Override
    public void register(final RegisterInfoBean bean) {
        String verCode = mEtVerCode.getText().toString();
        bean.setVerCode(verCode);
        if (TextUtils.isEmpty(verCode)) {
            Toast.makeText(mContext, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        mModel.register(bean).subscribeOn(Schedulers.io())
                .compose(mView.<RegisterDto>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterDto>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onNext(final RegisterDto dto) {
                        if (dto != null) {
                            if ("101".equals(dto)) {
                                mView.registerSuccess();
                            }
                            Toast.makeText(mContext, dto.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 获取验证码
     * @param zone 区号
     * @param phone 电话号码
     */
    @Override
    public void getSms(String zone, final String phone) {
        if (isNoGetSms){
            return;
        }
        Observable.interval(1, TimeUnit.SECONDS)
                .take(120)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mTvVerCodeInfo.setText("获取验证码");
                        isNoGetSms = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mTvVerCodeInfo.setText("接受短信大约需要"+(120-aLong)+"秒钟");
                    }
                });
        isNoGetSms = true;
//        cn.smssdk.SMSSDK.getVerificationCode(zone, phone);
    }

    @Override
    public void countDown(final int interval, final int time) {

    }

    /**
     * 登录成功用户保存用户信息
     *
     * @param obj 服务器返回的用户信息
     */
    private void setUserInfo(final RegisterInfoBean obj) {
        SharedPreferencesUtils.setParam(mContext, "is_login", true);
    }
}
