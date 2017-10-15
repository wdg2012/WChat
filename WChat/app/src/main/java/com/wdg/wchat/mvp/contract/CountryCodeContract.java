package com.wdg.wchat.mvp.contract;

import android.app.Activity;
import android.content.Context;

import com.wdg.wchat.bean.bean.CountryCodeBean;
import com.wdg.wchat.bean.dto.CountryCodeDto;

import java.util.List;

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
    }

    interface View extends BaseContract.View{
        void updateCountryCodes(CountryCodeBean codeBean);
    }

    interface Presenter extends BaseContract.Presenter{
        void getCountryCodes(String filePath);
    }
}
