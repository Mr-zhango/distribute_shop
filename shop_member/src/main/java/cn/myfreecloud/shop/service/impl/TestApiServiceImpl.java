package cn.myfreecloud.shop.service.impl;

import cn.myfreecloud.shop.api.service.TestApiService;
import cn.myfreecloud.shop.BaseApiService;
import cn.myfreecloud.shop.BaseResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangyang
 * @date: 2020/4/29 22:17
 * @description:
 */
@RestController
public class TestApiServiceImpl extends BaseApiService implements TestApiService {

    @Override
    public Map<String, Object> test(Integer id, String name) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("resultCode", 200);
        hashMap.put("resultMsg", "success");
        hashMap.put("data",id.toString());
        return hashMap;
    }

    @Override
    public BaseResponse testBaseResponse(Integer id, String name) {
        return setResultSuccess();
    }

    @Override
    public BaseResponse testSetRedis() {
        baseRedisService.setString("111","123",null);
        return setResultSuccess();
    }
}
