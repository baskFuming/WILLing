package com.xxx.willing.ui.app.activity.gvishop.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.GviBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

/**
 * @author FM
 * @desc 平级子布局
 * @date 2019-12-06
 */

public class GviChildAdapter extends BaseQuickAdapter<GviBean.DataBean.ListBean, BaseViewHolder> {


    public GviChildAdapter(@Nullable List<GviBean.DataBean.ListBean> data) {
        super(R.layout.gvi_child_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GviBean.DataBean.ListBean item) {
        helper.setText(R.id.item_title, mContext.getString(R.string.item_phone_securities) + item.getPrice())
                .setText(R.id.item_price, item.getGviPrice() + "GVI");
        GlideUtil.load(mContext, item.getLogos(), helper.getView(R.id.item_img));
    }
}
