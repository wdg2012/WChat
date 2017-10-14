package com.wdg.wchat.mvp.model;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdg.wchat.bean.dto.CountryCodeDto;
import com.wdg.wchat.mvp.contract.CountryCodeContract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public class CountryCodeModel implements CountryCodeContract.Model {

    @Override
    public String getConutryCodeJson(Context context) {
        String result = null;
        InputStream in = null;
        ByteArrayOutputStream out =null;

        try {
            in = context.getClass().getClassLoader().getResourceAsStream("assets/country_code.json");
            out = new ByteArrayOutputStream();
            if(in != null) {
                int len = -1;
                byte[] buffer = new byte[1024];
                //循环
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                result = out.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) { in.close(); }
                if (out != null) { out.close(); }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public List<CountryCodeDto> getCountryCodes(String json) {
        List<CountryCodeDto> data = null;
        if(!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            data = gson.fromJson(json, new TypeToken<List<CountryCodeDto>>(){}.getType());
        }
        return data;
    }
}
