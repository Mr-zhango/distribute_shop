package cn.myfreecloud.shop;

import java.util.UUID;

/**
 * @author: zhangyang
 * @date: 2020/5/2 15:43
 * @description:
 */
public class TokenUtils {
    // 产生token
    public static String getMemberToken() {
        return Constants.TOKEN_MEMBER + "-" + UUID.randomUUID();
    }

    // 生成支付token
    public static String getPayToken() {
        return Constants.TOKEN_PAY + "-" + UUID.randomUUID();
    }
}
