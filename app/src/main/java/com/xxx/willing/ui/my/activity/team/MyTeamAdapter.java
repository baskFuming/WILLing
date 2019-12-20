package com.xxx.willing.ui.my.activity.team;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.MyTeamBean;
import com.xxx.willing.model.utils.StringUtil;

import java.util.List;

public class MyTeamAdapter extends BaseQuickAdapter<MyTeamBean.ListBean, BaseViewHolder> {

    MyTeamAdapter(@Nullable List<MyTeamBean.ListBean> data) {
        super(R.layout.item_my_team, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTeamBean.ListBean item) {
        helper.setText(R.id.item_my_team_account, StringUtil.getPhone(item.getTelphone()))
                .setText(R.id.item_my_team_number, item.getAchievement() + "")
                .setText(R.id.item_my_team_gvinumber, item.getGviAmount() + "")
                .setText(R.id.item_my_team_time, item.getCreateTime());
    }
}
