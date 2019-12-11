package com.xxx.willing.ui.app.activity.partner;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
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
import com.xxx.willing.view.MyTabLayout;

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
    MyTabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;
    private List<BaseFragment> fragments = new ArrayList<>();
    private String mTitle;

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
        mTitle = getString(R.string.partner_rules);
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(mTitle);
        List<String> list = Arrays.asList(status);

        fragments.add(PartnerFragment.getInstance(ApiType.PARTNER_LIST_ALL, list.get(0)));
        fragments.add(PartnerFragment.getInstance(ApiType.PARTNER_LIST_AERA_ALL, list.get(1)));
        fragments.add(PartnerFragment.getInstance(ApiType.PARTNER_LIST_CITY_ALL, list.get(2)));
        fragments.add(PartnerFragment.getInstance(ApiType.PARTNER_LIST_DIRECTOR_ALL, list.get(3)));

        mTabLayout.setSelectedIndicatorHeight(6);
        mTabLayout.setTabIndicatorWidth(80);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.main_tab_default_color), getResources().getColor(R.color.main_tab_select_color));
        mTabLayout.setTabTextSize(38, 44);
        mTabLayout.setTextSelectedBold(true);
        mTabLayout.setTabMode(MyTabLayout.GRAVITY_CENTER);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_tab_select_color));

        mViewPager.setOffscreenPageLimit(fragments.size() - 1);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, list));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, "", mTitle);
                break;
        }
    }
}
