package cn.myfreecloud.shop.api.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pay")
public interface PayOrderService {
    // 查询用户 by id
    @RequestMapping(value = "/updatePayOrder")
    String payOrder(@RequestParam("payId") String payId,@RequestParam("temp") int temp);

}
