package com.xxx.willing.ui.wallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.StringUtil;
import com.xxx.willing.model.utils.ZXingUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Page 充值页
 * @Author xxx
 */
public class RechargeActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, String address, String symbol) {
        Intent intent = new Intent(activity, RechargeActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("symbol", symbol);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        symbol = intent.getStringExtra("symbol");
    }

    @BindView(R.id.recharge_image)
    ImageView mImage;
    @BindView(R.id.recharge_address)
    TextView mAddress;

    private String symbol;
    private String address;

    @Override
    protected String initTitle() {
        return symbol;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initData() {
        initBundle();

        mAddress.setText(StringUtil.getAddress(address));
        Bitmap bitmap = ZXingUtil.createQRCode(address, (int) getResources().getDimension(R.dimen.zxCode_size));
        mImage.setImageBitmap(bitmap);
    }

    @OnClick({R.id.recharge_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.recharge_btn:
                KeyBoardUtil.copy(this, address);
                break;
        }
    }

}
