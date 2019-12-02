package com.xxx.willing.ui.my.activity.sign;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;

import java.util.List;

public class SignAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public SignAdapter(@Nullable List<Object> data) {
        super(R.layout.item_my_sign, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        helper.setText(R.id.item_my_sign_time, (helper.getAdapterPosition() + 1) + "天");
        ImageView imageView = helper.getView(R.id.item_my_sign_icon);
        View left = helper.getView(R.id.item_my_sign_left);
        View right = helper.getView(R.id.item_my_sign_right);

        //TODO 判断下是否有奖励
        if ((Boolean) item) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.VISIBLE);
//            GlideUtil.load(mContext, "", imageView);
        }

        //TODO 用于第一根和最后一根隐藏
        if (helper.getAdapterPosition() == 0) {
            left.setVisibility(View.INVISIBLE);
        } else {
            left.setVisibility(View.VISIBLE);
        }
        if (helper.getAdapterPosition() == getData().size() - 1) {
            right.setVisibility(View.INVISIBLE);
        } else {
            right.setVisibility(View.VISIBLE);
        }

        //TODO 是否是今天
        View status;
        if (helper.getAdapterPosition() == 1) {
            helper.setVisible(R.id.item_my_sign_today_status, true)
                    .setVisible(R.id.item_my_sign_status, false);
            status = helper.getView(R.id.item_my_sign_today_status);
        } else {
            helper.setVisible(R.id.item_my_sign_today_status, false)
                    .setVisible(R.id.item_my_sign_status, true);
            status = helper.getView(R.id.item_my_sign_status);
        }

        //TODO 签到过
        if (helper.getAdapterPosition() < 4) {
            status.setBackgroundResource(R.drawable.shape_item_sign_true);
            left.setBackgroundColor(Color.parseColor("#3C7FFF"));
            //TODO 判断下一天是否也签到过
            if (helper.getAdapterPosition() + 1 < 4) {
                right.setBackgroundColor(Color.parseColor("#3C7FFF"));
            } else {
                right.setBackgroundColor(Color.parseColor("#DFE2E5"));
            }
        } else {
            status.setBackgroundResource(R.drawable.shape_item_sign_false);
            left.setBackgroundColor(Color.parseColor("#DFE2E5"));
            right.setBackgroundColor(Color.parseColor("#DFE2E5"));
        }
    }
}
