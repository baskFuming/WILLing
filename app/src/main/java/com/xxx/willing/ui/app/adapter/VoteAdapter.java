package com.xxx.willing.ui.app.vote.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 投票加盟 适配器
 * @date 2019-12-03
 */

public class VoteAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public VoteAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.item_vote, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
//        mVoteImg = (ImageView) findViewById(R.id.vote_img);
//        mVoteName = (TextView) findViewById(R.id.vote_name);
//        mVoteId = (TextView) findViewById(R.id.vote_id);
//        mVotes = (TextView) findViewById(R.id.votes);
//        mVoteBtn = (TextView) findViewById(R.id.vote_btn);
//        mVoteTime = (CountDownTimerView) findViewById(R.id.vote_time);
//        mVoteTeStatus = (TextView) findViewById(R.id.vote_te_status);

    }
}
