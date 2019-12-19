package com.xxx.willing.ui.my;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;

import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.vote.JoinDetailsActivity;
import com.xxx.willing.ui.my.activity.AccountSettingActivity;
import com.xxx.willing.ui.my.activity.InviteFriendActivity;
import com.xxx.willing.ui.my.activity.join.JoinApplyActivity;
import com.xxx.willing.ui.my.activity.record.MoRecordActivity;
import com.xxx.willing.ui.my.activity.sign.SignActivity;
import com.xxx.willing.ui.my.activity.team.MyTeamActivity;
import com.xxx.willing.ui.my.activity.userinfo.AccountInfoActivity;
import com.xxx.willing.ui.my.activity.vote.MyVoteActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

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

    private String name;
    private String phone;
    private String icon;
    private SharedPreferencesUtil instance;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        instance = SharedPreferencesUtil.getInstance();
        loadData();
        mRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        EventBus.getDefault().post(EventBusConfig.EVENT_NOTICE_USER);
        mRefresh.setRefreshing(false);
    }

    @OnClick({R.id.img_sign, R.id.invite_friend, R.id.re_my_vote, R.id.account_info, R.id.re_my_team, R.id.re_my_join,
            R.id.re_my_money, R.id.re_my_account_setting})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_sign://签到
                SignActivity.actionStart(getActivity());
                break;
            case R.id.invite_friend://邀请好友
                InviteFriendActivity.actionStart(getActivity());
                break;
            case R.id.account_info://账户信息
                AccountInfoActivity.actionStart(getActivity(), icon, name, phone);
                break;
            case R.id.re_my_vote://我的投票
                MyVoteActivity.actionStart(getActivity());
                break;
            case R.id.re_my_team: //我的团队
                MyTeamActivity.actionStart(getActivity());
                break;
            case R.id.re_my_join://  加盟申请
                Integer statusFran = instance.getInt(SharedConst.IS_VOTE_FRAN);
                if (statusFran == ApiType.VOTE_SUCCESS_STATUS) {
                    JoinDetailsActivity.actionStarts(getActivity(), instance.getInt(SharedConst.VALUE_USER_FRAN_ID));
                } else if (statusFran == ApiType.VOTE_PROGRESS_STATUS) {
                    ToastUtil.showToast("加盟申请审核中");
                } else {
                    JoinApplyActivity.actionStart(getActivity());
                }
                break;
            case R.id.re_my_money: //资金记录
                MoRecordActivity.actionStart(getActivity());
                break;
            case R.id.re_my_account_setting://账户设置
                AccountSettingActivity.actionStart(getActivity());
                break;
        }
    }

    @Override
    public void onEventBus(String eventFlag) {
        super.onEventBus(eventFlag);
        switch (eventFlag) {
            case EventBusConfig.EVENT_UPDATE_USER:
                loadData();
                break;
        }
    }

    private void loadData() {
        name = instance.getString(SharedConst.VALUE_USER_NAME);
        phone = instance.getString(SharedConst.VALUE_USER_PHONE);
        icon = instance.getString(SharedConst.VALUE_USER_ICON);
        int star = instance.getInt(SharedConst.VALUE_USER_STAR);
        Integer statusFran = instance.getInt(SharedConst.IS_VOTE_FRAN);

        mName.setText(name);
        mPhone.setText(phone);
        GlideUrlUtil.loadCircle(getContext(), HttpConfig.HTTP_IMG_URL + icon,  R.mipmap.my_icon,mIcon);
        switch (star) {
            case 1:
                mLevel.setImageResource(R.mipmap.level_0);
                break;
            case 2:
                mLevel.setImageResource(R.mipmap.level_1);
                break;
            case 3:
                mLevel.setImageResource(R.mipmap.level_2);
                break;
            case 4:
                mLevel.setImageResource(R.mipmap.level_3);
                break;
            case 5:
                mLevel.setImageResource(R.mipmap.level_4);
                break;
            case 6:
                mLevel.setImageResource(R.mipmap.level_5);
                break;
        }

        switch (statusFran) {
            case ApiType.VOTE_SUCCESS_STATUS:
                mReview.setText(R.string.my_submitted_success);
                break;
            case ApiType.VOTE_PROGRESS_STATUS:
                mReview.setText(R.string.my_submitted_review);
                break;
            default:
                mReview.setText("");
                break;
        }
    }
}
