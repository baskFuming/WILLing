package com.xxx.willing.ui.app.vote.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 团队信息
 * @date 2019-12-03
 */

public class TeamMesAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public TeamMesAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.team_mess_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

    }
}
