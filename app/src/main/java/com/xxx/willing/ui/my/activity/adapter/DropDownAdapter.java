package com.xxx.willing.ui.my.activity.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.AssetRecordBean;
import com.xxx.willing.model.http.utils.ApiType;

import java.util.List;

/**
 * @author FM
 * @desc 资金记录适配器
 * @date 2019-12-09
 */

public class DropDownAdapter extends BaseQuickAdapter<AssetRecordBean, BaseViewHolder> {

    public DropDownAdapter(@Nullable List<AssetRecordBean> data) {
        super(R.layout.drow_down_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssetRecordBean item) {
        String type;
        String amount;
        switch (item.getTypes()) {
            case ApiType.ASSET_RECORD_RECHARGE_TYPE:
                type = "充值";
                amount = "+" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_TRANSFER_TYPE:
                type = "转账";
                amount = "-" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_EXCHANGE_TYPE:
                type = item.getBaseSymbol() + "兑换" + item.getTargetSymbol();
                amount = "-" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_VOTE_TYPE:
                type = "投票释放";
                amount = "+" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_TEAM_VOTE_TYPE:
                type = "团队投票收益";
                amount = "+" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_SIGN_TYPE:
                type = "BVSE签到奖励";
                amount = "+" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_JOIN_TYPE:
                type = "加盟店收益";
                amount = "+" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_BRAND_TYPE:
                type = "品牌收益";
                amount = "+" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_TASK_TYPE:
                type = "任务收益";
                amount = "+" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_RANK_TYPE:
                type = "排名收益";
                amount = "+" + item.getAmount();
                break;
            case ApiType.ASSET_RECORD_CEO_TYPE:
                type = "总裁收益";
                amount = "+" + item.getAmount();
                break;
            default:
                type = "平台福利";
                amount = "+" + item.getAmount();
                break;
        }
        helper.setText(R.id.item_type, type)
                .setText(R.id.item_time, item.getCreateTime())
                .setText(R.id.item_reduce_coin, amount)
                .setText(R.id.item_poundage, mContext.getString(R.string.item_poundage) + item.getFee())
                .setText(R.id.item_balance, mContext.getString(R.string.item_balance) + item.getBalance());
    }
}
