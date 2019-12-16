package com.xxx.willing.ui.my.activity.sign;

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
    private Integer signProgress;

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
                    //执行签到动画
                    mStepView.startSignAnimation(() -> {
                        if (signPopWindow == null || !signPopWindow.isShowing()) {
                            signPopWindow = SignPopWindow.getInstance(this);
                            signPopWindow.setCallback(this);
                            signPopWindow.show();
                        }
                    });
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
        sign();
    }

    /**
     * @Model 获取信息
     */
    private void getInfo() {
        Api.getInstance().getSignInfo()
                .flatMap((Function<BaseBean<SignInfoBean>, Observable<BaseBean<List<TaskInfoBean>>>>) bean -> {
                    if (bean != null) {
                        SignInfoBean data = bean.getData();
                        if (data != null) {
                            List<SignInfoBean.ListBean> list = data.getList();
                            if (list != null) {
                                List<StepBean> list1 = new ArrayList<>();
                                for (SignInfoBean.ListBean listBean : list) {
                                    if (listBean.getDay() == 5) {
                                        list1.add(new StepBean(listBean.getStatus(), listBean.getDay(), R.mipmap.sign_5_icon));
                                    } else if (listBean.getDay() == 7) {
                                        list1.add(new StepBean(listBean.getStatus(), listBean.getDay(), R.mipmap.sign_7_icon));
                                    } else {
                                        list1.add(new StepBean(listBean.getStatus(), listBean.getDay()));
                                    }
                                }
                                mStepView.setStepNum(list1);
                            }
                            todaySign = data.isTodaySign();
                            if (todaySign) {
                                mSignBtn.setText("已连续签到" + signProgress + "天");
                                mSignBtn.setBackgroundColor(Color.parseColor("#AADDDDDD"));
                                mSignBtn.setEnabled(false);
                            } else {
                                mSignBtn.setText("马上签到领币");
                                mSignBtn.setEnabled(true);
                                mSignBtn.setBackgroundResource(R.drawable.selector_btn_login);
                            }
                            signProgress = data.getSignProgress();
                        }
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
                            if (list != null) {
                                TaskInfoBean taskInfoBean = list.get(1);//投票
                                TaskInfoBean taskInfoBean1 = list.get(2);  //邀请

                                if (taskInfoBean.getStatus() == 1) {
                                    mInviteBtn.setText("已完成");
                                    if (taskInfoBean1.getStatus() == 1) {
                                        mVoteBtn.setText("已完成");
                                    } else {
                                        mVoteBtn.setText("去投票");
                                    }
                                } else {
                                    mVoteBtn.setText("去投票");
                                    mVoteBtn.setEnabled(false);
                                    mInviteBtn.setText("邀请好友");
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
                                ToastUtil.showToast("签到成功");
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

    /**
     * @Model 任务
     */
    private void task(int taskId) {
        Api.getInstance().task(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
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
