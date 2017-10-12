package com.wdg.wchat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wdg.wchat.R;
import com.wdg.wchat.bean.event.FinshSplashEvent;
import com.wdg.wchat.bean.event.RegisterSuccessEvent;
import com.wdg.wchat.mvp.contract.SplashContract;
import com.wdg.wchat.mvp.presenter.SplashPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.layout_visible)
    LinearLayout mLayoutVisible;
    private boolean isInstail;
    private SplashPresenter mPresenter;
    private String tag = SplashActivity.class.getSimpleName();
    private Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPresenter = new SplashPresenter(this);
        mPresenter.init();
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                toFirstLoginActivity();
                break;
            case R.id.btn_register:
                toRegisterActivity();
                break;
        }
    }

    @Override
    public void showLoginAndRegisterLayout() {
        mLayoutVisible.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void toFirstLoginActivity() {
        startActivity(new Intent(this, FirstLoginActivity.class));
    }

    @Override
    public void toMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void toAlreadyLoginActivity() {
        startActivity(new Intent(this, AlreadyLoginActivity.class));
        finish();
    }

    @Override
    public void toRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));

    }

    @Subscribe
    public void loginSuccess(FinshSplashEvent event) {
        if (event.getResult()) {
            finish();
        }
    }

    /**
     * 注册成功
     * @param event
     */
    @Subscribe
    public void registerSuccess(RegisterSuccessEvent event){
        if (event.getResult()){
            finish();
        }
    }
}
