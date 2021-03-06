package com.xxx.willing.ui.wallet.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.WalletCoinBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;

import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.wallet.activity.WalletExchangeRecordActivity;
import com.xxx.willing.ui.wallet.window.WalletExchangeCheckWindow;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WalletExchangeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private int targetDecimal;

    public static WalletExchangeFragment getInstance() {
        return new WalletExchangeFragment();
    }

    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
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
    TextView mTargetAmount;

    private List<WalletCoinBean.ListBean> mBaseList = new ArrayList<>();
    private List<WalletCoinBean.ListBean> mTargetList = new ArrayList<>();
    private WalletExchangeCheckWindow mBaseCheckWindow;
    private WalletExchangeCheckWindow mTargetCheckWindow;
    private int basePosition;
    private int targetPosition;

    private double bvseBaseFee;
    private double bvseTargetFee;
    private double gviBaseFee;
    private double gviTargetFee;

    private boolean isExchangeParam;    //是否切换了参数
    private double rate = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet_exchange;
    }

    @Override
    protected void initData() {
//        mBaseCheckWindow = WalletExchangeCheckWindow.getInstance(getContext(), mBaseList, (adapter, view, position) -> {
//            WalletCoinBean.ListBean listBean = mBaseList.get(position);
//            if (listBean.getCoinId() == mTargetList.get(targetPosition).getCoinId()) {
//                ToastUtil.showToast(getString(R.string.exchange_error_2));
//                return;
//            }
//            WalletExchangeFragment.this.basePosition = position;
//            updateParam();
//            mBaseCheckWindow.dismiss();
//        });
//        mTargetCheckWindow = WalletExchangeCheckWindow.getInstance(getContext(), mTargetList, (adapter, view, position) -> {
//            WalletCoinBean.ListBean listBean = mTargetList.get(position);
//            if (listBean.getCoinId() == mBaseList.get(basePosition).getCoinId()) {
//                ToastUtil.showToast(getString(R.string.exchange_error_2));
//                return;
//            }
//            WalletExchangeFragment.this.targetPosition = position;
//            updateParam();
//            mTargetCheckWindow.dismiss();
//        });

        mBaseAmount.setHint(getString(R.string.wallet_exchange_base_amount) + 0);

        mRefresh.setOnRefreshListener(this);
        getExchangeList();
    }

    @OnClick({R.id.wallet_exchange_base_linear, R.id.wallet_exchange_target_linear, R.id.wallet_exchange_change, R.id.wallet_exchange_record, R.id.wallet_exchange_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.wallet_exchange_base_linear:
                if (isExchangeParam) {
                    if (mTargetCheckWindow != null)
                        mTargetCheckWindow.show();
                } else {
                    if (mBaseCheckWindow != null)
                        mBaseCheckWindow.show();
                }
                break;
            case R.id.wallet_exchange_target_linear:
                if (isExchangeParam) {
                    if (mBaseCheckWindow != null)
                        mBaseCheckWindow.show();
                } else {
                    if (mTargetCheckWindow != null)
                        mTargetCheckWindow.show();
                }
                break;
            case R.id.wallet_exchange_change:
                exchangeParam();
                break;
            case R.id.wallet_exchange_record:
                WalletExchangeRecordActivity.actionStart(getActivity());
                break;
            case R.id.wallet_exchange_btn:
                exchange();
                break;
        }
    }

    @OnTextChanged(R.id.wallet_exchange_base_amount)
    public void OnTextChanged(CharSequence charSequence) {
        String string = charSequence.toString();
        double baseAmount;
        try {
            baseAmount = Double.parseDouble(string);
        } catch (Exception e) {
            baseAmount = 0;
        }
        String string1 = new BigDecimal(baseAmount * rate).setScale(targetDecimal, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        mTargetAmount.setText(string1);
    }

    @Override
    public void onRefresh() {
        getExchangeList();
    }

    //兑换参数
    private void exchangeParam() {
        isExchangeParam = !isExchangeParam;
        updateParam();
    }

    //更新参数
    @SuppressLint("SetTextI18n")
    private void updateParam() {
        WalletCoinBean.ListBean baseCoinBean;
        WalletCoinBean.ListBean targetCoinBean;
        if (isExchangeParam) {
            targetCoinBean = mBaseList.get(basePosition);
            baseCoinBean = mTargetList.get(targetPosition);
        } else {
            baseCoinBean = mBaseList.get(basePosition);
            targetCoinBean = mTargetList.get(targetPosition);
        }

        double fee;
        mBaseSymbol.setText(baseCoinBean.getCoinSymbol());
        mTargetSymbol.setText(targetCoinBean.getCoinSymbol());
        GlideUrlUtil.loadCircle(getContext(), HttpConfig.HTTP_IMG_URL + baseCoinBean.getCoinUrl(), R.mipmap.my_icon, mBaseIcon);
        GlideUrlUtil.loadCircle(getContext(), HttpConfig.HTTP_IMG_URL + targetCoinBean.getCoinUrl(), R.mipmap.my_icon, mTargetIcon);
        if (baseCoinBean.getCoinId() == 10000004) {    //GVI 左边
            fee = gviBaseFee;
        } else if (baseCoinBean.getCoinId() == 10000005) {  //BVSE 左边
            fee = bvseBaseFee;
        } else if (targetCoinBean.getCoinId() == 10000004) {  //GVI 右边
            fee = gviTargetFee;
        } else if (targetCoinBean.getCoinId() == 10000005) {  //BVSE 右边
            fee = bvseTargetFee;
        } else {
            fee = 0;
        }
        targetDecimal = targetCoinBean.getCoinDecimal();
        KeyBoardUtil.setFilters(mBaseAmount, baseCoinBean.getCoinDecimal());
        mBaseAmount.setHint(getString(R.string.wallet_exchange_base_amount) + targetCoinBean.getBalance());
        this.rate = baseCoinBean.getCoinPriceUsdt() / targetCoinBean.getCoinPriceUsdt();
        String rate = new BigDecimal(this.rate).setScale(baseCoinBean.getCoinDecimal(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        mBaseAmount.setText("");
        mTargetAmount.setText("");
        mRate.setText(getString(R.string.wallet_exchange_rate) + rate);
        mFee.setText(getString(R.string.wallet_exchange_fee) + (fee * 100) + "%");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBaseCheckWindow != null) {
            mBaseCheckWindow.dismiss();
            mBaseCheckWindow = null;
        }
        if (mTargetCheckWindow != null) {
            mTargetCheckWindow.dismiss();
            mTargetCheckWindow = null;
        }
    }

    /**
     * @Model 兑换
     */
    private void exchange() {
        String baseAmount = mBaseAmount.getText().toString();
        double amount;
        try {
            amount = Double.parseDouble(baseAmount);
        } catch (Exception e) {
            ToastUtil.showToast(getString(R.string.exchange_error));
            return;
        }
        if (amount <= 0) {
            ToastUtil.showToast(getString(R.string.exchange_error_1));
            return;
        }

        int baseCoinId;
        int targetCoinId;
        if (isExchangeParam) {
            baseCoinId = mTargetList.get(targetPosition).getCoinId();
            targetCoinId = mBaseList.get(basePosition).getCoinId();
        } else {
            baseCoinId = mBaseList.get(basePosition).getCoinId();
            targetCoinId = mTargetList.get(targetPosition).getCoinId();
        }

        Api.getInstance().exchange(amount, baseCoinId, targetCoinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
                            BooleanBean data = bean.getData();
                            if (data != null && data.isResult()) {
                                ToastUtil.showToast(getString(R.string.exchange_success));
                                //更新资产
                                EventBus.getDefault().post(EventBusConfig.EVENT_UPDATE_WALLET);
                                //更新当前页面
                                getExchangeList();
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
                .subscribe(new ApiCallback<WalletCoinBean>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<WalletCoinBean> bean) {
                        if (bean != null) {
                            WalletCoinBean data = bean.getData();
                            if (data != null) {
                                bvseBaseFee = data.getBvseBaseFee();
                                bvseTargetFee = data.getBvseTargetFee();
                                gviBaseFee = data.getGviBaseFee();
                                gviTargetFee = data.getGviTargetFee();

                                List<WalletCoinBean.ListBean> list = data.getList();
                                if (list != null && list.size() != 0) {
                                    mBaseList.clear();
                                    mTargetList.clear();
                                    for (int i = 0; i < list.size(); i++) {
                                        WalletCoinBean.ListBean walletCoinBean = list.get(i);
                                        if (walletCoinBean.isBase()) {
                                            mBaseList.add(walletCoinBean);
                                        }
                                        if (walletCoinBean.isTarget()) {
                                            mTargetList.add(walletCoinBean);
                                        }
                                    }

                                    Collections.reverse(mBaseList);
                                    if (mBaseCheckWindow != null)
                                        mBaseCheckWindow.notifyData(mBaseList);
                                    if (mTargetCheckWindow != null)
                                        mTargetCheckWindow.notifyData(mTargetList);
                                    updateParam();
                                }
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
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }
                });
    }
}
