package com.xxx.willing.ui.my.activity.vote;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.VoteRecordBean;

import java.util.List;

public class VoteRecordAdapter extends BaseQuickAdapter<VoteRecordBean, BaseViewHolder> {

    VoteRecordAdapter(@Nullable List<VoteRecordBean> data) {
        super(R.layout.item_vote_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteRecordBean item) {
//        helper.setText(R.id.item_vote_record_number,"")
//                .setText(R.id.item_vote_record_time,"");
    }
}
