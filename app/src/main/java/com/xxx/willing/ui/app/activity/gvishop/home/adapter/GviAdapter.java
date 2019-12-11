package com.xxx.willing.ui.app.activity.gvishop.home.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.GviBean;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 商城适配器
 * @date 2019-12-06
 */

public class GviAdapter extends BaseQuickAdapter<GviBean.DataBean, BaseViewHolder> {

    private GviBean.DataBean bean;

    public void notify(GviBean.DataBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public GviAdapter(@Nullable List<GviBean.DataBean> data) {
        super(R.layout.gvi_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GviBean.DataBean item) {
        helper.setText(R.id.life_type, item.getName())
                .setText(R.id.title, item.getDetails());
        RecyclerView recyclerView = helper.getView(R.id.item_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        Object tag = recyclerView.getTag();
        GviChildAdapter gviChildAdapter;
        if (tag == null) {
            gviChildAdapter = new GviChildAdapter(bean.getList());
            recyclerView.setTag(gviChildAdapter);
            recyclerView.setAdapter(gviChildAdapter);
        } else {
            gviChildAdapter = (GviChildAdapter) tag;
            bean.getList();
            gviChildAdapter.notifyDataSetChanged();
        }
    }

}
