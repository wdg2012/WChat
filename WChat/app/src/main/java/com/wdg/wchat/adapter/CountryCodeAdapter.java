package com.wdg.wchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wdg.wchat.R;
import com.wdg.wchat.bean.dto.CountryCodeDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 国家代码列表适配器
 * Created by HuangBin on 2017/10/14.
 */
public class CountryCodeAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int ITEM_GROUP = 0x100;
    private final int ITEM_SUB = 0x101;

    private Context mContext;
    private LayoutInflater mLayInflater;
    private List<CountryCodeDto> mData;
    private OnItemClickListener mListener;

    public CountryCodeAdapter(Context context, List<CountryCodeDto> data){
        mContext = context;
        mLayInflater = LayoutInflater.from(mContext);
        mData = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).isCountry_group() ? ITEM_GROUP : ITEM_SUB;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case ITEM_GROUP:
                content = mLayInflater.inflate(R.layout.item_country_code_group, parent, false);
                viewHolder = new GroupViewHolder(content);
                break;
            case ITEM_SUB:
                content = mLayInflater.inflate(R.layout.item_country_code_sub, parent, false);
                viewHolder = new SubViewHolder(content);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CountryCodeDto countryCodeDto = mData.get(position);
        switch (holder.getItemViewType()){
            case ITEM_GROUP:
                GroupViewHolder gHolder = (GroupViewHolder) holder;
                gHolder.tvGroupName.setText(countryCodeDto.getCountry_group_name());
                break;
            case ITEM_SUB:
                SubViewHolder sHolder = (SubViewHolder) holder;
                sHolder.tvCountryName.setText(countryCodeDto.getCountry_name_cn());
                sHolder.tvCountryCode.setText(countryCodeDto.getCountry_code().replace("+", ""));
                break;
        }
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvGroupName)
        TextView tvGroupName;

        public GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SubViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.tvCountryName)
        TextView tvCountryName;
        @BindView(R.id.tvCountryCode)
        TextView tvCountryCode;

        public SubViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

}
