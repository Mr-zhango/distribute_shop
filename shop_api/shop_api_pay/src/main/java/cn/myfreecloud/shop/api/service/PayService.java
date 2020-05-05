package cn.myfreecloud.shop.api.service;

import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.entity.PaymentInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pay")
public interface PayService {
    // 查询用户 by id
    @RequestMapping(value = "/createPayToken", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    BaseResponse createPayToken(@RequestBody PaymentInfo PaymentInfo);

    // 使用支付令牌查找支付信息
    @RequestMapping("/findPayToken")
    BaseResponse findPayToken (@RequestParam("payToken") String  payToken);
}
