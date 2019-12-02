package com.xxx.willing.ui.wallet.window;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.base.dialog.BaseDialog;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.utils.DownTimeUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PasswordWindow extends BaseDialog {

    private Callback callback;
    private BaseActivity activity;

    public static PasswordWindow getInstance(BaseActivity context) {
        return new PasswordWindow(context);
    }

    @BindView(R.id.window_password_edit)
    public EditText mPasswordEdit;

    private PasswordWindow(BaseActivity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.window_password;
    }

    @Override
    protected void initData() {
        setCancelable(true); // 是否可以按“返回键”消失
    }

    @Override
    protected double setWidth() {
        return 0.8;
    }

    @OnClick({R.id.window_password_btn, R.id.window_password_return})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.window_password_btn:
                String password = mPasswordEdit.getText().toString();
                if (callback != null) callback.callback(password);
                break;
            case R.id.window_password_return:
                KeyBoardUtil.closeKeyBord(getContext(), mPasswordEdit);
                dismiss();
                break;
        }
    }

    public interface Callback {
        void callback(String password);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
