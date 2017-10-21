package com.wdg.wchat.mvp.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.bean.bean.CountryCodeBean;
import com.wdg.wchat.bean.bean.NetSubscriber;
import com.wdg.wchat.bean.dto.CountryCodeDto;
import com.wdg.wchat.mvp.contract.CountryCodeContract;
import com.wdg.wchat.mvp.model.CountryCodeModel;
import com.wdg.wchat.utils.ProgressDialogUtil;
import com.wdg.wchat.view.ClearEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public class CountryCodePresenter implements CountryCodeContract.Presenter {

    private final String COUNTRYCODE_PATH = "assets/country_code.json";

    private CountryCodeContract.View mView;
    private CountryCodeContract.Model mModel;
    private MyAPP mContext;
    private CountryCodeBean countryCodeBean;

    public CountryCodePresenter(CountryCodeContract.View view){
        mView = view;
        mModel = new CountryCodeModel();
        mContext = MyAPP.getApp();
        subscribeEditTextEvent();
    }

    @Override
    public void NoDataFinishActivity() {
        RelativeLayout titleLayout = mView.getTitleLayout();
        if(View.VISIBLE == titleLayout.getVisibility()){
            mView.NoDataFinishActivity();
        }else{
            ClearEditText clearEditText = mView.getClearEditText();
            clearEditText.getText().clear();
            if(mView instanceof Activity) {
                InputMethodManager imm = (InputMethodManager) ((Activity) mView).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(clearEditText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
            mView.showTitleLayout();
        }
    }

    @Override
    public void onItemClick(int position) {
        List<CountryCodeDto> list = mView.getCountryCodeDtoList();
        String countryCode = list.get(position).getCountry_code();
        mView.finishActivity(countryCode);
    }

    @Override
    public void onTouchLetterChanged(String letter) {
        Map<String, Integer> map = mView.getLetterMap();
        Integer position = map.get(letter);
        if (position != null) {
            mView.moveToPosition(position);
        }
    }

    @Override
    public void showSearchLayout() {
        ClearEditText clearEditText = mView.getClearEditText();
        clearEditText.requestFocus();
        if(mView instanceof Activity) {
            InputMethodManager imm = (InputMethodManager) ((Activity) mView).getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(clearEditText, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        mView.showSearchLayout();
    }

    @Override
    public void subscribeEditTextEvent() {
        final ClearEditText clearEditText = mView.getClearEditText();
        RxTextView.textChanges(clearEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, String>() {

                    @Override
                    public String call(CharSequence charSequence) {
                        String key = charSequence.toString();
                        if (clearEditText.isFocused()) {
                            clearEditText.setClearDrawableVisible(key.length() > 0);
                        }
                        return key;
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, CountryCodeBean>() {

                    @Override
                    public CountryCodeBean call(String key) {
                        return TextUtils.isEmpty(key) ?
                                countryCodeBean :
                                mModel.searchCountryCodes(key, countryCodeBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<CountryCodeBean>(){

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(CountryCodeBean codeBean) {
                        super.onNext(codeBean);
                        updata(codeBean);
                    }

                });
    }

    @Override
    public void getCountryCodes() {
        final Dialog dialog = mView.showProgress();
        Observable.just(COUNTRYCODE_PATH)
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<CountryCodeBean>() {

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
                    public void onNext(CountryCodeBean codeBean) {
                        super.onNext(codeBean);
                        dialog.dismiss();
                        countryCodeBean = codeBean;
                        updata(codeBean);
                    }

                });
    }

    private void updata(CountryCodeBean codeBean){
        mView.getCountryCodeDtoList().clear();
        mView.getLetterMap().clear();
        if (codeBean != null) {
            mView.getCountryCodeDtoList().addAll(codeBean.getCountryCodeDtoList());
            mView.getLetterMap().putAll(codeBean.getLetterIndexMap());
            mView.getCountryCodeAdapter().notifyDataSetChanged();
        }
    }

}
