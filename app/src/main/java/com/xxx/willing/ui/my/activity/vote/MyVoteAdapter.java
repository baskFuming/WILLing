package com.xxx.willing.ui.my.activity.vote;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.MyVoteBean;

import java.util.List;

public class MyVoteAdapter extends BaseQuickAdapter<MyVoteBean, BaseViewHolder> {

    MyVoteAdapter(@Nullable List<MyVoteBean> data) {
        super(R.layout.item_my_vote, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyVoteBean item) {
        helper.addOnClickListener(R.id.re_join_name)
                .addOnClickListener(R.id.re_vote_number);
//                .setText(R.id.join_id, "")
//                .setText(R.id.join_name, "")
//                .setText(R.id.vote_time, "")
//                .setText(R.id.vote_day, "")
//                .setText(R.id.vote_number, "")
//                .setText(R.id.release_day, "")
//                .setText(R.id.release_number, "");
    }
}
