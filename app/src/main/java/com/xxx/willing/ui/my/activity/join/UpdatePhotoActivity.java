package com.xxx.willing.ui.my.activity.join;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.utils.CameraUtil;
import com.xxx.willing.model.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

/**
 * @author FM
 * @desc 宣传图
 * @date 2019-11-28
 */
public class UpdatePhotoActivity extends BaseTitleActivity implements BaseQuickAdapter.OnItemClickListener {

    private JoinPhotoAdapter mAdapter;

    public static void actionStart(Activity activity, JoinEntry joinEntry) {
        Intent intent = new Intent(activity, UpdatePhotoActivity.class);
        intent.putExtra("entry", joinEntry);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        joinEntry = (JoinEntry) intent.getSerializableExtra("entry");
    }

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;

    private JoinEntry joinEntry;
    private List<File> mList;
    private int position;

    @Override
    protected String initTitle() {
        return getString(R.string.up_photo);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phont;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initData() {
        initBundle();

        if (joinEntry == null) {
            joinEntry = new JoinEntry();
        }
        mList = joinEntry.getFileList();
        if (mList == null) {
            mList = new ArrayList<>();
            mList.add(null);
        }

        mAdapter = new JoinPhotoAdapter(mList);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.btn_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                submitJoin();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        this.position = position;
        CameraUtil.openPhoto(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CameraUtil.openPhoto(this);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CameraUtil.onActivityResult(this, requestCode, resultCode, data, (bitmap, file) -> {
            if (mList.get(position) == null) {
                mList.set(position, file);
                mList.add(null);
            } else {
                mList.set(position, file);
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    /**
     * 返回
     */
    private void submitJoin() {
        if (mList == null || mList.size() <= 1) {
            ToastUtil.showToast(R.string.update_card_error);
            return;
        }
        joinEntry.setFileList(mList);
        finish();
    }

}

