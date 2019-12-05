package com.xxx.willing.ui.app.vote.activity.partner;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.ui.app.vote.activity.partner.fragment.PartnerFragment;
import com.xxx.willing.ui.app.vote.adapter.ViewPagerAdapter;
import com.xxx.willing.view.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 合伙人列表
 * @date 2019-12-03
 */

public class PartnerActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PartnerActivity.class);
        activity.startActivity(intent);
    }

    @BindArray(R.array.partner_status)
    String[] status;
    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;
    private List<BaseFragment> fragments = new ArrayList<>();


    @Override
    protected String initTitle() {
        return getString(R.string.partner_list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_partner;
    }

    @Override
    protected void initData() {
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(R.string.partner_rules);
        List<String> list = Arrays.asList(status);

        fragments.add(PartnerFragment.getInstance(ApiType.PARTNER_LIST_ALL, list.get(0)));
        fragments.add(PartnerFragment.getInstance(ApiType.PARTNER_LIST_AERA_ALL, list.get(1)));
        fragments.add(PartnerFragment.getInstance(ApiType.PARTNER_LIST_CITY_ALL, list.get(2)));
        fragments.add(PartnerFragment.getInstance(ApiType.PARTNER_LIST_DIRECTOR_ALL, list.get(3)));

        mViewPager.setOffscreenPageLimit(fragments.size() - 1);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, list));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, "", getString(R.string.partner_rules));
                break;
        }
    }
}
