package com.xxx.willing.ui.my.activity.record;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.ui.my.activity.adapter.DropDownAdapter;
import com.xxx.willing.ui.my.activity.adapter.DropDownLeftAdapter;
import com.xxx.willing.ui.my.activity.adapter.DropDownRightAdapter;
import com.xxx.willing.ui.wallet.adapter.WalletMarketAdapter;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author FM
 * @desc 资金记录   目前为假数据，数据以借口返回为主
 * @date 2019-11-28
 */

public class MoRecordActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MoRecordActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

//    @BindView(R.id.main_recycler)
//    RecyclerView mRecycler;
//    @BindView(R.id.main_not_data)
//    LinearLayout mNotData;
//    @BindView(R.id.main_refresh)
//    SwipeRefreshLayout mRefresh;

    private String headers[] = {"全部", "全部"};
    private List<View> popupViews = new ArrayList<>();

    private DropDownLeftAdapter dropDownLeftAdapter;
    private DropDownRightAdapter dropDownRightAdapter;
    private DropDownAdapter dropDownAdapter;

    //添加集合数据
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private List<BaseBean> mList = new ArrayList<>();

    private int position;
    private int page = UIConfig.PAGE_DEFAULT;


    @Override
    protected String initTitle() {
        return getString(R.string.my_money);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mo_record;
    }

    @Override
    protected void initData() {

        //左边
        final ListView cityView = new ListView(this);
        dropDownLeftAdapter = new DropDownLeftAdapter(this, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(dropDownLeftAdapter);

        //右边
        final ListView agesView = new ListView(this);
        dropDownRightAdapter = new DropDownRightAdapter(this, Arrays.asList(ages));
        agesView.setDividerHeight(0);
        agesView.setAdapter(dropDownRightAdapter);

        //添加到集合
        popupViews.add(cityView);
        popupViews.add(agesView);

        //左边点击事件
        cityView.setOnItemClickListener((parent, view, position, id) -> {
            dropDownLeftAdapter.setCheckItem(position);
            dropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
            dropDownMenu.closeMenu();
        });

        //右边点击事件
        agesView.setOnItemClickListener((parent, view, position, id) -> {
            dropDownRightAdapter.setCheckItem(position);
            dropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
            dropDownMenu.closeMenu();
        });

        final RecyclerView mRecycler = new RecyclerView(this);
        mRecycler.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dropDownAdapter = new DropDownAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(dropDownAdapter);
        dropDownAdapter.setOnLoadMoreListener(this, mRecycler);

        //添加到菜单
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mRecycler);

        leftLoadDate();
        RightLoadDate();
        LoadDate();
    }

    @Override
    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
    }


    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    //左边网络数据请求
    private void leftLoadDate() {

    }

    //右边网络数据请求
    private void RightLoadDate() {
    }

    //条件查询数据
    private void LoadDate() {

    }
}
