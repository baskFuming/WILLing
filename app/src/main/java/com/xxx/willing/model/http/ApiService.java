package com.xxx.willing.model.http;

import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.http.bean.AppVersionBean;
import com.xxx.willing.model.http.bean.BannerBean;
import com.xxx.willing.model.http.bean.BrandBean;
import com.xxx.willing.model.http.bean.FranchiseeBean;
import com.xxx.willing.model.http.bean.IsSettingPayPswBean;
import com.xxx.willing.model.http.bean.JoinInfoBean;
import com.xxx.willing.model.http.bean.LoginBean;
import com.xxx.willing.model.http.bean.MessageBean;
import com.xxx.willing.model.http.bean.MyTeamBean;
import com.xxx.willing.model.http.bean.MyVoteBean;
import com.xxx.willing.model.http.bean.UserInfo;
import com.xxx.willing.model.http.bean.VoteRecordBean;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.http.bean.WalletCoinBean;
import com.xxx.willing.model.http.bean.WalletExchangeBean;
import com.xxx.willing.model.http.bean.WalletMarketBean;
import com.xxx.willing.model.http.bean.WalletReleaseBean;
import com.xxx.willing.model.http.bean.WalletTransactionBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.youth.banner.Banner;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {

    //----------------------------------------------------------展示列表----------------------------------------------------------------------------------------------------------------------------//
    //获取加盟商列表
    @POST("/franchiseesKList")
    @FormUrlEncoded
    Observable<BaseBean<PageBean<FranchiseeBean>>> getFranchiseeList(
            @Field("brandId") int brandId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取品牌列表
    @GET("/brandsList")
    Observable<BaseBean<PageBean<BrandBean>>> getBrandList();

    //获取首页轮播
    @GET("/getBannerList")
    Observable<BaseBean<PageBean<BannerBean>>> getBannerList();

    //获取公告信息
    @GET("/getMessageList")
    Observable<BaseBean<PageBean<MessageBean>>> getMessageList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取我的团队
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<MyTeamBean>>> getMyTeamList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取我的团队
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<MyTeamBean>>> getMyTeamList(
            @Query("userId") int userId,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取我的投票
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<MyVoteBean>>> getMyVoteList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取投票记录
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<VoteRecordBean>>> getVoteRecordList(
            @Query("voteId") int voteId,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取钱包充值转账记录
    @GET("/CT/invest/getDepositLogs")
    Observable<BaseBean<PageBean<WalletTransactionBean>>> getTransferRecordList(
            @Query("type") int type,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取钱包账号列表
    @GET("/getUserWalletList")
    Observable<BaseBean<WalletAccountBean>> getWalletAccountList();

    //获取钱包释放列表
    @GET("/getReleaseRecordList")
    Observable<BaseBean<PageBean<WalletReleaseBean>>> getWalletReleaseList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取钱包兑换记录
    @GET("/getExchangeRecordList")
    Observable<BaseBean<PageBean<WalletExchangeBean>>> getWalletExchangeList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取钱包行情列表
    @GET("/getMarketList")
    Observable<BaseBean<PageBean<WalletMarketBean>>> getWalletMarketList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );


    //获取可兑换列表
    @GET(HttpConfig.BASE_URL_PATH + "/getExchangeCoinList")
    Observable<BaseBean<WalletCoinBean>> getExchangeList();


    //----------------------------------------------------------获取信息----------------------------------------------------------------------------------------------------------------------------//
    //获取加盟信息
    @POST("/franchiseeInfo")
    Observable<BaseBean<JoinInfoBean>> getJoinInfo();

    //----------------------------------------------------------执行操作----------------------------------------------------------------------------------------------------------------------------//

    //申请加盟
    @POST("/CT/invest/getDepositLogs")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> submitJoin(
            @Field("franName") String franName,
            @Field("brandId") int brandId,
            @Field("period") int period,
            @Field("address") String address,
            @Field("details") String details,
            @Field("exTurnover") String exTurnover,
            @Field("imgBanner") List<String> imgBanner,
            @Field("list") List<Map<String, String>> list
    );

    //转账
    @POST(HttpConfig.BASE_URL_PATH + "/doChange")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> withdrawal(
            @Field("coinId") int coinId,
            @Field("amount") double amount,
            @Field("fee") double fee,
            @Field("address") String address,
            @Field("jyPassword") String jyPassword
    );

    //兑换
    @POST(HttpConfig.BASE_URL_PATH + "/doExchange")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> exchange(
            @Field("amount") double baseAmount,
            @Field("baseCoinId") Integer baseCoinId,
            @Field("targetCoinId") Integer targetCoinId
    );

    //-------------------------------------------------------登录-------------------------------------------------------------------------------------------------------------------------------//


    //登录
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/login")
    Observable<BaseBean<LoginBean>> login(
            @Field("phone") String account,
            @Field("password") String password,
            @Field("area") String area
    );

    //发送短信验证码
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/sendPhoneMassage")
    Observable<BaseBean<Object>> sendSMSCode(
            @Field("phone") String phone,
            @Field("area") String area
    );

    //发送修改密码验证码
    @POST(HttpConfig.BASE_URL_PATH + "/sendUpdatePhoneMassage")
    Observable<BaseBean<Object>> sendUpdateSMSCode();

    //注册
//    @FormUrlEncoded
//    @POST(HttpConfig.BASE_URL_PATH + "/register")
//    Observable<BaseBean<Object>> register(
//            @Field("phone") String phone,
//            @Field("password") String password,
//            @Field("code") String smsCode,
//            @Field("area") String area
//    );

    //注册
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/registerTest")
    Observable<BaseBean<Object>> register(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("code") String smsCode,
            @Field("area") String area
    );

    //忘记密码
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/forgetPassword")
    Observable<BaseBean<Object>> forgetPsw(
            @Field("phone") String phone,
            @Field("newPassword") String newPassword,
            @Field("code") String smsCode,
            @Field("area") String area
    );

    //修改登录密码
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/updatePassword")
    Observable<BaseBean<Object>> modifyLoginPsw(
            @Field("oldPassword") String password,
            @Field("newPassword") String mode,
            @Field("code") String smsCode
    );

    //修改支付密码
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/updatePayPassword")
    Observable<BaseBean<Object>> modifyPayPsw(
            @Field("payPassword") String password,
            @Field("code") String smsCode
    );

    //设置支付密码
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/setPayPassword")
    Observable<BaseBean<Object>> settingPayPsw(
            @Field("payPassword") String payPassword
    );

    //检查是否设置过支付密码
    @POST(HttpConfig.BASE_URL_PATH + "/selectPayPassword")
    Observable<BaseBean<IsSettingPayPswBean>> checkIsSettingPayPassword();

    //检查app版本
    @GET(HttpConfig.BASE_URL_PATH + "/checkUpdate")
    Observable<BaseBean<AppVersionBean>> checkAppVersion();

    //退出登录
    @POST(HttpConfig.BASE_URL_PATH + "/loginOut")
    Observable<BaseBean<Object>> outLogin();

    //意见反馈
    @POST(HttpConfig.BASE_URL_PATH + "")
    @FormUrlEncoded
    Observable<BaseBean<Object>> submitFeedback(
            @Field("content") String amount,
            @Field("phone") String address
    );

    //获取用户信息
    @POST(HttpConfig.BASE_URL_PATH + "/getUserInfo")
    Observable<BaseBean<UserInfo>> getUserInfo();

    //上传用户信息
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/getUserInfo")
    Observable<BaseBean<BooleanBean>> updateUserInfo(
            @Field("picUrl") String picUrl,
            @Field("nickName") String nickName
    );

    //上传单张图片
    @Multipart
    @POST(HttpConfig.BASE_URL_PATH + "/upLoadImg")
    Observable<BaseBean<String>> upLoadPhoto(
            @Part MultipartBody.Part file
    );

    //上传多张图片
    @Multipart
    @POST(HttpConfig.BASE_URL_PATH + "/upLoadImgMap")
    Observable<BaseBean<Map<String, String>>> upLoadPhotoMap(
            @PartMap Map<String, RequestBody> files
    );

    //实名认证
    @FormUrlEncoded
    @POST(HttpConfig.BASE_URL_PATH + "/uc/approve/real/name")
    Observable<BaseBean<String>> realName(
            @Field("realName") String realName,
            @Field("idCard") String idCard,
            @Field("idCardFront") String idCardFront,
            @Field("idCardBack") String idCardBack,
            @Field("handHeldIdCard") String handHeldIdCard
    );

}