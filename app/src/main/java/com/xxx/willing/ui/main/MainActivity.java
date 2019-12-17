package com.xxx.willing.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.BuildConfig;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.base.fragment.FragmentManager;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.AppVersionBean;
import com.xxx.willing.model.http.bean.IsSettingPayPswBean;
import com.xxx.willing.model.http.bean.UserInfo;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ExitAppUtil;
import com.xxx.willing.model.utils.PermissionUtil;
import com.xxx.willing.model.utils.SystemUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.AppFragment;
import com.xxx.willing.ui.my.MyFragment;
import com.xxx.willing.ui.vote.VoteFragment;
import com.xxx.willing.ui.wallet.WalletFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 主页
 * @Author xxx
 */
public class MainActivity extends BaseActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    //页面下标
    private static final int VOTE_TYPE = R.id.main_vote;     //投票
    private static final int APP_TYPE = R.id.main_app;     //应用
    private static final int WALLET_TYPE = R.id.main_wallet; //钱包
    private static final int MY_TYPE = R.id.main_my;         //我的

    @BindView(R.id.main_vote_image)
    ImageView mVoteImage;
    @BindView(R.id.main_app_image)
    ImageView mAppImage;
    @BindView(R.id.main_wallet_image)
    ImageView mWalletImage;
    @BindView(R.id.main_my_image)
    ImageView mMyImage;

    @BindView(R.id.main_vote_text)
    TextView mVoteText;
    @BindView(R.id.main_app_text)
    TextView mAppText;
    @BindView(R.id.main_wallet_text)
    TextView mWalletText;
    @BindView(R.id.main_my_text)
    TextView mMyText;

    private int nowType = VOTE_TYPE;   //当前选中下标
    private int lastType = VOTE_TYPE;   //上一个下标

    private ExitAppUtil exitAppUtil;    //双击退出

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        //初始化读写权限
        PermissionUtil.checkPermission(this, PermissionUtil.READ_PERMISSION, PermissionUtil.WRITE_PERMISSION);

        //初始化双击退出
        exitAppUtil = ExitAppUtil.getInstance();

        //检测是否设置过支付密码
        checkIsSettingPayPassword();
        //加载用户信息
        loadInfo();

        //版本更新
        if (!BuildConfig.DEBUG) {
            checkAppVersion();
        }

        //加载首页数据
        selectorItem();
    }

    @OnClick({R.id.main_vote, R.id.main_app, R.id.main_wallet, R.id.main_my})
    public void onClick(View v) {
        nowType = v.getId();
        if (nowType != lastType) {
            defaultItem();
            selectorItem();
            lastType = nowType;
        }
    }

    @Override
    public void onBackPressed() {
        exitAppUtil.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitAppUtil.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onEventBus(String eventFlag) {
        //首先调用父类
        super.onEventBus(eventFlag);
        switch (eventFlag) {
            case EventBusConfig.EVENT_LOGIN:
                //等待页面渲染完毕
                this.getWindow().getDecorView().post(() -> {
                    if (!BuildConfig.DEBUG) {
                        checkAppVersion();
                    }
                    checkIsSettingPayPassword();
                    //加载用户信息
                    loadInfo();
                });
                break;
            case EventBusConfig.EVENT_NOTICE_USER:
                //加载用户信息
                loadInfo();
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nowType = savedInstanceState.getInt("type", VOTE_TYPE);

        //切换数据
        selectorItem();
        defaultItem();
        lastType = nowType;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", nowType);
    }

    //选中的
    public void selectorItem() {
        switch (nowType) {
            case VOTE_TYPE:
                mVoteImage.setImageResource(R.mipmap.main_vote_selection);
                mVoteText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, VoteFragment.class, R.id.main_frame);
                break;
            case APP_TYPE:
                mAppImage.setImageResource(R.mipmap.main_app_selection);
                mAppText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, AppFragment.class, R.id.main_frame);
                break;
            case WALLET_TYPE:
                mWalletImage.setImageResource(R.mipmap.main_wallet_selection);
                mWalletText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, WalletFragment.class, R.id.main_frame);
                break;
            case MY_TYPE:
                mMyImage.setImageResource(R.mipmap.main_my_selection);
                mMyText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, MyFragment.class, R.id.main_frame);
                break;
        }
    }

    //撤销上次选中的
    public void defaultItem() {
        switch (lastType) {
            case VOTE_TYPE:
                mVoteImage.setImageResource(R.mipmap.main_vote_default);
                mVoteText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
            case APP_TYPE:
                mAppImage.setImageResource(R.mipmap.main_app_default);
                mAppText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
            case WALLET_TYPE:
                mWalletImage.setImageResource(R.mipmap.main_wallet_default);
                mWalletText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
            case MY_TYPE:
                mMyImage.setImageResource(R.mipmap.main_my_default);
                mMyText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
        }
    }

    /**
     * @Model 检查App版本
     */
    private void checkAppVersion() {
        Api.getInstance().checkAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AppVersionBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<AppVersionBean> bean) {
                        if (bean != null) {
                            AppVersionBean data = bean.getData();
                            if (data != null) {
                                String version = data.getVersion();
                                if (!SystemUtil.getVersionName(MainActivity.this).equals(version)) {
                                    UpdateWindow.getInstance(MainActivity.this, data.getDownloadUrl());
                                }
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
     * @Model 检查是否设置支付密码
     */
    private void checkIsSettingPayPassword() {
        Api.getInstance().checkIsSettingPayPassword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<IsSettingPayPswBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<IsSettingPayPswBean> bean) {
                        if (bean != null) {
                            IsSettingPayPswBean data = bean.getData();
                            if (data != null) {
                                SharedPreferencesUtil.getInstance().saveBoolean(SharedConst.IS_SETTING_PAY_PSW, data.isResult());
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
     * @Model 获取用户信息
     */
    private void loadInfo() {
        Api.getInstance().getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<UserInfo>(this) {

                    @Override
                    public void onSuccess(BaseBean<UserInfo> bean) {
                        if (bean != null) {
                            UserInfo data = bean.getData();
                            if (data != null) {
                                SharedPreferencesUtil.getInstance().saveString(SharedConst.VALUE_USER_INVITE_CODE, data.getInvestCode());
                                SharedPreferencesUtil.getInstance().saveString(SharedConst.VALUE_USER_ICON, data.getAvatar());
                                SharedPreferencesUtil.getInstance().saveString(SharedConst.VALUE_USER_NAME, data.getNickname());
                                SharedPreferencesUtil.getInstance().saveString(SharedConst.VALUE_USER_PHONE, data.getTelphone());
                                SharedPreferencesUtil.getInstance().saveInt(SharedConst.VALUE_USER_STAR, data.getStar());
                                SharedPreferencesUtil.getInstance().saveInt(SharedConst.VALUE_USER_FRAN_ID, data.getFranId());

                                SharedPreferencesUtil.getInstance().saveInt(SharedConst.IS_VOTE_FRAN, data.getFranStatus());

                                EventBus.getDefault().post(EventBusConfig.EVENT_UPDATE_USER);
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