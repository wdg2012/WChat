package com.wdg.wchat.mvp.presenter;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdg.wchat.apiService.CommonApiService;
import com.wdg.wchat.apiService.UserApiService;
import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.bean.bean.NetSubscriber;
import com.wdg.wchat.bean.dto.RegisterDto;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.bean.dto.UploadImageDto;
import com.wdg.wchat.mvp.contract.VerCodeContract;
import com.wdg.wchat.mvp.model.VerCodeModel;
import com.wdg.wchat.utils.RetrofitUtils;
import com.wdg.wchat.utils.SharedPreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;


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
     * @param bean 用户的注册信息
     */
    @Override
    public void register(final RegisterInfoBean bean) {
        String verCode = mEtVerCode.getText().toString();
        bean.setVerCode(verCode);
        if (TextUtils.isEmpty(verCode)) {
            Toast.makeText(mContext, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String path = bean.getHeadPhoto();
     final   ProgressDialog pd =  mView.showProgressDialog();
        Observable.just(path).subscribeOn(Schedulers.io()).map(new Func1<String, File>() {
            @Override
            public File call(final String s) {
                try {
                    return Luban.with(mContext).load(s).get().get(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }).flatMap(new Func1<File, Observable<UploadImageDto>>() {

            @Override
            public Observable<UploadImageDto> call(final File file) {
                CommonApiService service = RetrofitUtils.createService(CommonApiService.class);
                MultipartBody.Part headPhoto = RetrofitUtils.makeMulPart(file, "smfile");
                return service.uploadImage("https://sm.ms/api/upload", headPhoto);
            }
        }).flatMap(new Func1<UploadImageDto, Observable<RegisterDto>>() {
            @Override
            public Observable<RegisterDto> call(UploadImageDto uploadImageDto) {
                bean.setDelete_path(uploadImageDto.getData().getDelete());
                bean.setHead_path(uploadImageDto.getData().getUrl());
                return mModel.register(bean);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new NetSubscriber<RegisterDto>() {
            @Override
            public void onNext(RegisterDto registerDto) {
                super.onNext(registerDto);
                if (registerDto.getCode().equals("101")){
                    mView.registerSuccess();
                }
                Toast.makeText(mContext,registerDto.getError(),Toast.LENGTH_SHORT).show();;
            }

            @Override
            public void onCompleted() {
                pd.cancel();
            }
        });
    }

    /**
     * 获取验证码
     *
     * @param zone  区号
     * @param phone 电话号码
     */
    @Override
    public void getSms(String zone, final String phone) {
        if (isNoGetSms) {
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
                        mTvVerCodeInfo.setText("接受短信大约需要" + (120 - aLong) + "秒钟");
                    }
                });
        isNoGetSms = true;
        cn.smssdk.SMSSDK.getVerificationCode(zone, phone);
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
