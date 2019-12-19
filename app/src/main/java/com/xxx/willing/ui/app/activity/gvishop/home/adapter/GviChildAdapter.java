package com.xxx.willing.ui.app.activity.gvishop.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.bean.GviBean;


import java.util.List;

/**
 * @author FM
 * @desc 平级子布局
 * @date 2019-12-06
 */

public class GviChildAdapter extends BaseQuickAdapter<GviBean.ListBean, BaseViewHolder> {

    GviChildAdapter(@Nullable List<GviBean.ListBean> data) {
        super(R.layout.gvi_child_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GviBean.ListBean item) {
        helper.setText(R.id.item_title, item.getName() + "￥" + item.getPrice())
                .setText(R.id.item_price, item.getGviPrice() + "GVI");
        GlideUrlUtil.loadBack(mContext, HttpConfig.HTTP_IMG_URL + item.getLogos(), R.mipmap.vote_banner_default, helper.getView(R.id.item_img));
    }
}
