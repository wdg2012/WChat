package com.wdg.wchat.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import com.wdg.wchat.bean.dto.LoginDto;

import rx.Observable;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public interface AlreadyLoginContract extends BaseContract{
    interface Model extends BaseContract.Model{
      Observable<LoginDto> passwordOfLogin( String phone,String password);
        Observable<LoginDto> smsOfLogin(String phone,String verCode);
    }

    interface View extends BaseContract.View{
        void toMainActivity();
        ImageView getHeadImageView();
        TextView getAccountView();
        TextView getVerCodeView();
        void showPasswordLoginLayout();
        void showSmsLoginLayout();
        TextView getPasswordView();


    }

    interface Presenter extends BaseContract.Presenter{
        void popMore(android.view.View view);
        void passwordLogin(String phone,String password);
        void switchLoginType();
        void clickVerCode();
        void smsLogin(String phone,String verCode);
        void login();
        void autoLogin();


    }
}
