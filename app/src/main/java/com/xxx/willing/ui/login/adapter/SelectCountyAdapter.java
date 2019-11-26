package com.xxx.willing.ui.login.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.SelectCountyBean;

import java.util.List;

public class SelectCountyAdapter extends BaseQuickAdapter<SelectCountyBean, BaseViewHolder> {

    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public SelectCountyAdapter(@Nullable List<SelectCountyBean> data) {
        super(R.layout.item_select_county, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectCountyBean item) {
        helper.setText(R.id.item_select_county_name, item.getZhName())
                .setText(R.id.item_select_county_code, item.getAreaCode());

        if (helper.getAdapterPosition() == position) {
            helper.setTextColor(R.id.item_select_county_name, Color.parseColor("#4A79E7"))
                    .setTextColor(R.id.item_select_county_code, Color.parseColor("#4A79E7"));
        } else {
            helper.setTextColor(R.id.item_select_county_name, Color.parseColor("#666666"))
                    .setTextColor(R.id.item_select_county_code, Color.parseColor("#666666"));
        }
    }
}
