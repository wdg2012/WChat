package com.wdg.wchat.mvp.contract;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

import com.wdg.wchat.adapter.CountryCodeAdapter;
import com.wdg.wchat.bean.bean.CountryCodeBean;
import com.wdg.wchat.bean.dto.CountryCodeDto;
import com.wdg.wchat.view.ClearEditText;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public interface CountryCodeContract {

    interface Model extends BaseContract.Model{
        String getConutryCodeJson(Context context, String filePath);
        List<CountryCodeDto> getCountryCodes(String json);
        CountryCodeBean getCountryCodeBean(List<CountryCodeDto> data);
        CountryCodeBean searchCountryCodes(String key, CountryCodeBean codeBean);
    }

    interface View extends BaseContract.View{
        void NoDataFinishActivity();
        void finishActivity(String countryCode);
        List<CountryCodeDto> getCountryCodeDtoList();
        void moveToPosition(int position);
        Map<String, Integer> getLetterMap();
        CountryCodeAdapter getCountryCodeAdapter();
        void showTitleLayout();
        void showSearchLayout();
        RelativeLayout getTitleLayout();
        ClearEditText getClearEditText();
    }

    interface Presenter extends BaseContract.Presenter{
        void NoDataFinishActivity();
        void onItemClick(int position);
        void onTouchLetterChanged(String letter);
        void getCountryCodes();
        void showSearchLayout();
        void subscribeEditTextEvent();
    }

}
