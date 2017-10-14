package com.wdg.wchat.mvp.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.wdg.wchat.base.MyAPP;
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
    public void getCountryCodes(final Activity context) {
        if(context != null) {
            final Dialog dialog = ProgressDialogUtil.show(context);
            Observable.just("getCountryCodes")
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<String, String>() {

                        @Override
                        public String call(String str) {
                            return mModel.getConutryCodeJson(context);
                        }
                    })
                    .map(new Func1<String, List<CountryCodeDto>>() {

                        @Override
                        public List<CountryCodeDto> call(String json) {
                            return mModel.getCountryCodes(json);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetSubscriber<List<CountryCodeDto>>(){

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
                        public void onNext(List<CountryCodeDto> countryCodeDtos) {
                            super.onNext(countryCodeDtos);
                            dialog.dismiss();
                            mView.updateCountryCodes(countryCodeDtos);
                        }

                    });
        }
    }

}
