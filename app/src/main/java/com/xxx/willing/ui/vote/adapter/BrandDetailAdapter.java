package com.xxx.willing.ui.vote.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

public class BrandDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BrandDetailAdapter(@Nullable List<String> data) {
        super(R.layout.item_brand, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String url) {
        GlideUtil.loadFillet(mContext, url, holder.getView(R.id.brand_detail_image));

    }
}
