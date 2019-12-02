package com.xxx.willing.ui.wallet.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.WalletCoinBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.wallet.activity.WalletExchangeRecordActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WalletExchangeFragment extends BaseFragment {

    public static WalletExchangeFragment getInstance(){
        return new WalletExchangeFragment();
    }

    @BindView(R.id.wallet_exchange_rate)
    TextView mRate;
    @BindView(R.id.wallet_exchange_fee)
    TextView mFee;

    @BindView(R.id.wallet_exchange_base_icon)
    ImageView mBaseIcon;
    @BindView(R.id.wallet_exchange_base_symbol)
    TextView mBaseSymbol;
    @BindView(R.id.wallet_exchange_base_amount)
    EditText mBaseAmount;
    @BindView(R.id.wallet_exchange_target_icon)
    ImageView mTargetIcon;
    @BindView(R.id.wallet_exchange_target_symbol)
    TextView mTargetSymbol;
    @BindView(R.id.wallet_exchange_target_amount)
    EditText mTargetAmount;

    private int baseCoinId;
    private int targetCoinId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet_exchange;
    }

    @Override
    protected void initData() {
//        getExchangeList();
    }

    @OnClick({R.id.wallet_exchange_change, R.id.wallet_exchange_record, R.id.wallet_exchange_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.wallet_exchange_change:

                break;
            case R.id.wallet_exchange_record:
                WalletExchangeRecordActivity.actionStart(getActivity());
                break;
            case R.id.wallet_exchange_btn:
                exchange();
                break;
        }
    }

    /**
     * @Model 兑换
     */
    private void exchange() {
        String baseAmount = mBaseAmount.getText().toString();
        double amount = Double.parseDouble(baseAmount);

        Api.getInstance().exchange(amount, baseCoinId, targetCoinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
                            BooleanBean data = bean.getData();
                            if (data != null) {
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

    /**
     * @Model 获取兑换列表
     */
    private void getExchangeList() {
        Api.getInstance().getExchangeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<WalletCoinBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<List<WalletCoinBean>> bean) {
                        if (bean != null) {
                            List<WalletCoinBean> list = bean.getData();
                            if (list != null && list.size() != 0) {

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
