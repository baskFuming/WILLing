package com.xxx.willing.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.AddOrderBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.js.ShopJsVo;
import com.xxx.willing.model.log.LogConst;
import com.xxx.willing.model.log.LogUtil;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.home.ShopWindow;
import com.xxx.willing.ui.app.activity.gvishop.my.address.SettingAddressActivity;
import com.xxx.willing.ui.app.activity.gvishop.my.address.ShipAddressActivity;
import com.xxx.willing.ui.app.activity.gvishop.my.address.pop.SubmitPop;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc GVI商城通用web页面
 * @date 2019-12-13
 */

public class BaseWebShopActivity extends BaseActivity implements SubmitPop.Callback {

    private ShopWindow mShopWindow;
    private SubmitPop mSubmitPop;
    private int orderId;
    private int id;

    public static void actionStart(Activity activity, String title, int id) {
        Intent intent = new Intent(activity, BaseWebShopActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
//        String title = intent.getStringExtra("title");
        id = intent.getIntExtra("id", 0);
    }

    @BindView(R.id.use_help_web)
    WebView mWebView;
    @BindView(R.id.web_progress)
    ProgressBar mProgress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_shop;
    }

    @Override
    protected void initData() {
        showLoading();
        //初始化
        mShopWindow = ShopWindow.getInstance(this);
        mSubmitPop = SubmitPop.getInstance(BaseWebShopActivity.this, this);

        initBundle();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initDate() {
        String token = SharedPreferencesUtil.getInstance().getString(SharedConst.ENCRYPT_VALUE_TOKEN);
        final WebSettings webSetting;
        webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);  //支持js
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);  //支持弹窗
        webSetting.setBlockNetworkImage(false);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        String launcher = SharedPreferencesUtil.getInstance().getString(SharedConst.CONSTANT_LAUNCHER);
        try {
            launcher = launcher.split(",")[1];
        } catch (Exception e) {
            launcher = "zh";
        }
        mWebView.loadUrl(HttpConfig.SHOP_DETAIL_URL + "?language=" + launcher + "&id=" + id + "&token=" + token);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  //接受所有证书
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                try {
                    ShopJsVo shopJsVo = new Gson().fromJson(message, ShopJsVo.class);
                    if (shopJsVo == null) {
                        LogUtil.showLog(LogConst.SHOP_JS_TAG, message);
                        return true;
                    }
                    //手机号输入框弹起
                    if (shopJsVo.getColor() == 0 && shopJsVo.getSizeId() == 0) {
                        if (mShopWindow != null) {
                            mShopWindow.setCallback(phone -> addOrder(shopJsVo, phone));
                            mShopWindow.setType(ShopWindow.TYPE_PHONE_EDIT);
                            mShopWindow.show();
                        }
                        return true;
                    } else {
                        //跳转下单页面
                        ShipAddressActivity.actionStart(BaseWebShopActivity.this, shopJsVo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    if (mProgress != null) {
                        mProgress.setVisibility(View.GONE);
                        hideLoading();
                    }
                } else {
                    if (mProgress != null) {
                        mProgress.setVisibility(View.VISIBLE);
                        mProgress.setProgress(newProgress);
                    }
                }
            }
        });
    }


    /**
     * @Model 下单
     */
    private void addOrder(ShopJsVo shopJsVo, String detail) {
        Api.getInstance()
                .addOrder(shopJsVo.getId(), shopJsVo.getNum(), shopJsVo.getColor(), shopJsVo.getSizeId(), detail, 0)
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
                                    if (mSubmitPop != null) {
                                        ToastUtil.showToast(getString(R.string.add_order_success_1));
                                        mShopWindow.dismiss();
                                        mSubmitPop.setNumber(shopJsVo.getPrice() + "");
                                        mSubmitPop.show();
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
        mSubmitPop.dismiss();
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
                                mShopWindow.setType(ShopWindow.TYPE_PHONE_SUCCESS);
                            } else {
                                mShopWindow.setType(ShopWindow.TYPE_PHONE_FAIL);
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

    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShopWindow != null) {
            mShopWindow.dismiss();
            mShopWindow = null;
        }
        if (mSubmitPop != null) {
            mSubmitPop.dismiss();
            mSubmitPop = null;
        }
        //清空所有Cookie
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        if (mWebView != null) {
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearCache(true);
            mWebView.stopLoading();
            mWebView.destroy();
        }
    }


    @Override
    protected void onPause() {
        mWebView.reload();
        mWebView.onPause();
        super.onPause();
    }
}
