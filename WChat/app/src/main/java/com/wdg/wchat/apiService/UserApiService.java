package com.wdg.wchat.apiService;


import com.wdg.wchat.bean.dto.LoginDto;
import com.wdg.wchat.bean.dto.RegisterDto;
import com.wdg.wchat.bean.dto.UserInfoUpdateDto;

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

public interface UserApiService {
    /**
     * 注册
     * @param phone 手机号
     * @param password 密码
     * @param country 国家
     * @param head_path
     * @param delete_path
     * @param ver_code 验证码
     * @param user_nick 昵称
     * @return
     */
    @Multipart
    @POST("user/register")
    Observable<RegisterDto> register(@Part("phone") RequestBody phone,
                                     @Part("password") RequestBody password,
                                     @Part("country")RequestBody country,
                                     @Part ("head_path")RequestBody head_path,
                                     @Part("delete_path") RequestBody delete_path,
                                     @Part("ver_code")RequestBody ver_code,
                                     @Part("user_nick")RequestBody user_nick);
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("user/login")
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

    /**
     * 更新用户头像
     * @param user_id
     * @param head_path
     * @param delete_path
     * @return
     */
    @POST("user/updateHeadPhoto")
    @FormUrlEncoded
    Observable<UserInfoUpdateDto>updateHeadPhoto(@Field("user_id") String user_id,
                                                 @Field("head_path") String head_path,
                                                 @Field("delete_path")String delete_path);

    /**
     * 更新用户所在地
     * @param user_id
     * @param user_area 地区
     * @param user_city 城市
     * @param country 国家
     * @return
     */
    @POST("user/updateArea")
    @FormUrlEncoded
    Observable<UserInfoUpdateDto>updateArea(@Field("user_id") String user_id,
                                            @Field("user_area") String user_area,
                                            @Field("user_city")String user_city,
                                            @Field("country") String country);

    /**
     * 更新用户的聊天id
     * @param user_id
     * @param chatId
     * @return
     */
    @POST("user/updateChatId")
    @FormUrlEncoded
    Observable<UserInfoUpdateDto>updateChatId(@Field("user_id")String user_id,
                                              @Field("chatId")String chatId);

    /**
     * 更新用户的性别
     * @param user_id
     * @param user_sex 性别
     * @return
     */
    @POST("user/updateSex")
    @FormUrlEncoded
    Observable<UserInfoUpdateDto>updateSex(@Field("user_id")String user_id,
                                              @Field("user_sex")String user_sex);

    /**
     * 更新用户的签名
     * @param user_id
     * @param updateSign
     * @return
     */
    @POST("user/updateSign")
    @FormUrlEncoded
    Observable<UserInfoUpdateDto>updateSign(@Field("user_id")String user_id,
                                           @Field("updateSign")String updateSign);
}
