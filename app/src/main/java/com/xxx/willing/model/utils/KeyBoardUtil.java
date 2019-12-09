package com.xxx.willing.model.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xxx.willing.R;

/**
 * 键盘工具类
 */
public class KeyBoardUtil {

    /**
     * 关闭软键盘
     *
     * @param context   页面上下文
     * @param mEditText 输入框
     */
    public static void closeKeyBord(Context context, EditText... mEditText) {
        if (context != null && mEditText != null) {
            // 虚拟键盘隐藏 判断view是否为空
            // 隐藏虚拟键盘
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            for (EditText editText : mEditText) {
                if (editText != null) {
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }
        }
    }

    /**
     * 设置输入框不可见
     *
     * @param isChecked 是否可见
     * @param editText  输入框
     */
    public static void setInputTypePassword(boolean isChecked, EditText editText) {
        if (isChecked) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editText.setSelection(editText.getText().length());
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editText.setSelection(editText.getText().length());
        }
    }

    /**
     * 复制到粘贴板
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void copy(Context context, String message) {
        if (context != null) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm != null) {
                cm.setText(message == null ? "" : message);
                ToastUtil.showToast(context.getString(R.string.copy_success));
            }
        }
    }


    /**
     * 设置限定输入小点位数
     *
     * @param editText 输入框
     * @param count    位数
     */
    public static void setFilters(EditText editText, final int count) {
        editText.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if (source.equals(".") && dest.toString().length() == 0) {
                return "0.";
            }
            if (dest.toString().contains(".")) {
                int index = dest.toString().indexOf(".");
                int length = dest.toString().substring(index).length();
                if (length == count + 1) {
                    return "";
                }
            }
            return null;
        }});
    }
}