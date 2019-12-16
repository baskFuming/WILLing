package com.xxx.willing.ui.app.activity.gvishop.my.address.pop;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.popup.BasePopup;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 提交订单pop
 * @date 2019-12-05
 */

public class SubmitPop extends BasePopup {

    private Callback callback;

    public SubmitPop(Context context, Callback callback, String number) {
        super(context);
        this.callback = callback;
        this.number = number;
    }

    public static SubmitPop getInstance(Context context, Callback callback, String number) {
        return new SubmitPop(context, callback, number);
    }

    @BindView(R.id.coin_number)
    TextView mNumber;

    private String number;

    @Override
    protected int getLayoutId() {
        return R.layout.submit_pop;
    }

    @Override
    protected void initData() {
        mNumber.setText(number + "");
    }

    @OnClick({R.id.window_close_pop, R.id.window_change})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.window_close_pop:
                dismiss();
                break;
            case R.id.window_change:
                if (callback != null) callback.onCallback();
                break;
        }
    }

    public interface Callback {
        void onCallback();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
