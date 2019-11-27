package com.xxx.willing.model.http;

import com.xxx.willing.ConfigClass;
import com.xxx.willing.model.http.bean.AppVersionBean;
import com.xxx.willing.model.http.bean.IsSettingPayPswBean;
import com.xxx.willing.model.http.bean.LoginBean;
import com.xxx.willing.model.http.bean.SelectCountyBean;
import com.xxx.willing.model.http.bean.UserInfo;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.http.bean.WalletCoinBean;
import com.xxx.willing.model.http.bean.WalletExchangeBean;
import com.xxx.willing.model.http.bean.WalletMarketBean;
import com.xxx.willing.model.http.bean.WalletReleaseBean;
import com.xxx.willing.model.http.bean.WalletTransactionBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.bean.base.PageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //----------------------------------------------------------展示列表----------------------------------------------------------------------------------------------------------------------------//

    //获取钱包充值转账记录
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<WalletTransactionBean>>> getTransferRecordList(
            @Query("type") int type,
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    //获取钱包账号列表
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<WalletAccountBean>>> getWalletAccountList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    //获取钱包释放列表
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<WalletReleaseBean>>> getWalletReleaseList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    //获取钱包释放列表
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<WalletExchangeBean>>> getWalletExchangeList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    //获取钱包行情列表
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<WalletMarketBean>>> getWalletMarketList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );


    //获取可兑换列表
    @POST(ConfigClass.BASE_URL_PATH + "/getExchangeList")
    Observable<BaseBean<List<WalletCoinBean>>> getExchangeList();


    //----------------------------------------------------------获取信息----------------------------------------------------------------------------------------------------------------------------//


    //----------------------------------------------------------执行操作----------------------------------------------------------------------------------------------------------------------------//

    //转账
    @POST(ConfigClass.BASE_URL_PATH + "/doChange")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> withdrawal(
            @Field("coinId") int coinId,
            @Field("amount") double amount,
            @Field("fee") double fee,
            @Field("address") String address,
            @Field("jyPassword") String jyPassword,
            @Field("remark") String remark
    );

    //兑换
    @POST(ConfigClass.BASE_URL_PATH + "/exchange")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> exchange(
            @Field("amount") double baseAmount,
            @Field("baseCoinId") Integer baseCoinId,
            @Field("targetCoinId") Integer targetCoinId
    );

    //-------------------------------------------------------登录-------------------------------------------------------------------------------------------------------------------------------//


    //登录
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/login")
    Observable<BaseBean<LoginBean>> login(
            @Field("phone") String account,
            @Field("password") String password,
            @Field("area") String area
    );

    //发送短信验证码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/sendPhoneMassage")
    Observable<BaseBean<Object>> sendSMSCode(
            @Field("phone") String phone,
            @Field("area") String area
    );

    //发送修改密码验证码
    @POST(ConfigClass.BASE_URL_PATH + "/sendUpdatePhoneMassage")
    Observable<BaseBean<Object>> sendUpdateSMSCode();

    //注册
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/register")
    Observable<BaseBean<Object>> register(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("code") String smsCode,
            @Field("area") String area
    );

    //忘记密码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/forgetPassword")
    Observable<BaseBean<Object>> forgetPsw(
            @Field("phone") String phone,
            @Field("newPassword") String newPassword,
            @Field("code") String smsCode,
            @Field("area") String area
    );

    //修改登录密码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/updatePassword")
    Observable<BaseBean<Object>> modifyLoginPsw(
            @Field("oldPassword") String password,
            @Field("newPassword") String mode,
            @Field("code") String smsCode
    );

    //修改支付密码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/updatePayPassword")
    Observable<BaseBean<Object>> modifyPayPsw(
            @Field("payPassword") String password,
            @Field("code") String smsCode
    );

    //设置支付密码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/setPayPassword")
    Observable<BaseBean<Object>> settingPayPsw(
            @Field("payPassword") String payPassword
    );

    //获取城市编码
    @POST(ConfigClass.BASE_URL_PATH + "")
    Observable<BaseBean<List<SelectCountyBean>>> getCounty();

    //检查app版本
    @GET(ConfigClass.BASE_URL_PATH + "/checkUpdate")
    Observable<BaseBean<AppVersionBean>> checkAppVersion();

    //退出登录
    @POST(ConfigClass.BASE_URL_PATH + "/loginOut")
    Observable<BaseBean<Object>> outLogin();

    //检查是否设置过支付密码
    @POST(ConfigClass.BASE_URL_PATH + "/selectPayPassword")
    Observable<BaseBean<IsSettingPayPswBean>> checkIsSettingPayPassword();

    //意见反馈
    @POST(ConfigClass.BASE_URL_PATH + "")
    @FormUrlEncoded
    Observable<BaseBean<Object>> submitFeedback(
            @Field("content") String amount,
            @Field("phone") String address
    );

    //获取用户信息
    @POST(ConfigClass.BASE_URL_PATH + "/getUser")
    Observable<BaseBean<UserInfo>> getUserinfo();

}