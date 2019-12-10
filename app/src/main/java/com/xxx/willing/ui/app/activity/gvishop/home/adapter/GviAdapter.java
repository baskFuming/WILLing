package com.xxx.willing.ui.app.vote.activity.gvishop.home.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 商城适配器
 * @date 2019-12-06
 */

public class GviAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    private BaseBean bean;
    private Callback callback;

    public void notify(BaseBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public GviAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.gvi_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        helper.setText(R.id.life_type, "")
                .setText(R.id.title, "");

        RecyclerView recyclerView = helper.getView(R.id.item_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        Object tag = recyclerView.getTag();
        GviChildAdapter gviChildAdapter;
        if (tag == null) {
//            gviChildAdapter = new GviChildAdapter(bean.getList(item.getId()));
//            recyclerView.setTag(gviChildAdapter);
//            recyclerView.setAdapter(gviChildAdapter);
        } else {
            gviChildAdapter = (GviChildAdapter) tag;
//            bean.getList(item.getId());
            gviChildAdapter.notifyDataSetChanged();
        }
//        gviChildAdapter.setOnItemClickListener((adapter, view, position) -> {
//            if (callback != null) {
////                String address = findTypeProAdpater.getData().get(position).getAddress();
//                callback.onResponse("");
//            }
//        });
    }

    public interface Callback {
        void onResponse(String url);
    }

}
