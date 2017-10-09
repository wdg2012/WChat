package com.wdg.wchat.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.bean.bean.NetSubscriber;
import com.wdg.wchat.bean.dto.LoginDto;
import com.wdg.wchat.mvp.contract.AlreadyLoginContract;
import com.wdg.wchat.mvp.model.LoginModel;
import com.wdg.wchat.utils.SharedPreferencesUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public class AlreadyLoginPresenter implements AlreadyLoginContract.Presenter {
    private AlreadyLoginContract.View mView;
    private AlreadyLoginContract.Model mModel;
    private Context mContext;
    private boolean isSmsLogin;
    private CountDownTimer mCountDownTime;
    private TextView mTvGetVerCode;
    private boolean isCountDownfinsh;
    private TextView mEtAccount;
    private TextView mEtPassword;


    public AlreadyLoginPresenter(final AlreadyLoginContract.View view) {
        mView = view;
        mModel = new LoginModel();
        mContext = MyAPP.getApp();
        isSmsLogin = false;
        isCountDownfinsh = true;
        mEtPassword = mView.getPasswordView();
        mEtAccount = mView.getAccountView();
        initView(mContext);
    }

    /**
     * 倒计时初始化
     *
     * @param millisInFuture    总时间数
     * @param countDownInterval 时间间隔
     */
    private void startCountTimer(final long millisInFuture, long countDownInterval) {
        if (mCountDownTime == null) {
            mCountDownTime = new CountDownTimer(millisInFuture, countDownInterval) {
                @Override
                public void onTick(final long millisUntilFinished) {
                    mTvGetVerCode.setText(millisUntilFinished / 1000 + "秒");
                }

                @Override
                public void onFinish() {
                    mTvGetVerCode.setText("获取验证码");
                    isCountDownfinsh = true;
                }
            };
        }
        mCountDownTime.start();
    }

    /**
     * 设置头像和账号
     *
     * @param context 上下文
     */
    private void initView(Context context) {
        String head_path = (String) SharedPreferencesUtils.getParam(context, "head_path", "");
        String account = (String) SharedPreferencesUtils.getParam(context, "user_phone", "");
        ImageView ivHeadImageView = mView.getHeadImageView();
        TextView tvAccount = mView.getAccountView();
        Glide.with(context).load(head_path).into(ivHeadImageView);
        tvAccount.setText(account);
    }


    @Override
    public void popMore(View view) {

    }

    /**
     * 密码登录
     * @param phone 手机号
     * @param password 密码
     */
    @Override
    public void passwordLogin(String phone,String password) {
        if (TextUtils.isEmpty(phone) ||
                phone.length() != 11) {
            Toast.makeText(mContext, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password) ||
                password.length() < 6 ||
                password.length() > 16) {
            Toast.makeText(mContext, "密码格式不正确", Toast.LENGTH_SHORT).show();
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
                    public void onNext(final LoginDto dto) {
                        super.onNext(dto);
                        if (dto != null) {
                            if ("101".equals(dto.getCode())) {
                                LoginDto.ObjBean obj = dto.getObj();
                                setUserInfo(obj);
                                mView.toMainActivity();
                            }
                            Toast.makeText(mContext, dto.getError(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    /**
     * 切换登录方式
     * 登录方式有短信和密码
     */
    @Override
    public void switchLoginType() {
        isSmsLogin = !isSmsLogin;
        if (isSmsLogin) {
            mView.showSmsLoginLayout();
        } else {
            mView.showPasswordLoginLayout();
        }

    }

    /**
     * 获取验证码
     */
    @Override
    public void clickVerCode() {
        String phone = mEtAccount.getText().toString();
        mTvGetVerCode = mView.getVerCodeView();
        if (isCountDownfinsh){
            startCountTimer(120 * 1000, 1000);
            cn.smssdk.SMSSDK.getVerificationCode("86", phone);
        }
        isCountDownfinsh = false;

    }


    /**
     * 验证码登录
     * @param phone 手机号
     * @param verCode 验证码
     */
    @Override
    public void smsLogin(String phone,String verCode) {
        if (TextUtils.isEmpty(phone) ||
                phone.length() != 11) {
            Toast.makeText(mContext, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(verCode)){
            Toast.makeText(mContext, "验证码格式", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog pd = mView.showProgress();
        mModel.smsOfLogin(phone,verCode).subscribeOn(Schedulers.io())
                .compose(mView.<LoginDto>bindToLifecycle())
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
                                mView.toMainActivity();
                            }
                            Toast.makeText(mContext, dto.getError(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    /**
     * 登录
     */
    @Override
    public void login() {
      String password = mEtPassword.getText().toString();
      String account = mEtAccount.getText().toString();
        if (isSmsLogin){
            smsLogin(account,password);
        }else {
            passwordLogin(account,password);
        }
    }

    /**
     * 如果之前登录过的自动登录
     */
    @Override
    public void autoLogin() {
        boolean isLogin = (boolean) SharedPreferencesUtils.getParam(mContext,"is_login",false);
        if (isLogin){
            mView.toMainActivity();

        }
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
