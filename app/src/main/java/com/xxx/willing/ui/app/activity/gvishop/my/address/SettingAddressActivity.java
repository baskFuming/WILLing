package com.xxx.willing.ui.app.activity.gvishop.my.address;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.lljjcoder.Interface.OnCustomCityPickerItemClickListener;
import com.lljjcoder.bean.CustomCityData;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.MatchesConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.activity.LoginActivity;
import com.xxx.willing.ui.login.activity.RegisterActivity;
import com.xxx.willing.view.CityPickerUtil;
import com.xxx.willing.view.CityPickerView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.disposables.Disposable;


/**
 * @author FM
 * @desc 添加收货地址
 * @date 2019-12-05
 */

public class SettingAddressActivity extends BaseTitleActivity {


    public static void actionStartForResult(Activity activity, int tag) {
        Intent intent = new Intent(activity, SettingAddressActivity.class);
        intent.putExtra("tag", tag);
        activity.startActivityForResult(intent, UIConfig.REQUEST_CODE);
    }

    public static void actionStart(Activity activity, int tag) {
        Intent intent = new Intent(activity, SettingAddressActivity.class);
        intent.putExtra("tag", tag);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
    }

    @BindView(R.id.ed_enter_name)
    EditText mName;
    @BindView(R.id.ed_enter_phone)
    EditText mPhone;
    @BindView(R.id.ed_enter_chose_address)
    EditText mChoseAddress;
    @BindView(R.id.ed_enter_detail_address)
    EditText mDetailAddress;
    @BindView(R.id.switch_button)
    Switch mSwitchButton;


    public static final int ADD_TAG = 1;
    public static final int UPDATE_TAG = 2;

    private String address_id;
    private String city = "";
    private String province = "";
    private String district = "";
    private CityPickerView mCustomCityPicker;

    @Override
    protected String initTitle() {
        return getString(R.string.add_address_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_address;
    }

    @Override
    protected void initData() {
        initBundle();
//        Intent intent = getIntent();
//        UiTag = intent.getIntExtra("tag", 0);
    }

    @OnClick({R.id.add_save, R.id.clean_ed_content, R.id.chose_city_onclick})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.chose_city_onclick:
                //初始化地址选择
                mCustomCityPicker = CityPickerUtil.getInstance(this, mName, new OnCustomCityPickerItemClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSelected(CustomCityData province, CustomCityData city, CustomCityData district) {
                        super.onSelected(province, city, district);
                        //省份
                        SettingAddressActivity.this.province = province.getName();
                        //城市
                        SettingAddressActivity.this.city = city.getName();
                        //区县（如果设定了两级联动，那么该项返回空）
                        SettingAddressActivity.this.district = district.getName();
                        //获取当前地址
                        mChoseAddress.setText(province.getName() + city.getName() + district.getName());
                    }
                });
                break;
            case R.id.clean_ed_content:
                mDetailAddress.setText("");
                break;
            case R.id.add_save://地址保存
                mSwitchButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    if (isChecked) {
                        setDefaultAddress();
                    } else {
                        saveAddress();
                    }
                });
                break;
        }
    }

    //保存地址
    public void saveAddress() {
        String name = mName.getText().toString();
        String phone = mPhone.getText().toString();
        String city = mChoseAddress.getText().toString();
        String cityDetails = mDetailAddress.getText().toString();
        if (name.isEmpty()) {
            showEditError(mName);
            ToastUtil.showToast(getString(R.string.add_error_1));
            return;
        }

        if (phone.isEmpty()) {
            showEditError(mPhone);
            ToastUtil.showToast(getString(R.string.add_error_2));
            return;
        }
        if (!phone.matches(MatchesConfig.MATCHES_PHONE)) {
            ToastUtil.showToast(getString(R.string.login_error_phone_2));
            showEditError(mPhone);
            return;
        }
        if (city.isEmpty()) {
            ToastUtil.showToast(getString(R.string.add_error_4));
            showEditError(mChoseAddress);
            return;
        }
        if (cityDetails.isEmpty()) {
            ToastUtil.showToast(getString(R.string.add_error_5));
            showEditError(mDetailAddress);
            return;
        }
        Api.getInstance().addAddress(name, phone, province, this.city, district, cityDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.add_success));
//                        Intent intent = new Intent(SettingAddressActivity.this, "");
//                        setResult(UIConfig.RESULT_CODE, intent);
//                        finish();
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
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideLoading();
                    }
                });

    }

    //设置默认地址
    private void setDefaultAddress() {
        Api.getInstance().setDefaultAddress(address_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.setting_success));

//                        Intent intent = new Intent(SettingAddressActivity.this, "");
//                        setResult(UIConfig.RESULT_CODE, intent);
//                        finish();
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
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideLoading();
                    }
                });
    }
}
