package cn.myfreecloud.shop.fegin;

import cn.myfreecloud.shop.api.service.PayService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "pay")
public interface PayServiceFegin extends PayService {
}
