package com.xxx.willing.base.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.xxx.willing.base.dialog.LoadingDialog;
import com.xxx.willing.model.utils.EditTextShakeHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends BaseLanguageActivity {

    private ImmersionBar mImmersionBar;
    private boolean isShowData;  //用于绑定Activity与网络请求的生命周期是否可以加载数据
    private Unbinder unbinder;
    private EditTextShakeHelper editTextShakeHelper;
    public LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //添加到AppManager中
        ActivityManager.getInstance().addActivity(this);

        //初始化ButterKnife
        unbinder = ButterKnife.bind(this);

        //可以加载数据
        isShowData = true;

        //沉浸式状态栏
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

        //初始化输入框错误抖动
        editTextShakeHelper = new EditTextShakeHelper(this);

        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //初始化弹框
        mLoadingDialog = LoadingDialog.getInstance(this);

        //初始化数据
        initData();
    }

    //是否可以展示数据
    public boolean isShowData() {
        return isShowData;
    }


    public void showLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    public void showEditError(View... views) {
        if (editTextShakeHelper != null) {
            editTextShakeHelper.shake(views);
        }
    }
    @Override
    public void finish() {
        super.finish();
        ActivityManager.getInstance().finishActivity(this);
    }

    @Override
    protected void onDestroy() {
        isShowData = false;
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
            mImmersionBar = null;
        }
        if (editTextShakeHelper != null) {
            editTextShakeHelper = null;
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);

    }

    //获取到Layout的ID
    protected abstract int getLayoutId();

    //初始化数据
    protected abstract void initData();

}
