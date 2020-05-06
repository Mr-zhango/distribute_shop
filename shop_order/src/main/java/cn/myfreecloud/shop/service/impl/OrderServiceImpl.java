package cn.myfreecloud.shop.service.impl;

import cn.myfreecloud.shop.BaseApiService;
import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.api.order.OrderService;
import cn.myfreecloud.shop.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyang
 * @date: 2020/5/5 22:15
 * @description:
 */
@RestController
public class OrderServiceImpl extends BaseApiService implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public BaseResponse updateOrderIdInfo(@RequestParam("isPay") Long isPay,
                                          @RequestParam("payId") String aliPayId,
                                          @RequestParam("orderNumber") String orderNumber) {

        int updateOrder = orderMapper.updateOrder(isPay, aliPayId, orderNumber);
        if (updateOrder <= 0) {
            return setResultError("系统错误!");
        }
        return setResultSuccess();
    }
}
