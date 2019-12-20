package com.xxx.willing.ui.app.activity.gvishop.my.address;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.lljjcoder.Interface.OnCustomCityPickerItemClickListener;
import com.lljjcoder.bean.CustomCityData;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.MatchesConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.MyAddressBean;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
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

    public static void actionStartForResult(Activity activity, int tag, MyAddressBean bean) {
        Intent intent = new Intent(activity, SettingAddressActivity.class);
        intent.putExtra("tag", tag);
        intent.putExtra("bean", bean);
        activity.startActivityForResult(intent, UIConfig.REQUEST_CODE);
    }

    public static void actionStart(Activity activity, int tag) {
        Intent intent = new Intent(activity, SettingAddressActivity.class);
        intent.putExtra("tag", tag);
        activity.startActivityForResult(intent, UIConfig.REQUEST_CODE);
    }

    public void initBundle() {
        Intent intent = getIntent();
        bean = (MyAddressBean) intent.getSerializableExtra("bean");
        if (bean == null) bean = new MyAddressBean();
        UiTag = intent.getIntExtra("tag", 0);
    }

    @BindView(R.id.ed_enter_name)
    EditText mName;
    @BindView(R.id.ed_enter_phone)
    EditText mPhone;
    @BindView(R.id.ed_enter_chose_address)
    TextView mChoseAddress;
    @BindView(R.id.ed_enter_detail_address)
    EditText mDetailAddress;
    @BindView(R.id.switch_button)
    Switch mSwitchButton;
    @BindView(R.id.add_save)
    TextView mBtn;

    private MyAddressBean bean;


    public static final int ADD_TAG = 1;
    public static final int UPDATE_TAG = 2;
    private int address_status = 1;  //默认地址1
    private int UiTag;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();

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

        if (UiTag == UPDATE_TAG) {
            mName.setText(bean.getConsignee());
            mPhone.setText(bean.getPhone());
            mChoseAddress.setText(bean.getProvinces() + bean.getCities() + bean.getCounties());
            mDetailAddress.setText(bean.getAddress());
            mBtn.setText("保存");
        } else {
            mBtn.setText("+添加新地址");
        }
    }

    @OnClick({R.id.add_save, R.id.clean_ed_content, R.id.re_enter_chose_address})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.re_enter_chose_address:
                if (mCustomCityPicker != null)
                    mCustomCityPicker.showCityPicker();
                break;
            case R.id.clean_ed_content:
                mDetailAddress.setText("");
                break;
            case R.id.add_save://地址保存
                switch (UiTag) {
                    case ADD_TAG:
                        saveAddress();
                        break;
                    case UPDATE_TAG:
                        updateAddress();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCustomCityPicker != null) {
            mCustomCityPicker.hide();
            mCustomCityPicker = null;
        }
    }

    //保存地址
    public void saveAddress() {
        String name = mName.getText().toString();
        String phone = mPhone.getText().toString();
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
        if (province == null || city == null || province.isEmpty() || city.isEmpty()) {
            ToastUtil.showToast(getString(R.string.add_error_4));
            showEditError(mChoseAddress);
            return;
        }
        if (cityDetails.isEmpty()) {
            ToastUtil.showToast(getString(R.string.add_error_5));
            showEditError(mDetailAddress);
            return;
        }
        int status = mSwitchButton.isChecked() ? 1 : 0;
        Api.getInstance().addAddress(name, phone, province, city, district, cityDetails, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.add_success));
                        SharedPreferencesUtil.getInstance().saveBoolean(SharedConst.IS_SETTING_ADDRESS, true);

                        Intent intent = new Intent();
                        MyAddressBean myAddressBean = new MyAddressBean();
                        myAddressBean.setProvinces(province);
                        myAddressBean.setCities(city);
                        myAddressBean.setCounties(district);
                        myAddressBean.setAddress(cityDetails);
                        myAddressBean.setPhone(phone);
                        myAddressBean.setConsignee(name);
                        myAddressBean.setStatus(1);
                        intent.putExtra("bean", myAddressBean);
                        setResult(UIConfig.RESULT_CODE);
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

    //设置默认地址
    private void setDefaultAddress() {
        Api.getInstance().setDefaultAddress(address_status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.setting_success));
                        setResult(UIConfig.RESULT_CODE);
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

    //修改地址
    public void updateAddress() {
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
        Api.getInstance().updateAddress(bean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.update_success));
                        setResult(UIConfig.RESULT_CODE);
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
}
