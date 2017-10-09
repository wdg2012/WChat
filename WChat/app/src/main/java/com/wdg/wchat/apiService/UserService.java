package com.wdg.wchat.apiService;


import com.wdg.wchat.bean.dto.LoginDto;
import com.wdg.wchat.bean.dto.RegisterDto;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;


/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */

public interface UserService {
    /**
     * 注册
     * @param phone 手机号
     * @param password 密码
     * @param country 国家
     * @param file 头像
     * @param ver_code 验证码
     * @param user_nick 昵称
     * @return
     */
    @Multipart
    @POST("user/register")
    Observable<RegisterDto> register(@Part("phone") RequestBody phone,
                                     @Part("password") RequestBody password,
                                     @Part("country")RequestBody country,
                                     @Part MultipartBody.Part  file,
                                     @Part("ver_code")RequestBody ver_code,
                                     @Part("user_nick")RequestBody user_nick);
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("user/passwordLogin")
    @FormUrlEncoded
    Observable<LoginDto> passwordLogin(@Field("phone") String username,
                                       @Field("password") String password);
    /**
     * 短信登录
     * @param phone 手机号
     * @param ver_code 密码
     * @return
     */
    @POST("user/sms_login")
    @FormUrlEncoded
    Observable<LoginDto> smsLogin(@Field("phone") String phone,
                                  @Field("ver_code") String ver_code);
}
