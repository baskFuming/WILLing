package com.xxx.willing.ui.app.activity.gvishop.my.order;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.ui.app.vote.activity.gvishop.my.order.fragment.MyOrderFragment;
import com.xxx.willing.ui.app.vote.adapter.ViewPagerAdapter;
import com.xxx.willing.view.MyTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * @author FM
 * @desc 我的订单
 * @date 2019-12-04
 */

public class MyOrderActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyOrderActivity.class);
        activity.startActivity(intent);
    }

    @BindArray(R.array.order_gvi_tab)
    String[] status;
    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.main_tab_layout)
    MyTabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;

    private List<BaseFragment> fragments = new ArrayList<>();


    @Override
    protected String initTitle() {
        return getString(R.string.my_order);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initData() {
        List<String> list = Arrays.asList(status);

        fragments.add(MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_ALL, list.get(0)));//全部
        fragments.add(MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_UNPAID, list.get(1)));//待付款
        fragments.add(MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_DELIVERY, list.get(2)));//待发货
        fragments.add(MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_GOODS, list.get(3)));//待收货
        fragments.add(MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_COMPLAINING, list.get(4)));//已收货

        mTabLayout.setSelectedIndicatorHeight(6);
        mTabLayout.setTabIndicatorWidth(80);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.wallet_tab_default_color), getResources().getColor(R.color.wallet_tab_select_color));
        mTabLayout.setTabTextSize(38, 44);
        mTabLayout.setTextSelectedBold(true);
        mTabLayout.setTabMode(MyTabLayout.GRAVITY_CENTER);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_tab_select_color));

        mViewPager.setOffscreenPageLimit(fragments.size() - 1);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, list));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
