package cn.myfreecloud.shop.api.service;

import cn.myfreecloud.shop.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/callBack")
public interface PayCallBackService {
    // 同步回调
    @RequestMapping("/synCallBackService")
    BaseResponse synCallBack(@RequestParam Map<String, String> params);

    // 异步回调
    @RequestMapping("/asynCallBackService")
    String asynCallBack(@RequestParam Map<String, String> params );
}
