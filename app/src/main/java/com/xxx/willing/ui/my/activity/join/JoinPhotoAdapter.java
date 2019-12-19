package com.xxx.willing.ui.my.activity.join;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.glide.GlideFileUtil;


import java.io.File;
import java.util.List;

public class JoinPhotoAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    JoinPhotoAdapter(@Nullable List<File> data) {
        super(R.layout.item_join_photo, data);
    }

    @SuppressLint("NewApi")
    @Override
    protected void convert(BaseViewHolder helper, File file) {
        ImageView imageView = helper.getView(R.id.item_join_photo_add);
        RelativeLayout relativeLayout = helper.getView(R.id.item_join_photo_bg);
        helper.addOnClickListener(R.id.item_join_photo_delete);

        View view = helper.getView(R.id.item_join_photo_delete);

        if (file != null) {
            imageView.setVisibility(View.GONE);
            GlideFileUtil.loadBack(mContext, file, R.mipmap.vote_banner_default, relativeLayout);
            view.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            relativeLayout.setBackgroundResource(R.drawable.creat_btn_bg);
            view.setVisibility(View.GONE);
        }
    }
}
