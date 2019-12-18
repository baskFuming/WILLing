package com.xxx.willing.ui.vote.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.MessageBean;

import java.util.List;

/**
 * @author FM
 * @desc 公告适配器
 * @date 2019-12-06
 */

public class NoticeCenterAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {

    public NoticeCenterAdapter(@Nullable List<MessageBean> data) {
        super(R.layout.item_notice, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.notice_title, item.getName())
                .setText(R.id.notice_date, item.getCreateTime())
                .setText(R.id.notice_content, item.getContent());
        final CheckBox checkBox = helper.getView(R.id.item_notice_center_btn);
        final ImageView imageView = helper.getView(R.id.notice_img);
        final TextView textView = helper.getView(R.id.notice_content);

        checkBox.setOnClickListener(view -> {
            if (checkBox.isChecked()) {
                checkBox.setText(mContext.getString(R.string.pack_up_content));
                imageView.setBackgroundResource(R.mipmap.vote_pack_up);
                textView.setMaxLines(10000);
                textView.invalidate();
            } else {
                checkBox.setText(mContext.getString(R.string.to_view_content));
                imageView.setBackgroundResource(R.mipmap.vote_down);
                textView.setMaxLines(4);
                textView.invalidate();
            }
        });
        /*checkBox.setOnClickListener(v -> {
            boolean checked = checkBox.isChecked();
            if (checked) {
                checkBox.setText(mContext.getString(R.string.pack_up_content));
                imageView.setBackgroundResource(R.mipmap.vote_pack_up);
//                textView.setMaxLines(10000);
//                textView.invalidate();
            } else {
                checkBox.setText(mContext.getString(R.string.to_view_content));
                imageView.setBackgroundResource(R.mipmap.vote_down);
//                textView.setMaxLines(4);
//                textView.invalidate();
            }
        });*/

    }
}
