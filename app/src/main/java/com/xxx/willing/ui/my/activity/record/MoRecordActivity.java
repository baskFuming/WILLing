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
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.AppVersionBean;
import com.xxx.willing.model.http.bean.AssetRecordBean;
import com.xxx.willing.model.http.bean.MyVoteBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.main.UpdateWindow;
import com.xxx.willing.ui.my.activity.AccountSettingActivity;
import com.xxx.willing.ui.my.activity.adapter.DropDownAdapter;
import com.xxx.willing.ui.my.activity.adapter.DropDownLeftAdapter;
import com.xxx.willing.ui.my.activity.adapter.DropDownRightAdapter;
import com.xxx.willing.ui.wallet.adapter.WalletMarketAdapter;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 资金记录   目前为假数据，数据以借口返回为主
 * @date 2019-11-28
 */

public class MoRecordActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private int leftType;
    private int rightType;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MoRecordActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    View mNotData;

    private String headers[] = {"全部", "全部"};
    private List<View> popupViews = new ArrayList<>();

    private DropDownLeftAdapter dropDownLeftAdapter;
    private DropDownRightAdapter dropDownRightAdapter;
    private DropDownAdapter dropDownAdapter;

    //添加集合数据
    private List<AssetRecordBean> mList = new ArrayList<>();
    private List<MoRecordEntry> mLeft = new ArrayList<>();
    private List<MoRecordEntry> mRight = new ArrayList<>();

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
        dropDownLeftAdapter = new DropDownLeftAdapter(this, mLeft);
        cityView.setDividerHeight(0);
        cityView.setAdapter(dropDownLeftAdapter);

        //右边
        final ListView agesView = new ListView(this);
        dropDownRightAdapter = new DropDownRightAdapter(this, mRight);
        agesView.setDividerHeight(0);
        agesView.setAdapter(dropDownRightAdapter);

        //添加到集合
        popupViews.add(cityView);
        popupViews.add(agesView);

        //左边点击事件
        cityView.setOnItemClickListener((parent, view, position, id) -> {
            page = UIConfig.PAGE_DEFAULT;
            MoRecordEntry moRecordEntry = mLeft.get(position);
            leftType = moRecordEntry.getType();
            dropDownLeftAdapter.setCheckItem(position);
            dropDownMenu.setTabText(moRecordEntry.getName());
            dropDownMenu.closeMenu();

            loadData();
        });

        //右边点击事件
        agesView.setOnItemClickListener((parent, view, position, id) -> {
            page = UIConfig.PAGE_DEFAULT;
            MoRecordEntry moRecordEntry = mLeft.get(position);
            rightType = moRecordEntry.getType();
            dropDownRightAdapter.setCheckItem(position);
            dropDownMenu.setTabText(moRecordEntry.getName());
            dropDownMenu.closeMenu();
            //加载数据
            loadData();
        });

        RecyclerView mRecycler = new RecyclerView(this);
        mRecycler.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dropDownAdapter = new DropDownAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(dropDownAdapter);
        dropDownAdapter.setOnLoadMoreListener(this, mRecycler);

        mNotData = LayoutInflater.from(this).inflate(R.layout.include_not_data, null);
        dropDownAdapter.addFooterView(mNotData);

        //添加到菜单
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mRecycler);

        leftLoadDate();
        RightLoadDate();

        loadData();
    }

    @Override
    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
        loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadData();
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
        String[] left = getResources().getStringArray(R.array.asset_record_left);
        mLeft.add(new MoRecordEntry(left[0], ApiType.ASSET_RECORD_ALL_TYPE));
        mLeft.add(new MoRecordEntry(left[1], ApiType.ASSET_RECORD_RECHARGE_TYPE));
        mLeft.add(new MoRecordEntry(left[2], ApiType.ASSET_RECORD_TRANSFER_TYPE));
        mLeft.add(new MoRecordEntry(left[3], ApiType.ASSET_RECORD_EXCHANGE_TYPE));
        mLeft.add(new MoRecordEntry(left[4], ApiType.ASSET_RECORD_VOTE_TYPE));
        mLeft.add(new MoRecordEntry(left[5], ApiType.ASSET_RECORD_TEAM_VOTE_TYPE));
        mLeft.add(new MoRecordEntry(left[6], ApiType.ASSET_RECORD_SIGN_TYPE));
//        mLeft.add(new MoRecordEntry(left[7], ApiType.ASSET_RECORD_JOIN_TYPE));
//        mLeft.add(new MoRecordEntry(left[8], ApiType.ASSET_RECORD_BRAND_TYPE));
        mLeft.add(new MoRecordEntry(left[9], ApiType.ASSET_RECORD_TASK_TYPE));
        mLeft.add(new MoRecordEntry(left[10], ApiType.ASSET_RECORD_RANK_TYPE));
        mLeft.add(new MoRecordEntry(left[11], ApiType.ASSET_RECORD_CEO_TYPE));
        dropDownRightAdapter.notifyDataSetChanged();
    }

    //右边网络数据请求
    private void RightLoadDate() {
        String[] right = getResources().getStringArray(R.array.asset_record_right);
        mRight.add(new MoRecordEntry(right[0], ApiType.ASSET_RECORD_COIN_ALL_TYPE));
        mRight.add(new MoRecordEntry(right[1], ApiType.ASSET_RECORD_GVI_TYPE));
        mRight.add(new MoRecordEntry(right[2], ApiType.ASSET_RECORD_BVSE_TYPE));
        mRight.add(new MoRecordEntry(right[3], ApiType.ASSET_RECORD_BTC_TYPE));
        mRight.add(new MoRecordEntry(right[4], ApiType.ASSET_RECORD_ETH_TYPE));
        mRight.add(new MoRecordEntry(right[5], ApiType.ASSET_RECORD_USDT_VOTE_TYPE));
        dropDownRightAdapter.notifyDataSetChanged();
    }

    //条件查询数据
    private void loadData() {
        Api.getInstance().getAssetRecordList(rightType, leftType, page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<AssetRecordBean>>(this) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<AssetRecordBean>> bean) {
                        PageBean<AssetRecordBean> data = bean.getData();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            dropDownAdapter.loadMoreEnd(true);
                            return;
                        }
                        List<AssetRecordBean> list = data.getList();
                        if (list == null || list.size() == 0 && page == UIConfig.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            dropDownAdapter.loadMoreEnd(true);
                            return;
                        }
                        mNotData.setVisibility(View.GONE);
                        if (page == UIConfig.PAGE_DEFAULT) {
                            mList.clear();
                        }
                        mList.addAll(list);
                        if (list.size() < UIConfig.PAGE_SIZE) {
                            dropDownAdapter.loadMoreEnd(true);
                        } else {
                            dropDownAdapter.loadMoreComplete();
                        }
                        dropDownAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mList.size() == 0) {
                            mNotData.setVisibility(View.VISIBLE);
                        }
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }
}
