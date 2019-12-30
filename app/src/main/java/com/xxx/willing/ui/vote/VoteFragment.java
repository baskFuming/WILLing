package com.xxx.willing.ui.vote;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.androidkun.xtablayout.XTabLayout;
import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.BannerBean;
import com.xxx.willing.model.http.bean.BrandBean;
import com.xxx.willing.model.http.bean.MessageBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.BannerInitUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.vote.activity.NoticeCenterActivity;
import com.xxx.willing.ui.vote.adapter.VoteAdapter;
import com.xxx.willing.ui.vote.fragment.VoteItemFragment;
import com.xxx.willing.view.GuideView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VoteFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.main_tab_layout)
    XTabLayout myTabLayout;
    @BindView(R.id.vote_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_home_view_flipper)
    ViewFlipper mViewFlipper;
    @BindView(R.id.vote_banner_init)
    com.xw.banner.Banner banner;

    private List<MessageBean> mNoticeList = new ArrayList<>();
    private List<BaseFragment> mFragment = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();
    private VoteAdapter mAdapter;
    private ImageView mskip;
    private GuideView guideView;
    private boolean isFirst;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vote;
    }

    @Override
    protected void initData() {
//        initListener();
        getBannerList();
        getMessageList();
        getBrandList();
        //滑动卡顿
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return true;
                }
            });
        }
        //下拉刷新
        mAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                mRefresh.setEnabled(true);
            } else {
                mRefresh.setEnabled(false);
            }
        });
        mRefresh.setOnRefreshListener(this);
    }

//    private void initListener() {
//        isFirst = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_FIRST);
//        View view = getLayoutInflater().inflate(R.layout.home_guide_view, null);
//        mskip = view.findViewById(R.id.img_skip);
//        if (isFirst){
//
//        }
//    }


    @OnClick({R.id.vote_relative})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.vote_relative:
                NoticeCenterActivity.actionStart(getActivity());
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (mFragment.size() != 0) {
            VoteItemFragment item = (VoteItemFragment) mAdapter.getItem(mViewPager.getCurrentItem());
            item.onRefresh();
        } else {
            getBannerList();
            getBrandList();
        }
        mRefresh.setRefreshing(false);
    }

    /**
     * @Model 获取品牌列表
     */
    private void getBrandList() {
        Api.getInstance().getBrandList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<BrandBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<BrandBean>> bean) {
                        if (bean != null) {
                            PageBean<BrandBean> data = bean.getData();
                            if (data != null) {
                                mTitle.clear();
                                mFragment.clear();
                                List<BrandBean> list = data.getList();
                                for (int i = 0; i < list.size(); i++) {
                                    BrandBean brandBean = list.get(i);
                                    mTitle.add(brandBean.getName());
                                    mFragment.add(VoteItemFragment.getInstance(brandBean));
                                }
                                mAdapter = new VoteAdapter(getChildFragmentManager(), mFragment, mTitle);
                                mViewPager.setAdapter(mAdapter);
                                myTabLayout.setupWithViewPager(mViewPager);
                                mViewPager.setOffscreenPageLimit(mFragment.size() - 1);
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
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

    /**
     * @Model 获取消息中心数据
     */
    private void getMessageList() {
        Api.getInstance().getMessageList(1, 100000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<MessageBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<MessageBean>> bean) {
                        if (bean != null) {
                            PageBean<MessageBean> data = bean.getData();
                            if (data != null) {
                                List<MessageBean> list = data.getList();
                                for (int i = 0; i < list.size(); i++) {
                                    MessageBean messageBean = list.get(i);
                                    if (messageBean != null) {
                                        addView(messageBean);
                                    }
                                }
                                if (list.size() == 1) {
                                    addView(list.get(0));
                                }
//                                addOnClick();
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    private void addView(MessageBean noticeCenterBean) {
                        View inflate = View.inflate(getContext(), R.layout.weight_view_flipper, null);
                        TextView mContext = inflate.findViewById(R.id.main_home_notice_content);
                        TextView mTime = inflate.findViewById(R.id.main_home_notice_time);
                        mContext.setText(noticeCenterBean.getName());
                        mTime.setText(noticeCenterBean.getCreateTime());
                        mViewFlipper.addView(inflate);
                        mNoticeList.add(noticeCenterBean);
                    }

                    private void addOnClick() {
                        mViewFlipper.setOnClickListener(v -> {
                            //目前版本统一跳转到列表
                            NoticeCenterActivity.actionStart(getActivity());
                        });
                    }
                });
    }

    /**
     * @Model 获取轮播
     */
    private void getBannerList() {
        Api.getInstance().getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<BannerBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<BannerBean>> bean) {
                        if (bean != null) {
                            PageBean<BannerBean> data = bean.getData();
                            if (data != null) {
                                List<BannerBean> list = data.getList();
                                List<String> list1 = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {
                                    list1.add(list.get(i).getImg());
                                }
                                BannerInitUtil.init(banner, list1, 0, position -> {
                                    String url = list.get(position).getUrl();
                                });
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