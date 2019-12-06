package com.xxx.willing.ui.vote.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.base.BaseBean;

import java.util.List;

/**
 * @author FM
 * @desc 公告适配器
 * @date 2019-12-06
 */

public class NoticeCenterAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public NoticeCenterAdapter(@Nullable List<BaseBean> data) {
        super(R.layout.notice_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        String message = "";
        helper.setText(R.id.notice_title, "")
                .setText(R.id.notice_date, "")
                .setText(R.id.notice_content, message);
        final CheckBox checkBox = helper.getView(R.id.item_notice_center_btn);
        final ImageView imageView  = helper.getView(R.id.notice_img);
        final TextView textView = helper.getView(R.id.notice_content);

//        if (item.isCheck()) {
//            checkBox.setText(mContext.getString(R.string.pack_up_content));
//            imageView.setBackgroundResource(R.mipmap.vote_pack_up);
//        } else {
//            checkBox.setText(mContext.getString(R.string.to_view_content));
//            imageView.setBackgroundResource(R.mipmap.vote_down);
//        }


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = checkBox.isChecked();
                if (checked) {
                    checkBox.setText(mContext.getString(R.string.pack_up_content));
                    imageView.setBackgroundResource(R.mipmap.vote_down);
                    textView.setMaxLines(10000);
                    textView.invalidate();
                } else {
                    checkBox.setText(mContext.getString(R.string.to_view_content));
                    imageView.setBackgroundResource(R.mipmap.vote_down);
                    textView.setMaxLines(4);
                    textView.invalidate();
                }
//                item.setCheck(checked);
            }
        });

    }
}
