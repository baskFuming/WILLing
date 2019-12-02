package com.xxx.willing.ui.vote;

import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.view.TabLayout;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.OnClick;

public class VoteFragment extends BaseFragment {

    @BindView(R.id.vote_banner)
    Banner mBanner;
    @BindView(R.id.vote_time)
    TextView mTime;
    @BindView(R.id.vote_content)
    TextView mContent;
    @BindView(R.id.vote_tab_layout)
    TabLayout mTabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vote;
    }

    @Override
    protected void initData() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab var1) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab var1) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab var1) {

            }
        });
    }

    @OnClick({R.id.vote_relative})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.vote_relative:

                break;
        }
    }
}