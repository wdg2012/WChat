package com.wdg.wchat.mvp.contract;

import android.app.ProgressDialog;
import android.widget.EditText;
import android.widget.TextView;

import com.wdg.wchat.bean.dto.RegisterDto;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.bean.dto.UploadImageDto;
import com.wdg.wchat.mvp.presenter.BasePresenter;

import java.io.File;

import rx.Observable;


/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public interface VerCodeContract {
    interface Model {
        Observable<RegisterDto> register(RegisterInfoBean bean);
        Observable<UploadImageDto> registerUpLoadImage(File file);
    }

    interface View extends BaseContract.View{
        EditText getVerCodeView();
        TextView getCountDownView();
        ProgressDialog showProgressDialog();
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        void register(RegisterInfoBean bean);
        void getSms(String zone,String phone);
    }
}
