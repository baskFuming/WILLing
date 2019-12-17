package com.xxx.willing.ui.app.activity.gvishop.my.order;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.ui.app.activity.gvishop.my.order.fragment.MyOrderFragment;
import com.xxx.willing.ui.app.adapter.ViewPagerAdapter;
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
    XTabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;

    private List<BaseFragment> fragments = new ArrayList<>();

    private MyOrderFragment allFragment;
    private MyOrderFragment unpaidFragment;
    private MyOrderFragment deliveryFragment;
    private MyOrderFragment goodsFragment;
    private MyOrderFragment complainigFragment;

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

        allFragment = MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_ALL, list.get(0));
        unpaidFragment = MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_UNPAID, list.get(1));
        deliveryFragment = MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_DELIVERY, list.get(2));
        goodsFragment = MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_GOODS, list.get(3));
        complainigFragment = MyOrderFragment.getInstance(ApiType.ORDER_COMMUNITY_COMPLAINING, list.get(4));

        fragments.add(allFragment);//全部
        fragments.add(unpaidFragment);//待付款
        fragments.add(deliveryFragment);//待发货
        fragments.add(goodsFragment);//待收货
        fragments.add(complainigFragment);//已收货

        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, list));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(fragments.size() - 1);
    }

    //订单完成切换状态
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == UIConfig.RESULT_CODE && data != null) {
            mViewPager.setCurrentItem(data.getIntExtra("type", 0)); //预加载当前页面数据
            if (unpaidFragment != null) unpaidFragment.onRefresh();
            if (deliveryFragment != null) deliveryFragment.onRefresh();
            if (goodsFragment != null) goodsFragment.onRefresh();
            if (complainigFragment != null) complainigFragment.onRefresh();
        }
    }
}
