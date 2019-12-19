package com.xxx.willing.model.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.xxx.willing.BuildConfig;
import com.xxx.willing.base.App;
import com.xxx.willing.model.utils.SystemUtil;

public class SharedPreferencesUtil {

    private static final Object SYC = new Object();

    private static SharedPreferencesUtil sharedPreferencesUtils;

    private SharedPreferences sharedPreferences;

    private String key;   //SP加密key

    private SharedPreferencesUtil() {
        String name = BuildConfig.APPLICATION_ID + "_sp";
        key = SystemUtil.getSerialNumber();
        sharedPreferences = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getInstance() {
        if (sharedPreferencesUtils == null) {
            synchronized (SYC) {
                if (sharedPreferencesUtils == null) {
                    sharedPreferencesUtils = new SharedPreferencesUtil();
                }
            }
        }
        return sharedPreferencesUtils;
    }


    /**
     * 保存
     */
    public void saveEncryptString(String tag, String content) {
        saveString(tag, SpEncryption.getInstance().encryptString(content, key));
    }

    public void saveString(String tag, String content) {
        if (tag != null && content != null) {
            sharedPreferences.edit().putString(tag, content).apply();
        }
    }

    public void saveInt(String tag, int content) {
        if (tag != null) {
            sharedPreferences.edit().putInt(tag, content).apply();
        }
    }

    public void saveBoolean(String tag, Boolean boo) {
        if (tag != null && boo != null) {
            sharedPreferences.edit().putBoolean(tag, boo).apply();
        }
    }

    /**
     * 获取
     */
    public String getDecryptionString(String tag) {
        return SpEncryption.getInstance().decryptString(getString(tag), key);
    }

    public String getString(String tag) {
        String data = "";
        if (tag != null) {
            data = sharedPreferences.getString(tag, "");
        }
        return data;
    }

    public Integer getInt(String tag) {
        int data = 0;
        if (tag != null) {
            data = sharedPreferences.getInt(tag, 0);
        }
        return data;
    }

    public Boolean getBoolean(String tag) {
        boolean data = false;
        if (tag != null) {
            data = sharedPreferences.getBoolean(tag, false);
        }
        return data;
    }

    /**
     * 清除
     */
    public void cleanAll() {
        sharedPreferences.edit()
                .remove(SharedConst.ENCRYPT_VALUE_TOKEN)
                .remove(SharedConst.IS_LOGIN)
                .remove(SharedConst.IS_VOTE_FRAN)
                .remove(SharedConst.IS_SETTING_PAY_PSW)
                .remove(SharedConst.VALUE_USER_NAME)
                .remove(SharedConst.VALUE_USER_PHONE)
                .remove(SharedConst.VALUE_USER_ICON)
                .remove(SharedConst.VALUE_USER_STAR)
                .remove(SharedConst.VALUE_USER_ID)
                .remove(SharedConst.VALUE_USER_INVITE_CODE)
                .remove(SharedConst.VALUE_USER_FRAN_ID)
                .remove(SharedConst.IS_SETTING_ADDRESS)
                .apply();
    }
}
