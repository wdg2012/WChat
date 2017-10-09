package com.wdg.wchat.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.wdg.wchat.R;
import com.wdg.wchat.mvp.contract.AlreadyLoginContract;
import com.wdg.wchat.mvp.presenter.AlreadyLoginPresenter;
import com.wdg.wchat.utils.ProgressDialogUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 登录界面
 * 邮箱18149542718@163
 */

public class AlreadyLoginActivity extends BaseActivity implements AlreadyLoginContract.View {
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.iv_head_image)
    ImageView mIvHeadImage;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.tv_issms_login)
    TextView mTvIssmsLogin;
    @BindView(R.id.tv_get_ver_code)
    TextView mTvGetVerCode;
    private AlreadyLoginContract.Presenter mLoginPresenter;
    private PopupWindow mPopupWindow;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_login);
        ButterKnife.bind(this);
        mLoginPresenter = new AlreadyLoginPresenter(this);
        mLoginPresenter.autoLogin();
    }

    @OnClick({R.id.iv_more, R.id.btnLogin,
            R.id.tv_issms_login,R.id.tv_get_ver_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                showPop(view);
                break;
            case R.id.btnLogin:
                mLoginPresenter.login();
                break;
            case R.id.tv_issms_login:
                mLoginPresenter.switchLoginType();
                break;
            case R.id.tv_get_ver_code:
                mLoginPresenter.clickVerCode();
                break;
        }
    }

    /**
     * 初始化popup
     * 点击more显示出来
     */
    private void initPopupWindow() {
        View content = getLayoutInflater().inflate(R.layout.popup_more_login, null);
        content.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        mPopupWindow = new PopupWindow(content,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        //View的初始化
        PopButtonClick popViewClick = new PopButtonClick(mPopupWindow);
        content.findViewById(R.id.tvSwtAccount).setOnClickListener(popViewClick);
        content.findViewById(R.id.tvFindPwd).setOnClickListener(popViewClick);
        content.findViewById(R.id.tvSeyCenter).setOnClickListener(popViewClick);
        content.findViewById(R.id.tvRegister).setOnClickListener(popViewClick);
        content.findViewById(R.id.layout_root).setOnClickListener(popViewClick);
    }

    /**
     * 显示popup
     *
     * @param view
     */
    private void showPop(View view) {
        if (mPopupWindow == null) {
            initPopupWindow();
        }
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 不显示popup
     *
     * @param popupWindow
     */
    private void dismiss(PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    /**
     * 如果登录验证成功跳转
     */
    @Override
    public void toMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 获取头像的View
     *
     * @return
     */
    @Override
    public ImageView getHeadImageView() {
        return mIvHeadImage;
    }

    /**
     * 获取账号的View
     *
     * @return
     */
    @Override
    public TextView getAccountView() {
        return mTvAccount;
    }

    @Override
    public TextView getVerCodeView() {
        return mTvGetVerCode;
    }

    /**
     * 密码登录布局
     */
    @Override
    public void showPasswordLoginLayout() {
        mTvGetVerCode.setVisibility(View.INVISIBLE);
        mTvIssmsLogin.setText("用短信验证码登录");
        mEtPassword.setText("");
        mEtPassword.setHint("请填写微信密码");
    }

    /**
     * 短信登录布局
     */
    @Override
    public void showSmsLoginLayout() {
        mTvGetVerCode.setVisibility(View.VISIBLE);
        mTvIssmsLogin.setText("用密码登录");
        mEtPassword.setText("");
        mEtPassword.setHint("请填写验证码");
    }

    @Override
    public TextView getPasswordView() {
        return mEtPassword;
    }

    @Override
    public ProgressDialog showProgress() {
        return ProgressDialogUtil.show(this);
    }


    /**
     * popup点击事件
     */
    private class PopButtonClick implements View.OnClickListener {
        PopupWindow popupWindow;

        public PopButtonClick(final PopupWindow popupWindow) {
            this.popupWindow = popupWindow;
        }

        @Override
        public void onClick(View view) {
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            Intent intent = null;
            switch (view.getId()) {
                case R.id.tvSwtAccount:
//                    intent = new Intent(AlreadyLoginActivity.this, PhoneLoginFActivity.class);
                    break;
                case R.id.tvFindPwd:
                    break;
                case R.id.tvSeyCenter:
                    break;
                case R.id.tvRegister:
                    intent = new Intent(AlreadyLoginActivity.this, RegisterActivity.class);
                    break;
                case R.id.layout_root:
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        }

    }
}
