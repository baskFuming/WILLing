package com.xxx.willing.ui.my.activity.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 资金记录适配器
 * @date 2019-12-09
 */

public class DropDownAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public DropDownAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.drow_down_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        helper.setText(R.id.item_type, "")
                .setText(R.id.item_time, "")
                .setText(R.id.item_reduce_coin, "-" + "")
                .setText(R.id.item_poundage, mContext.getString(R.string.item_poundage) + "")
                .setText(R.id.item_balance, mContext.getString(R.string.item_balance) + "");
    }
}
