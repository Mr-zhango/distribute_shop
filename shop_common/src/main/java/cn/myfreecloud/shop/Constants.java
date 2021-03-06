package cn.myfreecloud.shop;

/**
 * @author: zhangyang
 * @date: 2020/4/29 22:48
 * @description:
 */
public interface Constants {

    // 响应code
    String HTTP_RES_CODE_NAME = "code";
    // 响应msg
    String HTTP_RES_CODE_MSG = "msg";
    // 响应data
    String HTTP_RES_CODE_DATA = "data";
    // 响应请求成功
    String HTTP_RES_CODE_200_VALUE = "success";
    // 系统错误
    String HTTP_RES_CODE_500_VALUE = "fail";
    // 响应请求成功code
    Integer HTTP_RES_CODE_200 = 200;

    // 响应请求成功code
    Integer HTTP_RES_CODE_201 = 201;

    // 系统错误
    Integer HTTP_RES_CODE_500 = 500;

    String SMS_MAIL = "email";

    // 表示会员token
    String TOKEN_MEMBER = "TOKEN_MEMBER";

    // token 有效期 默认90天
    Long TOKEN_MEMBER_TIME = new Long(60 * 60 * 24 * 90);

    String COOKIE_MEMBER_TOKEN = "cookie_member_token";

    // 支付token
    String TOKEN_PAY = "TOKEN_pay";

    // 支付成功
    String PAY_SUCCESS = "success";
    // 支付白
    String PAY_FAIL = "fail";

    // 订单支付有效期 15分钟
    Long PAY_TOKEN_MEMBER_TIME =(long)  (60 * 15 );
}
