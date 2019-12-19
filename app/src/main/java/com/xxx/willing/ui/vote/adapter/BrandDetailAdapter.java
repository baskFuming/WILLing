package com.xxx.willing.ui.vote.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;

import java.util.List;

public class BrandDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BrandDetailAdapter(@Nullable List<String> data) {
        super(R.layout.item_brand, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String url) {
        GlideUrlUtil.loadFillet(mContext, HttpConfig.HTTP_IMG_URL + url, R.mipmap.vote_banner_default, holder.getView(R.id.brand_detail_image));
    }
}
