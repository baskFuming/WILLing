package com.xxx.willing.ui.app.activity.partner;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.PartnerListBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.partner.fragment.PartnerFragment;
import com.xxx.willing.ui.vote.adapter.VoteAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.main_tab_layout)
    XTabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;
    private List<BaseFragment> fragments = new ArrayList<>();
    private List<String> mTabTitle = new ArrayList<>();
    private String mTitle;
    private int page = UIConfig.PAGE_DEFAULT;
    private int position;
    private VoteAdapter voteAdapter;
    private PartnerFragment partnerFragment;

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

        loadDate();
    }


    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, HttpConfig.PARTNER_URL, mTitle);
                break;
        }
    }

    /**
     * @Model 获取合伙人列表title
     */
    private void loadDate() {
        Api.getInstance().partnerRatios(page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<PartnerListBean>>(this) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<PartnerListBean>> bean) {
                        if (bean != null) {
                            PageBean<PartnerListBean> data = bean.getData();
                            if (data != null) {
                                mTabTitle.clear();
                                fragments.clear();
                                List<PartnerListBean> list = data.getList();
                                for (int i = 0; i < list.size(); i++) {
                                    PartnerListBean brandBean = list.get(i);
                                    partnerFragment = PartnerFragment.getInstance(brandBean);
                                    mTabTitle.add(brandBean.getName());
                                    fragments.add(partnerFragment);
                                }
                                voteAdapter = new VoteAdapter(getSupportFragmentManager(), fragments, mTabTitle);
                                mViewPager.setAdapter(voteAdapter);
                                mTabLayout.setupWithViewPager(mViewPager);
                                mViewPager.setOffscreenPageLimit(fragments.size() - 1);
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }
}
