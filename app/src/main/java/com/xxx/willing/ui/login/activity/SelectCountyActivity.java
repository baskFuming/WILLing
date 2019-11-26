package com.xxx.willing.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.ConfigClass;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.SelectCountyBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.adapter.SelectCountyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 选择国家页
 * @Author xxx
 */
public class SelectCountyActivity extends BaseTitleActivity implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SelectCountyActivity.class);
        activity.startActivityForResult(intent, ConfigClass.REQUEST_CODE);
    }

    public static final String RESULT_CODE_KRY = "code";   //返回的Key
    public static final String RESULT_NAME_KRY = "name";   //返回的Key

    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;

    private SelectCountyAdapter mAdapter;
    private List<SelectCountyBean> mList = new ArrayList<>();

    @Override
    protected String initTitle() {
        return getString(R.string.select_county_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_county;
    }

    @Override
    protected void initData() {
        mAdapter = new SelectCountyAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mRefresh.setOnRefreshListener(this);

        loadData();
    }

    @OnClick({R.id.select_county_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_county_save:
                Intent intent = new Intent();
                SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();
                SelectCountyBean countyBean = mList.get(mAdapter.getPosition());
                instance.saveString(SharedConst.VALUE_COUNTY_CODE, countyBean.getAreaCode());
                instance.saveString(SharedConst.VALUE_COUNTY_CITY, countyBean.getZhName());
                intent.putExtra(SelectCountyActivity.RESULT_CODE_KRY, countyBean.getAreaCode());
                intent.putExtra(SelectCountyActivity.RESULT_NAME_KRY, countyBean.getZhName());
                setResult(ConfigClass.RESULT_CODE, intent);
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.setPosition(position);
        mAdapter.notifyDataSetChanged();
    }


    /**
     * @Model 获取城市编码
     */
    private void loadData() {
        Api.getInstance().getCounty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<SelectCountyBean>>(this) {

                    @Override
                    public void onSuccess(BaseBean<List<SelectCountyBean>> bean) {
                        if (bean != null) {
                            List<SelectCountyBean> list = bean.getData();
                            if (list != null && list.size() != 0) {
                                String saveCode = SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_COUNTY_CODE);
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getAreaCode().equals(saveCode)) {
                                        mAdapter.setPosition(i);
                                        break;
                                    }
                                }
                                mNotData.setVisibility(View.GONE);
                                mRecycler.setVisibility(View.VISIBLE);
                                mList.clear();
                                mList.addAll(list);
                                mAdapter.notifyDataSetChanged();
                                return;
                            }
                        }

                        mNotData.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                        mNotData.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(true);
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }
                });
    }
}
