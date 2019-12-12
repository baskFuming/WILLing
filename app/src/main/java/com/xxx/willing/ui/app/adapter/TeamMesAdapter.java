package com.xxx.willing.ui.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.VoteDetailBean;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

/**
 * @author FM
 * @desc 团队信息
 * @date 2019-12-03
 */

public class TeamMesAdapter extends BaseQuickAdapter<VoteDetailBean.ListBean, BaseViewHolder> {

    public TeamMesAdapter(@Nullable List<VoteDetailBean.ListBean> data) {
        super(R.layout.team_mess_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteDetailBean.ListBean item) {
        helper.setText(R.id.job_position, item.getUserRole())
                .setText(R.id.job_name, item.getName())
                .setText(R.id.job_content, item.getDatils())
                .addOnClickListener(R.id.job_all);

        GlideUtil.loadCircle(mContext, item.getUserImg(), helper.getView(R.id.user_img));
    }
}
