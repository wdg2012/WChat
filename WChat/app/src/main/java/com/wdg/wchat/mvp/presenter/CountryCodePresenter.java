package com.wdg.wchat.mvp.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.bean.bean.CountryCodeBean;
import com.wdg.wchat.bean.bean.NetSubscriber;
import com.wdg.wchat.bean.dto.CountryCodeDto;
import com.wdg.wchat.mvp.contract.CountryCodeContract;
import com.wdg.wchat.mvp.model.CountryCodeModel;
import com.wdg.wchat.utils.ProgressDialogUtil;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public class CountryCodePresenter implements CountryCodeContract.Presenter {

    private CountryCodeContract.View mView;
    private CountryCodeContract.Model mModel;
    private MyAPP mContext;

    public CountryCodePresenter(CountryCodeContract.View view){
        mView = view;
        mModel = new CountryCodeModel();
        mContext = MyAPP.getApp();
    }

    @Override
    public void getCountryCodes(String filePath) {
        if(!TextUtils.isEmpty(filePath)) {
            final Dialog dialog = mView.showProgress();
            Observable.just(filePath)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<String, String>() {

                        @Override
                        public String call(String path) {
                            return mModel.getConutryCodeJson(mContext, path);
                        }
                    })
                    .map(new Func1<String, List<CountryCodeDto>>() {

                        @Override
                        public List<CountryCodeDto> call(String json) {
                            return mModel.getCountryCodes(json);
                        }
                    })
                    .map(new Func1<List<CountryCodeDto>, CountryCodeBean>() {

                        @Override
                        public CountryCodeBean call(List<CountryCodeDto> countryCodeDtos) {
                            return mModel.getCountryCodeBean(countryCodeDtos);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetSubscriber<CountryCodeBean>(){

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                        }

                        @Override
                        public void onNext(CountryCodeBean countryCodeBean) {
                            super.onNext(countryCodeBean);
                            dialog.dismiss();
                            mView.updateCountryCodes(countryCodeBean);
                        }

                    });
        }
    }

}
