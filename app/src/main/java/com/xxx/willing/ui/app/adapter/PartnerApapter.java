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
        helper.setText(R.id.partner_des, item.getUserId())
                .setText(R.id.partner_id, "ID：" + item.getLevel())
                .setText(R.id.partner_count, item.getPrice());
        GlideUtil.loadCircle(mContext, String.valueOf(item.getAvatar()), helper.getView(R.id.partner_icon));
        TextView mLevel = helper.getView(R.id.partner_level);
        if (item.getLevel() == 1) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_1);
            mLevel.setText("TOP1");
        } else if (item.getLevel() == 2) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_2);
            mLevel.setText("TOP2");
        } else if (item.getLevel() == 3) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_3);
            mLevel.setText("TOP3");
        }
    }
}
