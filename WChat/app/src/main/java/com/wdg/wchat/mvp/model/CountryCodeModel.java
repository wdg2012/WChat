package com.wdg.wchat.mvp.model;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdg.wchat.bean.bean.CountryCodeBean;
import com.wdg.wchat.bean.dto.CountryCodeDto;
import com.wdg.wchat.mvp.contract.CountryCodeContract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public class CountryCodeModel implements CountryCodeContract.Model {

    @Override
    public String getConutryCodeJson(Context context, String filePath) {
        String result = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;

        try {
            in = context.getClass().getClassLoader().getResourceAsStream(filePath);
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

    @Override
    public CountryCodeBean getCountryCodeBean(List<CountryCodeDto> data) {
        CountryCodeBean countryCodeBean = null;
        if(data != null && data.size() > 0){
            countryCodeBean = new CountryCodeBean();
            countryCodeBean.setCountryCodeDtoList(data);
            countryCodeBean.setLetterIndexMap(new HashMap<String, Integer>());
            Map<String, Integer> letterIndexMap = countryCodeBean.getLetterIndexMap();
            int index = 0;
            //循环
            for(CountryCodeDto codeDto : data){
                if(codeDto.isCountry_group()){
                    letterIndexMap.put(codeDto.getCountry_group_name(), index);
                }
                index++;
            }
        }
        return countryCodeBean;
    }

}
