package com.wdg.wchat.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdg.wchat.R;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.bean.event.RegisterSuccessEvent;
import com.wdg.wchat.mvp.contract.VerCodeContract;
import com.wdg.wchat.mvp.presenter.VerCodePresenter;

import org.greenrobot.eventbus.EventBus;

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
    private VerCodePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercode);
        ButterKnife.bind(this);
        mInfoBean = getIntent().getParcelableExtra("bean");
        mTvPhoneNumber.setText(mInfoBean.getCountry()+" "+mInfoBean.getPhone());
        File file = new File(mInfoBean.getHeadPhoto());
        mInfoBean.setFile(file);
        mPresenter = new VerCodePresenter(this);
        //获取验证码
        mPresenter.getSms("86",mInfoBean.getPhone());
    }

    @OnClick({R.id.tvVerCodeInfo, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvVerCodeInfo:
                String phone = mInfoBean.getPhone();
                mPresenter.getSms("86",phone);
                break;
            case R.id.btnRegister:
                 mPresenter.register(mInfoBean);
                break;
        }
    }

    @Override
    public EditText getVerCodeView() {
        return mEtVerCode;
    }

    @Override
    public TextView getCountDownView() {
        return mTvVerCodeInfo;
    }

    @Override
    public ProgressDialog showProgressDialog() {
        return showProgress();
    }

    /**
     * 注册成功
     */
    @Override
    public void registerSuccess() {
        EventBus.getDefault().post(new RegisterSuccessEvent("register sucess",true));
        startActivity(new Intent(this,FirstLoginActivity.class));
    }

}
