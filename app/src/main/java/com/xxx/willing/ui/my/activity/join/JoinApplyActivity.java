package com.xxx.willing.ui.my.activity.join;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.Interface.OnCustomCityPickerItemClickListener;
import com.lljjcoder.bean.CustomCityData;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.JoinInfoBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.utils.ApiCode;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.activity.PasswordSettingActivity;
import com.xxx.willing.ui.wallet.activity.WithdrawalActivity;
import com.xxx.willing.view.CityPickerUtil;
import com.xxx.willing.view.CityPickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

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
    @BindView(R.id.join_photo_add)     //形象照
            ImageView mPhoto;
    @BindView(R.id.ed_join_person_introduce)  //用户简介
            EditText mJoinPerIntroduce;
    @BindView(R.id.ed_join_ed_person_number)   //简介
            TextView mNumber2;
    @BindView(R.id.ed_check)   //简介
            CheckBox mCheck;
    @BindView(R.id.ed_join_card)   //证件照片
            TextView mCard;
    @BindView(R.id.ed_join_trans_photo)   //宣传照片
            TextView mPhoto2;

    private PopupMenu mCheckBrandMenu;
    private PopupMenu mCheckTimeMenu;
    private CityPickerView mCityPickerView;

    private List<Object> mBrandList = new ArrayList<>();//品牌集合
    private List<Object> mTimeList = new ArrayList<>(); //加盟周期
    private JoinEntry joinEntry;
    private int checkTimeId;
    private int checkBrandId;
    private String province;
    private String city;
    private String district;

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
        //初始化身份证实体类
        joinEntry = new JoinEntry();

        //初始化地址选择
        mCityPickerView = CityPickerUtil.getInstance(this, mJoinIntroduce, new OnCustomCityPickerItemClickListener() {
            @Override
            public void onSelected(CustomCityData province, CustomCityData city, CustomCityData district) {
                JoinApplyActivity.this.province = province.getName();
                JoinApplyActivity.this.city = city.getName();
                JoinApplyActivity.this.district = district.getName();

                String address = JoinApplyActivity.this.province + JoinApplyActivity.this.city + JoinApplyActivity.this.district;
                mAddress.setText(address);
                super.onSelected(province, city, district);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }
        });

        getJoinInfo();
    }

    @SuppressLint("SetTextI18n")
    @OnTextChanged({R.id.ed_join_introduce})
    public void OnTextChanged(CharSequence charSequence) {
        int length = charSequence.length();
        mNumber1.setText(length + "/500");
    }

    @SuppressLint("SetTextI18n")
    @OnTextChanged({R.id.ed_join_person_introduce})
    public void OnTextChanged1(CharSequence charSequence) {
        int length = charSequence.length();
        mNumber2.setText(length + "/500");
    }

    @OnClick({R.id.ed_submit, R.id.ed_agree_click, R.id.re_click_address, R.id.re_click_card, R.id.ed_join_trans_photo, R.id.ed_join_rand, R.id.ed_join_time})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ed_agree_click://结盟协议
                BaseWebActivity.actionStart(this, HttpConfig.JOIN_HELP_URL, getString(R.string.join_agree_web));
                break;
            case R.id.re_click_address://选择地址
                if (mCityPickerView != null) {
                    mCityPickerView.showCityPicker();
                }
                break;
            case R.id.re_click_card://证件照上传
                UpdateCardActivity.actionStart(this, joinEntry);
                break;
            case R.id.ed_join_trans_photo: //上传图片
                UpdatePhotoActivity.actionStart(this, joinEntry);
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
            case R.id.ed_submit:
                submitJoin();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UIConfig.RESULT_CODE) {
            if (joinEntry.getFileHand() != null && joinEntry.getFileReverse() != null && joinEntry.getFilePositive() != null) {
                mCard.setText("上传完成");
            } else {
                mCard.setText("点击上传");
            }
            if (joinEntry.getFileList() == null || joinEntry.getFileList().size() <= 0) {
                mPhoto2.setText("上传完成");
            } else {
                mPhoto2.setText("点击上传");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCityPickerView != null) {
            mCityPickerView = null;
        }
        if (mCheckBrandMenu != null) {
            mCheckBrandMenu.dismiss();
            mCheckBrandMenu = null;
        }
        if (mCheckTimeMenu != null) {
            mCheckTimeMenu.dismiss();
            mCheckTimeMenu = null;
        }
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
            String time = (String) bean;
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
            String time = (String) bean;
            mJoinTime.setText(time);
            return false;
        });
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
        String name = mJoinName.getText().toString();
        if (name.isEmpty()) {
            showEditError(mJoinName);
            ToastUtil.showToast("加盟商");
            return;
        }
        if (province.isEmpty() || city.isEmpty()) {
            showEditError(mAddress);
            ToastUtil.showToast("地址不能为空");
            return;
        }
        String joinIntroduce = mJoinIntroduce.getText().toString();
        if (joinIntroduce.isEmpty()) {
            showEditError(mJoinName);
            ToastUtil.showToast("节点介绍不能为空");
            return;
        }
        String personnelRole = mJoinRole.getText().toString();
        if (personnelRole.isEmpty()) {
            showEditError(mJoinRole);
            ToastUtil.showToast("人员角色不能为空");
            return;
        }
        String personnelName = mName.getText().toString();
        if (personnelName.isEmpty()) {
            showEditError(mName);
            ToastUtil.showToast("姓名不能为空");
            return;
        }
        String phone = mPhone.getText().toString();
        if (phone.isEmpty()) {
            showEditError(mPhone);
            ToastUtil.showToast("联系电话不能为空");
            return;
        }
        if (joinEntry.getFilePhoto() == null) {
            showEditError(mPhone);
            ToastUtil.showToast("请上传形象照片");
            return;
        }
        if (joinEntry.getFileHand() == null || joinEntry.getFileReverse() == null || joinEntry.getFilePositive() == null) {
            showEditError(mCard);
            ToastUtil.showToast("请先上传证件照片");
            return;
        }
        String perIntroduce = mJoinPerIntroduce.getText().toString();
        if (perIntroduce.isEmpty()) {
            showEditError(mJoinPerIntroduce);
            ToastUtil.showToast("简介不能为空");
            return;
        }
        if (joinEntry.getFileList() == null || joinEntry.getFileList().size() <= 0) {
            showEditError(mPhoto2);
            ToastUtil.showToast("请先上传宣传照片");
            return;
        }
        boolean checked = mCheck.isChecked();
        if (checked) {
            showEditError(mCheck);
            ToastUtil.showToast("请先阅读协议");
            return;
        }

        List<String> list = new ArrayList<>();
        List<File> fileList = joinEntry.getFileList();
        list.add(joinEntry.getFileHand().getName());
        list.add(joinEntry.getFilePositive().getName());
        list.add(joinEntry.getFileReverse().getName());
        list.add(joinEntry.getFilePhoto().getName());
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            list.add(file.getName());
        }
        Api.getInstance().upLoadPhotoMap(Api.getMapFileRequestBody(list))
                .flatMap(new Function<BaseBean<Map<String, String>>, ObservableSource<BaseBean<BooleanBean>>>() {
                    @Override
                    public ObservableSource<BaseBean<BooleanBean>> apply(BaseBean<Map<String, String>> mapBaseBean) {

                        return Api.getInstance().submitJoin(1);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<BooleanBean> data) {
                        finish();
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
            for (int i = 0; i < 5; i++) {
                mBrandList.add("腾讯" + i);
                mTimeList.add(i + "个月");
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

}
