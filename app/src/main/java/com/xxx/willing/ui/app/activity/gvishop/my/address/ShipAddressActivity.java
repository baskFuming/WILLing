package com.xxx.willing.ui.app.activity.gvishop.my.address;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebShopActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.AddOrderBean;
import com.xxx.willing.model.http.bean.CommodityDetailBean;
import com.xxx.willing.model.http.bean.GviBean;
import com.xxx.willing.model.http.bean.MyAddressBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.http.js.ShopJsVo;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.home.ShopWindow;
import com.xxx.willing.ui.app.activity.gvishop.my.address.pop.SubmitPop;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 收货地址
 * @date 2019-12-05
 */

public class ShipAddressActivity extends BaseTitleActivity implements SubmitPop.Callback {

    private int orderId;
    private int addressId;
    private int commodityId;
    private ShopWindow mShopWindow;
    private MyAddressBean myAddressBean;
    private ShopJsVo shopJsVo;
    private int number = 1;
    private SubmitPop submitPop;
    private double gviPrice;
    private double freight;

    public static void actionStart(Activity activity, int commodityId, ShopJsVo shopJsVo) {
        Intent intent = new Intent(activity, ShipAddressActivity.class);
        intent.putExtra("bean", shopJsVo);
        intent.putExtra("commodityId", commodityId);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        shopJsVo = (ShopJsVo) intent.getSerializableExtra("bean");
        commodityId = intent.getIntExtra("commodityId", 0);
        if (shopJsVo == null) shopJsVo = new ShopJsVo();
    }

    @BindView(R.id.user_name)   //姓名
            TextView mName;
    @BindView(R.id.user_phone)   //电话
            TextView mPhone;
    @BindView(R.id.detail_address)  //地址
            TextView mDetailsAddress;
    @BindView(R.id.default_address)  //默认地址
            TextView mDefaultAddress;

    @BindView(R.id.brand_img)    //品牌名
            ImageView mBrandImage;
    @BindView(R.id.brand_name)    //品牌名
            TextView mBrandName;
    @BindView(R.id.course_type)    //课时类型
            TextView mCourseType;
    @BindView(R.id.course_type_price)//课时卷价位
            TextView CourseTypePrice;
    @BindView(R.id.useful_time)    //有效时间
            TextView mUserTime;
    @BindView(R.id.useful_time_li)    //有效时间
            LinearLayout mUserTimeLi;
    @BindView(R.id.te_count)      //购买数量
            TextView mNumber;
    @BindView(R.id.send_type)    //配送方式
            TextView mSendType;
    @BindView(R.id.send_class)   //免邮
            TextView mSendClass;
    @BindView(R.id.yu_action)    //优惠
            TextView mPreferential;
    @BindView(R.id.ed_remark_content)//备注
            EditText mRemark;
    @BindView(R.id.buy_number)//件数
            TextView mBuyNumber;
    @BindView(R.id.shop_price_all)//总价
            TextView mTotalprice;
    @BindView(R.id.re_onclick_setting)
    RelativeLayout mAddress;

    @Override
    protected String initTitle() {
        return getString(R.string.address_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected void initData() {
        initBundle();

        //初始化
        mShopWindow = ShopWindow.getInstance(this);
        submitPop = SubmitPop.getInstance(this, this);

        //获取商品详情
        getCommodityDetail();

        //是否设置过地址
        if (!SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_SETTING_ADDRESS)) {
            SettingAddressActivity.actionStart(this, SettingAddressActivity.ADD_TAG);
        } else {
            getDefaultAddress();
        }
    }

    @OnClick({R.id.count_reduce, R.id.count_add, R.id.submit_order, R.id.re_onclick_setting})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.re_onclick_setting://选择地址
                MyAddressManagerActivity.actionStartResult(this);
                break;
            case R.id.count_reduce:
                if (number > 1) {
                    number--;
                }
                mNumber.setText(String.valueOf(number));
                mBuyNumber.setText("共" + number + "件");
                mTotalprice.setText("￥" + (number * gviPrice + freight));
                break;
            case R.id.count_add:
                number++;
                mNumber.setText(String.valueOf(number));
                mBuyNumber.setText("共" + number + "件");
                mTotalprice.setText("￥" + (number * gviPrice + freight));
                break;
            case R.id.submit_order:
                addOrder();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UIConfig.RESULT_CODE && data != null) {
            Serializable serializable = data.getSerializableExtra("bean");
            if (serializable != null) {
                myAddressBean = ((MyAddressBean) serializable);
                setDefaultAddressUI();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShopWindow != null) {
            mShopWindow.dismiss();
            mShopWindow = null;
        }
        if (submitPop != null) {
            submitPop.dismiss();
            submitPop = null;
        }
    }

    private void setDefaultAddressUI() {
        if (myAddressBean != null) {
            addressId = myAddressBean.getId();
            String address = myAddressBean.getProvinces() + myAddressBean.getCities() + myAddressBean.getConsignee() + myAddressBean.getAddress();
            String name = myAddressBean.getConsignee();
            String phone = myAddressBean.getPhone();
            boolean isDefault = myAddressBean.getStatus() == 1;
            mName.setText(name);
            mPhone.setText(phone);
            mDetailsAddress.setText(address);
            if (isDefault) {
                mDefaultAddress.setVisibility(View.VISIBLE);
            } else {
                mDefaultAddress.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @Model 获取商品详情
     */
    private void getCommodityDetail() {
        Api.getInstance().getCommodityDetail(commodityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<CommodityDetailBean>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(BaseBean<CommodityDetailBean> bean) {
                        if (bean != null) {
                            CommodityDetailBean data = bean.getData();
                            if (data != null) {
                                CommodityDetailBean.CommodityBean commodity = data.getCommodity();
                                CommodityDetailBean.ShopBrandBean shopBrand = data.getShopBrand();
                                gviPrice = commodity.getGviPrice();
                                freight = commodity.getFreight();
                                number = shopJsVo.getNum();

                                try {
                                    JSONArray jsonArray = new JSONArray(commodity.getImg());
                                    GlideUrlUtil.loadFillet(ShipAddressActivity
                                            .this, jsonArray.getString(0), R.mipmap.vote_banner_default, mBrandImage);
                                } catch (Exception ignored) {
                                }

                                mBrandName.setText(commodity.getName());
                                mCourseType.setText(shopBrand.getName());
                                CourseTypePrice.setText(commodity.getGviPrice() + "GVI");
                                String timeValidity = commodity.getTimeValidity();
                                if (timeValidity == null || timeValidity.isEmpty()) {
                                    mUserTimeLi.setVisibility(View.GONE);
                                } else {
                                    mUserTimeLi.setVisibility(View.VISIBLE);
                                    mUserTime.setText(timeValidity);
                                }
                                mNumber.setText(number + "");

                                //快递方式
                                mSendType.setText(commodity.getExpress());
                                if (commodity.getFreight() == 0) {
                                    mSendClass.setText("免邮");
                                } else {
                                    mSendClass.setText(commodity.getFreight() + "GVI");
                                }
                                mPreferential.setText(commodity.getDiscount());
                                mBuyNumber.setText("共" + number + "件");
                                mTotalprice.setText("￥" + (number * gviPrice + freight));
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
     * @Model 获取默认地址
     */
    private void getDefaultAddress() {
        Api.getInstance().getDefaultAddress(1, 1, 100000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<MyAddressBean>>(this) {
                    @Override
                    public void onSuccess(BaseBean<PageBean<MyAddressBean>> bean) {
                        if (bean != null) {
                            PageBean<MyAddressBean> data = bean.getData();
                            if (data != null) {
                                List<MyAddressBean> list = data.getList();
                                if (list != null && list.size() != 0) {
                                    myAddressBean = list.get(0);
                                    setDefaultAddressUI();
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
     * @Model 下单
     */
    private void addOrder() {
        if (addressId == 0) {
            ToastUtil.showToast(getString(R.string.ship_address_error_1));
            return;
        }
        String remark = mRemark.getText().toString();
        Api.getInstance()
                .addOrder(shopJsVo.getId(), shopJsVo.getNum(), shopJsVo.getColor(), shopJsVo.getSizeId(), "", addressId, remark)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AddOrderBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<AddOrderBean> bean) {
                        if (bean != null) {
                            AddOrderBean data = bean.getData();
                            if (data != null) {
                                orderId = data.getOrderId();
                                if (orderId != 0) {
                                    //先确认付款
                                    if (submitPop != null) {
                                        ToastUtil.showToast(getString(R.string.add_order_success_1));
                                        submitPop.dismiss();
                                        submitPop.setNumber(shopJsVo.getPrice() + "");
                                        submitPop.show();
                                    }
                                } else {
                                    ToastUtil.showToast(getString(R.string.add_order_fail));
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

    @Override
    public void onCallback() {
        submitPop.dismiss();
        paymentOrder();
    }

    /**
     * @Model 支付
     */
    private void paymentOrder() {
        Api.getInstance().paymentOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
                            BooleanBean data = bean.getData();
                            if (data != null && data.isResult()) {
                                mShopWindow.setType(ShopWindow.TYPE_SHOP_SUCCESS);
                            } else {
                                mShopWindow.setType(ShopWindow.TYPE_SHOP_FAIL);
                            }
                            mShopWindow.show();
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
