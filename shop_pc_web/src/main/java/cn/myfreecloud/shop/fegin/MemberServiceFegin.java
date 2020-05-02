package cn.myfreecloud.shop.fegin;

import cn.myfreecloud.shop.api.service.MemberService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "member")
public interface MemberServiceFegin extends MemberService {
}
