package com.wdg.wchat.mvp.model;

import com.wdg.wchat.apiService.UserService;
import com.wdg.wchat.bean.bean.RegisterInfoBean;
import com.wdg.wchat.bean.dto.RegisterDto;
import com.wdg.wchat.mvp.contract.VerCodeContract;
import com.wdg.wchat.utils.RetrofitUtils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public class VerCodeModel implements VerCodeContract.Model {
    /**
     * 注册请求
     * @param bean 注册的信息
     * @return
     */
    @Override
    public Observable<RegisterDto> register(final RegisterInfoBean bean) {
        UserService userService = RetrofitUtils.createService(UserService.class);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), bean.getFile());
        MultipartBody.Part headPhoto = MultipartBody.Part.createFormData("headPhoto",bean.getFile().getName(), photoRequestBody);
        return userService.register(RequestBody.create(null, bean.getPhone()), RequestBody.create(null, bean.getPassword())
                , RequestBody.create(null, bean.getCountry()),headPhoto, RequestBody.create(null, bean.getVerCode()),
                RequestBody.create(null, bean.getNick()));
    }
}
