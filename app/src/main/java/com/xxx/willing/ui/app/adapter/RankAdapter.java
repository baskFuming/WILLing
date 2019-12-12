package com.xxx.willing.ui.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 排名适配器
 * @date 2019-12-03
 */

public class RankAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public RankAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.rank_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

    }
}
