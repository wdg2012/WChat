package com.wdg.wchat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdg.wchat.R;
import com.wdg.wchat.mvp.contract.FirstLoginContract;
import com.wdg.wchat.mvp.presenter.FirstLoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstLoginActivity extends BaseActivity implements FirstLoginContract.View {

    @BindView(R.id.etPhoneNumber)
    EditText mEtPhoneNumber;
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @BindView(R.id.tv_sms_login)
    TextView mTvSmsLogin;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.tv_get_ver_code)
    TextView mTvGetVerCode;
    @BindView(R.id.tv_login_method)
    TextView mTvLoginMethod;
    private FirstLoginContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        ButterKnife.bind(this);
        mPresenter = new FirstLoginPresenter(this);
    }

    @OnClick({R.id.tv_sms_login, R.id.btnLogin, R.id.tv_get_ver_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sms_login:
                mPresenter.switchLogin();
                break;
            case R.id.btnLogin:
                mPresenter.login();
                break;
            case R.id.tv_get_ver_code:
                mPresenter.getSms();
                break;
        }
    }


    @Override
    public TextView getAccountView() {
        return mEtPhoneNumber;
    }

    @Override
    public TextView getVerCodeView() {
        return mTvGetVerCode;
    }

    @Override
    public void showPasswordLoginLayout() {
        mTvGetVerCode.setVisibility(View.INVISIBLE);
        mTvSmsLogin.setText("用短信验证码登录");
        mEtPassword.setText("");
        mEtPassword.setHint("请填写微信密码");
        mTvLoginMethod.setText("密码");
    }

    @Override
    public void showSmsLoginLayout() {
        mTvGetVerCode.setVisibility(View.VISIBLE);
        mTvSmsLogin.setText("用密码登录");
        mEtPassword.setText("");
        mEtPassword.setHint("请填写验证码");
        mTvLoginMethod.setText("验证码");

    }

    @Override
    public TextView getPasswordView() {
        return mEtPassword;
    }

    @Override
    public void toMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
