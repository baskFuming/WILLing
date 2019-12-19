package com.xxx.willing.ui.app.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.bean.RankBean;


import java.util.List;

/**
 * @author FM
 * @desc 排名适配器
 * @date 2019-12-03
 */

public class RankAdapter extends BaseQuickAdapter<RankBean, BaseViewHolder> {

    public RankAdapter(@Nullable List<RankBean> data) {
        super(R.layout.rank_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankBean item) {
        helper.setText(R.id.user_account_id, item.getNickname())
                .setText(R.id.account, item.getAmount() + "");
        GlideUrlUtil.loadCircle(mContext, HttpConfig.HTTP_IMG_URL + item.getAvatar(), R.mipmap.my_icon, helper.getView(R.id.user_img));
        TextView mLevel = helper.getView(R.id.partner_level);
        int position = helper.getLayoutPosition();
        if (position == 0) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_1);
            mLevel.setText("1");
        } else if (position == 1) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_2);
            mLevel.setText("2");
        } else if (position == 2) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_3);
            mLevel.setText("3");
        }
    }
}
