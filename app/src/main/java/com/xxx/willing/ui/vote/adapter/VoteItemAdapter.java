package com.xxx.willing.ui.vote.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.bean.FranchiseeBean;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.model.utils.DownTimeUtil;

import com.xxx.willing.model.utils.StringUtil;

import java.util.List;

/**
 * @author FM
 * @desc 首页
 * @date 2019-12-06
 */

public class VoteItemAdapter extends BaseQuickAdapter<FranchiseeBean, BaseViewHolder> {

    public VoteItemAdapter(@Nullable List<FranchiseeBean> data) {
        super(R.layout.item_vote, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FranchiseeBean item) {
        int mVoteNumber = (int) item.getVoteNum();
        helper.setText(R.id.item_vote_name, item.getFranName())
                .setText(R.id.item_vote_id, "ID：" + item.getFranId())
                .setText(R.id.item_vote_number, mVoteNumber + mContext.getString(R.string.item_vote_number))
                .addOnClickListener(R.id.item_vote_1)
                .addOnClickListener(R.id.item_vote_name)
                .addOnClickListener(R.id.item_vote_btn);

        DownTimeUtil mDownTimeUtil;

        View view = helper.getView(R.id.item_vote_btn);
        Object tag = view.getTag();
        if (tag == null) {
            mDownTimeUtil = DownTimeUtil.getInstance();
            view.setTag(mDownTimeUtil);
        } else {
            mDownTimeUtil = (DownTimeUtil) tag;
            mDownTimeUtil.closeDownTime();
        }


//        LinearLayout linearLayout = helper.getView(R.id.item_vote_time_view);
//        TextView btn = helper.getView(R.id.item_vote_status);

        long startTime = StringUtil.getTime(item.getReleaseTime());
        long nowTime = StringUtil.getTime(item.getNowTime());
        long endTime = StringUtil.getTime(item.getEndTime());
        int downTime = 0;
        if (startTime >= nowTime) {
            //已开始
            downTime = (int) (endTime - nowTime) / 1000;
            helper.setText(R.id.item_vote_time_type, R.string.vote_end_time);
//            linearLayout.setVisibility(View.VISIBLE);
        } else if (nowTime >= endTime) {
            //已结束
            helper.setText(R.id.item_vote_time_type, R.string.vote_end_time);
//            helper.setText(R.id.item_vote_time_type, R.string.vote_end);
//            linearLayout.setVisibility(View.GONE);
            helper.setText(R.id.item_vote_time_h, "00")
                    .setText(R.id.item_vote_time_m, "00")
                    .setText(R.id.item_vote_time_s, "00");
        } else {
            //等待开始
            downTime = (int) (startTime - nowTime) / 1000;
            helper.setText(R.id.item_vote_time_type, R.string.vote_start_time);
//            linearLayout.setVisibility(View.VISIBLE);
        }

        if (downTime > 0) {
            mDownTimeUtil.openDownTime(downTime, new DownTimeUtil.Callback() {
                @Override
                public void run(int nowTime) {
                    //第一步计算小时
                    long h = nowTime / 3600;
                    //第二步计算分钟
                    long m = nowTime % 3600 / 60;
                    //第三步计算秒
                    long s = nowTime % 3600 % 60;
                    helper.setText(R.id.item_vote_time_h, h >= 10 ? "" + h : "0" + h)
                            .setText(R.id.item_vote_time_m, m >= 10 ? "" + m : "0" + m)
                            .setText(R.id.item_vote_time_s, s >= 10 ? "" + s : "0" + s);
                }

                @Override
                public void end() {
                    notifyItemChanged(helper.getAdapterPosition());
                }
            });
        }
        GlideUrlUtil.load(mContext, HttpConfig.HTTP_IMG_URL + item.getImgUrl(), R.mipmap.vote_banner_default, helper.getView(R.id.item_vote_img));
        TextView status = helper.getView(R.id.item_vote_status);
        switch (item.getStatus()) {
            case ApiType.VOTE_PROGRESS_STATUE:
                status.setText(R.string.item_vote_progress);
                status.setTextColor(Color.parseColor("#FFFFFF"));
                status.setBackgroundResource(R.drawable.shape_item_vote_progress);
                break;
            case ApiType.VOTE_SUCCESS_STATUE:
                status.setText(R.string.item_vote_success);
                status.setTextColor(Color.parseColor("#FFFFFF"));
                status.setBackgroundResource(R.drawable.shape_item_vote_success);
                break;
            case ApiType.VOTE_NOT_START_STATUE:
                status.setText(R.string.item_vote_not_start);
                status.setTextColor(Color.parseColor("#FFFFFF"));
                status.setBackgroundResource(R.drawable.shape_item_vote_not_start);
                break;
            case ApiType.VOTE_END_STATUE:
                status.setText(R.string.item_vote_end);
                status.setTextColor(Color.parseColor("#FE3848"));
                status.setBackgroundResource(R.drawable.shape_item_vote_end);
                break;
        }
    }
}
