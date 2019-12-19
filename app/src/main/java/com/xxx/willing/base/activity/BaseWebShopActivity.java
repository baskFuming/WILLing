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
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.js.ShopJsVo;
import com.xxx.willing.model.log.LogConst;
import com.xxx.willing.model.log.LogUtil;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.my.address.SettingAddressActivity;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc GVI商城通用web页面
 * @date 2019-12-13
 */

public class BaseWebShopActivity extends BaseActivity {

    public static void actionStart(Activity activity, String title, int id) {
        Intent intent = new Intent(activity, BaseWebShopActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getIntExtra("id", 0);
    }

    @BindView(R.id.use_help_web)
    WebView mWebView;
    @BindView(R.id.web_progress)
    ProgressBar mProgress;
    private String title;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_shop;
    }

    @Override
    protected void initData() {
        showLoading();

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

                        return true;
                    } else {
                        //是否设置过地址
                        if (!SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_SETTING_ADDRESS)) {
                            SettingAddressActivity.actionStart(BaseWebShopActivity.this, SettingAddressActivity.ADD_TAG);
                        }
                        //跳转下单页面

                    }
                    addOrder(shopJsVo, "", 1);
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
    private void addOrder(ShopJsVo shopJsVo, String detail, int addressId) {
        Api.getInstance().addOrder(shopJsVo.getId(), shopJsVo.getNum(), shopJsVo.getColor(), shopJsVo.getSizeId(), detail, addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        ToastUtil.showToast(getString(R.string.update_success));
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


    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
