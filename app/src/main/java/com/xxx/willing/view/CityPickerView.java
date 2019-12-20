package com.xxx.willing.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.Interface.OnCustomCityPickerItemClickListener;
import com.lljjcoder.bean.CustomCityData;
import com.lljjcoder.citywheel.CustomConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.widget.CanShow;
import com.lljjcoder.style.citypickerview.widget.wheel.OnWheelChangedListener;
import com.lljjcoder.style.citypickerview.widget.wheel.WheelView;
import com.lljjcoder.style.citypickerview.widget.wheel.adapters.ArrayWheelAdapter;
import com.lljjcoder.utils.utils;

import java.util.List;

public class CityPickerView implements CanShow, OnWheelChangedListener {

    private PopupWindow popwindow;
    private View popview;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Context mContext;
    private RelativeLayout mRelativeTitleBg;
    private TextView mTvOK;
    private TextView mTvTitle;
    private TextView mTvCancel;
    private CustomConfig config;
    private OnCustomCityPickerItemClickListener listener = null;

    public void setOnCustomCityPickerItemClickListener(OnCustomCityPickerItemClickListener listener) {
        this.listener = listener;
    }

    public CityPickerView(Context context) {
        this.mContext = context;
    }

    public void setCustomConfig(CustomConfig config) {
        this.config = config;
    }

    private void initView() {
        if (this.config == null) {
            ToastUtils.showLongToast(this.mContext, "请设置相关的config");
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
            this.popview = layoutInflater.inflate(com.lljjcoder.style.citypickerview.R.layout.pop_citypicker, (ViewGroup) null);
            this.mViewProvince = (WheelView) this.popview.findViewById(com.lljjcoder.style.citypickerview.R.id.id_province);
            this.mViewCity = (WheelView) this.popview.findViewById(com.lljjcoder.style.citypickerview.R.id.id_city);
            this.mViewDistrict = (WheelView) this.popview.findViewById(com.lljjcoder.style.citypickerview.R.id.id_district);
            this.mRelativeTitleBg = (RelativeLayout) this.popview.findViewById(com.lljjcoder.style.citypickerview.R.id.rl_title);
            this.mTvOK = (TextView) this.popview.findViewById(com.lljjcoder.style.citypickerview.R.id.tv_confirm);
            this.mTvTitle = (TextView) this.popview.findViewById(com.lljjcoder.style.citypickerview.R.id.tv_title);
            this.mTvCancel = (TextView) this.popview.findViewById(com.lljjcoder.style.citypickerview.R.id.tv_cancel);
            this.popwindow = new PopupWindow(this.popview, -1, -2);
            this.popwindow.setAnimationStyle(com.lljjcoder.style.citypickerview.R.style.AnimBottom);
            this.popwindow.setBackgroundDrawable(new ColorDrawable());
            this.popwindow.setTouchable(true);
            this.popwindow.setOutsideTouchable(false);
            this.popwindow.setFocusable(true);
            this.popwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                public void onDismiss() {
                    if (CityPickerView.this.config.isShowBackground()) {
                        utils.setBackgroundAlpha(CityPickerView.this.mContext, 1.0F);
                    }

                }
            });
            if (!TextUtils.isEmpty(this.config.getTitleBackgroundColorStr())) {
                if (this.config.getTitleBackgroundColorStr().startsWith("#")) {
                    this.mRelativeTitleBg.setBackgroundColor(Color.parseColor(this.config.getTitleBackgroundColorStr()));
                } else {
                    this.mRelativeTitleBg.setBackgroundColor(Color.parseColor("#" + this.config.getTitleBackgroundColorStr()));
                }
            }

            if (!TextUtils.isEmpty(this.config.getTitle())) {
                this.mTvTitle.setText(this.config.getTitle());
            }

            if (this.config.getTitleTextSize() > 0) {
                this.mTvTitle.setTextSize((float) this.config.getTitleTextSize());
            }

            if (!TextUtils.isEmpty(this.config.getTitleTextColorStr())) {
                if (this.config.getTitleTextColorStr().startsWith("#")) {
                    this.mTvTitle.setTextColor(Color.parseColor(this.config.getTitleTextColorStr()));
                } else {
                    this.mTvTitle.setTextColor(Color.parseColor("#" + this.config.getTitleTextColorStr()));
                }
            }

            if (!TextUtils.isEmpty(this.config.getConfirmTextColorStr())) {
                if (this.config.getConfirmTextColorStr().startsWith("#")) {
                    this.mTvOK.setTextColor(Color.parseColor(this.config.getConfirmTextColorStr()));
                } else {
                    this.mTvOK.setTextColor(Color.parseColor("#" + this.config.getConfirmTextColorStr()));
                }
            }

            if (!TextUtils.isEmpty(this.config.getConfirmText())) {
                this.mTvOK.setText(this.config.getConfirmText());
            }

            if (this.config.getConfirmTextSize() > 0) {
                this.mTvOK.setTextSize((float) this.config.getConfirmTextSize());
            }

            if (!TextUtils.isEmpty(this.config.getCancelTextColorStr())) {
                if (this.config.getCancelTextColorStr().startsWith("#")) {
                    this.mTvCancel.setTextColor(Color.parseColor(this.config.getCancelTextColorStr()));
                } else {
                    this.mTvCancel.setTextColor(Color.parseColor("#" + this.config.getCancelTextColorStr()));
                }
            }

            if (!TextUtils.isEmpty(this.config.getCancelText())) {
                this.mTvCancel.setText(this.config.getCancelText());
            }

            if (this.config.getCancelTextSize() > 0) {
                this.mTvCancel.setTextSize((float) this.config.getCancelTextSize());
            }

            this.mViewProvince.addChangingListener(this);
            this.mViewCity.addChangingListener(this);
            this.mViewDistrict.addChangingListener(this);
            this.mTvCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CityPickerView.this.listener.onCancel();
                    CityPickerView.this.hide();
                }
            });
            this.mTvOK.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pCurrent = CityPickerView.this.mViewProvince.getCurrentItem();
                    int cCurrent = CityPickerView.this.mViewCity.getCurrentItem();
                    int dCurrent = CityPickerView.this.mViewDistrict.getCurrentItem();
                    List<CustomCityData> provinceList = CityPickerView.this.config.getCityDataList();
                    CustomCityData province = (CustomCityData) provinceList.get(pCurrent);
                    List<CustomCityData> cityDataList = province.getList();
                    if (cityDataList != null) {
                        CustomCityData city = (CustomCityData) cityDataList.get(cCurrent);
                        List<CustomCityData> areaList = city.getList();
                        if (areaList != null) {
                            CustomCityData area = (CustomCityData) areaList.get(dCurrent);
                            CityPickerView.this.listener.onSelected(province, city, area);
                            CityPickerView.this.hide();
                        }
                    }
                }
            });
            if (this.config != null && this.config.isShowBackground()) {
                utils.setBackgroundAlpha(this.mContext, 0.5F);
            }

            this.setUpData();
        }
    }

    private void setUpData() {
        List<CustomCityData> proArra = this.config.getCityDataList();
        if (proArra != null) {
            ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter(this.mContext, proArra);
            arrayWheelAdapter.setItemResource(com.lljjcoder.style.citypickerview.R.layout.default_item_city);
            arrayWheelAdapter.setItemTextResource(com.lljjcoder.style.citypickerview.R.id.default_item_city_name_tv);
            this.mViewProvince.setViewAdapter(arrayWheelAdapter);
            this.mViewProvince.setVisibleItems(this.config.getVisibleItems());
            this.mViewCity.setVisibleItems(this.config.getVisibleItems());
            this.mViewDistrict.setVisibleItems(this.config.getVisibleItems());
            this.mViewProvince.setCyclic(this.config.isProvinceCyclic());
            this.mViewCity.setCyclic(this.config.isCityCyclic());
            this.mViewDistrict.setCyclic(this.config.isDistrictCyclic());
            this.mViewProvince.setDrawShadows(this.config.isDrawShadows());
            this.mViewCity.setDrawShadows(this.config.isDrawShadows());
            this.mViewDistrict.setDrawShadows(this.config.isDrawShadows());
            this.mViewProvince.setLineColorStr(this.config.getLineColor());
            this.mViewProvince.setLineWidth(this.config.getLineHeigh());
            this.mViewCity.setLineColorStr(this.config.getLineColor());
            this.mViewCity.setLineWidth(this.config.getLineHeigh());
            this.mViewDistrict.setLineColorStr(this.config.getLineColor());
            this.mViewDistrict.setLineWidth(this.config.getLineHeigh());
            this.updateCities();
            this.updateAreas();
        }
    }

    private void updateCities() {
        int pCurrent = this.mViewProvince.getCurrentItem();
        List<CustomCityData> proArra = this.config.getCityDataList();
        CustomCityData mProvinceBean = (CustomCityData) proArra.get(pCurrent);
        List<CustomCityData> pCityList = mProvinceBean.getList();
        if (pCityList != null) {
            ArrayWheelAdapter cityWheel = new ArrayWheelAdapter(this.mContext, pCityList);
            cityWheel.setItemResource(com.lljjcoder.style.citypickerview.R.layout.default_item_city);
            cityWheel.setItemTextResource(com.lljjcoder.style.citypickerview.R.id.default_item_city_name_tv);
            this.mViewCity.setViewAdapter(cityWheel);
            this.updateAreas();
        }
    }

    private void updateAreas() {
        int pCurrent = this.mViewProvince.getCurrentItem();
        int cCurrent = this.mViewCity.getCurrentItem();
        List<CustomCityData> provinceList = this.config.getCityDataList();
        CustomCityData province = provinceList.get(pCurrent);
        List<CustomCityData> cityDataList = province.getList();
        if (cityDataList != null) {
            CustomCityData city = (CustomCityData) cityDataList.get(cCurrent);
            List<CustomCityData> areaList = city.getList();
            if (areaList != null) {
                ArrayWheelAdapter districtWheel = new ArrayWheelAdapter(this.mContext, areaList);
                districtWheel.setItemResource(com.lljjcoder.style.citypickerview.R.layout.default_item_city);
                districtWheel.setItemTextResource(com.lljjcoder.style.citypickerview.R.id.default_item_city_name_tv);
                this.mViewDistrict.setViewAdapter(districtWheel);
                this.mViewDistrict.setCurrentItem(0);
            }
        }
    }

    public void showCityPicker() {
        this.initView();
        if (!this.isShow()) {
            this.popwindow.showAtLocation(this.popview, 80, 0, 0);
        }

    }

    public void hide() {
        if (this.isShow()) {
            this.popwindow.dismiss();
        }
    }

    public boolean isShow() {
        return popwindow != null && this.popwindow.isShowing();
    }

    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        try {
            if (wheel == this.mViewProvince) {
                this.updateCities();
            } else if (wheel == this.mViewCity) {
                this.updateAreas();
            } else if (wheel == this.mViewDistrict) {
            }
        } catch (Exception e) {
        }
    }

}
