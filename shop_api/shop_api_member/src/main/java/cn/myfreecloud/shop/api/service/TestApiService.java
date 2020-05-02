package cn.myfreecloud.shop.api.service;

import cn.myfreecloud.shop.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author: zhangyang
 * @date: 2020/4/29 22:17
 * @description:
 */
@RequestMapping("/member")
public interface TestApiService {

    @RequestMapping("/test")
    public Map<String, Object> test(Integer id, String name);

    @RequestMapping("/testBaseResponse")
    public BaseResponse testBaseResponse(Integer id, String name);

    @RequestMapping("/testSetRedis")
    public BaseResponse testSetRedis();
}
