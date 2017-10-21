package com.wdg.wchat.utils;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ${wdgan} on 2017/9/16 0016.
 * 邮箱18149542718@163
 *
 */

public class RetrofitUtils {
    private static final String API_SERVER = "http://47.93.21.48:8080/ssm_war/"; // 服务器的地址
    private static final String API_TEST = "http://192.168.1.101:8080/";
    private  static Retrofit sRetrofit;
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取 Retrofit 客户端
     * @return
     */
    private static Retrofit getRetrofit(){
        if (sRetrofit ==null) {
            if (null == mOkHttpClient) {
                mOkHttpClient =  createOkHttp();
            }
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(API_SERVER).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .client(mOkHttpClient)
                    .build();
        }
        return sRetrofit;
    }

    /**
     * 创建请求的Servcie
     * @param sClass
     * @param <T>
     * @return
     */
    public  static <T> T  createService(Class<T>  sClass){
        Retrofit retrofit = getRetrofit();
        return retrofit.create(sClass);
    }

    /**
     * 创建okHttp 客户端
     * @return
     */
    private static OkHttpClient createOkHttp(){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
      OkHttpClient  OkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        lock.unlock();
        return OkHttpClient;
    }

    /**
     * 创建MultipartBody.Part
     * @param path 文件路径
     * @param key 上传的参数
     * @return
     */
    public static MultipartBody.Part makeMulPart(String path,String key){
          File file = new File(path);
        return makeMulPart(file,key);
    }

    /**
     * 创建MultipartBody.Part
     * @param file 文件
     * @param key 上传的参数
     * @return
     */
    public static MultipartBody.Part makeMulPart(File file,String key){
        if (TextUtils.isEmpty(key)|| file==null){
            throw new NullPointerException("file or key is null");
        }
        MultipartBody.Part result;
        RequestBody photoRequestBody = RequestBody.create(
                MediaType.parse("application/octet-stream"), file);
        result = MultipartBody.Part.createFormData(
                key, file.getName(), photoRequestBody);
        return result;
    }
}
