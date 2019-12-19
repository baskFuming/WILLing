package com.xxx.willing.ui.app.activity.gvishop.my.address;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
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

    @BindView(R.id.user_name)   //姓名
            TextView mName;
    @BindView(R.id.user_phone)   //电话
            TextView mPhone;
    @BindView(R.id.detail_address)  //地址
            TextView mDetailsAddress;
    @BindView(R.id.brand_name)    //品牌名
            TextView mBrandName;
    @BindView(R.id.course_type)    //课时类型
            TextView mCourseType;
    @BindView(R.id.course_type_price)//课时卷价位
            TextView CourseTypePrice;
    @BindView(R.id.useful_time)    //有效时间
            TextView mUserTime;
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
    @BindView(R.id.re_onclick_setting)
    RelativeLayout mAddress;

    private int number = 1;
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
                SettingAddressActivity.actionStart(this, SettingAddressActivity.ADD_TAG);
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
                submitPop = SubmitPop.getInstance(this, this::submitOrder, "");
                break;
        }
    }

    /**
     * @Model 提交订单
     */
    private void submitOrder() {

    }
}
