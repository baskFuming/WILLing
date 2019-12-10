package com.xxx.willing.ui.app.vote.activity.gvishop.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

/**
 * @author FM
 * @desc 平级子布局
 * @date 2019-12-06
 */

public class GviChildAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public GviChildAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.gvi_child_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        helper.setText(R.id.item_title, "")
                .setText(R.id.item_price, "");
        GlideUtil.load(mContext, "", helper.getView(R.id.item_img));
    }
}
