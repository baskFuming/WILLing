package com.xxx.willing.ui.app.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.RankBean;
import com.xxx.willing.model.http.bean.TotalRankBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.GlideUtil;

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
                .setText(R.id.account, item.getAmount());
        GlideUtil.loadCircle(mContext, String.valueOf(item.getAvatar()), helper.getView(R.id.user_img));
        TextView mLevel = helper.getView(R.id.partner_level);
        int position = helper.getLayoutPosition();
        if (position == 0) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_1);
            mLevel.setText("1");
        }else if (position == 2) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_2);
            mLevel.setText("2");
        } else if (position == 3) {
            helper.getView(R.id.partner_level).setBackgroundResource(R.drawable.rank_3);
            mLevel.setText("3");
        }
    }
}
