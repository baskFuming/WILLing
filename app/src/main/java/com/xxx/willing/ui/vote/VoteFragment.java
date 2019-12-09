package com.xxx.willing.ui.vote;

import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.BannerBean;
import com.xxx.willing.model.http.bean.BrandBean;
import com.xxx.willing.model.http.bean.MessageBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.BannerUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.vote.activity.NoticeCenterActivity;
import com.xxx.willing.view.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VoteFragment extends BaseFragment {

    @BindView(R.id.vote_banner)
    Banner mBanner;
    @BindView(R.id.vote_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_home_view_flipper)
    ViewFlipper mViewFlipper;

    private List<MessageBean> mNoticeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vote;
    }

    @Override
    protected void initData() {
        getBannerList();
        getMessageList();
    }

    @OnClick({R.id.vote_relative})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.vote_relative:

                break;
        }
    }

    /**
     * @Model 获取
     */
    private void getBrandList(){
        Api.getInstance().getBrandList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<BrandBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<BrandBean>> bean) {
                        if (bean != null) {
                            PageBean<BrandBean> data = bean.getData();
                            if (data != null) {
                                List<BrandBean> list = data.getList();
                                for (int i = 0; i < list.size(); i++) {
                                    BrandBean brandBean = list.get(i);
//                                    mTabLayout.addTab(mTabLayout.newTab().setText(brandBean));
                                }
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
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
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
                                addOnClick();
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
                                BannerUtil.init(mBanner, list1, position -> {
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