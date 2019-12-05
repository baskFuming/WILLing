package com.xxx.willing.view;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lljjcoder.Interface.OnCustomCityPickerItemClickListener;
import com.lljjcoder.bean.CustomCityData;
import com.lljjcoder.citywheel.CustomConfig;
import com.xxx.willing.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityPickerUtil {

    public static CityPickerView getInstance(Context context, EditText closeEdit, OnCustomCityPickerItemClickListener clickListener) {
        CustomConfig cityConfig = new CustomConfig.Builder()
                .title(context.getString(R.string.chose_address))
                .titleBackgroundColor("#62B2F7")
                .titleTextColor("#FFFFFF")
                .confirTextColor("#FFFFFF")
                .cancelTextColor("#FFFFFF")
                .setCityData(initData(context))//设置数据源
                .provinceCyclic(true)//省份滚轮是否循环显示
                .cityCyclic(false)//城市滚轮是否循环显示
                .districtCyclic(false)//地区（县）滚轮是否循环显示
                .visibleItemsCount(7)//滚轮显示的item个数
                .build();
        CityPickerView customCityPicker = new CityPickerView(context);
        customCityPicker.setCustomConfig(cityConfig);
        if (closeEdit != null) {
            customCityPicker.setOnCustomCityPickerItemClickListener(clickListener);
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(closeEdit.getWindowToken(), 0); //强制隐藏键盘
        }
        return customCityPicker;
    }

    private static List<CustomCityData> initData(Context context) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = context.getAssets().open("city.json");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s);
            }
            try {
                List<CustomCityData> dataItem = getDataItem(new JSONArray(sb.toString()));
                //筛选
                for (int i = 0; i < dataItem.size(); i++) {
                    CustomCityData customCityData = dataItem.get(i);
                    if (customCityData.getName().equals("湖南")) {
                        dataItem.remove(i);
                        i--;
                    }
                }
                return dataItem;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<CustomCityData> getDataItem(JSONArray jsonArray) {
        ArrayList<CustomCityData> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            CustomCityData customCityData = new CustomCityData();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                customCityData.setName(name);
                customCityData.setId(i + "");

                JSONArray subJsonArray = jsonObject.getJSONArray("sub");
                if (subJsonArray != null && subJsonArray.length() != 0) {
                    List<CustomCityData> dataItem = getDataItem(subJsonArray);
                    if (dataItem != null) {
                        customCityData.setList(dataItem);
                    }
                }
            } catch (JSONException e) {
                // 没有下级 说明是直辖市 就添加本市信息
                List<CustomCityData> dataItem = new ArrayList<>();
                dataItem.add(customCityData);
                customCityData.setList(dataItem);
            }
            list.add(customCityData);
        }
        return list;
    }
}
