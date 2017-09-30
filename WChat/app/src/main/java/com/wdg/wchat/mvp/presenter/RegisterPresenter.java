package com.wdg.wchat.mvp.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.wdg.wchat.base.MyAPP;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.mvp.contract.RegisterContract;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;
    private RegisterContract.Model mModel;
    private RegisterInfoBean mRegisterInfoBean;
    private MyAPP mContext;

    public RegisterPresenter(final RegisterContract.View view) {
        mView = view;
        mRegisterInfoBean = new RegisterInfoBean();
        mContext = MyAPP.getApp();
    }

    @Override
    public String getVerCode(final String phone) {
        return null;
    }

    /**
     * 注册信息都不为空才可以获取注册的验证码
     */
    @Override
    public void checkRegisterInfo() {
        String phone = mView.getPhone();
        String password = mView.getPassword();
        String nickName = mView.getNick();
        String country = mView.getCounty();
        String headPhoto = mView.getHeadPhoto();

        if (phone.length()!=11){
            Toast.makeText(mContext, "手机号格式错误", Toast.LENGTH_SHORT).show();
            return;
        }else if (password.length()<6 || password.length()>16){
            Toast.makeText(mContext, "密码格式错误", Toast.LENGTH_SHORT).show();
            return;
        }else if (nickName.length()<3 || nickName.length()>10){
            Toast.makeText(mContext, "昵称长度3-10", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(country)){
            Toast.makeText(mContext, "国家未选择", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(headPhoto)){
            Toast.makeText(mContext, "未选择头像", Toast.LENGTH_SHORT).show();
            return;
        }
        mRegisterInfoBean.setPhone(phone);
        mRegisterInfoBean.setPassword(password);
        mRegisterInfoBean.setCountry(country);
        mRegisterInfoBean.setHeadPhoto(headPhoto);
        mRegisterInfoBean.setNick(nickName);
        mView.toVerCodeRegister(mRegisterInfoBean);
    }
}
