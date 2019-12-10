package com.xxx.willing.ui.vote.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.FranchiseeBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.CountDownTimerView;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.model.utils.StringUtil;

import java.util.List;

/**
 * @author FM
 * @desc 首页
 * @date 2019-12-06
 */

public class VoteItemAdapter extends BaseQuickAdapter<FranchiseeBean, BaseViewHolder> {

    public VoteItemAdapter(@Nullable List<FranchiseeBean> data) {
        super(R.layout.vote_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FranchiseeBean item) {
        helper.setText(R.id.vote_name, item.getFranName())
                .setText(R.id.vote_id, item.getFranId())
                .setText(R.id.item_vote_number, item.getVoteNum() + mContext.getString(R.string.item_vote_number))
                .addOnClickListener(R.id.vote_btn);

        GlideUtil.load(mContext, item.getImgUrl(), helper.getView(R.id.vote_img));

        CountDownTimerView timerView = helper.getView(R.id.vote_time);
        long nowTime = StringUtil.getTime(item.getNowTime());
        long endTime = StringUtil.getTime(item.getEndTime());
        timerView.setTime(endTime - nowTime);

        TextView status = helper.getView(R.id.vote_te_status);
        switch (item.getStatus()) {
            case 1:
                status.setText(R.string.item_vote_progress);
                status.setTextColor(Color.parseColor("#FFFFFF"));
                status.setBackgroundResource(R.drawable.shape_item_vote_progress);
                break;
            case 2:
                status.setText(R.string.item_vote_success);
                status.setTextColor(Color.parseColor("#FFFFFF"));
                status.setBackgroundResource(R.drawable.shape_item_vote_success);
                break;
            case 3:
                status.setText(R.string.item_vote_not_start);
                status.setTextColor(Color.parseColor("#FFFFFF"));
                status.setBackgroundResource(R.drawable.shape_item_vote_not_start);
                break;
            case 4:
                status.setText(R.string.item_vote_end);
                status.setTextColor(Color.parseColor("#FE3848"));
                status.setBackgroundResource(R.drawable.shape_item_vote_end);
                break;
        }
    }
}
