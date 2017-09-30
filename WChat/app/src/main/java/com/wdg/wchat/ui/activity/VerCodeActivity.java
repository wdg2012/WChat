package com.wdg.wchat.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdg.wchat.R;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.mvp.contract.VerCodeContract;
import com.wdg.wchat.mvp.presenter.VerCodePresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */

public class VerCodeActivity extends BaseActivity implements VerCodeContract.View {
    @BindView(R.id.tvPhoneNumber)
    TextView mTvPhoneNumber;
    @BindView(R.id.etVerCode)
    EditText mEtVerCode;
    @BindView(R.id.tvVerCodeInfo)
    TextView mTvVerCodeInfo;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;
    private RegisterInfoBean mInfoBean;
    private CountDownTimer mCountDownTimer;
    private boolean isRunning;
    private VerCodePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercode);
        ButterKnife.bind(this);
        mInfoBean = getIntent().getParcelableExtra("bean");
        mTvPhoneNumber.setText(mInfoBean.getCountry()+" "+mInfoBean.getPhone());
        initCountDownTime(120*1000,1000);
        //发送验证码
        cn.smssdk.SMSSDK.getVerificationCode("86", mInfoBean.getPhone());
        File file = new File(mInfoBean.getHeadPhoto());
        mInfoBean.setFile(file);
        mPresenter = new VerCodePresenter(this);
    }

    /**
     *
     * @param millisInFuture 倒计时时间
     * @param countDownInterval  时间间隔
     */
    private void initCountDownTime(long millisInFuture,long countDownInterval) {
        isRunning = true;
        mCountDownTimer = new CountDownTimer(millisInFuture,countDownInterval) {
            @Override
            public void onTick(final long millisUntilFinished) {
                mTvVerCodeInfo.setText("接受短信大约需要"+millisUntilFinished/1000+"秒钟");
            }

            @Override
            public void onFinish() {
                mTvVerCodeInfo.setText("获取验证码");
                isRunning = false;
            }
        };
        mCountDownTimer.start();
    }

    @OnClick({R.id.tvVerCodeInfo, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvVerCodeInfo:
                if (isRunning){
                    return;
                }
                cn.smssdk.SMSSDK.getVerificationCode("86", mInfoBean.getPhone());
                isRunning = true;
                mCountDownTimer.start();
                break;
            case R.id.btnRegister:
                 mPresenter.register(mInfoBean);
                break;
        }
    }

    @Override
    public String getVerCode() {
        return mEtVerCode.getText().toString();
    }

    @Override
    public ProgressDialog showProgressDialog() {
        return showProgress();
    }

}
