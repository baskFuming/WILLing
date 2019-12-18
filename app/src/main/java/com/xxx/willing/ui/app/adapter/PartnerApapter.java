package com.xxx.willing.ui.app.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.PartnerBean;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

/**
 * @author FM
 * @desc 合伙人列表适配器
 * @date 2019-12-04
 */

public class PartnerApapter extends BaseQuickAdapter<PartnerBean, BaseViewHolder> {

    public PartnerApapter(@Nullable List<PartnerBean> data) {
        super(R.layout.partner_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PartnerBean item) {
        helper.setText(R.id.partner_des, item.getNickName() + "")
                .setText(R.id.partner_id, "ID：" + item.getUserId())
                .setText(R.id.partner_count, item.getPrice() + "");
        GlideUtil.loadCircle(mContext, String.valueOf(item.getAvatar()), helper.getView(R.id.partner_icon));
        TextView mLevel = helper.getView(R.id.partner_level);
        int position = helper.getLayoutPosition();
        if (position == 0) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_1);
            mLevel.setText("Top1");
        } else if (position == 1) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_2);
            mLevel.setText("Top2");
        } else if (position == 2) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_3);
            mLevel.setText("Top3");
        }
    }
}
