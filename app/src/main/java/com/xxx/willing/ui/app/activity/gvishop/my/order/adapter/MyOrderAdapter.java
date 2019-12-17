package com.xxx.willing.ui.app.activity.gvishop.my.order.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.MyOrderBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.model.utils.GlideUtil;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @author FM
 * @desc 我的订单adapter
 * @date 2019-12-04
 */

public class MyOrderAdapter extends BaseQuickAdapter<MyOrderBean, BaseViewHolder> {


    public MyOrderAdapter(@Nullable List<MyOrderBean> data) {
        super(R.layout.my_order_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrderBean item) {
        helper.setText(R.id.order_number, mContext.getString(R.string.order_number) + item.getOrderNum())//订单编号
                .setText(R.id.order_goods_name, "")                                      //品牌名
                .setText(R.id.order_pay_price_account, "" + "GVI")                       //支付GVI数量
                .setText(R.id.order_pay_price, "")                                       //CNY数量
                .setText(R.id.order_goods_color, "")                                     //品牌颜色
                .setText(R.id.order_buy_number, "x" + "")                                //购买个数
                .setText(R.id.order_all_price, "")                                       //商品总价
                .setText(R.id.order_freight_money, "")                                   //运费
                .setText(R.id.order_total, "" + "GVI")                                   //合计
                .addOnClickListener(R.id.order_cancel_btn)
                .addOnClickListener(R.id.order_cancel_pay_btn)
                .addOnClickListener(R.id.order_un_delivery)
                .addOnClickListener(R.id.order_confirm_btn);

        TextView mpay = helper.getView(R.id.order_pay_price);
        TextView mStatus = helper.getView(R.id.order_status);
        TextView mCancleOrder = helper.getView(R.id.order_cancel_btn);          //待付款取消订单
        TextView mPayOrder = helper.getView(R.id.order_cancel_pay_btn);         //付款
        TextView mDelivery = helper.getView(R.id.order_un_delivery);            //待发货取消订单
        TextView mConfirm = helper.getView(R.id.order_confirm_btn);             //确认收货
        mpay.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        GlideUtil.load(mContext, "", helper.getView(R.id.order_goods_icon));//品牌图片
        //订单状态
        if (item.getStatus() == ApiType.ORDER_COMMUNITY_ALL) {                            //全部状态
            mStatus.setVisibility(View.GONE);
            //全部订单状态判断
            if (item.getStatus() == ApiType.ORDER_COMMUNITY_UNPAID) { //待付款
                mStatus.setText(mContext.getString(R.string.order_un_pay));
            } else if (item.getStatus() == ApiType.ORDER_COMMUNITY_DELIVERY) { //待发货
                mStatus.setText(mContext.getString(R.string.order_un_delivery));
                mCancleOrder.setVisibility(View.GONE);
                mPayOrder.setVisibility(View.GONE);
                mDelivery.setVisibility(View.VISIBLE);
            } else if (item.getStatus() == ApiType.ORDER_COMMUNITY_GOODS) { //待收货
                mStatus.setText(mContext.getString(R.string.order_merchants));
                mCancleOrder.setVisibility(View.GONE);
                mPayOrder.setVisibility(View.GONE);
                mConfirm.setVisibility(View.VISIBLE);
            } else if (item.getStatus() == ApiType.ORDER_COMMUNITY_COMPLAINING) { //已收货
                mStatus.setText(mContext.getString(R.string.order_complete));
                helper.getView(R.id.order_bottom_click).setVisibility(View.GONE);
            }
        } else if (item.getStatus() == ApiType.ORDER_COMMUNITY_UNPAID) {                  //待付款
            mStatus.setText(mContext.getString(R.string.order_un_pay));
        } else if (item.getStatus() == ApiType.ORDER_COMMUNITY_DELIVERY) {                //待发货
            mStatus.setText(mContext.getString(R.string.order_un_delivery));
            mCancleOrder.setVisibility(View.GONE);
            mPayOrder.setVisibility(View.GONE);
            mDelivery.setVisibility(View.VISIBLE);
        } else if (item.getStatus() == ApiType.ORDER_COMMUNITY_GOODS) {                   //待收货
            mStatus.setText(mContext.getString(R.string.order_merchants));
            mCancleOrder.setVisibility(View.GONE);
            mPayOrder.setVisibility(View.GONE);
            mConfirm.setVisibility(View.VISIBLE);
        } else if (item.getStatus() == ApiType.ORDER_COMMUNITY_COMPLAINING) {              //已收货
            mStatus.setText(mContext.getString(R.string.order_complete));
            helper.getView(R.id.order_bottom_click).setVisibility(View.GONE);   //底部按钮隐藏
        }
    }
}
