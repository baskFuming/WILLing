package com.xxx.willing.ui.app.activity.gvishop.my;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.ShopUser;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.my.address.ShipAddressActivity;
import com.xxx.willing.ui.app.activity.gvishop.my.order.MyOrderActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 商城首页
 * @date 2019-12-04
 */

public class GVIMyFragment extends BaseFragment {
    @BindView(R.id.user_icon)
    ImageView mIcon;
    @BindView(R.id.user_name)
    TextView mName;
    @BindView(R.id.my_gvi_account)
    TextView mAccount;

    @Override
    protected int getLayoutId() {
        return R.layout.gvi_my_fragment;
    }

    @Override
    protected void initData() {
        loadData();
    }

    @OnClick({R.id.re_my_order, R.id.re_my_address})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.re_my_order: //订单
                MyOrderActivity.actionStart(getActivity());
                break;
            case R.id.re_my_address: //我的地址
                ShipAddressActivity.actionStart(getActivity());
                break;
        }
    }

    //获取用户信息
    private void loadData() {
        Api.getInstance().getshopUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<ShopUser>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<ShopUser> bean) {
                        if (bean != null) {
                            mName.setText(bean.getData().getUserName());
                            mAccount.setText(bean.getData().getGviAmount()+"");
                            GlideUtil.loadCircle(getActivity(), bean.getData().getImg(), mIcon);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
