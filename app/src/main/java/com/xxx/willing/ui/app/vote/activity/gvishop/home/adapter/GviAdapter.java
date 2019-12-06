package com.xxx.willing.ui.app.vote.activity.gvishop.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 商城适配器
 * @date 2019-12-06
 */

public class GviAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public GviAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.gvi_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        helper.setText(R.id.life_type, "")
                .setText(R.id.title, "");
    }
}
