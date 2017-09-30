package com.wdg.wchat.mvp.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.bean.dto.RegisterDto;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.mvp.contract.VerCodeContract;
import com.wdg.wchat.mvp.model.VerCodeModel;

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

    public VerCodePresenter(final VerCodeContract.View view) {
        mView = view;
        mModel = new VerCodeModel();
        mContext = MyAPP.getApp();
    }

    /**
     * 注册
     * @param bean
     */
    @Override
    public void register(final RegisterInfoBean bean) {
      String verCode = mView.getVerCode();
        bean.setVerCode(verCode);
        if (TextUtils.isEmpty(verCode)){
            Toast.makeText(mContext, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mModel.register(bean).subscribeOn(Schedulers.io())
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
                if (dto!=null){
                    if ("101".equals(dto)){

                    }
                    Toast.makeText(mContext, dto.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
