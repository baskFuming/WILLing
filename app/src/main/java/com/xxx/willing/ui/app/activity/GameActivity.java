package com.xxx.willing.ui.app.activity;

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
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.GameBean;
import com.xxx.willing.model.http.bean.MemberAssetBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 游戏
 * @date 2019-12-03
 */

public class GameActivity extends BaseActivity {

    public static void actionStart(Activity activity, GameBean bean) {
        Intent intent = new Intent(activity, GameActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        bean = (GameBean) intent.getSerializableExtra("bean");
        if (bean == null) bean = new GameBean();
    }

    @BindView(R.id.web_progress)
    ProgressBar mProgress;
    @BindView(R.id.game_web)
    WebView mWebView;

    private GameBean bean;
    private int userId;
    private String Url;
    private String restart = null;
    private String score = null;
    private int gameid;
    private String funName;
    private String gameName;
    private String amount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
        initBundle();
        userId = SharedPreferencesUtil.getInstance().getInt(SharedConst.VALUE_USER_ID);
        gameid = bean.getId();
        gameName = bean.getName();

        //加载WebView
        Url = bean.getGameUrl() + "?" + "userId=" + userId + "&gameId=" + bean.getId() + "&baseUrl=" + HttpConfig.BASE_URL;
        final WebSettings webSetting;
        webSetting = mWebView.getSettings();
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        //设置支持缩放
        webSetting.setUseWideViewPort(true); //设置加载进来的页面自适应手机屏幕（可缩放）
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setJavaScriptEnabled(true);  //支持js
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);  //支持弹窗
        webSetting.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSetting.setMediaPlaybackRequiresUserGesture(false);
        }
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.loadUrl(Url);
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
        //接收相应事件
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    if (mProgress != null) {
                        mProgress.setVisibility(View.GONE);
                        getMemberAsset();
                    }
                } else {
                    if (mProgress != null) {
                        mProgress.setVisibility(View.VISIBLE);
                        mProgress.setProgress(newProgress);
                    }
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                JSONObject json = null;
                try {
                    json = new JSONObject(message);
                    restart = json.getString("restart");
                    score = json.getString("score");
                    if (score.equals("0")) {
                        //开始
                        if (restart.equals("0")) {
                            funName = "startOn";
                        } else {
                            funName = "again";
                        }
                        getStartGame();
                    } else if (score.equals("no")) {
                        //一次没打
                        score = "0";
                        getUpdateScore();
                    } else {
                        //结果
                        getUpdateScore();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                result.cancel();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return true;
            }
        });

    }

    /**
     * @model 获取用用户资产
     */
    private void getMemberAsset() {
        userId = SharedPreferencesUtil.getInstance().getInt(SharedConst.VALUE_USER_ID);
        Api.getInstance().getMemberAsset(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<MemberAssetBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<MemberAssetBean> bean) {
                        amount = String.valueOf(bean.getData().getAmount());
                        String funCount = "javascript:ctNumber('" + amount + "')";
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                            mWebView.loadUrl(funCount);
                        } else {
                            mWebView.evaluateJavascript(funCount, value -> {
                                //此处为 js 返回的结果
                            });
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }

    /**
     * @model 开始游戏
     */
    private void getStartGame() {
        userId = SharedPreferencesUtil.getInstance().getInt(SharedConst.VALUE_USER_ID);
        Api.getInstance().startGame(userId, gameid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean.getData().isResult()) {
                            String funName;
                            if (restart.equals("1")) {
                                funName = "startOn";
                            } else {
                                funName = "again";
                            }
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                                mWebView.loadUrl("javascript:" + funName + "(" + true + ")");
                            } else {
                                mWebView.evaluateJavascript("javascript:" + funName + "(" + true + ")", value -> {
                                    //此处为 js 返回的结果
                                });
                            }
                        } else {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                                mWebView.loadUrl("javascript:" + funName + "(" + false + ")");
                            } else {
                                mWebView.evaluateJavascript("javascript:" + funName + "(" + false + ")", value -> {
                                    //此处为 js 返回的结果
                                });
                            }
                        }
                        getMemberAsset();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (errorCode == -1079) {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                                mWebView.loadUrl("javascript:" + funName + "(" + false + ")");
                            } else {
                                mWebView.evaluateJavascript("javascript:" + funName + "(" + false + ")", value -> {
                                    //此处为 js 返回的结果
                                });
                            }
                        }
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }

    /**
     * @model 更新比赛得分
     */
    private void getUpdateScore() {
        userId = SharedPreferencesUtil.getInstance().getInt(SharedConst.VALUE_USER_ID);
        Api.getInstance().updateScore(userId, gameid, score)
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

                });
    }


    @Override
    protected void onResume() {
        if (mWebView != null) {
            mWebView.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        mWebView.reload();
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(this);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
        if (mWebView != null) {
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearCache(true);
            mWebView.stopLoading();
            mWebView.destroy();
        }
    }
}
