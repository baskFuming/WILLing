package com.xxx.willing.ui.my.activity.vote;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.MyVoteBean;

import java.util.List;

public class VoteRecordAdapter extends BaseQuickAdapter<MyVoteBean.ListBean, BaseViewHolder> {

    VoteRecordAdapter(@Nullable List<MyVoteBean.ListBean> data) {
        super(R.layout.item_vote_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyVoteBean.ListBean item) {
        helper.setText(R.id.item_vote_record_number, item.getVoteAmount() + "")
                .setText(R.id.item_vote_record_time, item.getCreateTime());
    }
}
