package com.xxx.willing.ui.my.activity.sign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.SignInfoBean;
import com.xxx.willing.model.http.bean.TaskInfoBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.my.activity.InviteFriendActivity;
import com.xxx.willing.ui.my.activity.sign.view.StepBean;
import com.xxx.willing.ui.my.activity.sign.view.StepsView;
import com.xxx.willing.ui.my.activity.vote.MyVoteActivity;
import com.xxx.willing.ui.my.activity.window.SignPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 任务中心，签到
 * @date 2019-11-27
 */

public class SignActivity extends BaseTitleActivity implements SignPopWindow.Callback {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SignActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.text_rules)
    TextView mTeRules;
    @BindView(R.id.te_trip)
    TextView mTrip;
    @BindView(R.id.task_progress)
    ProgressBar mProgress;
    @BindView(R.id.step_view)
    StepsView mStepView;
    @BindView(R.id.te_invite_friend)
    TextView mInviteBtn;
    @BindView(R.id.te_go_vote)
    TextView mVoteBtn;
    @BindView(R.id.te_sign_btn)
    TextView mSignBtn;
    private SignPopWindow signPopWindow;
    private boolean todaySign;
    private Integer days;

    @Override
    protected String initTitle() {
        return getString(R.string.sign_task);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initData() {
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(getString(R.string.sign_task_rules));

        //获取信息
        getInfo();
    }

    @OnClick({R.id.main_content, R.id.te_sign_btn, R.id.te_invite_friend, R.id.te_go_vote})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content://任务规则
                BaseWebActivity.actionStart(this, HttpConfig.TASK_URL, getString(R.string.sign_task_rules));
                break;
            case R.id.te_sign_btn://签到弹框
                //判断今天是否签到过
                if (todaySign) {
                    ToastUtil.showToast("今天已经签到过");
                } else {
                    //执行签到
                    mStepView.startSignAnimation(this::sign);
                }
                break;
            case R.id.te_invite_friend:
                InviteFriendActivity.actionStart(this);
                break;
            case R.id.te_go_vote:
                MyVoteActivity.actionStart(this);
                break;
        }
    }

    @Override
    public void callback() {
        if (signPopWindow != null) {
            signPopWindow.dismiss();
            signPopWindow = null;
        }
    }

    /**
     * @Model 获取信息
     */
    private void getInfo() {
        Api.getInstance().getSignInfo()
                .flatMap((Function<BaseBean<SignInfoBean>, Observable<BaseBean<List<TaskInfoBean>>>>) bean -> {
                    if (bean != null) {
                        SignInfoBean data = bean.getData();
                        days = data.getDays();
                        todaySign = data.isTodaySign();
                        List<StepBean> list = new ArrayList<>();
                        int end = days > 7 ? days : 7;
                        for (int i = 1; i <= end; i++) {
                            int status;
                            if (i >= days) {
                                status = 0;
                            } else {
                                status = 1;
                            }

                            if (todaySign && i == days) {
                                status = 1;
                            } else if (!todaySign && i == days - 1) {
                                status = 1;
                            }

                            int icon = 0;
                            if (i == 5) {
                                icon = R.mipmap.sign_5_icon;
                            } else if (i == 7) {
                                icon = R.mipmap.sign_7_icon;
                            }

                            if (icon == 0) {
                                list.add(new StepBean(status, i));
                            } else {
                                list.add(new StepBean(status, i, icon));
                            }
                            if (i > 7) {
                                list.remove(0);
                            }
                        }
                        SignActivity.this.runOnUiThread(() -> {
                            mStepView.setStepNum(list);
                            if (todaySign) {
                                mSignBtn.setText("已连续签到" + days + "天");
                                mSignBtn.setBackgroundColor(Color.parseColor("#AADDDDDD"));
                                mSignBtn.setEnabled(false);
                            } else {
                                mSignBtn.setText("马上签到领币");
                                mSignBtn.setEnabled(true);
                                mSignBtn.setBackgroundResource(R.drawable.selector_btn_login);
                            }
                        });
                    }
                    return Api.getInstance().getTaskInfo();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<TaskInfoBean>>(this) {

                    @Override
                    public void onSuccess(BaseBean<List<TaskInfoBean>> bean) {
                        if (bean != null) {
                            List<TaskInfoBean> list = bean.getData();
                            if (list != null && list.size() >= 3) {
                                boolean b1 = checkTask(list.get(0));//签到
                                boolean b2 = checkTask(list.get(1));//投票
                                boolean b3 = checkTask(list.get(2));//邀请
                                if (b1) {
                                    if (b2) {
                                        set2True();
                                        if (b3) {
                                            set3True();
                                        } else {
                                            set3False();
                                        }
                                    } else {
                                        set2False();
                                        set3Default();
                                    }
                                } else {
                                    set2Default();
                                    set3Default();
                                }
                                int progress = days;
                                if (b2) {
                                    progress += 7;
                                }
                                if (b3) {
                                    progress += 7;
                                }
                                mProgress.setMax(21);
                                mProgress.setProgress(progress);
                            }
                        }
                    }

                    private void set2False() {
                        mVoteBtn.setText("去投票");
                        mVoteBtn.setEnabled(true);
                        mVoteBtn.setBackgroundResource(R.drawable.selector_btn_login);
                    }

                    private void set2Default() {
                        mVoteBtn.setText("去投票");
                        mVoteBtn.setEnabled(false);
                        mVoteBtn.setBackgroundColor(Color.parseColor("#AADDDDDD"));
                    }

                    private void set2True() {
                        mVoteBtn.setText("已完成");
                        mVoteBtn.setEnabled(false);
                        mVoteBtn.setBackgroundColor(Color.parseColor("#AADDDDDD"));
                    }

                    private void set3False() {
                        mInviteBtn.setText("邀请好友");
                        mInviteBtn.setEnabled(true);
                        mInviteBtn.setBackgroundResource(R.drawable.selector_btn_login);
                    }

                    private void set3Default() {
                        mInviteBtn.setText("邀请好友");
                        mInviteBtn.setEnabled(false);
                        mInviteBtn.setBackgroundColor(Color.parseColor("#AADDDDDD"));
                    }

                    private void set3True() {
                        mInviteBtn.setText("已完成");
                        mInviteBtn.setEnabled(false);
                        mInviteBtn.setBackgroundColor(Color.parseColor("#AADDDDDD"));
                    }

                    private boolean checkTask(TaskInfoBean taskInfoBean) {
                        return taskInfoBean.getStatus() == 1;
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
     * @Model 签到
     */
    private void sign() {
        Api.getInstance().sign()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
                            BooleanBean data = bean.getData();
                            if (data != null && data.isResult()) {
                                if (signPopWindow == null || !signPopWindow.isShowing()) {
                                    signPopWindow = SignPopWindow.getInstance(SignActivity.this);
                                    signPopWindow.setCallback(SignActivity.this);
                                    signPopWindow.show();
                                }
                                getInfo();
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

}
