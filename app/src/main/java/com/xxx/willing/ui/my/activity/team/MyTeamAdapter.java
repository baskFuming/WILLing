package com.xxx.willing.ui.my.activity.team;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.MyTeamBean;

import java.util.List;

public class MyTeamAdapter extends BaseQuickAdapter<MyTeamBean, BaseViewHolder> {

    MyTeamAdapter(@Nullable List<MyTeamBean> data) {
        super(R.layout.item_my_team, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTeamBean item) {
//        helper.setText(R.id.item_my_team_account, "")
//                .setText(R.id.item_my_team_number, "")
//                .setText(R.id.item_my_team_time, "");
    }
}
