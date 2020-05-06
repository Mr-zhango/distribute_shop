package cn.myfreecloud.shop.api.order;

import cn.myfreecloud.shop.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/order")
public interface OrderService {
    @RequestMapping("/updateOrderIdInfo")
    BaseResponse updateOrderIdInfo(@RequestParam("isPay") Long isPay,
                                   @RequestParam("payId") String aliPayId,
                                   @RequestParam("orderNumber") String orderNumber);
}
