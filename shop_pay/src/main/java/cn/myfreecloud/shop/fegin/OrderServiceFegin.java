package cn.myfreecloud.shop.fegin;

import cn.myfreecloud.shop.api.order.OrderService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;


@Component
@FeignClient("order")
public interface OrderServiceFegin extends OrderService {

}
