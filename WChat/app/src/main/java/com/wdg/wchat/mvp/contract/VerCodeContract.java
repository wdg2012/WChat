package com.wdg.wchat.mvp.contract;

import android.app.ProgressDialog;

import com.wdg.wchat.bean.dto.RegisterDto;
import com.wdg.wchat.bean.bean.RegisterInfoBean;

import rx.Observable;


/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public interface VerCodeContract {
    interface Model {
        Observable<RegisterDto> register(RegisterInfoBean bean);
    }

    interface View {
        String getVerCode();
        ProgressDialog showProgressDialog();
    }

    interface Presenter {
        void register(RegisterInfoBean bean);
    }
}
