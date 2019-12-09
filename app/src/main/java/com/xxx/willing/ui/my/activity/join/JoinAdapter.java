package com.xxx.willing.ui.my.activity.join;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;

import java.util.List;

public class JoinAdapter extends BaseQuickAdapter<JoinEntry, BaseViewHolder> {

    JoinAdapter(@Nullable List<JoinEntry> data) {
        super(R.layout.item_join_team, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JoinEntry item) {
        TextView joinRole = helper.itemView.findViewById(R.id.ed_join_role);
        TextView name = helper.itemView.findViewById(R.id.ed_join_user_name);
        TextView phone = helper.itemView.findViewById(R.id.ed_join_phone);
        EditText editText = helper.itemView.findViewById(R.id.ed_join_person_introduce);
        TextView card = helper.itemView.findViewById(R.id.ed_join_card);
        ImageView imageView = helper.itemView.findViewById(R.id.join_photo_add);

        joinRole.setText(item.getRole());
        name.setText(item.getName());
        phone.setText(item.getPhone());
        card.setText(item.isSettingCard());

        helper.itemView.findViewById(R.id.ed_join_role);
        helper.itemView.findViewById(R.id.ed_join_user_name);
        helper.itemView.findViewById(R.id.ed_join_phone);
        helper.itemView.findViewById(R.id.ed_join_card);

        helper.addOnClickListener(R.id.re_click_card)
                .addOnClickListener(R.id.join_photo_add);

        if (item.getFilePhoto() != null) {
            GlideUtil.load(mContext, item.getFilePhoto(), imageView);
        } else {
            imageView.setImageResource(R.mipmap.join_add);
        }

        KeyBoardUtil.setFilters(editText, 500);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                item.setPerIntroduce(s.toString());
                helper.setText(R.id.ed_join_ed_person_number, length + "/500");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public List<JoinEntry> getData(BaseActivity activity) {
        List<JoinEntry> data = getData();
        for (int i = 0; i < data.size(); i++) {
            JoinEntry joinEntry = data.get(i);

            TextView joinRole = (TextView) getViewByPosition(i, R.id.ed_join_role);
            TextView name = (TextView) getViewByPosition(i, R.id.ed_join_user_name);
            TextView phone = (TextView) getViewByPosition(i, R.id.ed_join_phone);
            ImageView card = (ImageView) getViewByPosition(i, R.id.ed_join_card);
            EditText joinPerIntroduce = (EditText) getViewByPosition(i, R.id.ed_join_person_introduce);

            if (joinRole == null) return null;
            if (name == null) return null;
            if (phone == null) return null;
            if (card == null) return null;
            if (joinPerIntroduce == null) return null;

            String personnelRole = joinRole.getText().toString();
            if (personnelRole.isEmpty()) {
                activity.showEditError(joinRole);
                ToastUtil.showToast("人员角色不能为空");
                return null;
            }
            String personnelName = name.getText().toString();
            if (personnelName.isEmpty()) {
                activity.showEditError(name);
                ToastUtil.showToast("姓名不能为空");
                return null;
            }
            String phoneStr = phone.getText().toString();
            if (phoneStr.isEmpty()) {
                activity.showEditError(phone);
                ToastUtil.showToast("联系电话不能为空");
                return null;
            }
            if (joinEntry.getFilePhoto() == null) {
                activity.showEditError(phone);
                ToastUtil.showToast("请上传形象照片");
                return null;
            }
            if (joinEntry.getFileHand() == null || joinEntry.getFileBack() == null || joinEntry.getFileFront() == null) {
                activity.showEditError(card);
                ToastUtil.showToast("请先上传证件照片");
                return null;
            }
            String perIntroduce = joinPerIntroduce.getText().toString();
            if (perIntroduce.isEmpty()) {
                activity.showEditError(joinPerIntroduce);
                ToastUtil.showToast("简介不能为空");
                return null;
            }
        }
        return data;
    }

}
