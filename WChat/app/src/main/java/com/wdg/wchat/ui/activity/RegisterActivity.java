package com.wdg.wchat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.wdg.wchat.R;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.bean.event.RegisterSuccessEvent;
import com.wdg.wchat.mvp.contract.RegisterContract;
import com.wdg.wchat.mvp.presenter.RegisterPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 注册页面
 * Created by HuangBin on 2017/9/11.
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    private static final int IMAGE_PICKER = 0x113;
    private static final int COUNTRYCODE_BACK = 0x114;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.layout_back)
    LinearLayout mLayoutBack;
    @BindView(R.id.etNickName)
    EditText mEtNickName;
    @BindView(R.id.ivPhoto)
    ImageView mIvPhoto;
    @BindView(R.id.tvCountry)
    TextView mTvCountry;
    @BindView(R.id.etPhoneNumber)
    EditText mEtPhoneNumber;
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;
    private String headPhoto;
    private RegisterPresenter mPresenter;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mPresenter = new RegisterPresenter(this);
    }

    @OnClick({R.id.iv_back, R.id.ivPhoto,
            R.id.btnRegister, R.id.tvCountry})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ivPhoto:
                intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.btnRegister:
                mPresenter.checkRegisterInfo();
                break;
            case R.id.tvCountry:
                intent = new Intent(this, CountryCodeActivity.class);
                startActivityForResult(intent, COUNTRYCODE_BACK);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void registerSuccess(RegisterSuccessEvent event){
        if (event.getResult()){
            finish();
        }
    }
    /**
     * 设置头像
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {

            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

            headPhoto = images.get(0).path;
            if (!TextUtils.isEmpty(headPhoto)) {
                Glide.with(this)
                        .load(headPhoto)
                        .placeholder(R.color.voice_login)
                        .error(R.color.voice_login)
                        .into(mIvPhoto);
            }
        }
        else if(resultCode == RESULT_OK){
            if(requestCode == COUNTRYCODE_BACK){
                String countryCode = data.getStringExtra("countryCode");
                mTvCountry.setText(countryCode);
            }
        }
    }

    @Override
    public String getPhone() {
        return mEtPhoneNumber.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public String getNick() {
        return mEtNickName.getText().toString();
    }

    @Override
    public String getCounty() {
        return mTvCountry.getText().toString();
    }

    @Override
    public String getHeadPhoto() {
        return headPhoto;
    }

    @Override
    public void toVerCodeRegister(RegisterInfoBean bean) {
        Intent intent = new Intent(this, VerCodeActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);
    }
}
