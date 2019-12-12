package com.xxx.willing.ui.my.activity.join;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.utils.GlideUtil;

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

        if (file != null) {
            imageView.setVisibility(View.GONE);
            GlideUtil.loadBack(mContext, file, relativeLayout);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
