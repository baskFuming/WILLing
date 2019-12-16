package com.xxx.willing.ui.my.activity.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 修改昵称
 * @date 2019-11-27
 */

public class ModifyNameActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, String name) {
        Intent intent = new Intent(activity, ModifyNameActivity.class);
        intent.putExtra("name", name);
        activity.startActivityForResult(intent, UIConfig.REQUEST_CODE);
    }

    public void initBundle() {
        Intent intent = getIntent();
        nickName = intent.getStringExtra("name");
    }

    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.ed_nick_name)
    EditText mModifyName;

    private String nickName;

    @Override
    protected String initTitle() {
        return getString(R.string.modify_nickname);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_name;
    }

    @Override
    protected void initData() {
        initBundle();

        KeyBoardUtil.setFiltersDW(mModifyName);

        if (nickName != null) {
            mModifyName.setText(nickName);
            mModifyName.setSelection(nickName.length());
        }
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(getString(R.string.content_save));
    }

    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content://保存
                String nickName = mModifyName.getText().toString();
                if (nickName.isEmpty()) {
                    ToastUtil.showToast(getString(R.string.modify_name_error_1));
                    return;
                }
                if (nickName.length() < 3) {
                    ToastUtil.showToast(getString(R.string.modify_name_error_2));
                    return;
                }
                upLoadName(nickName);

                break;
        }
    }

    /**
     * @Model 修改昵称
     */
    private void upLoadName(String nickName) {
        Api.getInstance().updateUserInfo(null, nickName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
                            BooleanBean data = bean.getData();
                            if (data != null && data.isResult()) {
                                //发送EventBus 更新首页
                                EventBus.getDefault().post(EventBusConfig.EVENT_NOTICE_USER);

                                Intent intent = new Intent();
                                intent.putExtra("name", nickName);
                                setResult(UIConfig.RESULT_CODE, intent);
                                finish();
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
}
