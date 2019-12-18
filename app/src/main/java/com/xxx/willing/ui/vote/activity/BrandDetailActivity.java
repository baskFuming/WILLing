package com.xxx.willing.ui.vote.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.http.bean.BrandBean;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.ui.vote.adapter.BrandDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BrandDetailActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, BrandBean bean) {
        Intent intent = new Intent(activity, BrandDetailActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        bean = (BrandBean) intent.getSerializableExtra("bean");
        if (bean == null) bean = new BrandBean();
    }

    @BindView(R.id.brand_detail_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.brand_detail_content)
    TextView mContent;

    private BrandBean bean;

    @Override
    protected String initTitle() {
        return bean.getGVIName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand_detail;
    }

    @Override
    protected void initData() {
        initBundle();

        mContent.setText(bean.getDetails());
        BrandDetailAdapter mAdapter = new BrandDetailAdapter(bean.getImg());
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
    }
}
