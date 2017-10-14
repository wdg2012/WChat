package com.wdg.wchat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdg.wchat.R;
import com.wdg.wchat.adapter.CountryCodeAdapter;
import com.wdg.wchat.bean.dto.CountryCodeDto;
import com.wdg.wchat.mvp.contract.CountryCodeContract;
import com.wdg.wchat.mvp.presenter.CountryCodePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 国家地区选择
 * Created by HuangBin on 2017/10/14.
 */
public class CountryCodeActivity extends BaseActivity
        implements CountryCodeContract.View,
        CountryCodeAdapter.OnItemClickListener {

    @BindView(R.id.layBack)
    LinearLayout layBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivRightBtn)
    ImageView ivRightBtn;
    @BindView(R.id.rvCountryCode)
    RecyclerView rvCountryCode;

    private CountryCodeAdapter adapter;
    private List<CountryCodeDto> countryCodes;
    private CountryCodePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init(){
        tvTitle.setText("选择国家和地区代码");
        countryCodes = new ArrayList<>();
        adapter = new CountryCodeAdapter(this, countryCodes);
        adapter.setOnItemClickListener(this);
        rvCountryCode.setLayoutManager(new LinearLayoutManager(this));
        rvCountryCode.setAdapter(adapter);
        presenter = new CountryCodePresenter(this);
        presenter.getCountryCodes(this);
    }

    @OnClick({R.id.layBack, R.id.ivRightBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layBack:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.ivRightBtn:
                break;
        }
    }

    @Override
    public void updateCountryCodes(List<CountryCodeDto> data) {
        if(data != null){
            countryCodes.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        String countryCode = countryCodes.get(position).getCountry_code();
        Intent data = new Intent().putExtra("countryCode", countryCode);
        setResult(RESULT_OK, data);
        finish();
    }
}
