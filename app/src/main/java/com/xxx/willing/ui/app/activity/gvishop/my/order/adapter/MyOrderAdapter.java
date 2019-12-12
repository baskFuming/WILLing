package com.xxx.willing.ui.app.activity.gvishop.my.order.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 *  @desc  我的订单adapter
 *  @author FM
 *  @date   2019-12-04
 */

public class MyOrderAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {


    public MyOrderAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.my_order_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

    }
}
