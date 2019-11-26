package com.xxx.willing.ui.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.UserInfo;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.activity.LoginActivity;
import com.xxx.willing.ui.my.activity.AccountSettingActivity;
import com.xxx.willing.ui.my.activity.CallMeActivity;
import com.xxx.willing.ui.my.activity.InviteFriendActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 我的布局
 * @Author xxx
 */
public class MyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.user_icon)
    ImageView mIcon;
    @BindView(R.id.user_name)
    TextView mName;
    @BindView(R.id.user_phone)
    TextView mPhone;
    @BindView(R.id.img_level)
    ImageView mLevel;
    @BindView(R.id.submit_review)
    TextView mReview;
    @BindView(R.id.main_my_refresh)
    SwipeRefreshLayout mRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mRefresh.setOnRefreshListener(this);
        loadInfo();
    }

    @Override
    public void onRefresh() {
        loadInfo();
    }

    @OnClick({R.id.img_sign, R.id.invite_friend, R.id.re_my_vote, R.id.re_my_team, R.id.re_my_join,
            R.id.re_my_money, R.id.re_my_account_setting, R.id.account_setting_out_login})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_sign://签到
                break;
            case R.id.invite_friend://邀请好友
                InviteFriendActivity.actionStart(getActivity());
                break;
            case R.id.re_my_vote://我的投票
                break;
            case R.id.re_my_team: //我的团队
                break;
            case R.id.re_my_join://  加盟申请
                break;
            case R.id.re_my_money: //资金记录
                break;
            case R.id.re_my_account_setting://账户设置
                break;
            case R.id.account_setting_out_login://退出登录
                outLogin();
                break;
        }
    }

    /**
     * @Model 获取用户信息
     */
    private void loadInfo() {
        Api.getInstance().getUserinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<UserInfo>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<UserInfo> bean) {
                        if (bean != null) {
                            UserInfo data = bean.getData();
                            if (data != null) {
                                mName.setText(data.getUserName());
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

    @Override
    public void onResume() {
        super.onResume();
        loadInfo();
    }

    /**
     * @Model 退出登录
     */
    private void outLogin() {
        Api.getInstance().outLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        if (bean != null) {
                            ToastUtil.showToast(bean.getMessage());
                            SharedPreferencesUtil.getInstance().cleanAll(); //清空所有数据
                            startActivity(new Intent(getActivity(), LoginActivity.class));
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
}
