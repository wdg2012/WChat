package com.wdg.wchat.apiService;

import com.wdg.wchat.bean.dto.RegisterDto;
import com.wdg.wchat.bean.dto.UploadImageDto;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ${wdgan} on 2017/10/10 0010.
 * 邮箱18149542718@163
 */
public interface CommonApiService {

    /**
     * 上传图片
     * @param url 地址
     * @param image 图片
     * @return
     */
    @Multipart
    @POST("")
    Observable<UploadImageDto> uploadImage(@Url String url, @Part MultipartBody.Part image);
}
