package com.xxx.willing.ui.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.bean.GameBean;


import java.util.List;

/**
 * @author FM
 * @desc 游戏
 * @date 2019-12-13
 */

public class AppGameAdapter extends BaseQuickAdapter<GameBean, BaseViewHolder> {

    public AppGameAdapter(@Nullable List<GameBean> data) {
        super(R.layout.item_app_game, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GameBean item) {
        helper.setText(R.id.item_app_game_name, item.getName());
        GlideUrlUtil.loadCircle(mContext, HttpConfig.HTTP_IMG_URL + item.getPicUrl(), R.mipmap.my_icon, helper.getView(R.id.item_app_game_icon));
    }
}
