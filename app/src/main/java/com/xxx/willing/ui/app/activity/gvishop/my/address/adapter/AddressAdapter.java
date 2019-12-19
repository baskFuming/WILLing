package com.xxx.willing.ui.app.activity.gvishop.my.address.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.MyAddressBean;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 地址管理适配器
 * @date 2019-12-05
 */

public class AddressAdapter extends BaseQuickAdapter<MyAddressBean, BaseViewHolder> {

    public AddressAdapter(@Nullable List<MyAddressBean> data) {
        super(R.layout.address_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyAddressBean item) {
        helper.setText(R.id.user_name_item, item.getConsignee())
                .setText(R.id.user_phone_item, item.getPhone())
                .setText(R.id.detail_address, item.getAddress())
                .addOnClickListener(R.id.update_address_item)
                .addOnClickListener(R.id.default_address);
    }
}
