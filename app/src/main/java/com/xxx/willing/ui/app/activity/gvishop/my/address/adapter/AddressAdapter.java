package com.xxx.willing.ui.app.vote.activity.gvishop.my.address.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 地址管理适配器
 * @date 2019-12-05
 */

public class AddressAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public AddressAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.address_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        String address = "";
        helper.setText(R.id.user_name_item, "")
                .setText(R.id.user_phone_item, "")
                .setText(R.id.detail_address, address)
                .addOnClickListener(R.id.update_address_item)
                .addOnClickListener(R.id.default_address);
    }
}
