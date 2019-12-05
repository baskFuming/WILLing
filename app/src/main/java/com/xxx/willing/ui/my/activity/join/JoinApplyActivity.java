package com.xxx.willing.ui.my.activity.join;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.JoinInfoBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.utils.ApiCode;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.activity.PasswordSettingActivity;
import com.xxx.willing.ui.wallet.activity.WithdrawalActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 加盟申请
 * @date 2019-11-28
 */

public class JoinApplyActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, JoinApplyActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.ed_join_name)   //加盟商名称
            EditText mJoinName;
    @BindView(R.id.ed_join_rand)   //品牌
            TextView mJoinRand;
    @BindView(R.id.ed_join_time)   //周期
            TextView mJoinTime;
    @BindView(R.id.ed_join_address) //地址
            TextView mAddress;
    @BindView(R.id.ed_join_lines)   //额度
            EditText mJoinLines;
    @BindView(R.id.ed_join_introduce)  //项目简介
            EditText mJoinIntroduce;
    @BindView(R.id.ed_join_ed_number)   //简介长度
            TextView mNumber1;
    @BindView(R.id.ed_join_role)    //角色
            EditText mJoinRole;
    @BindView(R.id.ed_join_user_name)  //姓名
            EditText mName;
    @BindView(R.id.ed_join_phone)     //电话
            EditText mPhone;
    @BindView(R.id.ed_join_person_introduce)  //用户简介
            EditText mJoinPerIntroduce;
    @BindView(R.id.ed_join_ed_person_number)   //简介
            TextView mNumber2;
    @BindView(R.id.ed_check)   //简介
            CheckBox mCheck;

    private List<Object> mBrandList = new ArrayList<>();//品牌集合
    private List<Object> mTimeList = new ArrayList<>(); //加盟周期
    private PopupMenu mCheckBrandMenu;
    private PopupMenu mCheckTimeMenu;
    private int checkTimeId;
    private int checkBrandId;

    @Override
    protected String initTitle() {
        return getString(R.string.my_join);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_applay;
    }

    @Override
    protected void initData() {
        getJoinInfo();
    }

    @OnTextChanged({R.id.ed_join_introduce, R.id.ed_join_person_introduce})
    public void OnTextChanged(CharSequence charSequence) {
        int length = charSequence.length();
        mNumber1.setText(length + "/500");
        mNumber2.setText(length + "/500");

    }

    @OnClick({R.id.ed_agree_click, R.id.re_click_card, R.id.ed_join_trans_photo, R.id.ed_join_rand,R.id.ed_join_time})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ed_agree_click://结盟协议
                JoinWebActivity.actionStart(this);
                break;
            case R.id.re_click_card://证件照上传
                UpdateCardActivity.actionStart(this);
                break;
            case R.id.ed_join_trans_photo: //上传图片
                UpdatePhotoActivity.actionStart(this);
                break;
            case R.id.ed_join_rand:
                if (mCheckBrandMenu != null) {
                    mCheckBrandMenu.show();
                }
                break;
            case R.id.ed_join_time:
                if (mCheckTimeMenu != null) {
                    mCheckTimeMenu.show();
                }
                break;
        }
    }

    /**
     * @Model 申请加盟
     */
    private void submitJoin() {
        String joinName = mJoinName.getText().toString();
        if (joinName.isEmpty()) {
            ToastUtil.showToast("加盟商名称不能为空");
            showEditError(mJoinName);
            return;
        }
        if (checkBrandId == 0) {
            ToastUtil.showToast("请选择品牌");
            showEditError(mJoinRand);
            return;
        }
        if (checkTimeId == 0) {
            ToastUtil.showToast("请选择加盟周期");
            showEditError(mJoinTime);
            return;
        }

        Api.getInstance().submitJoin(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {

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
     * @Model 获取加盟信息
     */
    private void getJoinInfo() {
        if (true) {
            for (int i = 0; i < 10; i++) {
                mBrandList.add("修学");
                mTimeList.add("1年");
            }
            initCheckBrandMenu();
            initCheckTimeMenu();
            return;
        }
        Api.getInstance().getJoinInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<JoinInfoBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<JoinInfoBean> bean) {
                        initCheckBrandMenu();
                        initCheckTimeMenu();
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


    //初始化选择品牌
    private void initCheckBrandMenu() {
        mCheckBrandMenu = new PopupMenu(this, mJoinRand);
        Menu menu = mCheckBrandMenu.getMenu();
        for (int i = 0; i < mBrandList.size(); i++) {
            menu.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, (String) mBrandList.get(i));
        }
        mCheckBrandMenu.setOnMenuItemClickListener(menuItem -> {
            Object bean = mBrandList.get(menuItem.getItemId() - 1);
            checkBrandId = 1;
            String time = "";
            mJoinRand.setText(time);
            return false;
        });
    }

    //初始化选择品牌
    private void initCheckTimeMenu() {
        mCheckTimeMenu = new PopupMenu(this, mJoinTime);
        Menu menu = mCheckTimeMenu.getMenu();
        for (int i = 0; i < mTimeList.size(); i++) {
            menu.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, (String) mTimeList.get(i));
        }
        mCheckTimeMenu.setOnMenuItemClickListener(menuItem -> {
            Object bean = mTimeList.get(menuItem.getItemId() - 1);
            checkTimeId = 1;
            String time = "";
            mJoinTime.setText(time);
            return false;
        });
    }


}
