package com.xxx.willing.ui.app.activity.gvishop.my.address;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.ui.app.activity.gvishop.my.address.pop.SubmitPop;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 收货地址
 * @date 2019-12-05
 */

public class ShipAddressActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ShipAddressActivity.class);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
    }

    private int number = 1;
    @BindView(R.id.te_count)
    TextView mNumber;
    @BindView(R.id.re_onclick_setting)
    RelativeLayout mAddress;
    private SubmitPop submitPop;

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
    }

    @OnClick({R.id.count_reduce, R.id.count_add, R.id.submit_order, R.id.re_onclick_setting})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.re_onclick_setting://无地址 设置地址
                SettingAddressActivity.actionStartForResult(this, SettingAddressActivity.UPDATE_TAG);
                break;
            case R.id.count_reduce:
                if (number > 1) {
                    number--;
                }
                mNumber.setText(String.valueOf(number));
                break;
            case R.id.count_add:
                number++;
                mNumber.setText(String.valueOf(number));
                break;
            case R.id.submit_order:
                submitPop = SubmitPop.getInstance(this, () -> {
                    submitOrder();
                }, "");

                break;
        }
    }

    /**
     * @Model 提交订单
     */
    private void submitOrder() {
    }
}
