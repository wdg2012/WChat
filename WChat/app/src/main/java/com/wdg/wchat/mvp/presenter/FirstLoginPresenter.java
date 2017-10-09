package com.wdg.wchat.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.provider.Telephony;
import android.widget.TextView;
import android.widget.Toast;

import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.bean.bean.NetSubscriber;
import com.wdg.wchat.bean.dto.LoginDto;
import com.wdg.wchat.bean.event.FinshSplashEvent;
import com.wdg.wchat.mvp.contract.FirstLoginContract;
import com.wdg.wchat.mvp.model.FirstLoginModel;
import com.wdg.wchat.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${wdgan} on 2017/10/9 0009.
 * 邮箱18149542718@163
 */
public class FirstLoginPresenter implements FirstLoginContract.Presenter {
    private FirstLoginContract.View mView;
    private FirstLoginContract.Model mModel;
    private TextView mTvAccount;
    private TextView mTvPassword;
    private TextView mTvGetVerCode;
    private Context mContext;
    private boolean isSms;
    private boolean isNoGetSms;

    public FirstLoginPresenter(final FirstLoginContract.View view) {
        mView = view;
        mModel = new FirstLoginModel();
        mTvAccount = mView.getAccountView();
        mTvPassword = mView.getPasswordView();
        mTvGetVerCode = mView.getVerCodeView();
        mContext = MyAPP.getApp();
    }

    /**
     * 密码登录
     */
    @Override
    public void passwordLogin() {
        String phone = mTvAccount.getText().toString();
        String password = mTvPassword.getText().toString();
        if (checkPhoneAndPassword(phone, password)){
            return;
        }
        final ProgressDialog pd = mView.showProgress();
        mModel.passwordOfLogin(phone,password).subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<LoginDto>() {
                    @Override
                    public void onCompleted() {
                        pd.cancel();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(final LoginDto dto) {
                        super.onNext(dto);
                        if (dto != null) {
                            if ("101".equals(dto.getCode())) {
                                LoginDto.ObjBean obj = dto.getObj();
                                setUserInfo(obj);
                                EventBus.getDefault().post(new FinshSplashEvent("finsh",true));
                                mView.toMainActivity();
                            }
                            Toast.makeText(mContext, dto.getError(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    /**
     * 验证码登录
     */
    @Override
    public void SmsLogin() {
        String phone = mTvAccount.getText().toString();
        String  sms = mTvPassword.getText().toString();
        if(checkPhoneAndSms(phone,sms)){
            return;
        }
        final ProgressDialog pd = mView.showProgress();
        mModel.smsOfLogin(phone,sms).compose(mView.<LoginDto>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<LoginDto>() {
                    @Override
                    public void onCompleted() {
                        pd.cancel();
                    }

                    @Override
                    public void onNext(final LoginDto dto) {
                        super.onNext(dto);
                        if (dto != null) {
                            if ("101".equals(dto.getCode())) {
                                LoginDto.ObjBean obj = dto.getObj();
                                setUserInfo(obj);
                                EventBus.getDefault().post(new FinshSplashEvent("finsh",true));
                                mView.toMainActivity();
                            }
                            Toast.makeText(mContext, dto.getError(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    /**
     * 获取验证码
     */
    @Override
    public void getSms() {
        if (isNoGetSms){
            return;
        }

        String phone = mTvAccount.getText().toString();
        Observable.interval(1, TimeUnit.SECONDS)
                .take(120).compose(mView.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mTvGetVerCode.setText("获取验证码");
                        isNoGetSms = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mTvGetVerCode.setText((120-aLong)+"秒");
                    }
                });
        isNoGetSms = true;
//        cn.smssdk.SMSSDK.getVerificationCode("86", phone);
    }

    /**
     * 验证码登录调用smsLogin
     * 密码登录调用passwordLogin
     *
     */
    @Override
    public void login() {
       if (isSms){
           SmsLogin();
       }else {
           passwordLogin();
       }
    }

    /**
     * 登录方式切换
     */
    @Override
    public void switchLogin() {
        if (isSms){
            mView.showPasswordLoginLayout();
        }else {
            mView.showSmsLoginLayout();
        }
        isSms = !isSms;
    }

    /**
     * 判断验证码和手机号格式是否错误
     * @param phone 手机号
     * @param sms 验证码
     */
    private boolean checkPhoneAndSms(final String phone, final String sms) {
        boolean result = false;
        if (phone == null || phone.length() != 11) {
            Toast.makeText(mContext, "手机号格式错误", Toast.LENGTH_SHORT).show();
            result = true;
            return result;
        }
        if (sms == null || (sms.length() !=4)) {
            Toast.makeText(mContext, "验证码格式错误", Toast.LENGTH_SHORT).show();
            result = true;
            return result;
        }
        return result;
    }

    /**
     *判断密码和手机号是否格式错误
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    private  boolean checkPhoneAndPassword(final String phone,final String password) {
        boolean result = false;
        if (phone == null || phone.length() != 11) {
            Toast.makeText(mContext, "手机号格式错误", Toast.LENGTH_SHORT).show();
            result = true;
            return result;
        }
        if (password == null || (password.length() < 6 || password.length() > 16)) {
            Toast.makeText(mContext, "密码格式错误", Toast.LENGTH_SHORT).show();
            result = true;
            return result;
        }
        return result;
    }
    /**
     * 登录成功用户保存用户信息
     *
     * @param obj 服务器返回的用户信息
     */
    private void setUserInfo(final LoginDto.ObjBean obj) {
        SharedPreferencesUtils.setParam(mContext, "user_id", obj.getUser_id() + "");
        SharedPreferencesUtils.setParam(mContext, "user_country", obj.getUser_country());
        SharedPreferencesUtils.setParam(mContext, "user_nick", obj.getUser_nick());
        SharedPreferencesUtils.setParam(mContext, "user_phone", obj.getUser_phone());
        SharedPreferencesUtils.setParam(mContext, "head_path", obj.getHead_path());
        SharedPreferencesUtils.setParam(mContext,"is_login",true);
    }
}
