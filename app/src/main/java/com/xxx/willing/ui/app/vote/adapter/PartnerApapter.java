package com.xxx.willing.ui.app.vote.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 *  @desc   合伙人列表适配器
 *  @author FM
 *  @date   2019-12-04
 */

public class PartnerApapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public PartnerApapter(@Nullable List<BaseBean> data) {
        super(R.layout.partner_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

    }
}
